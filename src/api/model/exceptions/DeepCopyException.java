package api.model.exceptions;

public class DeepCopyException extends MyException {
    public DeepCopyException(Object obj) {
        super("Unable to deep copy object: " + obj.toString());
    }
}
