package api.model.statements;

import api.model.Locks;
import api.model.ProgramState;
import api.model.collections.IMap;
import api.model.exceptions.*;
import api.model.expressions.IExpression;
import api.model.types.IType;
import api.model.types.IntType;
import api.model.types.StringType;
import api.model.values.IntValue;
import api.model.values.StringValue;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

public class ReadFileStatement implements IStatement {
    String      varId;
    IExpression expression;

    public ReadFileStatement(IExpression expression, String varId) {
        this.expression = expression;
        this.varId      = varId;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        var heap        = state.getHeap();

        // Check if the variable is defined
        if (!symbolTable.isDefined(varId))
            throw new UndefinedVariableException(varId);

        // Check if the variable's type is integer
        if (!symbolTable.get(varId).getType().equals(new IntType()))
            throw new InvalidTypeException(symbolTable.get(varId).getType(), new IntType());

        // Cast expression value to string
        Locks.heapLock.readLock().lock();
        var value = expression.evaluate(symbolTable, heap);
        Locks.heapLock.readLock().unlock();
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeException(new StringType(), value.getType());
        var stringValue = (StringValue) value;

        // Check if file is open
        Locks.fileTableLock.readLock().lock();
        if (!state.getFileTable().isDefined(stringValue))
            throw new FileException(stringValue.getRawValue(), "File is not open");
        var file = state.getFileTable().get(stringValue);
        Locks.fileTableLock.readLock().unlock();

        // set the variable value to the parsed file line
        try {
            var line = file.readLine();
            if (line == null) {
                throw new EOFException();
            }
            if (line.length() == 0)
                symbolTable.set(varId, new IntValue(0));
            else
                symbolTable.set(varId, new IntValue(Integer.parseInt(line)));
        } catch (NumberFormatException exception) {
            throw new ParseException(new IntType(), new StringType());
        } catch (IOException exception) {
            throw new FileException(stringValue.getRawValue(), exception);
        }

        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) {
        if (!typeEnvironment.isDefined(varId))
            throw new UndefinedVariableException(varId);
        var expectedVarType        = new IntType();
        var expectedExpressionType = new StringType();
        var varType                = typeEnvironment.get(varId);
        var expressionType         = expression.typeCheck(typeEnvironment);
        if (!expressionType.equals(expectedExpressionType))
            throw new InvalidTypeException(expectedExpressionType, expressionType);
        if (!varType.equals(expectedVarType))
            throw new InvalidTypeException(expectedVarType, varType);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "readFile(" + expression + "," + varId + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression, varId);
    }
}
