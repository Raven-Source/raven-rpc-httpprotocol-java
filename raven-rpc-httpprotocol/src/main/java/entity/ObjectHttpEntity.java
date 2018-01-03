package entity;

import formatting.MediaTypeFormatter;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.util.Args;
import raven.serializer.DataSerializer;
import raven.serializer.withJackson.JacksonSerializer;

import java.io.*;

public class ObjectHttpEntity<T> extends AbstractHttpEntity {

    private final T data;
    private final MediaTypeFormatter formatter;

    private final byte[] buffer;
    private final long length;


    private DataSerializer serializer = new JacksonSerializer();

    public T getData() {
        return data;
    }

    public MediaTypeFormatter getFormatter() {
        return formatter;
    }

    public ObjectHttpEntity(T data, MediaTypeFormatter formatter) throws IOException {
        this.data = data;
        this.formatter = formatter;
        this.buffer = formatter.writeToBytes(data);
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
