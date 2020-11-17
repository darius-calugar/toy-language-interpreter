package api.model.exceptions;

public class HeapFullException extends MyException {
    public HeapFullException() {
        super("Heap is full, cannot add more values");
    }
}
