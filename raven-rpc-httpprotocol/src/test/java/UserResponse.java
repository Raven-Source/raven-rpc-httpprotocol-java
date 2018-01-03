import raven.rpc.contractmodel.DefaultResponseModel;
import raven.serializer.withJackson.annotations.JsonPropertyFormat;
import raven.serializer.withJackson.annotations.JsonPropertyFormatType;


@JsonPropertyFormat(JsonPropertyFormatType.PascalCase)
public class UserResponse extends DefaultResponseModel<User, Integer>{

    @Override
    public User getData() {
        return super.getData();
    }

    @Override
    public void setData(User user) {
        super.setData(user);
    }
}