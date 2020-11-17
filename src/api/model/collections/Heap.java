package api.model.collections;

import api.model.exceptions.HeapFullException;
import api.model.exceptions.IllegalHeapAccess;
import api.model.exceptions.OutOfBoundsException;
import api.model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap {
    private Map<Integer, IValue> map             = new HashMap<>();
    private int                  nextFreeAddress = 1;

    @Override
    public boolean isDefined(Integer address) {
        return map.containsKey(address);
    }

    @Override
    public int allocate(IValue value) throws HeapFullException {
        map.put(ComputeNextFreeAddress(), value);
        return nextFreeAddress;
    }

    @Override
    public void set(Integer address, IValue value) throws OutOfBoundsException, IllegalHeapAccess {
        if (address <= 0)
            throw new OutOfBoundsException("The address " + address.toString() + " is invalid inside the heap");
        var oldValue = map.getOrDefault(address, null);
        if (oldValue == null)
            throw new IllegalHeapAccess(address);
        map.put(address, value);
    }

    @Override
    public IValue free(Integer address) throws OutOfBoundsException, IllegalHeapAccess {
        if (address <= 0)
            throw new OutOfBoundsException("The address " + address.toString() + " is invalid inside the heap");
        var freedValue = map.getOrDefault(address, null);
        if (freedValue == null)
            throw new IllegalHeapAccess(address);
        map.remove(address);
        return freedValue;
    }

    @Override
    public IValue get(Integer address) throws OutOfBoundsException, IllegalHeapAccess {
        if (address <= 0)
            throw new OutOfBoundsException("The address " + address.toString() + " is invalid inside the heap");
        var value = map.getOrDefault(address, null);
        if (value == null)
            throw new IllegalHeapAccess(address);
        return value;
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        map = content;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return map;
    }

    private int ComputeNextFreeAddress() throws HeapFullException {
        // TODO - Test this
        var lastPotentialAddress = nextFreeAddress > 1 ? nextFreeAddress - 1 : Integer.MAX_VALUE;
        while (map.containsKey(nextFreeAddress)) {
            nextFreeAddress = nextFreeAddress < Integer.MAX_VALUE ? nextFreeAddress + 1 : 1;
            if (lastPotentialAddress == nextFreeAddress)
                throw new HeapFullException();
        }
        return nextFreeAddress;
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (var entry : map.entrySet()) {
            result.append(entry.getKey().toString());
            result.append(" -> ");
            result.append(entry.getValue().toString());
            result.append('\n');
        }
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
