package api.model;

import api.model.collections.IDictionary;
import api.model.collections.IList;
import api.model.collections.IStack;
import api.model.statements.IStatement;
import api.model.values.IValue;

public class ProgramState {
    private final IStack<IStatement>          executionStack;
    private final IDictionary<String, IValue> symbolTable;
    private final IList<IValue>               outputList;
    private final IStatement                  originalStatement;

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> outputList, IStatement statement) {
        this.executionStack    = executionStack;
        this.symbolTable       = symbolTable;
        this.outputList        = outputList;
        this.originalStatement = statement.deepCopy();
        executionStack.push(statement);
    }

    @Override
    public String toString() {
        return "================ STATE ================\n" +
               "Execution Stack:\n" + executionStack + "\n\n" +
               "Symbol Table:\n" + symbolTable + "\n\n" +
               "Output List:\n" + outputList + "\n\n";
    }

    //region getters/setters

    public IStack<IStatement> getExecutionStack() { return executionStack; }

    public IDictionary<String, IValue> getSymbolTable() { return symbolTable; }

    public IList<IValue> getOutputList() { return outputList; }

    //endregion
}
