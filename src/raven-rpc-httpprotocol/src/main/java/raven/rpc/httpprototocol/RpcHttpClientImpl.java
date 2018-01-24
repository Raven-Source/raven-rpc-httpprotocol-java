package raven.rpc.httpprototocol;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import raven.rpc.httpprototocol.exception.ExceptionOptimize;
import raven.rpc.httpprototocol.extension.HttpEntitys;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author yi.liang
 * @since JDK1.8
 */
public class RpcHttpClientImpl
        extends AbstractRpcHttpClient
        implements RpcHttpClient {


    private CloseableHttpClient _httpClient;

    /**
     * @param host
     */
    public RpcHttpClientImpl(final String host) {
        this(host, MediaType.json);
    }

    /**
     * @param host
     * @param mediaType
     */
    public RpcHttpClientImpl(final String host, final String mediaType) {
        this(host, mediaType, DEFALUT_TIMEOUT);
    }

    /**
     * @param host
     * @param mediaType
     * @param timeout
     */
    public RpcHttpClientImpl(final String host, final String mediaType, final int timeout) {

        super(host, mediaType, timeout);

        _httpClient = initHttpClient();
    }

    /**
     * @param invokeMessage
     * @throws IOException
     * @throws TimeoutException
     * @throws HttpException
     */
    @Override
    public void invoke(final InvokeMessage invokeMessage)
            throws IOException, TimeoutException, HttpException {
        invoke(Void.class, invokeMessage);
    }

    /**
     * @param resultClazz
     * @param invokeMessage
     * @param <TResult>
     * @return
     * @throws IOException
     * @throws TimeoutException
     * @throws HttpException
     */
    @Override
    public <TResult> TResult invoke(final Class<TResult> resultClazz, final InvokeMessage invokeMessage)
            throws IOException, TimeoutException, HttpException {
        return (TResult) invoke(resultClazz, invokeMessage.getUrl(), invokeMessage.getContentData(), invokeMessage.getUrlParameters(), invokeMessage.getHttpMethod());
    }

    /**
     * @param resultClazz
     * @param url
     * @param urlParameters
     * @param httpMethod
     * @param <TResult>
     * @return
     * @throws IOException
     * @throws TimeoutException
     * @throws HttpException
     */
    @Override
    public <TResult> TResult invoke(final Class<TResult> resultClazz, final String url, final Map<String, String> urlParameters, final HttpMethod httpMethod)
            throws IOException, TimeoutException, HttpException {
        return invoke(resultClazz, url, null, urlParameters, httpMethod);
    }

    /**
     * @param resultClazz
     * @param url
     * @param contentData
     * @param urlParameters
     * @param httpMethod
     * @param <TData>
     * @param <TResult>
     * @return
     * @throws IOException
     * @throws TimeoutException
     * @throws HttpException
     */
    @Override
    public <TData, TResult> TResult invoke(final Class<TResult> resultClazz, final String url, final TData contentData, final Map<String, String> urlParameters, final HttpMethod httpMethod)
            throws IOException, TimeoutException, HttpException {

        CloseableHttpClient client = _httpClient;

        HttpRequest httpRequest = createHttpRequest(url, contentData, urlParameters, httpMethod);
        CloseableHttpResponse httpResponse = null;

        RpcContext rpcContext = new RpcContext();
        rpcContext.setRequestModel(contentData);

        //onRequestEvents
        onRequestEvents(httpRequest, rpcContext);

        try {
            httpResponse = client.execute(_httpHost, httpRequest);

            //onResponseEvents
            onResponseEvents(httpResponse, rpcContext);

            //处理状态
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine != null) {
                int statusCode = statusLine.getStatusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    throw ExceptionOptimize.createHttpException(statusLine);
                }
            }

            //获取HttpEntity
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity == null) {
                return null;
            }

            if (resultClazz == Void.TYPE) {
                return null;
            } else {
                // as TResult
                return HttpEntitys.readAs(resultClazz, httpEntity);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (httpResponse != null) httpResponse.close();
        }
    }

    /**
     * @return
     */
    private CloseableHttpClient initHttpClient() {

        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultHeaders(_defaultHeaders);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(_timeout)
                .setConnectionRequestTimeout(_timeout)
                .setSocketTimeout(_timeout)
                .build();

        builder.setDefaultRequestConfig(requestConfig);
        builder.setMaxConnTotal(200);
        builder.setMaxConnPerRoute(200);

        return builder.build();
    }


    @Override
    protected void finalize() throws java.lang.Throwable {

        super.finalize();
        if (_httpClient != null) {
            _httpClient.close();
        }

    }
}
