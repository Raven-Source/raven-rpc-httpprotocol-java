package raven.rpc.contractmodel;

public interface FullResponseModel<TData, TCode> extends CodeResponseModel<TCode> {
    TData getData();
    void setData(TData data);
}
