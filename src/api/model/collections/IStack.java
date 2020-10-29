package api.model.collections;

import api.model.exceptions.OutOfBoundsException;

public interface IStack<T> {
    /**
     Push an entry to the top of the stack.
     @param value Target entry
     */
    void push(T value);

    /**
     Pop an entry from the top of the stack.
     @return Popped entry
     @throws OutOfBoundsException Stack is empty
     */
    T pop() throws OutOfBoundsException;

    /**
     Get the entry at the top of the stack.
     Stack is not modified.
     @return top entry
     @throws OutOfBoundsException Stack is empty
     */
    T peek() throws OutOfBoundsException;

    /**
     @return Number of stored entries
     */
    int size();

    /**
     @return Whether the stack is empty
     */
    boolean isEmpty();
}
