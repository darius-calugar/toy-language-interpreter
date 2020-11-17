package api.model.exceptions;

import api.model.collections.Heap;

public class IllegalHeapAccess extends MyException {
    public IllegalHeapAccess(int address) {
        super("Cannot access uninitialized address: " + address);
    }
}
