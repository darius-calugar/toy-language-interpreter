package api.model.exceptions;

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
