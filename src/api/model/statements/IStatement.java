package api.model.statements;
import api.MyException;
import api.model.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
}
