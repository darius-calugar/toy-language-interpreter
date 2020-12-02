package api.repository;

import api.model.ProgramState;
import api.model.exceptions.MyException;

import java.util.List;

/**
 Repository interface for the interpreter
 */
public interface IRepository {
    /**
     Append the contents of the program state to a file in log format.
     */
    void logProgramState(ProgramState state) throws MyException;

    List<ProgramState> getStates();

    void setStates(List<ProgramState> states);
}
