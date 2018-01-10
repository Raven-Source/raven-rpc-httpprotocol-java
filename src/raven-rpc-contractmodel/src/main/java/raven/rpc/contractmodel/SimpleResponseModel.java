package raven.rpc.contractmodel;

import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface SimpleResponseModel {
    List<KeyValue<String, String>> getExtension();

    String getCodeString();
}
