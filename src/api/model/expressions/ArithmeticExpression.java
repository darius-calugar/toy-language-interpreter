package api.model.expressions;

import api.model.collections.IDictionary;
import api.model.exceptions.DivisionByZeroException;
import api.model.exceptions.InvalidTypeException;
import api.model.types.IType;
import api.model.types.IntType;
import api.model.values.IValue;
import api.model.values.IntValue;

/**
 Expression that computes an arithmetic operation of 2 sub-expressions.
 @see ArithmeticOperation
 */
public class ArithmeticExpression implements IExpression {
    IExpression         lhs;
    IExpression         rhs;
    ArithmeticOperation operation;

    public ArithmeticExpression(IExpression lhs, IExpression rhs, ArithmeticOperation operation) {
        this.lhs       = lhs;
        this.rhs       = rhs;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) throws InvalidTypeException, DivisionByZeroException {
        // Fetch values of inner expression
        IType expectedType = new IntType();
        var   lValue       = lhs.evaluate(symTable);
        var   rValue       = rhs.evaluate(symTable);

        // Check expression value types
        if (!lValue.getType().equals(expectedType))
            throw new InvalidTypeException(expectedType, lValue.getType());
        if (!rValue.getType().equals(expectedType))
            throw new InvalidTypeException(expectedType, lValue.getType());

        // Fetch raw values of inner expression
        var lRawValue = ((IntValue) lValue).getRawValue();
        var rRawValue = ((IntValue) rValue).getRawValue();

        // Check for invalid operation scenarios
        if ((operation == ArithmeticOperation.divide || operation == ArithmeticOperation.mod) && rRawValue == 0)
            throw new DivisionByZeroException(this);

        // Apply appropriate operation
        return switch (operation) {
            case add -> new IntValue(lRawValue + rRawValue);
            case subtract -> new IntValue(lRawValue - rRawValue);
            case multiply -> new IntValue(lRawValue * rRawValue);
            case divide -> new IntValue(lRawValue / rRawValue);
            case mod -> new IntValue(lRawValue % rRawValue);
        };
    }

    @Override
    public String toString() {
        return switch (operation) {
            case add -> String.format("%s+%s",lhs, rhs);
            case subtract -> String.format("%s-%s",lhs, rhs);
            case multiply -> String.format("%s*%s",lhs, rhs);
            case divide -> String.format("%s/%s",lhs, rhs);
            case mod -> String.format("%s%%%s",lhs, rhs);
        };
    }
}
