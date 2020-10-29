package api.model.exceptions;

/**
 Base class for excteptions used by the interpreter.
 */
public class MyException extends RuntimeException {
    String message;

    public MyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
