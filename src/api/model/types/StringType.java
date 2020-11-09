package api.model.types;

import api.model.values.IValue;
import api.model.values.StringValue;

/**
 String type supported by the interpreter. Default value is the empty string.
 */
public class StringType implements IType{
    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass();
    }

    @Override
    public String toString() {
        return "str";
    }
}
