package api.model.statements;

import api.model.exceptions.MyException;
import api.model.ProgramState;

public class CompoundStatement implements IStatement{
    IStatement lhs;
    IStatement rhs;

    public CompoundStatement(IStatement lhs, IStatement rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var stack = state.getExecutionStack();
        stack.push(rhs);
        stack.push(lhs);
        return state;
    }

    @Override
    public String toString() {
        return String.format("%s;\n%s;", lhs, rhs);
    }

    public IStatement getLhs() { return lhs; }

    public IStatement getRhs() { return rhs; }
}
