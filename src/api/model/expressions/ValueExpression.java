package api.model.expressions;

import api.model.collections.IMap;
import api.model.collections.IHeap;
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
    public String toString() {
        return value.toString();
    }

    public IValue getValue() { return value; }
}
