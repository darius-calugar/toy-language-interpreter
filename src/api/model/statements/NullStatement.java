package api.model.statements;

import api.model.exceptions.MyException;
import api.model.ProgramState;

/**
 Dummy statement that executes an empty method.
 */
public class NullStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
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
