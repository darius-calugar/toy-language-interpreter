package api.model.statements;

import api.model.exceptions.MyException;
import api.model.ProgramState;

public class NullStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NullStatement();
    }

    @Override
    public String toString() {
        return "_;";
    }
}
