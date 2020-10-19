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
    IStatement                  original;

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> outputList, IStatement programStatement) {
        this.executionStack = executionStack;
        this.symbolTable    = symbolTable;
        this.outputList     = outputList;
        this.original       = programStatement; // TODO - Use deep copy
        executionStack.push(programStatement);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s",
                executionStack,
                symbolTable,
                outputList,
                original);
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

    public IStatement getOriginal() {
        return original;
    }

    public void setOriginal(IStatement original) {
        this.original = original;
    }

    //endregion
}
