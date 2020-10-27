package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

public interface IStack<T> {
    void push(T value);
    T pop() throws OutOfBoundsException;
    T peek();
    boolean isEmpty();
}
