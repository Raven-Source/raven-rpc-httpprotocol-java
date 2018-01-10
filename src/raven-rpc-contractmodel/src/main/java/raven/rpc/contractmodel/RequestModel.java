package raven.rpc.contractmodel;

/**
 * @param <THeader>
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface RequestModel<THeader> {
    THeader getHeader();

    void setHeader(THeader header);
}
