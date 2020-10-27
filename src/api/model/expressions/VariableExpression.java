package api.model.expressions;

import api.model.collections.IDictionary;
import api.model.exceptions.UndefinedVariableException;
import api.model.values.IValue;

public class VariableExpression implements IExpression {
    private final String varId;

    public VariableExpression(String varId) {
        this.varId = varId;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable) throws UndefinedVariableException {
        if (!symTable.isDefined(varId))
            throw new UndefinedVariableException(varId);
        return symTable.get(varId);
    }

    @Override
    public String toString() {
        return varId;
    }

    public String getVarId() { return varId; }
}