package api.model.expressions;

import api.model.collections.IMap;
import api.model.collections.IHeap;
import api.model.exceptions.UndefinedVariableException;
import api.model.values.IValue;

/**
 Expression that evaluates the value of a variable defined inside the symbol table of the program
 */
public class VariableExpression implements IExpression {
    private final String varId;

    public VariableExpression(String varId) {
        this.varId = varId;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws UndefinedVariableException {
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
