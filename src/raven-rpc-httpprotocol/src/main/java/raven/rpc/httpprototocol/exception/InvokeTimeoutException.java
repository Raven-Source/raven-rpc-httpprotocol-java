package raven.rpc.httpprototocol.exception;

/**
 * 远程调用超时异常
 *
 * @author yi.liang
 * @since JDK1.8
 */
public class InvokeTimeoutException extends Exception {
    public static final String ExceptionMessage = "远程调用超时";

    public InvokeTimeoutException() {
        super(ExceptionMessage);
    }

    public InvokeTimeoutException(String message) {
        super(message);
    }

}
