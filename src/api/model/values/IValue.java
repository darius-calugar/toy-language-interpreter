package api.model.values;

import api.model.types.IType;

import java.io.Serializable;

/**
 Interface for values of a type supported by the interpreter.
 */
public interface IValue extends Serializable {
    /**
     @return Type of the value
     */
    IType getType();
}
