package api.model.types;

import api.model.values.IValue;
import api.model.values.IntValue;

/**
 Signed Integer type supported by the interpreter.
 Default value is always 0
 */
public class IntType implements IType {
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
