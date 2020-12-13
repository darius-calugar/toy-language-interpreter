package api.model.statements;

import api.model.Locks;
import api.model.ProgramState;
import api.model.collections.IMap;
import api.model.exceptions.FileException;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.expressions.IExpression;
import api.model.types.IType;
import api.model.types.StringType;
import api.model.values.StringValue;

import java.io.IOException;

public class CloseReadFileStatement implements IStatement {
    IExpression expression;

    public CloseReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Locks.heapLock.writeLock().lock();
        var value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        Locks.heapLock.writeLock().unlock();

        // Cast expression value to string
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeException(new StringType(), value.getType());
        var stringValue = (StringValue) value;

        // Check if file is open
        Locks.fileTableLock.writeLock().lock();
        if (!state.getFileTable().isDefined(stringValue))
            throw new FileException(stringValue.getRawValue(), "File is not open");
        var file = state.getFileTable().get(stringValue);

        // Close the file
        try {
            file.close();
        } catch (IOException exception) {
            throw new FileException(stringValue.getRawValue(), exception);
        }
        state.getFileTable().remove(stringValue);
        Locks.fileTableLock.writeLock().unlock();

        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) {
        var expectedType   = new StringType();
        var expressionType = expression.typeCheck(typeEnvironment);
        if (expressionType.equals(expectedType))
            return typeEnvironment;
        throw new InvalidTypeException(expectedType, expressionType);
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression + ')';
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFileStatement(expression);
    }
}
