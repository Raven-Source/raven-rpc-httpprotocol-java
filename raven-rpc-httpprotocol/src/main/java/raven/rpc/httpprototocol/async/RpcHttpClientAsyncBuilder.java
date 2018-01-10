package raven.rpc.httpprototocol.async;

import lombok.Getter;

public class RpcHttpClientAsyncBuilder {

    @Getter
    private String host;

    @Getter
    private String mediaType;

    @Getter
    private int timeout;

    public RpcHttpClientAsyncBuilder host(final String host) {
        this.host = host;
        return this;
    }

    public RpcHttpClientAsyncBuilder mediaType(final String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public RpcHttpClientAsyncBuilder timeout(final int timeout) {
        this.timeout = timeout;
        return this;
    }

    public RpcHttpClientAsync build() {
        return new RpcHttpClientAsyncImpl(this.host, this.mediaType, this.timeout);
    }
}
