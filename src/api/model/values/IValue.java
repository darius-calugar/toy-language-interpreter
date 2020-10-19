package api.model.values;

import api.model.types.IType;

public interface IValue {
    IType getType();
    String ToString();
}
