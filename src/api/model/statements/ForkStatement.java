package api.model.statements;

import api.model.ProgramState;
import api.model.collections.MyStack;
import api.model.exceptions.MyException;

public class ForkStatement implements IStatement {
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return new ProgramState(
                new MyStack<>(),
                state.getSymbolTable().deepCopy(),
                state.getOutputList(),
                state.getFileTable(),
                state.getHeap(),
                statement);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement);
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
