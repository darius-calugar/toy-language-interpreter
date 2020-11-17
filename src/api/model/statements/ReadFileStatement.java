package api.model.statements;

import api.model.ProgramState;
import api.model.exceptions.*;
import api.model.expressions.IExpression;
import api.model.types.IntType;
import api.model.types.StringType;
import api.model.values.IntValue;
import api.model.values.StringValue;

import java.io.EOFException;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    IExpression expression;
    String      varId;

    public ReadFileStatement(IExpression expression, String varId) {
        this.expression = expression;
        this.varId      = varId;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        var heap        = state.getHeap();
        var value       = expression.evaluate(symbolTable, heap);

        // Check if the variable is defined
        if (!symbolTable.isDefined(varId))
            throw new UndefinedVariableException(varId);

        // Check if the variable's type is integer
        if (!symbolTable.get(varId).getType().equals(new IntType()))
            throw new InvalidTypeException(symbolTable.get(varId).getType(), new IntType());

        // Cast expression value to string
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeException(new StringType(), value.getType());
        var stringValue = (StringValue) value;

        // Check if file is open
        if (!state.getFileTable().isDefined(stringValue))
            throw new FileException(stringValue.getRawValue(), "File is not open");
        var file = state.getFileTable().get(stringValue);

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

        return state;
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
