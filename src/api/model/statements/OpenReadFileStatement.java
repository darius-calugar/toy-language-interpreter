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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFileStatement implements IStatement {
    private final IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        var heap        = state.getHeap();

        Locks.heapLock.readLock().lock();
        var value = expression.evaluate(symbolTable, heap);
        Locks.heapLock.readLock().unlock();

        // Cast to appropriate value type
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeException(new StringType(), value.getType());
        var stringValue = (StringValue) value;

        // Open the file
        if (state.getFileTable().isDefined(stringValue))
            throw new FileException((stringValue).getRawValue(), "File is already open");
        try {
            var file = new BufferedReader(new FileReader(stringValue.getRawValue()));
            state.getFileTable().set(stringValue, file);
        } catch (IOException exception) {
            throw new FileException(stringValue.getRawValue(), exception);
        }

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
        return "openRFile(" + expression + ')';
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFileStatement(expression);
    }
}
