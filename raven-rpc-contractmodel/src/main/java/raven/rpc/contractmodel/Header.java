package raven.rpc.contractmodel;

/**
 *
 */
public class Header {

    private String token;
    private String rpcID;
    private String traceID;
    private String version;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRpcID() {
        return rpcID;
    }

    public void setRpcID(String rpcID) {
        this.rpcID = rpcID;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
