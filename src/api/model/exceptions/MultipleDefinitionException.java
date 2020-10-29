package api.model.exceptions;

/**
 The symbol table already contains a copy of that variable id.
 */
public class MultipleDefinitionException extends  MyException {
    public MultipleDefinitionException(String varId) {
        super("Variable " + varId + " was already defined");
    }
}
