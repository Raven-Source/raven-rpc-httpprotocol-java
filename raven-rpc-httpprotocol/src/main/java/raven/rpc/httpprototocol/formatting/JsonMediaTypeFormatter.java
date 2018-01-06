package raven.rpc.httpprototocol.formatting;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.*;
import org.apache.http.entity.ContentType;
import org.apache.http.util.Args;
import raven.serializer.DataSerializer;
import raven.serializer.withJackson.JacksonSerializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * 提供payload方式的formatter
 * json格式 {@see raven.rpc.httpprototocol.MediaType.json}
 */
public class JsonMediaTypeFormatter extends MediaTypeFormatter {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PROTECTED)
    private DataSerializer serializer;

    /**
     *
     */
    public JsonMediaTypeFormatter() {
        super();
        defaultContentType = ContentType.APPLICATION_JSON;
        getSupportedContentTypes().add(defaultContentType);
        getSupportedCharsets().add(defaultContentType.getCharset());
        serializer = new JacksonSerializer();
    }

    @Override
    public boolean canReadType(Class clazz) {
        if (clazz == (Class) null)
            Args.notNull(clazz, "Class clazz");
        return true;
    }

    @Override
    public byte[] writeToBytes(Object value) throws IOException {

        return serializer.serialize(value);
    }

    @Override
    public <T> T readFrom(Class<T> clazz, HttpEntity httpEntity) throws IOException {
        try {
            InputStream inputStream = httpEntity.getContent();
            return serializer.deserialize(clazz, inputStream);
        } catch (IOException ex) {
            throw ex;
        }

    }

}
