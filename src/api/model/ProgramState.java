package api.model;

import api.model.collections.*;
import api.model.exceptions.MyException;
import api.model.exceptions.OutOfBoundsException;
import api.model.statements.IStatement;
import api.model.values.IValue;
import api.model.values.StringValue;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

/**
 Represents a program instance.
 Stores data  about the execution stack, the symbol table, and the output list.
 Keeps an instance to the original, unmodified statement assigned on creation.
 */
public class ProgramState {
    public static AtomicInteger nextFreeId = new AtomicInteger(1);

    private final int                               id;
    private final IStack<IStatement>                executionStack;
    private final IMap<String, IValue>              symbolTable;
    private final IList<IValue>                     outputList;
    private final IStatement                        originalStatement;
    private final IMap<StringValue, BufferedReader> fileTable;
    private final IHeap                             heap;

    public ProgramState(IStack<IStatement> executionStack,
                        IMap<String, IValue> symbolTable,
                        IList<IValue> outputList,
                        IMap<StringValue, BufferedReader> fileTable,
                        IHeap heap,
                        IStatement statement) {
        this.id                = nextFreeId.getAndIncrement();
        this.executionStack    = executionStack;
        this.symbolTable       = symbolTable;
        this.outputList        = outputList;
        this.fileTable         = fileTable;
        this.originalStatement = statement.deepCopy();
        this.heap              = heap;
        executionStack.push(statement);
    }

    /**
     Execute one step of the program state.

     @return Reference to the updated program state
     */
    public ProgramState oneStep() throws MyException {
        if (executionStack.isEmpty())
            throw new OutOfBoundsException("Execution stack is empty");
        var statement = executionStack.pop();
        return statement.execute(this);
    }

    public Boolean isNotCompleted() { return !executionStack.isEmpty(); }

    @Override
    public String toString() {
        return "ProgramState_" + id;
    }

    public int getId()                                      { return id; }

    public IStack<IStatement> getExecutionStack()           { return executionStack; }

    public IMap<String, IValue> getSymbolTable()            { return symbolTable; }

    public IList<IValue> getOutputList()                    { return outputList; }

    public IMap<StringValue, BufferedReader> getFileTable() { return fileTable; }

    public IHeap getHeap()                                  { return heap; }

    public IStatement getOriginalStatement()                { return originalStatement;}
}
