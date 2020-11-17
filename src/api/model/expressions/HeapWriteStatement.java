package api.model.expressions;

import api.model.collections.IDictionary;
import api.model.collections.IHeap;
import api.model.exceptions.ExpectedRefTypeException;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.types.RefType;
import api.model.values.IValue;
import api.model.values.RefValue;

public class HeapWriteStatement implements IExpression {
    String      varId;
    IExpression expression;

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        // Cast variable type to ref type
        var varValue = symTable.get(varId);
        if (!(varValue instanceof RefValue))
            throw new ExpectedRefTypeException(varValue.getType());

        // Cast expression to variable inner type
        var value = expression.evaluate(symTable, heap);
        if (!value.getType().equals(((RefType) varValue.getType()).getInnerType()))
            throw new InvalidTypeException(((RefType) varValue.getType()).getInnerType(), value.getType());

        // Set the heap value
        heap.set(((RefValue) varValue).getAddress(), value);

        return null;
    }

    @Override
    public String toString() {
        return "wH(" + varId + "," + expression.toString() + ")";
    }
}
