package api.repository;

import api.model.ProgramState;

public interface IRepository {
    ProgramState currentProgramState();
    void selectProgramState(int index);
    void addProgramState(ProgramState programState);
}
