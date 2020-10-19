package api.model.collections;

public interface IStack<T> {
    void push(T value);
    T pop();
}
