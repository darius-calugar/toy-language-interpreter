package api.model.values;

import api.model.types.IType;

public class BoolValue implements IValue {
    boolean bool;

    public BoolValue(boolean bool) {
        this.bool = bool;
    }

    public boolean getRawValue() {
        return bool;
    }

    @Override
    public IType getType() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s", bool);
    }
}
