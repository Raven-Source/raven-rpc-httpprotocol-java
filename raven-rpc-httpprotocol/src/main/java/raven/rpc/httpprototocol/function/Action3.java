package raven.rpc.httpprototocol.function;

@FunctionalInterface
public interface Action3<T1, T2, T3> {
    void invoke(final T1 t1, final T2 t2, final T3 t3);
}
