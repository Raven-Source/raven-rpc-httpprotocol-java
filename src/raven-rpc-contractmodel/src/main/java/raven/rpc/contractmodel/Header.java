package raven.rpc.contractmodel;

import lombok.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@ToString
@EqualsAndHashCode
public class Header {

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private String rpcID;

    @Getter
    @Setter
    private String traceID;

    @Getter
    @Setter
    private String version;


    /**
     * 构造函数
     */
    public Header(){

    }

}
