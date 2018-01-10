package raven.rpc.httpprototocol.function;

/**
 * @param <T>
 * @param <R>
 * @author yi.liang
 * @since JDK1.8
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> {

    R apply(T t) throws Exception;
}
