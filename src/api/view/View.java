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
import api.model.values.IValue;
import api.model.values.IntValue;
import api.model.values.True;
import api.repository.MonoRepository;
import com.sun.source.tree.NewArrayTree;

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
                new Stack<IStatement>(),
                new Dictionary<String, IValue>(),
                new List<IValue>(),
                example1());
        var repository = new MonoRepository(programState);
        var controller = new Controller(repository);
        var view = new View(controller);
        view.start();
    }

    static private IStatement example1() {
        return new CompoundStatement(
                new DeclareStatement("x", new IntType()),
                new CompoundStatement(
                        new AssignStatement("x", new ArithmeticExpression(new ValueExpression(new IntValue(1)), new ValueExpression(new IntValue(3)), ArithmeticOperation.add)),
                        new PrintStatement(new VariableExpression("x"))
                ));
    }

    static private IStatement example2() {
        return new NullStatement();
    }

    static private IStatement example3() {
        return new NullStatement();
    }
}
