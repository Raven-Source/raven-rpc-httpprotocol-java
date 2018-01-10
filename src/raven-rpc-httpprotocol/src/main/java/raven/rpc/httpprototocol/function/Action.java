package raven.rpc.httpprototocol.function;

/**
 * @param <T>
 * @author yi.liang
 * @since JDK1.8
 */
@FunctionalInterface
public interface Action<T> {

    void invoke(final T t);
}