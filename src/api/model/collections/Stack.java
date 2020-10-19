package api.model.collections;

public class Stack<T> implements IStack<T> {
    private java.util.Stack<T> stack;

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public T pop() {
        return stack.pop();
    }
}
