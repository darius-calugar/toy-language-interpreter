package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.util.LinkedList;

public class StackJavaDeque<T> implements IStack<T> {
    private final java.util.Deque<T> stack = new LinkedList<T>();

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public T pop() {
        if (stack.isEmpty())
            throw new OutOfBoundsException("Stack is empty");
        return stack.pop();
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var elem: stack) {
            result.append(elem.toString());
            result.append('\n');
        }
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
