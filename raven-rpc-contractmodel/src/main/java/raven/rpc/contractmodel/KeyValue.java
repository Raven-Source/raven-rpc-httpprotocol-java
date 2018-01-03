package raven.rpc.contractmodel;

/**
 *
 * @param <TKey>
 * @param <TValue>
 */
public class KeyValue<TKey, TValue> {

    private TKey key;
    private TValue value;

    public TKey getKey() {
        return key;
    }

    public void setKey(TKey key) {
        this.key = key;
    }

    public TValue getValue() {
        return value;
    }

    public void setValue(TValue value) {
        this.value = value;
    }


    public KeyValue(TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {
    }

}
