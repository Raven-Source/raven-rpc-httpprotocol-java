package raven.rpc.httpprototocol.async;

import raven.rpc.httpprototocol.HttpMethod;
import raven.rpc.httpprototocol.InvokeMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

/**
 * @author yi.liang
 * @since JDK1.8
 */
public interface RpcHttpClientAsync {


    /**
     * @param invokeMessage
     * @return
     * @throws Exception
     */
    CompletableFuture invokeAsync(final AsyncInvokeMessage invokeMessage)
            throws IOException, TimeoutException;

    /**
     * @param resultClazz
     * @param invokeMessage
     * @param <TResult>
     * @return
     * @throws Exception
     */
    <TResult> CompletableFuture<TResult> invokeAsync(final Class<TResult> resultClazz, final AsyncInvokeMessage invokeMessage)
            throws IOException, TimeoutException;

    /**
     * @param resultClazz
     * @param url
     * @param urlParameters
     * @param httpMethod
     * @param timeout
     * @param <TResult>
     * @return
     * @throws Exception
     */
    <TResult> CompletableFuture<TResult> invokeAsync(final Class<TResult> resultClazz, final String url, final Map<String, String> urlParameters, final HttpMethod httpMethod, final int timeout)
            throws IOException, TimeoutException;

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
    <TData, TResult> CompletableFuture<TResult> invokeAsync(final Class<TResult> resultClazz, final String url, final TData contentData, final Map<String, String> urlParameters, final HttpMethod httpMethod, final int timeout)
            throws IOException, TimeoutException;

}
