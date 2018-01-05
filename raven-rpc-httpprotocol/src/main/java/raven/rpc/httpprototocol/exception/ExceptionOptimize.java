package raven.rpc.httpprototocol.exception;

import org.apache.http.HttpException;
import org.apache.http.StatusLine;

import java.text.MessageFormat;

public class ExceptionOptimize {
    public static HttpException createHttpException(StatusLine statusLine) {
        return new HttpException(statusLine.toString());
    }
}
