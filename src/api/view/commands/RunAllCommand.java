package api.view.commands;

import api.view.TextMenu;

import java.util.List;

public class RunAllCommand extends Command {
    private final List<RunExampleCommand> commands;

    public RunAllCommand(String key, List<RunExampleCommand> commands) {
        super(key, "Run all examples");
        this.commands = commands;
    }

    @Override
    public void execute() {
        System.out.println("Running all available examples...");
        commands.forEach(Command::execute);
    }
}
