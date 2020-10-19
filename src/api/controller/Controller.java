package api.controller;

import api.model.ProgramState;
import api.repository.IRepository;

public class Controller {
    IRepository repository;
    boolean     displayOnStepFlag;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    // region Functionalities
    ProgramState oneStep() {
        // TODO - Implement
        return null;
    }

    ProgramState allStep() {
        // TODO - Implement
        return null;
    }
    // endregion

    // region Getters/Setters
    public boolean isDisplayOnStepFlag() {
        return displayOnStepFlag;
    }

    public void setDisplayOnStepFlag(boolean displayOnStepFlag) {
        this.displayOnStepFlag = displayOnStepFlag;
    }
    // endregion
}
