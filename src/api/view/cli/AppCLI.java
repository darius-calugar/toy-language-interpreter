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
import api.view.cli.TextMenu;

import java.util.Arrays;

public class AppCLI {
    static public void main(String[] args) {
        try {
            // Example 1 Controller
            System.out.println("Loading example 1...");
            BuiltInExamples.EXAMPLE1.getStatementTree().typeCheck(new MyMap<>());
            var programState1 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE1.getStatementTree());
            var repository1 = new Repository(programState1, "logs\\log1.txt");
            var controller1 = new Controller(repository1);

            // Example 2 Controller
            System.out.println("Loading example 2...");
            BuiltInExamples.EXAMPLE2.getStatementTree().typeCheck(new MyMap<>());
            var programState2 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE2.getStatementTree());
            var repository2 = new Repository(programState2, "logs\\log2.txt");
            var controller2 = new Controller(repository2);

            // Example 3 Controller
            System.out.println("Loading example 3...");
            BuiltInExamples.EXAMPLE3.getStatementTree().typeCheck(new MyMap<>());
            var programState3 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE3.getStatementTree());
            var repository3 = new Repository(programState3, "logs\\log3.txt");
            var controller3 = new Controller(repository3);

            // Example 4 Controller
            System.out.println("Loading example 4...");
            BuiltInExamples.EXAMPLE4.getStatementTree().typeCheck(new MyMap<>());
            var programState4 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE4.getStatementTree());
            var repository4 = new Repository(programState4, "logs\\log4.txt");
            var controller4 = new Controller(repository4);

            // Example 5 Controller
            System.out.println("Loading example 5...");
            BuiltInExamples.EXAMPLE5.getStatementTree().typeCheck(new MyMap<>());
            var programState5 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE5.getStatementTree());
            var repository5 = new Repository(programState5, "logs\\log5.txt");
            var controller5 = new Controller(repository5);

            // Example 6 Controller
            System.out.println("Loading example 6...");
            BuiltInExamples.EXAMPLE6.getStatementTree().typeCheck(new MyMap<>());
            var programState6 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE6.getStatementTree());
            var repository6 = new Repository(programState6, "logs\\log6.txt");
            var controller6 = new Controller(repository6);

            // Example 7 Controller
            System.out.println("Loading example 7...");
            BuiltInExamples.EXAMPLE7.getStatementTree().typeCheck(new MyMap<>());
            var programState7 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE7.getStatementTree());
            var repository7 = new Repository(programState7, "logs\\log7.txt");
            var controller7 = new Controller(repository7);

            // Example 8 Controller
            System.out.println("Loading example 8...");
            BuiltInExamples.EXAMPLE8.getStatementTree().typeCheck(new MyMap<>());
            var programState8 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE8.getStatementTree());
            var repository8 = new Repository(programState8, "logs\\log8.txt");
            var controller8 = new Controller(repository8);

            // Example 9 Controller
            System.out.println("Loading example 9...");
            BuiltInExamples.EXAMPLE9.getStatementTree().typeCheck(new MyMap<>());
            var programState9 = new ProgramState(
                    new MyStack<>(),
                    new MyMap<>(),
                    new MyList<>(),
                    new MyMap<>(),
                    new Heap(), BuiltInExamples.EXAMPLE9.getStatementTree());
            var repository9 = new Repository(programState9, "logs\\log9.txt");
            var controller9 = new Controller(repository9);

            System.out.println("Loaded all examples successfully\n");

            var textMenu = new TextMenu();
            var exampleCommands = Arrays.asList(
                    new RunExampleCommand("1", "Run example 1 (v0.1a)", controller1),
                    new RunExampleCommand("2", "Run example 2 (v0.1a)", controller2),
                    new RunExampleCommand("3", "Run example 3 (v0.1a)", controller3),
                    new RunExampleCommand("4", "Run example 4 (v0.2a)", controller4),
                    new RunExampleCommand("5", "Run example 5 (v0.3a)", controller5),
                    new RunExampleCommand("6", "Run example 6 (v0.3a)", controller6),
                    new RunExampleCommand("7", "Run example 7 (v0.3a)", controller7),
                    new RunExampleCommand("8", "Run example 8 (v0.3a)", controller8),
                    new RunExampleCommand("9", "Run example 9 (v0.4a)", controller9)
            );
            textMenu.addCommand(new ExitCommand("0", "Exit"));
            textMenu.addAllCommands(exampleCommands);
            textMenu.addCommand(new RunAllCommand("a", exampleCommands));
            textMenu.addCommand(new ClearLogsCommand("c", "Clear logs", "logs", "(.*).txt"));
            textMenu.show();
        } catch (MyException exception) {
            System.out.println("\u001b[31m" + exception.getMessage() + "\u001b[0m");
        }
    }
}
