package api.model.statements;

import api.model.Locks;
import api.model.ProgramState;
import api.model.collections.IMap;
import api.model.exceptions.InvalidTypeException;
import api.model.expressions.IExpression;
import api.model.types.BoolType;
import api.model.types.IType;
import api.model.values.BoolValue;

/**
 Statement that executes a sub-statement based on the truth-value of a condition expression.

 @see IExpression IExpression
 @see api.model.values.IValue IValue */
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
        var symbolTable    = state.getSymbolTable();
        var heap           = state.getHeap();

        Locks.heapLock.readLock().lock();
        var value = condition.evaluate(symbolTable, heap);
        Locks.heapLock.readLock().unlock();
        if (!value.getType().equals(new BoolType()))
            throw new InvalidTypeException(new BoolType(), value.getType());

        if (((BoolValue) value).getRawValue())
            executionStack.push(lhs);
        else
            executionStack.push(rhs);
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) {
        var expectedType = new BoolType();
        var conditionType = condition.typeCheck(typeEnvironment);
        if (!conditionType.equals(expectedType))
            throw new InvalidTypeException(expectedType, conditionType);
        return rhs.typeCheck(lhs.typeCheck(typeEnvironment));
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
