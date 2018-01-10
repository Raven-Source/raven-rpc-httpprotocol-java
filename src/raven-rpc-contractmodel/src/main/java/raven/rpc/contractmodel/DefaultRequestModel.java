package raven.rpc.contractmodel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
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
