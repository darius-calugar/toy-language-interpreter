package api.controller;

import api.model.ProgramState;
import api.model.exceptions.MyException;
import api.model.exceptions.OutOfBoundsException;
import api.model.values.IValue;
import api.model.values.RefValue;
import api.repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 Interpreter controller.
 */
public class Controller {
    IRepository     repository;
    ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void oneStep(List<ProgramState> states) {
        states.forEach(repository::logProgramState);
        var callables = states.stream()
                .map(state -> (Callable<ProgramState>) (state::oneStep))
                .collect(Collectors.toList());

        try {
            var newStates = executor.invokeAll(callables).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        } catch (MyException e) {
                            // TODO - Handle exceptions
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            states.addAll(newStates);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        states.forEach(repository::logProgramState);
        repository.setStates(states);
    }

    /**
     Execute all steps of the current program state.
     */
    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        var states = removeCompletedPrograms(repository.getStates());
        while (!states.isEmpty()) {
            oneStep(states);
            states = removeCompletedPrograms(repository.getStates());
        }
        executor.shutdownNow();
        repository.setStates(states);
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
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

    public IRepository getRepository() {
        return repository;
    }
}
