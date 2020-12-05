package api.view;

import api.view.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 Implements a text-based user interface that allows execution of commands.

 @see Command */
public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    /**
     Add a command to the user interface.
     */
    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    public void addAllCommands(List<? extends Command> commands) {
        this.commands.putAll(commands.stream().collect(Collectors.toMap(Command::getKey, (Command c) -> c)));
    }

    /**
     Print the main menu to the standard output.
     Automatically prints each stored command.
     */
    public void printMenu() {
        commands.values().stream()
                .map(command -> "\u001b[107m \u001b[0m " + command.getKey() + " : " + command.getDescription())
                .sorted()
                .forEach(System.out::println);
    }

    /**
     Start the main execution loop of the text menu.
     Standard input & output is used.
     */
    public void show() {
        var scanner = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            printMenu();
            System.out.print("\u001b[104m \u001b[0m ");
            System.out.print("\u001b[94m>\u001b[0m ");
            var key     = scanner.nextLine();
            var command = commands.get(key);
            if (command != null) {
                command.execute();
            } else {
                System.out.print("\u001b[41m \u001b[0m \u001b[31m");
                System.out.println("Invalid command!\u001b[0m");
            }
            System.out.println();
        }
    }
}
