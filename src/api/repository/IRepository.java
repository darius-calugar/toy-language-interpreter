package api.repository;

import api.model.ProgramState;
import api.model.exceptions.MyException;

/**
 Repository interface for the interpreter
 */
public interface IRepository {
    /**
     @return Currently selected program state
     */
    ProgramState currentProgramState();

    /**
     Append the contents of the program state to a file in log format.
     */
    void logCurrentProgramState() throws MyException;
}
