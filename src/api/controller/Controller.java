package api.controller;

import api.model.ProgramState;
import api.model.collections.IHeap;
import api.repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 Interpreter controller.
 */
public class Controller {
    private final IRepository     repository;
    private       ExecutorService executor;

    public static final int MAX_THREADS = 8;

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
                        } catch (ExecutionException e) {
                            System.out.println("Execution error: \"" + e.getCause().getMessage() + '\"');
                        } catch (InterruptedException e) {
                            e.printStackTrace();
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
        repository.setStates(new LinkedList<>(states));
    }

    /**
     Execute all steps of the current program state.
     */
    public void allStep() {
        executor = Executors.newFixedThreadPool(MAX_THREADS);
        var states = removeCompletedPrograms(repository.getStates());
        while (!states.isEmpty()) {
            var usedHeapContent = GarbageCollector.conservativeGarbageCollector(
                    repository.getStates().stream()
                            .map(s -> s.getSymbolTable().getContent().values())
                            .collect(Collectors.toList()),
                    repository.getStates().stream()
                            .map(ProgramState::getHeap)
                            .map(IHeap::getContent)
                            .findAny().orElse(new HashMap<>())
            );
            repository.getStates().stream()
                    .findAny()
                    .ifPresent(s -> s.getHeap().setContent(usedHeapContent));
            oneStep(states);
            states = removeCompletedPrograms(repository.getStates());
        }
        executor.shutdownNow();
        repository.setStates(states);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }


    public IRepository getRepository() {
        return repository;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
