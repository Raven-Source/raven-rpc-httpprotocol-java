package raven.rpc.contractmodel;


import lombok.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@ToString
@EqualsAndHashCode
public class KeyValue<TKey, TValue> {

    @Getter
    @Setter
    private TKey key;

    @Getter
    @Setter
    private TValue value;

    /**
     * 构造函数
     *
     * @param key
     * @param value
     */
    public KeyValue(TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }
}
