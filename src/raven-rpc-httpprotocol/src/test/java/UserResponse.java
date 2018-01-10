import raven.rpc.contractmodel.DefaultResponseModel;
import raven.serializer.withJackson.annotations.JsonPropertyFormat;
import raven.serializer.withJackson.annotations.JsonPropertyFormatType;

import java.util.ArrayList;


@JsonPropertyFormat(JsonPropertyFormatType.PascalCase)
class UserResponse extends DefaultResponseModel<User, Integer>{

    @Override
    public User getData() {
        return super.getData();
    }

    @Override
    public void setData(User user) {
        super.setData(user);
    }
}

@JsonPropertyFormat(JsonPropertyFormatType.PascalCase)
class UserListResponse extends ArrayList<User>{

}