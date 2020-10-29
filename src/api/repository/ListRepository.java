package api.repository;

import api.model.ProgramState;
import api.model.collections.IList;
import api.model.collections.ListJavaList;
import api.model.exceptions.OutOfBoundsException;

/**
 List implementation of the program repository.
 @see IList
 */
public class ListRepository implements IRepository {
    IList<ProgramState> stateList;
    int                 currentProgramStateIndex;

    public ListRepository() {
        this.stateList           = new ListJavaList<>();
        currentProgramStateIndex = 0;
    }

    @Override
    public ProgramState currentProgramState() {
        return stateList.get(currentProgramStateIndex);
    }

    @Override
    public void selectProgramState(int index) {
        if (index < 0 || index >= stateList.size())
            throw new OutOfBoundsException("Index out of bounds of the repository");
        currentProgramStateIndex = index;
    }

    @Override
    public void addProgramState(ProgramState programState) {
        stateList.push(programState);
    }
}
