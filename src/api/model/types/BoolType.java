package api.model.types;

import api.model.values.False;
import api.model.values.IValue;

public class BoolType implements IType {

    public IValue defaultValue() {
        return new False();
    }

    @Override
    public String toString() {
        return "bool";
    }
}