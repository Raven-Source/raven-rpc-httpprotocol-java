package raven.rpc.httpprototocol;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yi.liang
 * @since JDK1.8
 */
public class RpcContext {

    @Getter
    @Setter
    private Date sendStartTime;

    @Getter
    @Setter
    private Date receiveEndTime;

    @Getter
    @Setter
    private Date exceptionTime;

    @Getter
    @Setter
    private Object requestModel;

    @Getter
    @Setter
    private Object responseModel;

    @Getter
    @Setter
    private long responseSize;

    @Getter
    @Setter
    private boolean exceptionHandled;

    @Getter
    @Setter
    private Map<String, Object> items;

    public RpcContext(){

        items = new HashMap<>();
        exceptionHandled = false;
    }
}
