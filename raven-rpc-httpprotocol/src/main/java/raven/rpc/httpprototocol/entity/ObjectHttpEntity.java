package raven.rpc.httpprototocol.entity;

import raven.rpc.httpprototocol.formatting.MediaTypeFormatter;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.util.Args;
import raven.serializer.DataSerializer;
import raven.serializer.withJackson.JacksonSerializer;

import java.io.*;

/**
 * POJO对象HttpEntity，用于payload方式
 *
 * @param <T>
 */
public class ObjectHttpEntity<T> extends AbstractHttpEntity {

    private final T value;
    private final MediaTypeFormatter formatter;

    private final byte[] buffer;
    private final long length;

    private DataSerializer serializer = new JacksonSerializer();

    /**
     * 获取value
     *
     * @return
     */
    public T getValue() {
        return value;
    }

    /**
     * 获取formatter
     *
     * @return
     */
    public MediaTypeFormatter getFormatter() {
        return formatter;
    }

    /**
     * @param value     POJO对象
     * @param formatter {@see MediaTypeFormatter}
     * @throws IOException
     */
    public ObjectHttpEntity(T value, MediaTypeFormatter formatter) throws IOException {
        this.value = value;
        this.formatter = formatter;
        this.buffer = formatter.writeToBytes(value);
        this.length = buffer.length;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public long getContentLength() {
        return length;
    }

    @Override
    public InputStream getContent() throws IOException, UnsupportedOperationException {
        return new ByteArrayInputStream(buffer);
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {

        Args.notNull(outstream, "Output stream");
        System.out.printf("ObjectHttpEntity$writeTo");
        outstream.write(this.buffer);
        outstream.flush();
    }

    @Override
    public boolean isStreaming() {
        return false;
    }
}
