package raven.rpc.httpprototocol.function;

/**
 * @param <T1>
 * @param <T2>
 * @author yi.liang
 * @since JDK1.8
 */
@FunctionalInterface
public interface Action2<T1, T2> {
    void invoke(final T1 t1, final T2 t2);
}
