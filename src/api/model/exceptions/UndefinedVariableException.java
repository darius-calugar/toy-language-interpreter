package api.model.exceptions;

public class UndefinedVariableException extends MyException {
    public UndefinedVariableException(String varId) {
        super("Variable " + varId + " undefined!");
    }
}
