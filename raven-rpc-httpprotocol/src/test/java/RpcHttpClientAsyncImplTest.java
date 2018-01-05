import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import raven.rpc.httpprototocol.HttpMethod;
import raven.rpc.httpprototocol.InvokeMessage;
import raven.rpc.httpprototocol.MediaType;
import raven.rpc.httpprototocol.async.AsyncInvokeMessage;
import raven.rpc.httpprototocol.async.RpcHttpClientAsyncImpl;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.*;


public class RpcHttpClientAsyncImplTest {

    private final String domain = "http://127.0.0.1:9002";
    private final String DEFAULT_ENCODING = Charset.defaultCharset()
            .name();

    RpcHttpClientAsyncImpl httpClient;

    @Before
    public void before() {
        httpClient = new RpcHttpClientAsyncImpl(domain, MediaType.json);
    }

    @Test(expected = TimeoutException.class)
    public void postAsyncTimeout() throws Throwable {
        CompletableFuture<UserResponse> future = postAsyncTimeout(2000);
        future.thenAccept(res -> {
            if (res != null) {
                System.out.println("baseResponseModel.data.Name:" + res.getData().Name);
                System.out.println("baseResponseModel.data.ID:" + res.getData().ID);
            }

        });

        try {
            future.get();
        } catch (ExecutionException ex) {
            throw ex.getCause();
        }
    }

    @Test
    public void postAsyncNoTimeout() throws Exception {


        CompletableFuture<UserResponse> future = postAsyncTimeout(3500);
        future.thenAccept(res -> {
            if (res != null) {
                System.out.println("baseResponseModel.data.Name:" + res.getData().Name);
                System.out.println("baseResponseModel.data.ID:" + res.getData().ID);
            }

        });
        future.get();

    }


    private CompletableFuture<UserResponse> postAsyncTimeout(int timeout) throws Exception {

        String url = "/api/User/Update";

        UserResponse model = new UserResponse();
        User user = new User();
        user.Name = "好学生";
        user.ID = 123;

        httpClient = new RpcHttpClientAsyncImpl(domain, MediaType.json);
//        CompletableFuture<UserResponse> future = httpClient.invokeAsync(url, user, UserResponse.class);
        AsyncInvokeMessage<User> invokeMessage
                = new AsyncInvokeMessage<User>()
                .timeout(timeout)
                .url(url)
                .contentData(user);

        CompletableFuture<UserResponse> future = httpClient.invokeAsync(UserResponse.class, invokeMessage);
        return future;

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
    public void getAsync() throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        AsyncInvokeMessage<User> invokeMessage
                = new AsyncInvokeMessage<User>()
                .url("/api/Values/Get")
                .httpMethod(HttpMethod.Get);


        httpClient.invokeAsync(UserResponse.class, invokeMessage).thenAccept(baseResponseModel -> {
            System.out.println("baseResponseModel.data.Name:" + baseResponseModel.getData().Name);

            Assert.assertNotEquals(baseResponseModel.getData().Name, null);

            countDownLatch.countDown();
        });

        // Get2: res -> List<User>;
        invokeMessage = new AsyncInvokeMessage<User>()
                .url("/api/Values/Get2")
                .httpMethod(HttpMethod.Get);
        httpClient.invokeAsync(UserListResponse.class, invokeMessage).thenAccept(baseResponseModel -> {
            System.out.println("UserListResponse.size:" + baseResponseModel.size());

            Assert.assertThat(baseResponseModel.size(), Matchers.greaterThan(0));

            countDownLatch.countDown();
        });

        // Get3: res -> "hello";
        invokeMessage = new AsyncInvokeMessage<User>()
                .url("/api/Values/Get3")
                .httpMethod(HttpMethod.Get);
        httpClient.invokeAsync(String.class, invokeMessage).thenAccept(res -> {
            System.out.println(res);

            countDownLatch.countDown();
        });

        countDownLatch.await();
    }


    @Test
    public void getNoResultAsync() throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        AsyncInvokeMessage<User> invokeMessage
                = new AsyncInvokeMessage<User>()
                .url("/api/Values/Delete")
                .urlParameters(new HashMap<String, String>() {{
                    put("id", "123");
                }})
                .httpMethod(HttpMethod.Get);


        CompletableFuture future = httpClient.invokeAsync(Void.class, invokeMessage).thenAccept(baseResponseModel -> {

            Assert.assertNull(baseResponseModel);

            countDownLatch.countDown();
        }).exceptionally(throwable -> {

            countDownLatch.countDown();
            throwable.printStackTrace();
            return null;

        });

        countDownLatch.await();

        Assert.assertNull(future.get());
    }
}
