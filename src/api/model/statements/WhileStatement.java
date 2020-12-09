package api.model.statements;

import api.model.Locks;
import api.model.ProgramState;
import api.model.collections.IMap;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.expressions.IExpression;
import api.model.types.BoolType;
import api.model.types.IType;
import api.model.values.BoolValue;

/**
 Statement that executes a sub-statement until evaluating the condition expression returns true.
 */
public class WhileStatement implements IStatement {
    private final IExpression condition;
    private final IStatement  statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.condition = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        var heap        = state.getHeap();

        // Cast expression value to bool
        Locks.heapLock.readLock().lock();
        var value = condition.evaluate(symbolTable, heap);
        Locks.heapLock.readLock().unlock();
        if (!value.getType().equals(new BoolType()))
            throw new InvalidTypeException(new BoolType(), value.getType());
        var boolValue = (BoolValue) value;

        // Refill stack if expression is true
        if (boolValue.getRawValue()) {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(statement);
        }

        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) {
        var expectedType = new BoolType();
        var conditionType = condition.typeCheck(typeEnvironment);
        if (!conditionType.equals(expectedType))
            throw new InvalidTypeException(expectedType, conditionType);
        return statement.typeCheck(typeEnvironment);
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(condition, statement.deepCopy());
    }

    @Override
    public String toString() {
        return "(while (" + condition.toString() + ") " + statement.toString() + ")";
    }
}
