package api.model.collections;

import api.model.exceptions.HeapFullException;
import api.model.exceptions.IllegalHeapAccess;
import api.model.exceptions.OutOfBoundsException;
import api.model.values.IValue;

import java.util.Map;

public interface IHeap {

    /**
     @param address Target address
     @return Whether the key is defined inside the dictionary
     */
    boolean isDefined(Integer address);

    /**
     Set a value at the next free address inside the heap.
     @param value Target value
     */
    int allocate(IValue value) throws HeapFullException;

    /**
     Set the value at a given address.
     @param address Target address
     @param value New address value
     @throws OutOfBoundsException Address is invalid in the heap context
     @throws IllegalHeapAccess Address was not initialized
     */
    void set(Integer address, IValue value) throws OutOfBoundsException, IllegalHeapAccess;

    /**
     Free the value from a given address.

     @param address Target address
     @return Freed value
     */
    IValue free(Integer address) throws OutOfBoundsException, IllegalHeapAccess;

    /**
     @param address Target address
     @return value stored at the given address.
     */
    IValue get(Integer address) throws OutOfBoundsException, IllegalHeapAccess;

    void setContent(Map<Integer, IValue> content);

    Map<Integer, IValue> getContent();
}
