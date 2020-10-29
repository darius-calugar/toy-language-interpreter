package api.repository;

import api.model.ProgramState;
import api.model.exceptions.OutOfBoundsException;

/**
 Repository interface for the interpreter
 */
public interface IRepository {
    /**
     @return Currently selected program state
     */
    ProgramState currentProgramState();

    /**
     Select a program stored at a certain position.

     @param index Program's repository index
     @throws OutOfBoundsException Index is out of repository bounds
     */
    void selectProgramState(int index) throws OutOfBoundsException;

    /**
     Add a new program state at the end of the repository.
     @param programState New program
     */
    void addProgramState(ProgramState programState);
}
