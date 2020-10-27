package api.model.exceptions;

public class MultipleDefinitionException extends  MyException {
    public MultipleDefinitionException(String varId) {
        super("Variable " + varId + " was already defined");
    }
}
