package raven.rpc.contractmodel;

import java.util.List;

public interface SimpleResponseModel {
    List<KeyValue<String, String>> getExtension();

    String getCodeString();
}
