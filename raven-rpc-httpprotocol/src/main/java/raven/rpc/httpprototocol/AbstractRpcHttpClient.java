package raven.rpc.httpprototocol;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import raven.rpc.httpprototocol.entity.ObjectHttpEntity;
import raven.rpc.httpprototocol.formatting.JsonMediaTypeFormatter;
import raven.rpc.httpprototocol.formatting.MediaTypeFormatter;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * AbstractRpcHttpClient
 */
public abstract class AbstractRpcHttpClient {

    protected static final int DEFALUT_TIMEOUT = 10000;

    protected int _timeout;
    //private String _mediaType;
    protected MediaTypeFormatter _mediaTypeFormatter;

    protected Collection<Header> _defaultHeaders;
    protected HttpHost _httpHost;

    /**
     * 获取默认RequestHeader集合
     *
     * @return Collection<Header>
     */
    public Collection<Header> getDefaultRequestHeaders() {
        return _defaultHeaders;
    }

    public AbstractRpcHttpClient(final String host, final String mediaType, final int timeout) {

        _timeout = timeout > 0 ? timeout : DEFALUT_TIMEOUT;

        //_mediaType = mediaType;
        _mediaTypeFormatter = createMediaTypeFormatter(mediaType);

        _defaultHeaders = new ArrayList<>();
        _defaultHeaders.add(_mediaTypeFormatter.getAcceptHeader());
        _defaultHeaders.add(_mediaTypeFormatter.getContentTypeHeader());
        _httpHost = HttpHost.create(host);

    }

    /**
     * 创建 HttpRequest
     *
     * @param url
     * @param contentData
     * @param urlParameters
     * @param httpMethod
     * @param <TData>
     * @return
     * @throws IOException
     */
    protected <TData> HttpRequest createHttpRequest(final String url, final TData contentData, final Map<String, String> urlParameters, final HttpMethod httpMethod)
            throws IOException {

        //默认post
        HttpMethod method = httpMethod != null ? httpMethod : HttpMethod.Post;

        //处理url参数
        String requestUrl = createUrlParams(urlParameters, url);
        //创建httpRequest
        BasicHttpEntityEnclosingRequest httpRequest = new BasicHttpEntityEnclosingRequest(method.toString(), requestUrl);

        if (contentData != null &&
                (method.equals(HttpMethod.Post) || method.equals(HttpMethod.Put))
                ) {
            HttpEntity httpEntity = new ObjectHttpEntity(contentData, _mediaTypeFormatter);
            httpRequest.setEntity(httpEntity);
        }

        return httpRequest;

    }

    /**
     *
     * @param mediaType
     * @return
     */
    protected MediaTypeFormatter createMediaTypeFormatter(String mediaType) {
        MediaTypeFormatter mediaTypeFormatter = null;
        switch (mediaType) {
            //#if RavenRpcHttpProtocol40
            //#else
            //                case MediaType.bson:
            //                    mediaTypeFormatter = new BsonMediaTypeFormatter();
            //                    break;
            //                case MediaType.msgpack:
            //                    mediaTypeFormatter = new MsgPackTypeFormatter();
            //                    break;
            //#endif
//            case MediaType.form:
//                mediaTypeFormatter = new FormUrlEncodedMediaTypeFormatter();
//                break;
//            case MediaType.xml:
//                mediaTypeFormatter = new XmlMediaTypeFormatter();
//                break;
            case MediaType.json:
            default:
                mediaTypeFormatter = new JsonMediaTypeFormatter();
                break;
        }

        return mediaTypeFormatter;
    }

    /**
     * url参数
     *
     * @param urlParameters
     * @param baseUrl
     * @return
     */
    protected String createUrlParams(Map<String, String> urlParameters, String baseUrl) {

        if (urlParameters == null || urlParameters.size() == 0) {
            return baseUrl;
        }

        StringBuilder buffer = null;

        buffer = new StringBuilder();
        int i = 0;

        for (String key : urlParameters.keySet()) {
            if (i == 0) {
                buffer.append(MessageFormat.format("{0}={1}", key, urlParameters.get(key)));
                i++;
            } else {
                buffer.append(MessageFormat.format("&{0}={1}", key, urlParameters.get(key)));
            }
        }

        if (buffer != null && buffer.length() > 0) {
            int index = baseUrl.indexOf("?");
            if (index >= 0) {
                if (index < baseUrl.length() - 1) {
                    baseUrl += "&" + buffer.toString();
                } else {
                    baseUrl += buffer.toString();
                }
            } else {
                baseUrl += "?" + buffer.toString();
            }
        }

        return baseUrl;
    }
}
