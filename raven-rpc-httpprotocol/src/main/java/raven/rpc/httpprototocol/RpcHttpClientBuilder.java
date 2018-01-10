package raven.rpc.httpprototocol;

import lombok.Getter;

public class RpcHttpClientBuilder {

    @Getter
    private String host;

    @Getter
    private String mediaType;

    @Getter
    private int timeout;

    public RpcHttpClientBuilder host(final String host) {
        this.host = host;
        return this;
    }

    public RpcHttpClientBuilder mediaType(final String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public RpcHttpClientBuilder timeout(final int timeout) {
        this.timeout = timeout;
        return this;
    }

    public RpcHttpClient build() {
        return new RpcHttpClientImpl(this.host, this.mediaType, this.timeout);
    }
}
