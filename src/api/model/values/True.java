package api.model.values;

import api.model.types.IType;

public class True implements IValue {
    IType type;

    public boolean getRawValue() {
        return true;
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "True";
    }
}
