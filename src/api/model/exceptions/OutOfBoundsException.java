package api.model.exceptions;

/**
 An object's data was incorrectly accessed by an out of bounds index.
 */
public class OutOfBoundsException extends MyException {
    public OutOfBoundsException(String message) {
        super(message);
    }
}
