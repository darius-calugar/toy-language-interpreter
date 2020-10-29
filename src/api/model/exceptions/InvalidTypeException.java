package api.model.exceptions;

import api.model.types.IType;

/**
 Expected type does not match with the provided type.
 */
public class InvalidTypeException extends MyException {
    public InvalidTypeException(IType expectedType, IType providedType) {
        super(String.format("Invalid type (expected %s, provided %s)",
                expectedType, providedType));
    }
}
