package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.List;
import java.util.Map;

public interface IMap<K, T> {
    void set(K key, T value);

    T remove(K key) throws OutOfBoundsException;

    T get(K key) throws OutOfBoundsException;

    boolean isDefined(K key);

    void clear();

    int size();

    boolean isEmpty();

    Map<K, T> getContent();

    void setContent(Map<K, T> content);

    List<Map.Entry<K, T>> getEntries();

    List<K> getKeys();

    List<T> getValues();

    MyMap<K, T> deepCopy();
}
