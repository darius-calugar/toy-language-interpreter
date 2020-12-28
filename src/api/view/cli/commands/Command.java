package api.view.cli.commands;

/**
 Abstract base class for all user commands supported by the interpreter.
 */
public abstract class Command {
    private final String key;
    private final String description;

    public Command(String key, String description) {
        this.key         = key;
        this.description = description;
    }

    /**
     Start the command execution.
     */
    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
