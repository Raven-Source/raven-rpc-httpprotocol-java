package raven.rpc.httpprototocol.formatting;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 */
public abstract class MediaTypeFormatter {

    @Getter(AccessLevel.PUBLIC)
    private final List<Charset> supportedCharsets;

    @Getter(AccessLevel.PUBLIC)
    private final List<ContentType> supportedContentTypes;

    protected ContentType defaultContentType;

    protected MediaTypeFormatter() {
        supportedCharsets = new ArrayList<>();
        supportedContentTypes = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param mediaTypeFormatter
     */
    protected MediaTypeFormatter(MediaTypeFormatter mediaTypeFormatter) {
        supportedCharsets = mediaTypeFormatter.supportedCharsets;
        supportedContentTypes = mediaTypeFormatter.supportedContentTypes;
    }
/*
    protected List<Charset> getSupportedCharsets() {
        return supportedCharsets;
    }

    protected List<ContentType> getSupportedContentTypes() {
        return supportedContentTypes;
    }*/


    public Header getAcceptHeader() {
        return new BasicHeader(HttpHeaders.ACCEPT, defaultContentType.toString());
    }

    public Header getContentTypeHeader() {
        return new BasicHeader(HttpHeaders.CONTENT_TYPE, defaultContentType.toString());
    }

    public abstract boolean canReadType(Class clazz);

    public abstract byte[] writeToBytes(Object value) throws IOException;

    public abstract <T> T readFrom(Class<T> clazz, HttpEntity httpEntity) throws IOException;

}
