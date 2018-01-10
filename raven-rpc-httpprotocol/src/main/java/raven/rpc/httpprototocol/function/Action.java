package raven.rpc.httpprototocol.function;

@FunctionalInterface
public interface Action<T> {

    void invoke(final T t);
}