package raven.rpc.contractmodel;

import lombok.*;

/**
 *
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
