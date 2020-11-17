package api.model.exceptions;

import api.model.types.IType;

public class ExpectedRefTypeException extends MyException {
    public ExpectedRefTypeException(IType providedType) {
        super("Expected reference type value, received value of type " + providedType + " instead");
    }
}
