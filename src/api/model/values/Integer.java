package api.model.values;

import api.model.types.IType;

public class Integer implements IValue {
    int integer;

    public Integer(int integer) {
        this.integer = integer;
    }

    public int getRawValue() {
        return integer;
    }

    @Override
    public IType getType() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("%d", integer);
    }
}
