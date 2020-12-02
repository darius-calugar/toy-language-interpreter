package api.repository;

import api.model.ProgramState;
import api.model.exceptions.FileException;
import api.model.exceptions.MyException;
import api.model.values.StringValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 Implementation of the program repository.
 */
public class Repository implements IRepository {
    private final List<ProgramState> states;
    private final String             logFilePath;

    public Repository(ProgramState initialState, String logFilePath) {
        this.states      = new ArrayList<>();
        this.logFilePath = logFilePath;
        states.add(initialState);
    }

    public Repository(List<ProgramState> states, String logFilePath) {
        this.states      = new ArrayList<>();
        this.logFilePath = logFilePath;
        this.states.addAll(states);
    }

    @Override
    public void logProgramState(ProgramState state) throws MyException {
        try {
            var log = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            // TODO - standardize collection conversions to string
            log.println("---- LOG " + state.toString() + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            log.println("- Execution Stack:");
            log.println(state.getExecutionStack().toString());
            log.println("- Symbol Table:");
            log.println(state.getSymbolTable().toString());
            log.println("- Output List:");
            log.println(state.getOutputList().toString());
            log.println("- File Table:");
            log.println(state.getFileTable().getKeys()
                    .map(StringValue::toString)
                    .collect(Collectors.joining("\n")));
            log.println("- Heap:");
            log.println(state.getHeap().toString());
            log.println();
            log.println();
            log.flush();
            log.close();
        } catch (IOException inner) {
            throw new FileException(logFilePath, inner);
        }
    }

    @Override
    public List<ProgramState> getStates() {
        return states;
    }

    @Override
    public void setStates(List<ProgramState> states) {
        this.states.clear();
        this.states.addAll(states);
    }
}
