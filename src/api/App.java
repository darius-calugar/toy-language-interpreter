package api;

import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.DictionaryJavaMap;
import api.model.collections.ListJavaList;
import api.model.collections.StackJavaDeque;
import api.view.commands.ClearLogsCommand;
import api.view.commands.ExitCommand;
import api.view.commands.RunExampleCommand;
import api.model.expressions.ArithmeticExpression;
import api.model.expressions.ArithmeticOperation;
import api.model.expressions.ValueExpression;
import api.model.expressions.VariableExpression;
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
                example1());
        var repository1 = new Repository(programState1, "logs\\log1.txt");
        var controller1 = new Controller(repository1);

        // Example 2 Controller
        var programState2 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                example2());
        var repository2 = new Repository(programState2, "logs\\log2.txt");
        var controller2 = new Controller(repository2);

        // Example 3 Controller
        var programState3 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                example3());
        var repository3 = new Repository(programState3, "logs\\log3.txt");
        var controller3 = new Controller(repository3);

        // Example 4 Controller
        var programState4 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                new DictionaryJavaMap<>(),
                example4());
        var repository4 = new Repository(programState4, "logs\\log4.txt");
        var controller4 = new Controller(repository4);

        var textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));
        textMenu.addCommand(new RunExampleCommand("1", "Run example 1 (v0.1a)", controller1));
        textMenu.addCommand(new RunExampleCommand("2", "Run example 2 (v0.1a)", controller2));
        textMenu.addCommand(new RunExampleCommand("3", "Run example 3 (v0.1a)", controller3));
        textMenu.addCommand(new RunExampleCommand("4", "Run example 4 (v0.2a)", controller4));
        textMenu.addCommand(new ClearLogsCommand("c", "Clear logs", "logs", "(.*).txt"));
        textMenu.show();
    }

    /**
     int v;
     <br/>v = 2;
     <br/>print(v);
     */
    static private IStatement example1() {
        return new CompoundStatement(
                new DeclareStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    /**
     int a;
     <br/>int b;
     <br/>a = 2 + 3 * 5;
     <br/>b = a + 1;
     <br/>print(b);
     */
    static private IStatement example2() {
        return new CompoundStatement(
                new DeclareStatement("a", new IntType()),
                new CompoundStatement(
                        new DeclareStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("a", new ArithmeticExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticOperation.multiply
                                        ),
                                        ArithmeticOperation.add
                                )),
                                new CompoundStatement(
                                        new AssignStatement("b", new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticOperation.add
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }

    /**
     bool a;
     <br/>int v;
     <br/>a=true;
     <br/>if(a) v = 2;
     <br/>else  v = 3;
     <br/>print(v);
     */
    static private IStatement example3() {
        return new CompoundStatement(
                new DeclareStatement("a", new BoolType()),
                new CompoundStatement(
                        new DeclareStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(new VariableExpression("a"),
                                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }


    /**
     string varf;
     <br/>varf="test.in";
     <br/>openRFile(varf);
     <br/>int varc;
     <br/>readFile(varf,varc);
     <br/>print(varc);
     <br/>readFile(varf,varc);
     <br/>print(varc);
     <br/>closeRFile(varf)
     */
    static private IStatement example4() {
        return new CompoundStatement(
                new DeclareStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("resources\\test.in"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new DeclareStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
