package api.model.commands;

/**
 Stops the interpreter process upon execution.
 */
public class ExitCommand extends Command {
    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }

}
