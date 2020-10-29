package api.model.exceptions;

import api.model.expressions.IExpression;

public class DivisionByZeroException extends MyException {
    public DivisionByZeroException(IExpression expression) {
        super("Division by zero detected on expression: \"" + expression.toString() + "\"");
    }
}
