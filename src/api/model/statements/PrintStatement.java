package api.model.statements;

import api.model.exceptions.MyException;
import api.model.ProgramState;
import api.model.expressions.IExpression;

/**
 Statement that adds the value of an expression to the program's output list.
 @see api.model.values.IValue IValue
 @see IExpression IExpression
 */
public class PrintStatement implements IStatement {
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getOutputList().push(expression.evaluate(state.getSymbolTable()));
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public String toString() {
        return String.format("print(%s)", expression);
    }
}
