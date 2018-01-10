package raven.rpc.httpprototocol;

import org.apache.http.HttpException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author yi.liang
 * @since JDK1.8
 */
public interface RpcHttpClient {
    /**
     * @param invokeMessage
     * @return
     * @throws Exception
     */
    void invoke(final InvokeMessage invokeMessage)
            throws IOException, TimeoutException, HttpException;

    /**
     * @param resultClazz
     * @param invokeMessage
     * @param <TResult>
     * @return
     * @throws Exception
     */
    <TResult> TResult invoke(final Class<TResult> resultClazz, final InvokeMessage invokeMessage)
            throws IOException, TimeoutException, HttpException;

    /**
     * @param resultClazz
     * @param url
     * @param urlParameters
     * @param httpMethod
     * @param <TResult>
     * @return
     * @throws Exception
     */
    <TResult> TResult invoke(final Class<TResult> resultClazz, final String url, final Map<String, String> urlParameters, final HttpMethod httpMethod)
            throws IOException, TimeoutException, HttpException;

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
     */
    <TData, TResult> TResult invoke(final Class<TResult> resultClazz, final String url, final TData contentData, final Map<String, String> urlParameters, final HttpMethod httpMethod)
            throws IOException, TimeoutException, HttpException;

}
