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

/**
 Expression that evaluates a logic operation on 2 sub-expressions.

 @see LogicOperation */
public class LogicExpression implements IExpression {
    private final IExpression    lhs;
    private final IExpression    rhs;
    private final LogicOperation operation;

    public LogicExpression(IExpression lhs, IExpression rhs, LogicOperation operation) {
        this.lhs       = lhs;
        this.rhs       = rhs;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws InvalidTypeException {
        // Fetch values of inner expression
        IType expectedType = new BoolType();
        var   lValue       = lhs.evaluate(symTable, heap);
        var   rValue       = rhs.evaluate(symTable, heap);

        // Check expression value types
        if (!lValue.getType().equals(expectedType))
            throw new InvalidTypeException(expectedType, lValue.getType());
        if (!rValue.getType().equals(expectedType))
            throw new InvalidTypeException(expectedType, lValue.getType());

        // Fetch raw values of inner expression
        var lRawValue = ((BoolValue) lValue).getRawValue();
        var rRawValue = ((BoolValue) rValue).getRawValue();

        // Apply appropriate operation
        return switch (operation) {
            case and -> new BoolValue(lRawValue && rRawValue);
            case or -> new BoolValue(lRawValue || rRawValue);
            case xor -> new BoolValue((lRawValue || rRawValue) && !(lRawValue && rRawValue));
            case nand -> new BoolValue(!(lRawValue && rRawValue));
            case nor -> new BoolValue(!(lRawValue || rRawValue));
            case nxor -> new BoolValue(!((lRawValue || rRawValue) && !(lRawValue && rRawValue)));
        };
    }

    @Override
    public IType typeCheck(IMap<String, IType> typeEnvironment) throws MyException {
        var expectedType = new BoolType();
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
            case and -> String.format("%s&&%s", lhs, rhs);
            case or -> String.format("%s||%s", lhs, rhs);
            case xor -> String.format("%s^^%s", lhs, rhs);
            case nand -> String.format("%s!&%s", lhs, rhs);
            case nor -> String.format("%s!|%s", lhs, rhs);
            case nxor -> String.format("%s!^%s", lhs, rhs);
        };
    }

    public IExpression getLhs() {
        return lhs;
    }

    public IExpression getRhs() {
        return rhs;
    }

    public LogicOperation getOperation() {
        return operation;
    }
}
