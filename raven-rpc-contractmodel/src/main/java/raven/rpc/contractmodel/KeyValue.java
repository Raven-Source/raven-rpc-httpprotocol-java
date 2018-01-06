package raven.rpc.contractmodel;


import lombok.*;

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
