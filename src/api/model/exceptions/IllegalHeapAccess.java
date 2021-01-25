package api.model.exceptions;

public class IllegalHeapAccess extends MyException {
    public IllegalHeapAccess(int address) {
        super("Cannot access uninitialized address: " + address);
    }
}
