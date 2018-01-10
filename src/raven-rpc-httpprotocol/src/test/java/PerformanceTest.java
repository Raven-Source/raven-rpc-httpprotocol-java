import org.junit.Before;
import org.junit.Test;
import raven.rpc.httpprototocol.HttpMethod;
import raven.rpc.httpprototocol.InvokeMessage;
import raven.rpc.httpprototocol.MediaType;
import raven.rpc.httpprototocol.RpcHttpClientImpl;
import raven.rpc.httpprototocol.async.AsyncInvokeMessage;
import raven.rpc.httpprototocol.async.RpcHttpClientAsyncImpl;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.*;

public class PerformanceTest {


    private final String domain = "http://192.168.2.80:9002";
    private final String DEFAULT_ENCODING = Charset.defaultCharset()
            .name();

    @Test
    public void async() throws Exception {

        System.out.println("async");

        RpcHttpClientAsyncImpl asynchttpClient;
        asynchttpClient = new RpcHttpClientAsyncImpl(domain, MediaType.json);
        int seed = 10000;

        AsyncInvokeMessage<User> invokeMessage
                = new AsyncInvokeMessage<User>()
                .url("/api/Values/Get")
                .httpMethod(HttpMethod.Get);

        asynchttpClient.invokeAsync(UserResponse.class, invokeMessage).get();

        ThrowingSupplier<CompletableFuture<?>>[] suppliers = new ThrowingSupplier[seed];
        CompletableFuture<?>[] completableFutures = new CompletableFuture[seed];

        for (int i = 0; i < seed; i++) {
            suppliers[i] = () -> asynchttpClient.invokeAsync(UserResponse.class, invokeMessage);
        }

        long startTime, endTime;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < seed; i++) {
            completableFutures[i] = suppliers[i].get();
        }

        CompletableFuture.allOf(completableFutures).join();
        endTime = System.currentTimeMillis();

        System.out.printf("seed: %d, valuesApi_json: GetAsync: %dms\r\n", seed, (endTime - startTime));
        System.out.printf("tps: %d\r\n", Math.round(seed / (double) (endTime - startTime) * 1000));
    }

    public void sync() throws Exception {

        System.out.println("sync");

        RpcHttpClientImpl httpClient;
        httpClient = new RpcHttpClientImpl(domain, MediaType.json);

        int seed = 10000;

        InvokeMessage<User> invokeMessage
                = new AsyncInvokeMessage<User>()
                .url("/api/Values/Get")
                .httpMethod(HttpMethod.Get);

        Supplier<UserResponse> callable = new Supplier<UserResponse>() {
            @Override
            public UserResponse get() {
                try {
                    return httpClient.invoke(UserResponse.class, invokeMessage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
        };

        CompletableFuture<?>[] completableFutures = new CompletableFuture[seed];

        long startTime, endTime;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < seed; i++) {
            completableFutures[i] = CompletableFuture.supplyAsync(callable);
        }

        CompletableFuture.allOf(completableFutures).join();
        endTime = System.currentTimeMillis();

        System.out.printf("seed: %d, valuesApi_json: GetAsync: %dms\r\n", seed, (endTime - startTime));
        System.out.printf("tps: %d\r\n", Math.round(seed / (double) (endTime - startTime) * 1000));
    }
}

@FunctionalInterface
interface ThrowingSupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;
}
