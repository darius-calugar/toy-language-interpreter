package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class MyStack<T> implements IStack<T> {
    private final java.util.Deque<T> stack = new LinkedList<>();

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public T pop() throws OutOfBoundsException {
        if (stack.isEmpty())
            throw new OutOfBoundsException("Stack is empty");
        return stack.pop();
    }

    @Override
    public T peek() throws OutOfBoundsException {
        if (stack.isEmpty())
            throw new OutOfBoundsException("Stack is empty");
        return stack.peek();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public List<T> getContent() {return List.copyOf(stack);}

    @Override
    public void setContent(List<T> content) {
        stack.clear();
        stack.addAll(content);
    }

    @Override
    public IStack<T> deepCopy() {
        try {
            ByteArrayOutputStream byteOutput   = new ByteArrayOutputStream();
            ObjectOutputStream    objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(this);
            ByteArrayInputStream byteInput   = new ByteArrayInputStream(byteOutput.toByteArray());
            ObjectInputStream    objectInput = new ObjectInputStream(byteInput);
            @SuppressWarnings({"unchecked"})
            var result = (MyStack<T>) objectInput.readObject();
            return result;
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var elem : stack) {
            result.append(elem.toString());
            result.append('\n');
        }
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
