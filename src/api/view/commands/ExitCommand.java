package api.view.commands;

/**
 Stops the interpreter process upon execution.
 */
public class ExitCommand extends Command {
    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.out.println("Quitting...");
        System.exit(0);
    }

}
