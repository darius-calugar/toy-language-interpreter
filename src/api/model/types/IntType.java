package api.model.types;

import api.model.values.IValue;
import api.model.values.Integer;

public class IntType implements IType {
    @Override
    public IValue defaultValue() {
        return new Integer(0);
    }

    @Override
    public String toString() {
        return "int";
    }
}
