package api.view.cli.commands;

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
            var examplePreview = controller.getRepository().getStates().stream()
                    .map(s -> s.getOriginalStatement().toString())
                    .reduce("", String::concat);
            if (examplePreview.length() > 40)
                examplePreview = examplePreview.substring(0, 37) + "...";
            controller.allStep();
            System.out.println("Ran example \"" + examplePreview + "\"");
        } catch (MyException exception) {
            System.out.println("\u001b[41m \u001b[0m \u001b[31m ERROR: " + exception.getMessage() + "\u001b[0m");
        }
    }
}
