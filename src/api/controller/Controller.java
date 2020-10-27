package api.controller;

import api.model.ProgramState;
import api.model.exceptions.OutOfBoundsException;
import api.model.statements.IStatement;
import api.repository.IRepository;

public class Controller {
    ProgramState state;
    IRepository repository;
    boolean     displayOnStepFlag;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.state = repository.currentProgramState();
    }

    // region Functionalities
    public ProgramState oneStep() {
        var stack = state.getExecutionStack();
        if (stack.peek() != null) {
            var statement = stack.pop();
            statement.execute(state);
        }

        if (displayOnStepFlag) {
            System.out.println(state.toString());
        }
        return state;
    }

    public ProgramState allStep() {
        // TODO - Implement
        return state;
    }
    // endregion

    // region Getters/Setters

    public ProgramState getState() {
        return state;
    }

    public IRepository getRepository() {
        return repository;
    }

    public boolean isDisplayOnStepFlag() {
        return displayOnStepFlag;
    }

    public void setDisplayOnStepFlag(boolean displayOnStepFlag) {
        this.displayOnStepFlag = displayOnStepFlag;
    }

    // endregion
}
