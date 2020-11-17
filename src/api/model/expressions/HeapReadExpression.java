package api.model.expressions;

import api.model.collections.IDictionary;
import api.model.collections.IHeap;
import api.model.exceptions.ExpectedRefTypeException;
import api.model.exceptions.MyException;
import api.model.values.IValue;
import api.model.values.RefValue;

public class HeapReadExpression implements IExpression {
    private final IExpression expression;

    public HeapReadExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        var value = expression.evaluate(symTable, heap);
        if (!(value instanceof RefValue))
            throw new ExpectedRefTypeException(value.getType());
        return heap.get(((RefValue) value).getAddress());
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }
}
