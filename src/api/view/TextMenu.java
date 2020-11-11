package api.view;

import api.model.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    /**
     Print the main menu to the standard output.
     Automatically prints each stored command.
     */
    public void printMenu() {
        for (var command : commands.values()) {
            System.out.print("\u001b[107m \u001b[0m");
            System.out.printf("%2s - %s%n", command.getKey(), command.getDescription());
        }
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
                System.out.print("\u001b[41m \u001b[0m ");
                System.out.print("\u001b[31m");
                System.out.println("Invalid command!\u001b[0m");
            }
            System.out.println();
        }
    }
}
