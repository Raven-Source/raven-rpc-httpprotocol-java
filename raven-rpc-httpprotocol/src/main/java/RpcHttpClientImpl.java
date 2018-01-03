import entity.ObjectHttpEntity;
import formatting.MediaTypeFormatter;
import formatting.JsonMediaTypeFormatter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.nio.entity.ContentBufferEntity;

import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class RpcHttpClientImpl implements RpcHttpClient {

    private String _mediaType;
    private MediaTypeFormatter _mediaTypeFormatter;

    public RpcHttpClientImpl(String mediaType) {

        _mediaType = mediaType;
        _mediaTypeFormatter = CreateMediaTypeFormatter(mediaType);

    }

    public CompletableFuture<HttpResponse> InvokeAsync(String url) throws Exception {

        final CompletableFuture<HttpResponse> completableFuture = new CompletableFuture<>();

        HttpGet httpGet = new HttpGet();
        httpGet.setURI(new URI(url));

        CloseableHttpAsyncClient asyncHttpClient = org.apache.http.impl.nio.client.HttpAsyncClients.createDefault();
        asyncHttpClient.start();
        Future<HttpResponse> future = asyncHttpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {

                completableFuture.complete(httpResponse);
            }

            @Override
            public void failed(Exception e) {

                completableFuture.completeExceptionally(e);
            }

            @Override
            public void cancelled() {

                completableFuture.completeExceptionally(new CancellationException());
            }
        });

        return completableFuture;

    }

    public <TData, TResult> CompletableFuture<TResult> InvokeAsync(String url, TData contentData, Class<TResult> resultClazz) throws Exception {

        final CompletableFuture<TResult> completableFuture = new CompletableFuture<>();

        HttpPost httpPost = new HttpPost();
        httpPost.setURI(new URI(url));

        HttpEntity httpEntity = new ObjectHttpEntity(contentData, _mediaTypeFormatter);
        httpPost.setEntity(httpEntity);
        httpPost.setHeader(_mediaTypeFormatter.getAcceptHeader());
        httpPost.setHeader(_mediaTypeFormatter.getContentTypeHeader());

        CloseableHttpAsyncClient asyncHttpClient = org.apache.http.impl.nio.client.HttpAsyncClients.createDefault();
        asyncHttpClient.start();
        Future<HttpResponse> future = asyncHttpClient.execute(httpPost, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {

                try {
                    TResult result = HttpResponseExtensions.readAs(resultClazz, httpResponse);
                    completableFuture.complete(result);
                }
                catch (Exception ex){
                    completableFuture.completeExceptionally(ex);
                }
            }

            @Override
            public void failed(Exception ex) {

                completableFuture.completeExceptionally(ex);
            }

            @Override
            public void cancelled() {

                completableFuture.completeExceptionally(new CancellationException());
            }
        });

        return completableFuture;

    }

    public HttpResponse Invoke(String url) throws Exception {
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(new URI(url));

        return org.apache.http.impl.client.HttpClients.createDefault().execute(httpGet);


    }


    private MediaTypeFormatter CreateMediaTypeFormatter(String mediaType) {
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
}
