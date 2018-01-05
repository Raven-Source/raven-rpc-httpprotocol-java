package raven.rpc.contractmodel;

/**
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }


        KeyValue kv = (KeyValue) o;

        if (getKey() != null ? !getKey().equals(kv.getKey()) : kv.getKey() != null) {
            return false;
        }
        if (getValue() != null ? !getValue().equals(kv.getValue()) : kv.getValue() != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {

        final StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        buffer.append("key=").append(this.key);
        buffer.append(",value=").append(this.value);
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    public int hashCode() {

        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }

}
