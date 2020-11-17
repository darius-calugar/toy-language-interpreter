package api.model.values;

import api.model.types.IType;
import api.model.types.RefType;

public class RefValue implements IValue {
    private final int   address;
    private final RefType type;

    public RefValue(int address, IType innerType) {
        this.address = address;
        this.type    = new RefType(innerType);
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "(" + address + "," + type.getInnerType() + ")";
    }
}
