package api.model.statements;

import api.model.ProgramState;
import api.model.exceptions.StatementException;

public class IfStatement implements IStatement {
    // TODO - Implement if statement
    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NullStatement();
        // TODO - Implement deep copy
    }

}
