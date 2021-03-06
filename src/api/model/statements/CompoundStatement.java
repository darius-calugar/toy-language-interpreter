package api.model.statements;

import api.model.ProgramState;
import api.model.collections.IMap;
import api.model.types.IType;

/**
 Statement that executes 2 sub-statements in left-to-right order.
 */
public class CompoundStatement implements IStatement {
    IStatement lhs;
    IStatement rhs;

    public CompoundStatement(IStatement lhs, IStatement rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        var stack = state.getExecutionStack();
        stack.push(rhs);
        stack.push(lhs);
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) {
        return rhs.typeCheck(lhs.typeCheck(typeEnvironment));
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(lhs.deepCopy(), rhs.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%s; %s)", lhs, rhs);
    }

    public IStatement getLhs() { return lhs; }

    public IStatement getRhs() { return rhs; }
}
