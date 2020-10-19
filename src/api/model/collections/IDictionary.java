package api.model.collections;

public interface IDictionary<K,T> {
    boolean isDefined(K key);
    void set(K key, T value);
    T remove(K key);
    T get(K key);
}
