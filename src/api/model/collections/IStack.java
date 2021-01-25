package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.List;

public interface IStack<T> {
    void push(T value);

    T pop() throws OutOfBoundsException;

    T peek() throws OutOfBoundsException;

    void clear();

    int size();

    boolean isEmpty();

    List<T> getContent();

    void setContent(List<T> content);

    IStack<T> deepCopy();
}
