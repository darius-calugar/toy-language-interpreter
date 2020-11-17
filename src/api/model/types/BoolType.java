package api.model.types;

import api.model.values.BoolValue;
import api.model.values.IValue;

/**
 Boolean type supported by the interpreter.
 Default value is always false.
 */
public class BoolType implements IType {
    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
