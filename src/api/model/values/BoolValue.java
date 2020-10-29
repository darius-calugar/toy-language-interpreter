package api.model.values;

import api.model.types.BoolType;
import api.model.types.IType;

/**
 Boolean value.
 @see BoolType
 */
public class BoolValue implements IValue {
    boolean bool;

    public BoolValue(boolean bool) {
        this.bool = bool;
    }

    /**
     @return Raw java value
     */
    public boolean getRawValue() {
        return bool;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.format("%s", bool);
    }
}
