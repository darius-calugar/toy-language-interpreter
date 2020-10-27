package api.repository;

import api.model.ProgramState;

public class MonoRepository implements IRepository {
    ProgramState state;

    public MonoRepository(ProgramState state) {
        this.state = state;
    }

    @Override
    public ProgramState currentProgramState() {
        return state;
    }
}
