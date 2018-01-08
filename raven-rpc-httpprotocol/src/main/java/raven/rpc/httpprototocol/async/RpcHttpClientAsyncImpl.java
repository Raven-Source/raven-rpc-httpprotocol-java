package raven.rpc.httpprototocol.async;

import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import raven.rpc.httpprototocol.AbstractRpcHttpClient;
import raven.rpc.httpprototocol.HttpMethod;
import raven.rpc.httpprototocol.MediaType;
import raven.rpc.httpprototocol.exception.ExceptionOptimize;
import raven.rpc.httpprototocol.extension.CompletableFutures;
import raven.rpc.httpprototocol.extension.HttpEntitys;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/**
 * 异步 http 客户端
 */
public class RpcHttpClientAsyncImpl
        extends AbstractRpcHttpClient
        implements RpcHttpClientAsync {

    private CloseableHttpAsyncClient _asyncHttpClient;

    /**
     * @param host
     */
    public RpcHttpClientAsyncImpl(final String host) {
        this(host, MediaType.json);
    }

    /**
     * @param host
     * @param mediaType
     */
    public RpcHttpClientAsyncImpl(final String host, final String mediaType) {
        this(host, mediaType, DEFALUT_TIMEOUT);
    }

    /**
     * @param host
     * @param mediaType
     * @param timeout
     */
    public RpcHttpClientAsyncImpl(final String host, final String mediaType, final int timeout) {

        super(host, mediaType, timeout);

        _asyncHttpClient = initHttpClient();
        _asyncHttpClient.start();
    }

    /**
     * @param invokeMessage
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public CompletableFuture invokeAsync(final AsyncInvokeMessage invokeMessage)
            throws IOException, TimeoutException {
        return invokeAsync(Void.class, invokeMessage);
    }

    /**
     * @param resultClazz
     * @param invokeMessage
     * @param <TResult>
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public <TResult> CompletableFuture<TResult> invokeAsync(final Class<TResult> resultClazz, final AsyncInvokeMessage invokeMessage)
            throws IOException, TimeoutException {
        return invokeAsync(resultClazz, invokeMessage.getUrl(), invokeMessage.getContentData(), invokeMessage.getUrlParameters(), invokeMessage.getHttpMethod(), invokeMessage.getTimeout());
    }

    /**
     * @param resultClazz
     * @param url
     * @param urlParameters
     * @param httpMethod
     * @param timeout
     * @param <TResult>
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public <TResult> CompletableFuture<TResult> invokeAsync(final Class<TResult> resultClazz, final String url, final Map<String, String> urlParameters, final HttpMethod httpMethod, final int timeout)
            throws IOException, TimeoutException {
        return invokeAsync(resultClazz, url, null, urlParameters, httpMethod, timeout);
    }

    /**
     * @param resultClazz
     * @param url
     * @param contentData
     * @param urlParameters
     * @param httpMethod
     * @param timeout
     * @param <TData>
     * @param <TResult>
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public <TData, TResult> CompletableFuture<TResult> invokeAsync(final Class<TResult> resultClazz, final String url, final TData contentData, final Map<String, String> urlParameters, final HttpMethod httpMethod, final int timeout)
            throws IOException, TimeoutException {

        CloseableHttpAsyncClient client = _asyncHttpClient;

        HttpRequest httpRequest = createHttpRequest(url, contentData, urlParameters, httpMethod);

        final CompletableFuture<TResult> completableFuture = new CompletableFuture<>();

        //http异步执行
        client.execute(_httpHost, httpRequest, CompletableFutures.shiftToFutureCallback(completableFuture
                //处理httpResponse，并给到completableFuture
                , httpResponse -> {

                    StatusLine statusLine = httpResponse.getStatusLine();
                    if (statusLine != null) {
                        int statusCode = statusLine.getStatusCode();
                        if (statusCode < 200 || statusCode >= 300) {
                            throw ExceptionOptimize.createHttpException(statusLine);
                        }
                    }

                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity == null) {
                        return null;
                    }

                    if (resultClazz == Void.TYPE) {
                        return null;
                    } else {
                        return HttpEntitys.readAs(resultClazz, httpEntity);
                    }
                }
        ));

        //处理超时
        Duration duration = Duration.ofMillis(timeout > 0 ? timeout : _timeout);
        CompletableFuture<TResult> res = CompletableFutures.withTimeout(completableFuture, duration);

        return res;
    }

    /**
     * @return
     */
    private CloseableHttpAsyncClient initHttpClient() {

        HttpAsyncClientBuilder builder = HttpAsyncClientBuilder.create().setDefaultHeaders(_defaultHeaders);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(_timeout).setConnectionRequestTimeout(_timeout).build();
        builder.setDefaultRequestConfig(requestConfig);
//        DefaultConnectingIOReactor defaultConnectingIOReactor = new DefaultConnectingIOReactor();
//        PoolingNHttpClientConnectionManager manager = new PoolingNHttpClientConnectionManager(defaultConnectingIOReactor);
//        builder.setConnectionManager(manager);
        builder.setMaxConnTotal(200);
        builder.setMaxConnPerRoute(200);

        return builder.build();
    }


}
