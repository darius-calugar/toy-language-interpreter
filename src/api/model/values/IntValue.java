package api.model.values;

import api.model.types.IType;
import api.model.types.IntType;

import java.util.Objects;

/**
 Signed integer value.
 @see IntType
 */
public class IntValue implements IValue {
    private final int integer;
    private final IType type;

    public IntValue(int integer) {
        this.integer = integer;
        this.type = new IntType();
    }

    /**
     @return Raw java value
     */
    public int getRawValue() {
        return integer;
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%d", integer);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        var that = (IntValue) o;
        return Objects.equals(integer, that.integer) &&
               Objects.equals(type, that.type);
    }
}
