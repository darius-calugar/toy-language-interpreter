package api.model.expressions;

import api.model.collections.IMap;
import api.model.collections.IHeap;
import api.model.exceptions.MyException;
import api.model.types.IType;
import api.model.values.IValue;

/**
 Expression that returns a constant value.
 */
public class ValueExpression implements IExpression {
    private final IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) {
        return value;
    }

    @Override
    public IType typeCheck(IMap<String, IType> typeEnvironment) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public IValue getValue() { return value; }
}
