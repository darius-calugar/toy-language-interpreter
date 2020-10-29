package api.model.values;

import api.model.types.IType;

/**
 Interface for values of a type supported by the interpreter.
 */
public interface IValue {
    /**
     @return Type of the value
     */
    IType getType();
}
