package api.model.commands;

import api.controller.Controller;
import api.model.exceptions.MyException;

/**
 Runs the program stored in a controller instance.
 */
public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.setDisplayOnStepFlag(true);
            controller.allStep();
        } catch (MyException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }
}
