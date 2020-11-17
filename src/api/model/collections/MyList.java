package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private java.util.List<T> list = new ArrayList<>();

    @Override
    public void push(T value) {
        list.add(value);
    }

    @Override
    public T remove(int index) throws OutOfBoundsException {
        if (index < 0 || index >= list.size())
            throw new OutOfBoundsException("Index out of list's bounds");
        return list.remove(index);
    }

    @Override
    public T get(int index) throws OutOfBoundsException {
        if (index < 0 || index >= list.size())
            throw new OutOfBoundsException("Index out of list's bounds");
        return list.get(index);
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
    public void setContent(List<T> content) {
        list = content;
    }

    @Override
    public List<T> getContent() {
        return list;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        var shouldAddSeparator = false;
        for (var element: list) {
            if (shouldAddSeparator)
                stringBuilder.append("\n");
            stringBuilder.append(element.toString());
            shouldAddSeparator = true;
        }
        return stringBuilder.toString();
    }
}
