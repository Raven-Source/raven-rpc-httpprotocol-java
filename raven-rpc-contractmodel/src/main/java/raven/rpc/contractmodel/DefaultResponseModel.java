package raven.rpc.contractmodel;
import java.util.ArrayList;
import java.util.List;

public class DefaultResponseModel<TData, TCode> implements FullResponseModel<TData, TCode> {

    private TCode code;

    private TData data;

    private List<KeyValue<String, String>> extension;

    private String message;

    //private Class<? extends TCode> codeClass;

    @Override
    public TCode getCode() {
        return code;
    }

    @Override
    public void setCode(TCode code) {
        this.code = code;
    }

    @Override
    public TData getData() {
        return data;
    }

    @Override
    public void setData(TData data) {
        this.data = data;
    }

    @Override
    public List<KeyValue<String, String>> getExtension() {
        return extension;
    }

    public void setExtension(List<KeyValue<String, String>> extension) {
        this.extension = extension;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DefaultResponseModel() {
        extension = new ArrayList<>();
    }

    @Override
    public String getCodeString() {
        if (code instanceof CodeEnum) {
            return ((CodeEnum) code).getValue().toString();
        }

        return code.toString();
    }

}
