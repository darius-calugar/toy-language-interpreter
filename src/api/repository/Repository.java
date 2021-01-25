package api.repository;

import api.model.ProgramState;
import api.model.exceptions.FileException;
import api.model.exceptions.MyException;
import api.model.values.StringValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
            log.format("%2$tH:%2$tM:%2$tS.%2$tL  %2$td-%2$tb-%2$tY  %1$s\n", state.toString(), LocalDateTime.now());
            log.println("Execution Stack:");
            log.println(state.getExecutionStack().isEmpty() ? "\t<empty>" : state.getExecutionStack().getContent().stream()
                    .map(x -> "\t" + x.toString())
                    .collect(Collectors.joining("\n"))
            );
            log.println("Symbol Table:");
            log.println(state.getSymbolTable().isEmpty() ? "\t<empty>" : state.getSymbolTable().getContent().entrySet().stream()
                    .map(x -> "\t" + x.getKey() + " -> " + x.getValue())
                    .collect(Collectors.joining("\n"))
            );
            log.println("Heap:");
            log.println(state.getHeap().isEmpty() ? "\t<empty>" : state.getHeap().getContent().entrySet().stream()
                    .map(x -> "\t" + x.getKey() + " -> " + x.getValue())
                    .collect(Collectors.joining("\n"))
            );
            log.println("File Table:");
            log.println(state.getFileTable().isEmpty() ? "\t<empty>" : state.getFileTable().getKeys().stream()
                    .map(StringValue::toString)
                    .collect(Collectors.joining("\n"))
            );
            log.println("Output List:");
            log.println(state.getOutputList().isEmpty() ? "\t<empty>" : state.getOutputList().getContent().stream()
                    .map(x -> "\t" + x.toString())
                    .collect(Collectors.joining("\n"))
            );
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

    public static String generateLogPath(Date date) {
        return System.getProperty("user.dir") + "/logs/log_" +
               new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(date) +
               ".txt";
    }
}
