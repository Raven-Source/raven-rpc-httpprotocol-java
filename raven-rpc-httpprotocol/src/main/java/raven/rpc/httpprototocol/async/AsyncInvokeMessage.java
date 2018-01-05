package raven.rpc.httpprototocol.async;

import raven.rpc.httpprototocol.HttpMethod;
import raven.rpc.httpprototocol.InvokeMessage;

import java.util.Map;

public class AsyncInvokeMessage<TData> extends InvokeMessage<TData> {

    private int timeout;

    public int getTimeout() {
        return timeout;
    }

    public AsyncInvokeMessage<TData> timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public AsyncInvokeMessage<TData> url(String url) {
        return (AsyncInvokeMessage<TData>) super.url(url);
    }

    @Override
    public AsyncInvokeMessage<TData> contentData(TData contentData) {
        return (AsyncInvokeMessage<TData>) super.contentData(contentData);
    }

    @Override
    public AsyncInvokeMessage<TData> urlParameters(Map<String, String> urlParameters) {
        return (AsyncInvokeMessage<TData>) super.urlParameters(urlParameters);
    }

    @Override
    public AsyncInvokeMessage<TData> httpMethod(HttpMethod httpMethod) {
        return (AsyncInvokeMessage<TData>) super.httpMethod(httpMethod);
    }
}
