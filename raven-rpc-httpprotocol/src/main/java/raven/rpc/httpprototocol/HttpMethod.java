package raven.rpc.httpprototocol;

import org.apache.http.util.Args;

/**
 * HttpMethod: get,post,put,delete....
 */
public class HttpMethod {

    public static final HttpMethod Get = new HttpMethod("GET");
    public static final HttpMethod Put = new HttpMethod("PUT");
    public static final HttpMethod Post = new HttpMethod("POST");
    public static final HttpMethod Delete = new HttpMethod("DELETE");
    public static final HttpMethod Head = new HttpMethod("HEAD");
    public static final HttpMethod Options = new HttpMethod("OPTIONS");
    public static final HttpMethod Trace = new HttpMethod("TRACE");
    public String method;


    public HttpMethod(String method) {

        Args.notNull(method, "method");
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    @Override
    public int hashCode() {
        return this.method.toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(HttpMethod.class)) {
            return this.hashCode() == ((HttpMethod) obj).hashCode();
        }
        return false;
    }

}
