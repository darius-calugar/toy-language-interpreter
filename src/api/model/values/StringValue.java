package api.model.values;

import api.model.types.IType;
import api.model.types.StringType;

import java.util.Objects;

/**
 String value.

 @see StringType */
public class StringValue implements IValue {
    private final String string;
    private final IType  type;

    public StringValue(String string) {
        this.string = string;
        this.type   = new StringType();
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
        return '\"' + string + '\"';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        var that = (StringValue) o;
        return Objects.equals(string, that.string) &&
               Objects.equals(type, that.type);
    }
}
