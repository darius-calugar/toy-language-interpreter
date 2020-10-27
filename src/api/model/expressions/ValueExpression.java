package api.model.expressions;

import api.model.collections.IDictionary;
import api.model.values.IValue;

public class ValueExpression implements IExpression {
    private final IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    // region Getters/Setters
    public IValue getValue() { return value; }
    // endregion
}
