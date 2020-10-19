package api.model.statements;

import api.MyException;
import api.model.ProgramState;
import api.model.expressions.IExpression;

import java.beans.Expression;

public class PrintStatement implements IStatement {
    IExpression expression;

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getOutputList().push(expression.evaluate(state.getSymbolTable()));
        return state;
    }

    @Override
    public String toString() {
        return String.format("print(%s)", expression);
    }
}
