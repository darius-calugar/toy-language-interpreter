package api.model.values;

import api.model.types.BoolType;
import api.model.types.IType;

public class False implements IValue {
    IType type = new BoolType();

    public boolean getRawValue() {
        return false;
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "False";
    }
}
