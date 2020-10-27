package api.view;

import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.Dictionary;
import api.model.collections.List;
import api.model.collections.Stack;
import api.model.expressions.ValueExpression;
import api.model.statements.AssignStatement;
import api.model.statements.CompoundStatement;
import api.model.statements.DeclareStatement;
import api.model.statements.IStatement;
import api.model.types.BoolType;
import api.model.types.IntType;
import api.model.values.IValue;
import api.model.values.True;
import api.repository.MonoRepository;

public class View {
    Controller controller;

    View(Controller controller) {
        this.controller = controller;
    }

    void start() {
        // Hardcoded part
        controller.getState().getExecutionStack().push(
                new CompoundStatement(
                        new DeclareStatement("b", new BoolType()),
                        new AssignStatement("b", new ValueExpression(new True()))
                        ));
        controller.getState().getExecutionStack().push(
                new DeclareStatement("i", new IntType()));
        System.out.println(controller.getState().toString());

        controller.setDisplayOnStepFlag(true);
        controller.oneStep();
        controller.oneStep();
        controller.oneStep();
    }


    static public void main(String[] args) {
        var programState = new ProgramState(
                new Stack<IStatement>(),
                new Dictionary<String, IValue>(),
                new List<IValue>());
        var repository = new MonoRepository(programState);
        var controller = new Controller(repository);
        var view = new View(controller);
        view.start();
    }
}
