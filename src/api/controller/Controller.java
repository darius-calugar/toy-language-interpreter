package api.controller;

import api.model.ProgramState;
import api.model.exceptions.OutOfBoundsException;
import api.model.values.IValue;
import api.model.values.RefValue;
import api.repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 Interpreter controller.
 */
public class Controller {
    IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    /**
     Execute one step of the current program state.

     @return Reference to the updated program state
     */
    public ProgramState oneStep(ProgramState state) {
        var stack = state.getExecutionStack();
        if (stack.isEmpty())
            throw new OutOfBoundsException("Execution stack is empty");
        var statement = stack.pop();
        statement.execute(state);
        return state;
    }

    /**
     Execute all steps of the current program state.
     */
    public void allStep() {
        var state = repository.currentProgramState();
        var stack = state.getExecutionStack();
        repository.logCurrentProgramState();
        while (!stack.isEmpty()) {
            oneStep(state);
            repository.logCurrentProgramState();
            state.getHeap().setContent(safeGarbageCollector(
                    getAddresses(state.getSymbolTable().getContent().values()),
                    state.getHeap().getContent()
            ));
            repository.logCurrentProgramState();
        }
    }

    Map<Integer, IValue> safeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()) ||
                             heap.values().stream()
                                     .filter(v -> v instanceof RefValue)
                                     .anyMatch(v -> ((RefValue) v).getAddress() == e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer, IValue> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddresses(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    public ProgramState getState() {
        return getRepository().currentProgramState();
    }

    public IRepository getRepository() {
        return repository;
    }
}
