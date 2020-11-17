package api.model.types;

import api.model.values.IValue;
import api.model.values.RefValue;

public class RefType implements IType {
    private final IType innerType;

    public RefType(IType innerType) {
        this.innerType = innerType;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, innerType);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RefType && innerType.equals(((RefType) obj).getInnerType());
    }

    public IType getInnerType() {
        return innerType;
    }

    @Override
    public String toString() {
        return "Ref " + innerType;
    }
}
