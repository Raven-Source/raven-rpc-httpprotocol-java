package raven.rpc.contractmodel;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
public class DefaultRequestModel implements RequestModel<Header> {

    @Getter
    @Setter
    private Header header;

    /**
     *
     */
    public DefaultRequestModel()
    {
        header = new Header();
    }

}
