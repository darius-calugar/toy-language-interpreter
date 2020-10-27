package api.model;

import api.model.collections.IDictionary;
import api.model.collections.IList;
import api.model.collections.IStack;
import api.model.statements.IStatement;
import api.model.values.IValue;

public class ProgramState {
    IStack<IStatement>          executionStack;
    IDictionary<String, IValue> symbolTable;
    IList<IValue>               outputList;

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> outputList) {
        this.executionStack = executionStack;
        this.symbolTable    = symbolTable;
        this.outputList     = outputList;
    }

    @Override
    public String toString() {
        return "================ STATE ================\n" +
               "Execution Stack:\n" + executionStack + "\n\n" +
               "Symbol Table:\n" + symbolTable + "\n\n" +
               "Output List:\n" + outputList + "\n\n";
    }

    //region getters/setters

    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IDictionary<String, IValue> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IList<IValue> getOutputList() {
        return outputList;
    }

    public void setOutputList(IList<IValue> outputList) {
        this.outputList = outputList;
    }

    //endregion
}
