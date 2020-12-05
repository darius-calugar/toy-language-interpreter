package api.model.statements;

import api.model.Locks;
import api.model.ProgramState;
import api.model.exceptions.ExpectedRefTypeException;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.expressions.IExpression;
import api.model.types.RefType;
import api.model.values.RefValue;

public class HeapWriteStatement implements IStatement {
    private final String      varId;
    private final IExpression expression;

    public HeapWriteStatement(String varId, IExpression expression) {
        this.varId      = varId;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symTable = state.getSymbolTable();
        var heap     = state.getHeap();

        // Cast variable type to ref type
        var varValue = symTable.get(varId);
        if (!(varValue instanceof RefValue))
            throw new ExpectedRefTypeException(varValue.getType());

        // Cast expression to variable inner type
        Locks.heapLock.readLock().lock();
        var value = expression.evaluate(symTable, heap);
        Locks.heapLock.readLock().unlock();
        if (!value.getType().equals(((RefType) varValue.getType()).getInnerType()))
            throw new InvalidTypeException(((RefType) varValue.getType()).getInnerType(), value.getType());

        // Set the heap value
        heap.set(((RefValue) varValue).getAddress(), value);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWriteStatement(varId, expression);
    }

    @Override
    public String toString() {
        return "wH(" + varId + "," + expression.toString() + ")";
    }
}
