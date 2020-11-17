package api.model;

import api.model.collections.*;
import api.model.statements.IStatement;
import api.model.values.IValue;
import api.model.values.StringValue;

import java.io.BufferedReader;

/**
 Represents a program instance.
 Stores data  about the execution stack, the symbol table, and the output list.
 Keeps an instance to the original, unmodified statement assigned on creation.
 */
public class ProgramState {
    private final IStack<IStatement>                       executionStack;
    private final IDictionary<String, IValue>              symbolTable;
    private final IList<IValue>                            outputList;
    private final IStatement                               originalStatement;
    private final IDictionary<StringValue, BufferedReader> fileTable;
    private final IHeap                                    heap;

    public ProgramState(IStack<IStatement> executionStack,
                        IDictionary<String, IValue> symbolTable,
                        IList<IValue> outputList,
                        IDictionary<StringValue, BufferedReader> fileTable,
                        IHeap heap,
                        IStatement statement) {
        this.executionStack    = executionStack;
        this.symbolTable       = symbolTable;
        this.outputList        = outputList;
        this.fileTable         = fileTable;
        this.originalStatement = statement.deepCopy();
        this.heap              = heap;
        executionStack.push(statement);
    }

    @Override
    public String toString() {
        return "exeStack=[" + executionStack + "]," +
               "symTable=[" + symbolTable + "], " +
               "outList=[" + outputList + "], " +
               "fileTable=[" + fileTable + "], " +
               "heap=[" + heap + "]";
    }

    public IStack<IStatement> getExecutionStack()                  { return executionStack; }

    public IDictionary<String, IValue> getSymbolTable()            { return symbolTable; }

    public IList<IValue> getOutputList()                           { return outputList; }

    public IDictionary<StringValue, BufferedReader> getFileTable() { return fileTable; }

    public IHeap getHeap()                                         { return heap; }

    public IStatement getOriginalStatement()                       { return originalStatement;}
}
