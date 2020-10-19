package api.model.statements;

import api.MyException;
import api.model.ProgramState;

public class CompoundStatement implements IStatement{
    IStatement lhs;
    IStatement rhs;

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getExecutionStack().push(rhs);
        state.getExecutionStack().push(lhs);
        return state;
    }

    @Override
    public String toString() {
        return String.format("(%s; %s)", lhs, rhs);
    }
}
