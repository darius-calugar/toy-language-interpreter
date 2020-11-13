package api.model.values;

import api.model.types.BoolType;
import api.model.types.IType;

import java.util.Objects;

/**
 Boolean value.

 @see BoolType */
public class BoolValue implements IValue {
    private final boolean bool;
    private final IType   type;

    public BoolValue(boolean bool) {
        this.bool = bool;
        this.type = new BoolType();
    }

    /**
     @return Raw java value
     */
    public boolean getRawValue() {
        return bool;
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s", bool);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        var that = (BoolValue) o;
        return Objects.equals(bool, that.bool) &&
               Objects.equals(type, that.type);
    }
}
