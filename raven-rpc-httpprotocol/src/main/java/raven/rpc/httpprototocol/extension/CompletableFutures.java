package raven.rpc.httpprototocol.extension;

import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.Args;
import raven.rpc.httpprototocol.function.ThrowingFunction;

import java.time.Duration;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * CompletableFuture Extensions
 */
public class CompletableFutures {


    /**
     * 异步任务执行超时时间
     *
     * @param future
     * @param duration
     * @param <T>
     * @return
     * @throws TimeoutException
     */
    public static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> future, Duration duration) throws TimeoutException {

        if (future.isDone() || future.isCancelled() || future.isCompletedExceptionally() || duration == Duration.ZERO) {
            return future;
        }

        final CompletableFuture<T> timeout = timeoutAfter(duration);

        return future.applyToEither(timeout, f -> {
            timeout.cancel(true);
            return f;
        });
    }

    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(
                    1);

    /**
     * 在一段时间后超时
     *
     * @param duration
     * @return
     */
    private static <T> CompletableFuture<T> timeoutAfter(final Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture();
        scheduler.schedule(() -> {
            if (!promise.isCancelled()) {

                final TimeoutException ex = new TimeoutException("Timeout after " + duration);
                promise.completeExceptionally(ex);
            }

        }, duration.toMillis(), MILLISECONDS);
        return promise;
    }

    /**
     * 创建 FutureCallback {@link FutureCallback}, 并处理完成结果，进过 function 处理后，用结果修改 completableFuture 状态
     *
     * @param completableFuture
     * @param function
     * @param <T>               FutureCallback 泛型
     * @param <R>               CompletableFuture 泛型
     * @return
     */
    public static <T, R> FutureCallback<T> shiftToFutureCallback(final CompletableFuture<R> completableFuture, final ThrowingFunction<T, R> function) {

        Args.notNull(function, "function");
        return new FutureCallback<T>() {
            @Override
            public void completed(T result) {

                try {
                    R value = function.apply(result);
                    completableFuture.complete(value);
                } catch (Exception ex) {
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
        };
    }

}
