package api.model.collections;

import api.model.exceptions.HeapFullException;
import api.model.exceptions.IllegalHeapAccess;
import api.model.exceptions.OutOfBoundsException;
import api.model.values.IValue;

import java.util.Map;

public interface IHeap {
    int allocate(IValue value) throws HeapFullException;

    void set(Integer address, IValue value) throws OutOfBoundsException, IllegalHeapAccess;

    IValue free(Integer address) throws OutOfBoundsException, IllegalHeapAccess;

    IValue get(Integer address) throws OutOfBoundsException, IllegalHeapAccess;

    boolean isDefined(Integer address);

    void clear();

    int size();

    boolean isEmpty();

    Map<Integer, IValue> getContent();

    void setContent(Map<Integer, IValue> content);

    IHeap deepCopy();
}
