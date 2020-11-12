package api.model.exceptions;

/**
 Signals unexpected behaviour from a file operation.
 */
public class FileException extends MyException {
    public FileException(String filePath, Exception innerException) {
        super("Error when trying to operate on the file \"" + filePath + "\": " + innerException.getMessage());
    }

    public FileException(String filePath) {
        super("Error when trying to operate on the file \"" + filePath + "\".");
    }
}
