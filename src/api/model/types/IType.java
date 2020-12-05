package api.model.types;

import api.model.values.IValue;

import java.io.Serializable;

/**
 Interface for types supported by the interpreter.
 */
public interface IType extends Serializable {
    /**
     @return Default value of the type.
     */
    IValue defaultValue();
}
