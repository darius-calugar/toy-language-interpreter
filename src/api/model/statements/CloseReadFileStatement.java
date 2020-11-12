package api.model.statements;

import api.model.ProgramState;
import api.model.exceptions.FileException;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.expressions.IExpression;
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
        // Cast expression value to string
        var value = expression.evaluate(state.getSymbolTable());
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeException(new StringType(), value.getType());
        var stringValue = (StringValue) value;

        // Check if file is open
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

        return state;
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
