package raven.rpc.contractmodel;


/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface CodeResponseModel<TCode> extends SimpleResponseModel {

    TCode getCode();
    void setCode(TCode code);
}