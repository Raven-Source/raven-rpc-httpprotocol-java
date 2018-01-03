import raven.serializer.withJackson.annotations.JsonPropertyFormat;
import raven.serializer.withJackson.annotations.JsonPropertyFormatType;

@JsonPropertyFormat(JsonPropertyFormatType.PascalCase)
public class User {
    public long ID;
    public String Name;
}
