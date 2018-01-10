package raven.rpc.httpprototocol.function;

@FunctionalInterface
public interface Action2<T1, T2> {
    void invoke(final T1 t1, final T2 t2);
}
