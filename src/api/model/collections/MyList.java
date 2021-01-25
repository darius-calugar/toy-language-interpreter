package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.io.*;
import java.util.List;
import java.util.Vector;

public class MyList<T> implements IList<T>, Serializable {
    private final java.util.List<T> list = new Vector<>();

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public void add(T value, int index) throws OutOfBoundsException {
        if (index < 0 || index >= list.size())
            throw new OutOfBoundsException("Index out of list's bounds");
        list.add(index, value);
    }

    @Override
    public T remove(int index) throws OutOfBoundsException {
        if (index < 0 || index >= list.size())
            throw new OutOfBoundsException("Index out of list's bounds");
        return list.remove(index);
    }

    @Override
    public T set(T value, int index) throws OutOfBoundsException {
        return null;
    }

    @Override
    public T get(int index) throws OutOfBoundsException {
        if (index < 0 || index >= list.size())
            throw new OutOfBoundsException("Index out of list's bounds");
        return list.get(index);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(T value) {
        return list.contains(value);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public List<T> getContent() {
        return List.copyOf(list);
    }

    @Override
    public void setContent(List<T> content) {
        list.clear();
        list.addAll(content);
    }

    @Override
    public IList<T> deepCopy() {
        try {
            ByteArrayOutputStream byteOutput   = new ByteArrayOutputStream();
            ObjectOutputStream    objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(this);
            ByteArrayInputStream byteInput   = new ByteArrayInputStream(byteOutput.toByteArray());
            ObjectInputStream    objectInput = new ObjectInputStream(byteInput);
            @SuppressWarnings({"unchecked"})
            var result = (MyList<T>) objectInput.readObject();
            return result;
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        var stringBuilder      = new StringBuilder();
        var shouldAddSeparator = false;
        for (var element : list) {
            if (shouldAddSeparator)
                stringBuilder.append("\n");
            stringBuilder.append(element.toString());
            shouldAddSeparator = true;
        }
        return stringBuilder.toString();
    }
}
