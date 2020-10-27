package api.model.collections;

public interface IList<T> {
    void push(T value);
    T remove(int index);
    T get(int index);
    int size();
}
