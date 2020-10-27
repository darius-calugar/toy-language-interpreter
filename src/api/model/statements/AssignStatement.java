package api.model.statements;

import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.ProgramState;
import api.model.exceptions.UndefinedVariableException;
import api.model.expressions.IExpression;

public class AssignStatement implements IStatement {
    String      varId;
    IExpression expression;

    public AssignStatement(String varId, IExpression expression) {
        this.varId = varId;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws UndefinedVariableException, InvalidTypeException {
        var symbolTable = state.getSymbolTable();
        var value = expression.evaluate(symbolTable);

        if (!symbolTable.isDefined(varId))
            throw new UndefinedVariableException(varId);
        var varType = symbolTable.get(varId).getType();

        if (!value.getType().equals(varType))
            throw new InvalidTypeException(varType, value.getType());

        symbolTable.set(varId, value);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NullStatement();
        // TODO - Implement deep copy
    }

    @Override
    public String toString() {
        return String.format("%s=%s", varId, expression);
    }

    public String getVarId() { return varId; }

    public IExpression getExpression() { return expression; }
}
