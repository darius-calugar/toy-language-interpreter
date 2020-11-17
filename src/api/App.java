package api;

import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.DictionaryJavaMap;
import api.model.collections.Heap;
import api.model.collections.ListJavaList;
import api.model.collections.StackJavaDeque;
import api.model.expressions.*;
import api.view.commands.ClearLogsCommand;
import api.view.commands.ExitCommand;
import api.view.commands.RunExampleCommand;
import api.model.statements.*;
import api.model.types.BoolType;
import api.model.types.IntType;
import api.model.types.StringType;
import api.model.values.BoolValue;
import api.model.values.IntValue;
import api.model.values.StringValue;
import api.repository.Repository;
import api.view.TextMenu;

public class App {
    static public void main(String[] args) {
        // Example 1 Controller
        var programState1 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                new Heap(),
                BuiltInExamples.EXAMPLE1.getStatementTree());
        var repository1 = new Repository(programState1, "logs\\log1.txt");
        var controller1 = new Controller(repository1);

        // Example 2 Controller
        var programState2 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                new Heap(),
                BuiltInExamples.EXAMPLE2.getStatementTree());
        var repository2 = new Repository(programState2, "logs\\log2.txt");
        var controller2 = new Controller(repository2);

        // Example 3 Controller
        var programState3 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                new Heap(),
                BuiltInExamples.EXAMPLE3.getStatementTree());
        var repository3 = new Repository(programState3, "logs\\log3.txt");
        var controller3 = new Controller(repository3);

        // Example 4 Controller
        var programState4 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                new Heap(),
                BuiltInExamples.EXAMPLE4.getStatementTree());
        var repository4 = new Repository(programState4, "logs\\log4.txt");
        var controller4 = new Controller(repository4);

        // Example 5 Controller
        var programState5 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                new Heap(),
                BuiltInExamples.EXAMPLE5.getStatementTree());
        var repository5 = new Repository(programState5, "logs\\log5.txt");
        var controller5 = new Controller(repository5);

        // Example 5 Controller
        var programState6 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                new Heap(),
                BuiltInExamples.EXAMPLE6.getStatementTree());
        var repository6 = new Repository(programState6, "logs\\log6.txt");
        var controller6 = new Controller(repository6);

        // Example 5 Controller
        var programState7 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                new Heap(),
                BuiltInExamples.EXAMPLE7.getStatementTree());
        var repository7 = new Repository(programState7, "logs\\log7.txt");
        var controller7 = new Controller(repository7);

        var textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));
        textMenu.addCommand(new RunExampleCommand("1", "Run example 1 (v0.1a)", controller1));
        textMenu.addCommand(new RunExampleCommand("2", "Run example 2 (v0.1a)", controller2));
        textMenu.addCommand(new RunExampleCommand("3", "Run example 3 (v0.1a)", controller3));
        textMenu.addCommand(new RunExampleCommand("4", "Run example 4 (v0.2a)", controller4));
        textMenu.addCommand(new RunExampleCommand("5", "Run example 5 (v0.3a)", controller5));
        textMenu.addCommand(new RunExampleCommand("6", "Run example 6 (v0.3a)", controller6));
        textMenu.addCommand(new RunExampleCommand("7", "Run example 7 (v0.3a)", controller7));
        textMenu.addCommand(new ClearLogsCommand("c", "Clear logs", "logs", "(.*).txt"));
        textMenu.show();
    }
}
