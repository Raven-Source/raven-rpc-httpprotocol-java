package raven.rpc.contractmodel;


public interface CodeResponseModel<TCode> extends SimpleResponseModel {

    TCode getCode();
    void setCode(TCode code);
}