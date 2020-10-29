package api.model.statements;

import api.model.ProgramState;
import api.model.exceptions.InvalidTypeException;
import api.model.expressions.IExpression;
import api.model.types.BoolType;
import api.model.values.BoolValue;

public class IfStatement implements IStatement {
    IExpression condition;
    IStatement  lhs;
    IStatement  rhs;

    public IfStatement(IExpression condition, IStatement lhs, IStatement rhs) {
        this.condition = condition;
        this.lhs       = lhs;
        this.rhs       = rhs;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InvalidTypeException {
        var executionStack = state.getExecutionStack();
        var symbolTable = state.getSymbolTable();

        var value = condition.evaluate(symbolTable);
        if (!value.getType().equals(new BoolType()))
            throw new InvalidTypeException(new BoolType(), value.getType());

        if (((BoolValue)value).getRawValue())
            executionStack.push(lhs);
        else
            executionStack.push(rhs);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition, lhs.deepCopy(), rhs.deepCopy());
    }

    @Override
    public String toString() {
        return "if (" + condition + ") -> " + lhs + " else ->" + rhs;
    }
}
