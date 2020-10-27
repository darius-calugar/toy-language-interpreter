package api.model.values;

import api.model.types.IType;
import api.model.types.IntType;

public class IntValue implements IValue {
    int integer;

    public IntValue(int integer) {
        this.integer = integer;
    }

    public int getRawValue() {
        return integer;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.format("%d", integer);
    }
}
