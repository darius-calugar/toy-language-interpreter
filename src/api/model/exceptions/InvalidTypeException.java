package api.model.exceptions;

import api.model.types.IType;

public class InvalidTypeException extends MyException {
    public InvalidTypeException(IType expectedType, IType providedType) {
        super(String.format("Invalid type (expected %s, provided %s)",
                expectedType, providedType));
    }
}
