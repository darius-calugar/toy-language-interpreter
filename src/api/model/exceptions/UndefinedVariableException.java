package api.model.exceptions;

/**
 Tried accessing a variable inside the symbol table with an invalid id.
 */
public class UndefinedVariableException extends MyException {
    public UndefinedVariableException(String varId) {
        super("Variable " + varId + " undefined!");
    }
}
