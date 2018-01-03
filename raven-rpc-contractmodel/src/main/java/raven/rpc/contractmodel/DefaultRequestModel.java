package raven.rpc.contractmodel;

/**
 *
 */
public class DefaultRequestModel implements RequestModel<Header> {

    private Header header;

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public void setHeader(Header header) {
        this.header = header;
    }

    /**
     *
     */
    public DefaultRequestModel()
    {
        header = new Header();
    }

}
