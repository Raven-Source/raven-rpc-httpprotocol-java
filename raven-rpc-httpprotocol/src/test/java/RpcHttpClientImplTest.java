import org.apache.http.*;
import org.apache.http.client.entity.*;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import raven.rpc.contractmodel.DefaultRequestModel;
import raven.rpc.contractmodel.DefaultResponseModel;

import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RpcHttpClientImplTest {

    private final String domain = "http://127.0.0.1:9002/";
    private final String DEFAULT_ENCODING = Charset.defaultCharset()
            .name();

    @Test
    public void InvokeAsync() throws Exception {

        String url = domain + "api/Values/Get";
        RpcHttpClientImpl httpClient = new RpcHttpClientImpl(MediaType.json);
        CompletableFuture<HttpResponse> future = httpClient.InvokeAsync(url);

        CompletableFuture res = future.thenAccept(new Consumer<HttpResponse>() {
            @Override
            public void accept(HttpResponse httpResponse) {
                String returnValue = null;
                System.out.println(httpResponse.getEntity().getContentLength());
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity he = httpResponse.getEntity();
                    try {
                        returnValue = new String(EntityUtils.toByteArray(he), DEFAULT_ENCODING);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        res.get();
    }

    @Test
    public void InvokeAsync2() throws Exception {

        String url = domain + "api/User/Update";

        UserResponse model = new UserResponse();
        User user = new User();
        user.Name = "好学生";
        user.ID = 123;

        RpcHttpClientImpl httpClient = new RpcHttpClientImpl(MediaType.json);
        CompletableFuture<UserResponse> future = httpClient.InvokeAsync(url, user, UserResponse.class);

        CompletableFuture res = future.thenAccept(baseResponseModel -> {
            System.out.println("baseResponseModel.data.Name:" + baseResponseModel.getData().Name);
            System.out.println("baseResponseModel.data.ID:" + baseResponseModel.getData().ID);
        });

        /*CompletableFuture res = future.thenAccept(new Consumer<HttpResponse>() {
            @Override
            public void accept(HttpResponse httpResponse) {
                String returnValue = null;
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                System.out.println("StatusCode:" + statusCode);
                System.out.println("ContentLength:" + httpResponse.getEntity().getContentLength());

                if (statusCode == 200) {
                    HttpEntity he = httpResponse.getEntity();

                    try {
                        returnValue = new String(EntityUtils.toByteArray(he), DEFAULT_ENCODING);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });*/

        res.get();
    }

    @Test
    public void Invoke() throws Exception {

        String url = domain + "api/Values/Get";
        RpcHttpClientImpl httpClient = new RpcHttpClientImpl(MediaType.json);
        HttpResponse res = httpClient.Invoke(url);

        System.out.println(res.getEntity().getContentLength());

    }

}
