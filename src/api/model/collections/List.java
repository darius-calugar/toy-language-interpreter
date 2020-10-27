package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.LinkedList;

public class List<T> implements IList<T> {
    java.util.List<T> list = new LinkedList<T>();

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
    public String toString() {
        var stringBuilder = new StringBuilder();
        var shouldAddComma = false;
        stringBuilder.append("[ ");
        for (var element: list) {
            if (shouldAddComma)
                stringBuilder.append(", ");
            stringBuilder.append(element.toString());
            shouldAddComma = true;
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}
