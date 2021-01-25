package api.model.collections;

import api.model.exceptions.HeapFullException;
import api.model.exceptions.IllegalHeapAccess;
import api.model.exceptions.OutOfBoundsException;
import api.model.values.IValue;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MyHeap implements IHeap, Serializable {
    private final Map<Integer, IValue> map             = new HashMap<>();
    private       int                  nextFreeAddress = 1;

    @Override
    public boolean isDefined(Integer address) {
        return map.containsKey(address);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
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
        map.clear();
        map.putAll(content);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return Map.copyOf(map);
    }

    @Override
    public IHeap deepCopy() {
        try {
            ByteArrayOutputStream byteOutput   = new ByteArrayOutputStream();
            ObjectOutputStream    objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(this);
            ByteArrayInputStream byteInput   = new ByteArrayInputStream(byteOutput.toByteArray());
            ObjectInputStream    objectInput = new ObjectInputStream(byteInput);
            return (MyHeap) objectInput.readObject();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
            return null;
        }
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

    private int ComputeNextFreeAddress() throws HeapFullException {
        var lastPotentialAddress = nextFreeAddress > 1 ? nextFreeAddress - 1 : Integer.MAX_VALUE;
        while (map.containsKey(nextFreeAddress)) {
            nextFreeAddress = nextFreeAddress < Integer.MAX_VALUE ? nextFreeAddress + 1 : 1;
            if (lastPotentialAddress == nextFreeAddress)
                throw new HeapFullException();
        }
        return nextFreeAddress;
    }
}
