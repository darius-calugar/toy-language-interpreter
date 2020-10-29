package api.model.types;

import api.model.values.IValue;

/**
 Interface for types supported by the interpreter.
 */
public interface IType {
    /**
     @return Default value of the type.
     */
    IValue defaultValue();
}
