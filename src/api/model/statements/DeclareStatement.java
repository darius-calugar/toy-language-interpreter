package api.model.statements;

import api.model.exceptions.MultipleDefinitionException;
import api.model.ProgramState;
import api.model.types.IType;

/**
 Statement that defines a variable id to the state's symbol table and associate's it with the default value of it's type.
 @see api.model.values.IValue IValue
 @see IType IType
 */
public class DeclareStatement implements IStatement {
    String varId;
    IType  type;

    public DeclareStatement(String varId, IType type) {
        this.varId = varId;
        this.type  = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MultipleDefinitionException {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.isDefined(varId))
            throw new MultipleDefinitionException(varId);
        symbolTable.set(varId, type.defaultValue());
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new DeclareStatement(varId, type);
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), varId);
    }

    public IType getType()   { return type; }

    public String getVarId() { return varId; }
}
