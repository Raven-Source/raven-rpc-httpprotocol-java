package formatting;

import org.apache.http.entity.ContentType;
import org.apache.http.util.Args;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaTypeFormatterCollection extends ArrayList<MediaTypeFormatter> {

    public MediaTypeFormatterCollection() {
        this(MediaTypeFormatterCollection.createDefaultFormatters());
    }

    public MediaTypeFormatterCollection(List<MediaTypeFormatter> formatters) {
        this.verifyAndSetFormatters(formatters);
    }


    public MediaTypeFormatter FindReader(String mediaType) {

        for (MediaTypeFormatter mediaTypeFormatter : this) {
            //if (mediaTypeFormatter != null && mediaTypeFormatter.CanReadType(type)) {
            for (ContentType contentType : mediaTypeFormatter.getSupportedContentTypes()) {
                if (contentType != null && mediaType.indexOf(contentType.getMimeType()) >= 0)
                    return mediaTypeFormatter;
            }
            // }
        }
        return (MediaTypeFormatter) null;

    }


    private static List<MediaTypeFormatter> createDefaultFormatters() {
        return Arrays.asList(new MediaTypeFormatter[]{
                (MediaTypeFormatter) new JsonMediaTypeFormatter()
        });
    }

    private void verifyAndSetFormatters(List<MediaTypeFormatter> formatters) {
        if (formatters == null)
            Args.notNull(formatters, "formatters");
        for (MediaTypeFormatter formatter : formatters) {
            if (formatter == null)
                Args.notNull(formatter, "formatter");
            this.add(formatter);
        }
    }
}
