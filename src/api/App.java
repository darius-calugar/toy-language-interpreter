package api;

import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.DictionaryJavaMap;
import api.model.collections.ListJavaList;
import api.model.collections.StackJavaDeque;
import api.model.commands.ExitCommand;
import api.model.commands.RunExampleCommand;
import api.model.expressions.ArithmeticExpression;
import api.model.expressions.ArithmeticOperation;
import api.model.expressions.ValueExpression;
import api.model.expressions.VariableExpression;
import api.model.statements.*;
import api.model.types.BoolType;
import api.model.types.IntType;
import api.model.values.BoolValue;
import api.model.values.IntValue;
import api.repository.ListRepository;
import api.view.TextMenu;

public class App {
    static public void main(String[] args) {
        // Example 1 Controller
        var programState1 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                example1());
        var repository1 = new ListRepository();
        repository1.addProgramState(programState1);
        var controller1 = new Controller(repository1);

        // Example 2 Controller
        var programState2 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                example2());
        var repository2 = new ListRepository();
        repository2.addProgramState(programState2);
        var controller2 = new Controller(repository2);

        // Example 3 Controller
        var programState3 = new ProgramState(
                new StackJavaDeque<>(),
                new DictionaryJavaMap<>(),
                new ListJavaList<>(),
                example3());
        var repository3 = new ListRepository();
        repository3.addProgramState(programState3);
        var controller3 = new Controller(repository3);

        var textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));
        textMenu.addCommand(new RunExampleCommand("1", "Run example 1", controller1));
        textMenu.addCommand(new RunExampleCommand("2", "Run example 2", controller2));
        textMenu.addCommand(new RunExampleCommand("3", "Run example 3", controller3));
        textMenu.show();
    }

    /**
     int v;
     v = 2;
     print(v);
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
     int b;
     a = 2 + 3 * 5;
     b = a + 1;
     print(b);
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
     int v;
     a=true;
     if(a) v = 2;
     else  v = 3;
     print(v);
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
}
