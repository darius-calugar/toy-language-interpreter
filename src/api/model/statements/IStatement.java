package api.model.statements;
import api.model.exceptions.MyException;
import api.model.ProgramState;
import api.model.exceptions.StatementException;

public interface IStatement {
    ProgramState execute(ProgramState state);

    IStatement deepCopy();
}
