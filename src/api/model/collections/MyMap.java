package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MyMap<K,T> implements IMap<K,T> {
    private java.util.Map<K,T> map = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public void set(K key, T value) {
        map.put(key, value);
    }

    @Override
    public T remove(K key) throws OutOfBoundsException {
        if (!map.containsKey(key))
            throw new OutOfBoundsException("Could not find the specified key in the dictionary");
        return map.remove(key);
    }

    @Override
    public T get(K key) throws OutOfBoundsException {
        if (!map.containsKey(key))
            throw new OutOfBoundsException("Could not find the specified key in the dictionary");
        return map.get(key);
    }

    @Override
    public void setContent(Map<K, T> content) {
        map = content;
    }

    @Override
    public Map<K, T> getContent() {
        return map;
    }

    @Override
    public Stream<K> getKeys() {
        return map.keySet().stream();
    }

    @Override
    public Stream<T> getValues() {
        return map.values().stream();
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (var entry: map.entrySet()) {
            result.append(entry.getKey().toString());
            result.append(" -> ");
            result.append(entry.getValue().toString());
            result.append('\n');
        }
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
