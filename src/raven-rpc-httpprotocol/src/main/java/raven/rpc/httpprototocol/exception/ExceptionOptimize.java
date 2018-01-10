package raven.rpc.httpprototocol.exception;

import org.apache.http.HttpException;
import org.apache.http.StatusLine;

import java.text.MessageFormat;

/**
 * @author yi.liang
 * @since JDK1.8
 */
public class ExceptionOptimize {
    public static HttpException createHttpException(StatusLine statusLine) {
        return new HttpException(statusLine.toString());
    }
}
