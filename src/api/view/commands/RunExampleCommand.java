package api.view.commands;

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
            controller.allStep();
        } catch (MyException exception) {
            System.out.println("\u001b[41m \u001b[0m \u001b[31m ERROR: " + exception.getMessage() + "\u001b[0m");
        }
    }
}
