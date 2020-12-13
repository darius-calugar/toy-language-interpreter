package api.model.statements;

import api.model.Locks;
import api.model.ProgramState;
import api.model.collections.IMap;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.UndefinedVariableException;
import api.model.expressions.IExpression;
import api.model.types.IType;

/**
 Statement that assigns a value to a variable id inside the program's symbol table.

 @see IExpression IExpression
 @see api.model.values.IValue IValue */
public class AssignStatement implements IStatement {
    String      varId;
    IExpression expression;

    public AssignStatement(String varId, IExpression expression) {
        this.varId      = varId;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws UndefinedVariableException, InvalidTypeException {
        var symbolTable = state.getSymbolTable();

        Locks.heapLock.readLock().lock();
        var value = expression.evaluate(symbolTable, state.getHeap());
        Locks.heapLock.readLock().unlock();

        if (!symbolTable.isDefined(varId))
            throw new UndefinedVariableException(varId);
        var varType = symbolTable.get(varId).getType();

        if (!value.getType().equals(varType))
            throw new InvalidTypeException(varType, value.getType());

        symbolTable.set(varId, value);
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) {
        if (!typeEnvironment.isDefined(varId))
            throw new UndefinedVariableException(varId);
        var varType        = typeEnvironment.get(varId);
        var expressionType = expression.typeCheck(typeEnvironment);
        if (varType.equals(expressionType))
            return typeEnvironment;
        throw new InvalidTypeException(varType, expressionType);
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(varId, expression);
    }

    @Override
    public String toString() {
        return String.format("%s=%s", varId, expression);
    }

    public String getVarId()           { return varId; }

    public IExpression getExpression() { return expression; }
}
