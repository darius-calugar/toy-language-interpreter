package api.view;

import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.Dictionary;
import api.model.collections.List;
import api.model.collections.Stack;
import api.model.expressions.ArithmeticExpression;
import api.model.expressions.ArithmeticOperation;
import api.model.expressions.ValueExpression;
import api.model.expressions.VariableExpression;
import api.model.statements.*;
import api.model.types.BoolType;
import api.model.types.IntType;
import api.model.values.BoolValue;
import api.model.values.IntValue;
import api.repository.MonoRepository;

public class View {
    Controller controller;

    View(Controller controller) {
        this.controller = controller;
    }

    void start() {
        System.out.println(controller.getState().toString());
        controller.setDisplayOnStepFlag(true);
        controller.allStep();
    }

    static public void main(String[] args) {
        var programState = new ProgramState(
                new Stack<>(),
                new Dictionary<>(),
                new List<>(),
                example3());
        var repository = new MonoRepository(programState);
        var controller = new Controller(repository);
        var view       = new View(controller);
        view.start();
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
                        new PrintStatement(new VariableExpression("x"))
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
