package raven.rpc.httpprototocol.formatting;

import org.apache.http.entity.ContentType;
import org.apache.http.util.Args;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class MediaTypeFormatterCollection extends ArrayList<MediaTypeFormatter> {

    /**
     * Constructor
     */
    public MediaTypeFormatterCollection() {
        this(MediaTypeFormatterCollection.createDefaultFormatters());
    }

    /**
     * Constructor
     *
     * @param formatters
     */
    public MediaTypeFormatterCollection(List<MediaTypeFormatter> formatters) {
        this.verifyAndSetFormatters(formatters);
    }

    /**
     * @param mediaType
     * @return
     */
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

    /**
     * @return
     */
    private static List<MediaTypeFormatter> createDefaultFormatters() {
        return Arrays.asList(new MediaTypeFormatter[]{
                (MediaTypeFormatter) new JsonMediaTypeFormatter()
        });
    }

    /**
     * @param formatters
     */
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
