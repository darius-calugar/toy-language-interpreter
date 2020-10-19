package api.repository;

import api.model.ProgramState;

public interface IRepository {
    ProgramState currentProgramState();
}
