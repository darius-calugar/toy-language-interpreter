package api.model.values;

import api.model.types.IType;
import api.model.types.StringType;

/**
 String value.
 @see StringType
 */
public class StringValue implements IValue {
    private final String string;
    private final IType type;

    public StringValue(String string) {
        this.string = string;
        this.type = new StringType();
    }

    /**
     @return Raw java value
     */
    public String getRawValue() {
        return string;
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        return string;
    }
}
