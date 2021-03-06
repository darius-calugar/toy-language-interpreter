package api.model.expressions;

import api.model.collections.IMap;
import api.model.collections.IHeap;
import api.model.exceptions.DivisionByZeroException;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.types.IType;
import api.model.types.IntType;
import api.model.values.IValue;
import api.model.values.IntValue;

/**
 Expression that computes an arithmetic operation of 2 sub-expressions.

 @see ArithmeticOperation */
public class ArithmeticExpression implements IExpression {
    private final IExpression         lhs;
    private final IExpression         rhs;
    private final ArithmeticOperation operation;

    public ArithmeticExpression(IExpression lhs, IExpression rhs, ArithmeticOperation operation) {
        this.lhs       = lhs;
        this.rhs       = rhs;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws InvalidTypeException, DivisionByZeroException {
        // Fetch values of inner expression
        IType expectedType = new IntType();
        var   lValue       = lhs.evaluate(symTable, heap);
        var   rValue       = rhs.evaluate(symTable, heap);

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
    public IType typeCheck(IMap<String, IType> typeEnvironment) throws MyException {
        var expectedType = new IntType();
        var lhsType      = lhs.typeCheck(typeEnvironment);
        var rhsType      = rhs.typeCheck(typeEnvironment);
        if (!lhsType.equals(expectedType))
            throw new InvalidTypeException(expectedType, lhsType);
        if (!rhsType.equals(expectedType))
            throw new InvalidTypeException(expectedType, rhsType);
        return expectedType;
    }

    @Override
    public String toString() {
        return switch (operation) {
            case add -> String.format("%s+%s", lhs, rhs);
            case subtract -> String.format("%s-%s", lhs, rhs);
            case multiply -> String.format("%s*%s", lhs, rhs);
            case divide -> String.format("%s/%s", lhs, rhs);
            case mod -> String.format("%s%%%s", lhs, rhs);
        };
    }

    public IExpression getLhs() {
        return lhs;
    }

    public IExpression getRhs() {
        return rhs;
    }

    public ArithmeticOperation getOperation() {
        return operation;
    }
}
