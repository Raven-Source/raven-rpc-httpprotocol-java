package raven.rpc.contractmodel;

/**
 *
 * @param <THeader>
 */
public interface RequestModel<THeader> {
    THeader getHeader();
    void setHeader(THeader header);
}
