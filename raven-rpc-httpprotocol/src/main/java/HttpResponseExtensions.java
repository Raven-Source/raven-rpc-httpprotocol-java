import entity.ObjectHttpEntity;
import formatting.MediaTypeFormatter;
import formatting.MediaTypeFormatterCollection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.Args;

import java.io.IOException;
import java.util.List;

public class HttpResponseExtensions {

    private static MediaTypeFormatterCollection _defaultMediaTypeFormatterCollection;

    private static MediaTypeFormatterCollection getDefaultMediaTypeFormatterCollection() {
        if (HttpResponseExtensions._defaultMediaTypeFormatterCollection == null)
            HttpResponseExtensions._defaultMediaTypeFormatterCollection = new MediaTypeFormatterCollection();
        return HttpResponseExtensions._defaultMediaTypeFormatterCollection;
    }

    public static <T> T readAs(Class<T> clazz, HttpResponse httpResponse) throws IOException {
        return readAs(clazz, httpResponse, HttpResponseExtensions.getDefaultMediaTypeFormatterCollection());
    }

    public static <T> T readAs(Class<T> clazz, HttpResponse httpResponse, List<MediaTypeFormatter> formatters) throws IOException {
        if (httpResponse == null)
            Args.notNull(httpResponse, "httpResponse");
        if (clazz == null)
            Args.notNull(clazz, "clazz");
        if (formatters == null)
            Args.notNull(formatters, "formatters");

        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity.getClass().isAssignableFrom(ObjectHttpEntity.class)) {
            ObjectHttpEntity objectHttpEntity = (ObjectHttpEntity) httpEntity;
            if (objectHttpEntity != null && objectHttpEntity.getData() != null && clazz.isAssignableFrom(objectHttpEntity.getData().getClass()))
                return (T) objectHttpEntity.getData();
        }

        String mediaType = httpResponse.getEntity().getContentType().getValue().trim().toLowerCase();
//        if(mediaType == null){
//
//        }
//        MediaTypeConstants.ApplicationOctetStreamMediaType;
        MediaTypeFormatter reader = new MediaTypeFormatterCollection(formatters).FindReader(mediaType);
        if (reader != null)
            return HttpResponseExtensions.readAsCore(clazz, httpEntity, reader);

        return null;
    }


    private static <T> T readAsCore(Class<T> clazz, HttpEntity httpEntity, MediaTypeFormatter formatter) throws IOException {
        return formatter.readFrom(clazz, httpEntity);
    }
}
