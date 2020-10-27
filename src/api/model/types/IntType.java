package api.model.types;

import api.model.values.IValue;
import api.model.values.IntValue;

public class IntType implements IType {
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass();
    }

    @Override
    public String toString() {
        return "int";
    }
}
