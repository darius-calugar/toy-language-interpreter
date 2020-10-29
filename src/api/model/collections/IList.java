package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

public interface IList<T> {
    /**
     Add an entry at the end of the list.

     @param value Target entry
     */
    void push(T value);

    /**
     Remove an value from a certain position.
     Empty space is automatically filled by shifting the following entries to the left.

     @param index Target entry position
     @return Removed entry
     */
    T remove(int index) throws OutOfBoundsException;

    /**
     Get the entry at a certain position.

     @param index Target entry position
     @return entry at given index
     */
    T get(int index) throws OutOfBoundsException;

    /**
     @return Number of stored entries
     */
    int size();

    /**
     @return Whether the list is empty
     */
    boolean isEmpty();
}
