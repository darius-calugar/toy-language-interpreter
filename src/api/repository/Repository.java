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
import java.util.stream.Collectors;

/**
 Implementation of the program repository.
 */
public class Repository implements IRepository {
    private final ProgramState state;
    private final String       logFilePath;

    public Repository(ProgramState state, String logFilePath) {
        this.state       = state;
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState currentProgramState() {
        return state;
    }

    @Override
    public void logCurrentProgramState() throws MyException {
        try {
            var log = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            // TODO - standardize collection conversions to string
            log.println("---- LOG " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
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
            log.println();
            log.println();
            log.flush();
            log.close();
        } catch (IOException inner) {
            throw new FileException(logFilePath, inner);
        }
    }
}
