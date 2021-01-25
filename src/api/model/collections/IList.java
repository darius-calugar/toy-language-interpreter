package api.model.collections;

import api.model.exceptions.DeepCopyException;
import api.model.exceptions.OutOfBoundsException;

import java.util.List;

public interface IList<T> {
    void add(T value);

    void add(T value, int index) throws OutOfBoundsException;

    T remove(int index) throws OutOfBoundsException;

    T set(T value, int index) throws OutOfBoundsException;

    T get(int index) throws OutOfBoundsException;

    void clear();

    boolean contains(T value);

    int size();

    boolean isEmpty();

    List<T> getContent();

    void setContent(List<T> content);

    IList<T> deepCopy() throws DeepCopyException;
}
