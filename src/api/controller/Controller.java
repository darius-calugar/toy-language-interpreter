package api.controller;

import api.model.ProgramState;
import api.model.exceptions.OutOfBoundsException;
import api.repository.IRepository;

/**
 Interpreter controller.
 */
public class Controller {
    IRepository repository;
    boolean     displayOnStepFlag;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    /**
     Execute one step of the current program state.

     @return Reference to the updated program state
     */
    public ProgramState oneStep(ProgramState state) {
        var stack = state.getExecutionStack();
        if (stack.isEmpty())
            throw new OutOfBoundsException("Execution stack is empty");
        var statement = stack.pop();
        statement.execute(state);
        return state;
    }

    /**
     Execute all steps of the current program state.
     */
    public void allStep() {
        var state = repository.currentProgramState();
        var stack = state.getExecutionStack();
        repository.logCurrentProgramState();
        while (!stack.isEmpty()) {
            oneStep(state);
            repository.logCurrentProgramState();
        }
    }

    /**
     Display the state data to the output stream.
     */
    public void display() {
        System.out.println(repository.currentProgramState().toString());
    }

    // region Getters/Setters
    public ProgramState getState() {
        return getRepository().currentProgramState();
    }

    public IRepository getRepository() {
        return repository;
    }

    public boolean getDisplayOnStepFlag() {
        return displayOnStepFlag;
    }

    public void setDisplayOnStepFlag(boolean displayOnStepFlag) {
        this.displayOnStepFlag = displayOnStepFlag;
    }

    public boolean isEmpty() {
        return getRepository().currentProgramState().getExecutionStack().isEmpty();
    }
    // endregion
}
