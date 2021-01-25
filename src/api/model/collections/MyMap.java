package api.model.collections;

import api.model.exceptions.DeepCopyException;
import api.model.exceptions.OutOfBoundsException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyMap<K, T> implements IMap<K, T>, Serializable {
    private final java.util.Map<K, T> map = new HashMap<>();

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
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Map<K, T> getContent() {
        return Map.copyOf(map);
    }

    @Override
    public void setContent(Map<K, T> content) {
        map.clear();
        map.putAll(content);
    }

    @Override
    public List<Map.Entry<K, T>> getEntries() {
        return List.copyOf(map.entrySet());
    }

    @Override
    public List<K> getKeys() {
        return List.copyOf(map.keySet());
    }

    @Override
    public List<T> getValues() {
        return List.copyOf(map.values());
    }

    @Override
    public MyMap<K, T> deepCopy() throws DeepCopyException {
        try {
            ByteArrayOutputStream byteOutput   = new ByteArrayOutputStream();
            ObjectOutputStream    objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(this);
            ByteArrayInputStream byteInput   = new ByteArrayInputStream(byteOutput.toByteArray());
            ObjectInputStream    objectInput = new ObjectInputStream(byteInput);
            @SuppressWarnings({"unchecked"})
            var result = (MyMap<K, T>) objectInput.readObject();
            return result;
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "[" + map.entrySet().stream()
                .map(x -> "{" + x.getKey() + ":" + x.getValue() + "}")
                .collect(Collectors.joining(", ")) + "]";
    }
}
