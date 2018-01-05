package raven.rpc.httpprototocol;

import java.util.Map;

public class InvokeMessage<TData> {
    private String url;
    private TData contentData;
    private Map<String, String> urlParameters;
    private HttpMethod httpMethod;

    public String getUrl() {
        return url;
    }

    public TData getContentData() {
        return contentData;
    }

    public Map<String, String> getUrlParameters() {
        return urlParameters;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public InvokeMessage<TData> url(String url) {
        this.url = url;
        return this;
    }

    public InvokeMessage<TData> contentData(TData contentData) {
        this.contentData = contentData;
        return this;
    }

    public InvokeMessage<TData> urlParameters(Map<String, String> urlParameters) {
        this.urlParameters = urlParameters;
        return this;
    }

    public InvokeMessage<TData> httpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

}
