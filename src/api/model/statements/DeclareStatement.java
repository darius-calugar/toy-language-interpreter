package api.model.statements;

import api.MyException;
import api.model.ProgramState;
import api.model.types.IType;

public class DeclareStatement implements IStatement {
    String varId;
    IType type;

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.isDefined(varId))
            throw new MyException(String.format(""));
            // TODO - Use specific exception type
        // symbolTable.set(varId, ???);
        // TODO - Implement
        return state;
    }

    @Override
    public String toString() {
        return String.format("Variable '%s' is already declared", varId);
    }
}
