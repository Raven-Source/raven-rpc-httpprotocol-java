import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import raven.rpc.httpprototocol.*;
import raven.rpc.httpprototocol.async.AsyncInvokeMessage;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class RpcHttpClientImplTest {

    private final String domain = "http://127.0.0.1:9002";
    private final String DEFAULT_ENCODING = Charset.defaultCharset()
            .name();

    RpcHttpClient httpClient;

    @Before
    public void before() {

        //httpClient = new RpcHttpClientBuilder().host(domain).mediaType(MediaType.json).build();
        //httpClient = new RpcHttpClientAsyncImpl(domain, MediaType.json);
    }

    @Test(expected = java.net.SocketTimeoutException.class)
    public void postTimeout() throws Throwable {

        try {
            UserResponse res = post(2000);
            if (res != null) {
                System.out.println("baseResponseModel.data.Name:" + res.getData().Name);
                System.out.println("baseResponseModel.data.ID:" + res.getData().ID);
            }
        }
        catch (Throwable ex){
            throw ex;
        }

    }

    @Test
    public void postNoTimeout() throws Throwable {

        UserResponse res = post(3500);
        if (res != null) {
            System.out.println("baseResponseModel.data.Name:" + res.getData().Name);
            System.out.println("baseResponseModel.data.ID:" + res.getData().ID);
        }

    }


    private UserResponse post(int timeout) throws Throwable {

        httpClient = new RpcHttpClientBuilder().host(domain).mediaType(MediaType.json).timeout(timeout).build();
        String url = "/api/User/Update";

        UserResponse model = new UserResponse();
        User user = new User();
        user.Name = "好学生";
        user.ID = 123;

//        CompletableFuture<UserResponse> future = httpClient.invokeAsync(url, user, UserResponse.class);
        InvokeMessage<User> invokeMessage
                = new InvokeMessage<User>()
                //.timeout(timeout)
                .url(url)
                .contentData(user);

        UserResponse res = httpClient.invoke(UserResponse.class, invokeMessage);
        return res;

        /*future.thenAccept(res -> {
            if (res != null) {
                System.out.println("baseResponseModel.data.Name:" + res.getData().Name);
                System.out.println("baseResponseModel.data.ID:" + res.getData().ID);
            }
            countDownLatch.countDown();
        }).exceptionally(ex -> {
            ex.printStackTrace();
            countDownLatch.countDown();
            return null;
        });*/

    }

    @Test
    public void get() throws Exception {

        httpClient = new RpcHttpClientBuilder().host(domain).mediaType(MediaType.json).build();
        InvokeMessage<User> invokeMessage
                = new InvokeMessage<User>()
                .url("/api/Values/Get")
                .httpMethod(HttpMethod.Get);


        UserResponse baseResponseModel = httpClient.invoke(UserResponse.class, invokeMessage);
        System.out.println("baseResponseModel.data.Name:" + baseResponseModel.getData().Name);
        Assert.assertNotEquals(baseResponseModel.getData().Name, null);

        // Get2: res -> List<User>;
        invokeMessage = new InvokeMessage<User>()
                .url("/api/Values/Get2")
                .httpMethod(HttpMethod.Get);

        UserListResponse userListResponse = httpClient.invoke(UserListResponse.class, invokeMessage);

        System.out.println("UserListResponse.size:" + userListResponse.size());
        Assert.assertThat(userListResponse.size(), Matchers.greaterThan(0));

        // Get3: res -> "hello";
        invokeMessage = new InvokeMessage<User>()
                .url("/api/Values/Get3")
                .httpMethod(HttpMethod.Get);

        String res = httpClient.invoke(String.class, invokeMessage);
        System.out.println(res);

    }


    @Test
    public void getNoResult() throws Exception {

        httpClient = new RpcHttpClientBuilder().host(domain).mediaType(MediaType.json).build();
        InvokeMessage<User> invokeMessage
                = new InvokeMessage<User>()
                .url("/api/Values/Delete")
                .urlParameters(new HashMap<String, String>() {{
                    put("id", "123");
                }})
                .httpMethod(HttpMethod.Get);


        Void res = httpClient.invoke(Void.class, invokeMessage);

        Assert.assertNull(res);
    }
}
