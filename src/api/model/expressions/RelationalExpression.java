package api.model.expressions;

import api.model.collections.IMap;
import api.model.collections.IHeap;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.types.BoolType;
import api.model.types.IType;
import api.model.types.IntType;
import api.model.values.BoolValue;
import api.model.values.IValue;
import api.model.values.IntValue;

/**
 Expression that compares 2 IntType expressions
 @see RelationalOperation
 */
public class RelationalExpression implements IExpression {
    private final IExpression         lhs;
    private final IExpression         rhs;
    private final RelationalOperation operation;

    public RelationalExpression(IExpression lhs, IExpression rhs, RelationalOperation operation) {
        this.lhs       = lhs;
        this.rhs       = rhs;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws MyException {
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

        return switch (operation) {
            case less -> new BoolValue(lRawValue < rRawValue);
            case lessEqual -> new BoolValue(lRawValue <= rRawValue);
            case greater -> new BoolValue(lRawValue > rRawValue);
            case greaterEqual -> new BoolValue(lRawValue >= rRawValue);
            case equal -> new BoolValue(lRawValue == rRawValue);
            case notEqual -> new BoolValue(lRawValue != rRawValue);
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
        return new BoolType();
    }

    @Override
    public String toString() {
        return switch (operation) {
            case less -> String.format("%s<%s", lhs, rhs);
            case lessEqual -> String.format("%s<=%s", lhs, rhs);
            case greater -> String.format("%s>%s", lhs, rhs);
            case greaterEqual -> String.format("%s>=%s", lhs, rhs);
            case equal -> String.format("%s==%s", lhs, rhs);
            case notEqual -> String.format("%s!=%s", lhs, rhs);
        };
    }

    public IExpression getLhs() {
        return lhs;
    }

    public IExpression getRhs() {
        return rhs;
    }

    public RelationalOperation getOperation() {
        return operation;
    }
}
