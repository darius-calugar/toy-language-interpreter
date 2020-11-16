package api.model.statements;

import api.model.ProgramState;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.expressions.IExpression;
import api.model.types.BoolType;
import api.model.values.BoolValue;

/**
 Statement that executes a sub-statement until evaluating the condition expression returns true.
 */
public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement  statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement  = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        // Cast expression value to bool
        var value = expression.evaluate(state.getSymbolTable());
        if (!value.getType().equals(new BoolType()))
            throw new InvalidTypeException(new BoolType(), value.getType());
        var boolValue = (BoolValue) value;

        // Refill stack if expression is true
        if (boolValue.getRawValue()) {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(statement);
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression, statement);
    }

    @Override
    public String toString() {
        return "(while (" + expression.toString() + ") " + statement.toString() + ")";
    }
}
