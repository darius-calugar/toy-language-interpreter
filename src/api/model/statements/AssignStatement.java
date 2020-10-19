package api.model.statements;

import api.MyException;
import api.model.ProgramState;
import api.model.expressions.IExpression;

public class AssignStatement implements IStatement {
    String      varId;
    IExpression expression;

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(varId)) {
            var value = expression.evaluate(symbolTable);
            var varType = symbolTable.get(varId).getType();
            if (value.getType().equals(varType))
                symbolTable.set(varId, value);
            else
                throw new MyException(String.format("Types of '%s' and '%s' do not match", value.getType(), varType));
                // TODO - Use specific exception type
        }
        else
            throw new MyException(String.format("Variable '%s' was not declared", varId));
            // TODO - Use specific exception type
        return state;
    }

    @Override
    public String toString() {
        return String.format("%s=%s", varId, expression);
    }
}
