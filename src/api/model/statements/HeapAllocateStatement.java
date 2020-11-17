package api.model.statements;

import api.model.ProgramState;
import api.model.exceptions.ExpectedRefTypeException;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.exceptions.UndefinedVariableException;
import api.model.expressions.IExpression;
import api.model.types.RefType;
import api.model.values.RefValue;

public class HeapAllocateStatement implements IStatement {
    String      varId;
    IExpression expression;

    public HeapAllocateStatement(String varId, IExpression expression) {
        this.varId      = varId;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        var heap        = state.getHeap();

        // Check if varId exists in the symbol table
        if (!symbolTable.isDefined(varId))
            throw new UndefinedVariableException(varId);
        var varValue = symbolTable.get(varId);

        // Cast the variable to a reference type
        if (!(varValue.getType() instanceof RefType))
            throw new ExpectedRefTypeException(varValue.getType());

        // Cast the expression evaluation value to the variable type
        var value = expression.evaluate(symbolTable, heap);
        if (!((RefType) varValue.getType()).getInnerType().equals(value.getType()))
            throw new InvalidTypeException(((RefType) varValue.getType()).getInnerType(), value.getType());

        var address = heap.allocate(value);
        symbolTable.set(varId, new RefValue(address, (value.getType())));

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocateStatement(varId, expression);
    }

    @Override
    public String toString() {
        return "new(" + varId + "," + expression.toString() + ")";
    }
}