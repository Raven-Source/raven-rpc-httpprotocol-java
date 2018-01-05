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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }


        Header kv = (Header) o;

        if (getToken() != null ? !getToken().equals(kv.getToken()) : kv.getToken() != null) {
            return false;
        }
        if (getRpcID() != null ? !getRpcID().equals(kv.getRpcID()) : kv.getRpcID() != null) {
            return false;
        }
        if (getTraceID() != null ? !getTraceID().equals(kv.getTraceID()) : kv.getTraceID() != null) {
            return false;
        }
        if (getVersion() != null ? !getVersion().equals(kv.getVersion()) : kv.getVersion() != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {

        final StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        buffer.append("token=").append(this.token);
        buffer.append(",rpcID=").append(this.rpcID);
        buffer.append(",traceID=").append(this.traceID);
        buffer.append(",version=").append(this.version);
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    public int hashCode() {

        int result = getToken() != null ? getToken().hashCode() : 0;
        result = 31 * result + (getRpcID() != null ? getRpcID().hashCode() : 0);
        result = 31 * result + (getTraceID() != null ? getTraceID().hashCode() : 0);
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        return result;
    }
}
