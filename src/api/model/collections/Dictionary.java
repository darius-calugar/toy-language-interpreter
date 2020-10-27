package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.HashMap;

public class Dictionary<K,T> implements IDictionary<K,T> {
    java.util.Map<K,T> dictionary = new HashMap<K,T>();

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void set(K key, T value) {
        dictionary.put(key, value);
    }

    @Override
    public T remove(K key) throws OutOfBoundsException {
        if (!dictionary.containsKey(key))
            throw new OutOfBoundsException("Could not find the specified key in the dictionary");
        return dictionary.remove(key);
    }

    @Override
    public T get(K key) {
        return dictionary.get(key);
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (var entry: dictionary.entrySet()) {
            result.append(entry.toString());
            result.append('\n');
        }
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
