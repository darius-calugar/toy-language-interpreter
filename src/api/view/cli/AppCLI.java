package api.view.cli;

import api.BuiltInExamples;
import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.MyMap;
import api.model.collections.Heap;
import api.model.collections.MyList;
import api.model.collections.MyStack;
import api.model.exceptions.MyException;
import api.view.cli.commands.*;
import api.repository.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppCLI {
    static public void main(String[] args) {
        try {
            System.out.print("Log path: ");
            File file = new File(new Scanner(System.in).nextLine());
            try {
                if (!file.createNewFile()) {
                    file = new File(Repository.generateLogPath(new Date()));
                    System.out.println("Invalid file path. Using default (" + file.getPath() + ").");
                }
            } catch (IOException exception) {
                file = new File(Repository.generateLogPath(new Date()));
                System.out.println("Invalid file path. Using default (" + file.getPath() + ").");
            }
            String logPath = file.getPath();

            Arrays.stream(BuiltInExamples.values()).forEach(
                    (BuiltInExamples x) -> {
                        System.out.println("Loading " + x.name() + "...");
                        x.getStatementTree().typeCheck(new MyMap<>());
                    }
            );
            System.out.println("Loaded all examples successfully\n");

            var exampleCommands = IntStream.range(0, BuiltInExamples.values().length)
                    .mapToObj((int i) -> {
                        BuiltInExamples.values()[i].getStatementTree().typeCheck(new MyMap<>());
                        var programState = new ProgramState(
                                new MyStack<>(),
                                new MyMap<>(),
                                new MyList<>(),
                                new MyMap<>(),
                                new Heap(), BuiltInExamples.values()[i].getStatementTree());
                        var repository = new Repository(programState, logPath);
                        return new RunExampleCommand(String.valueOf(i + 1), "Example " + (i + 1), new Controller(repository));
                    })
                    .collect(Collectors.toList());

            var textMenu = new TextMenu();
            textMenu.addCommand(new ExitCommand("0", "Exit"));
            textMenu.addAllCommands(exampleCommands);
            textMenu.addCommand(new RunAllCommand("a", exampleCommands));
            textMenu.show();
        } catch (MyException exception) {
            System.out.println("\u001b[31m" + exception.getMessage() + "\u001b[0m");
        }
    }
}
