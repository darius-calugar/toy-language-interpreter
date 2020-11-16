package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.ArrayList;

public class ListJavaList<T> implements IList<T> {
    java.util.List<T> list = new ArrayList<>();

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
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
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
