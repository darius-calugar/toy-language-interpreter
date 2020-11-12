package api.model.exceptions;

import api.model.types.IType;

public class ParseException extends MyException {

    public ParseException(IType targetType, IType sourceType) {
        super("Unable to parse value from type " + sourceType.toString() + " to " + targetType.toString());
    }
}
