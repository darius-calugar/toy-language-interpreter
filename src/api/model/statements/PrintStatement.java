package api.model.statements;

import api.model.Locks;
import api.model.collections.IMap;
import api.model.exceptions.MyException;
import api.model.ProgramState;
import api.model.expressions.IExpression;
import api.model.types.IType;

/**
 Statement that adds the value of an expression to the program's output list.

 @see api.model.values.IValue IValue
 @see IExpression IExpression */
public class PrintStatement implements IStatement {
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        var heap        = state.getHeap();

        Locks.heapLock.readLock().lock();
        var value = expression.evaluate(symbolTable, heap);
        Locks.heapLock.readLock().unlock();

        Locks.outputListLock.writeLock().lock();
        state.getOutputList().push(value);
        Locks.outputListLock.writeLock().unlock();
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) {
        return typeEnvironment;
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
