package api.repository;

import api.model.ProgramState;
import api.model.exceptions.FileException;
import api.model.exceptions.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            log.println("# LOG " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            // TODO - Pretty printing
            log.println("Execution Stack:");
            log.println(state.getExecutionStack().toString());
            log.println("Symbol Table:");
            log.println(state.getSymbolTable().toString());
            log.println("Output List:");
            log.println(state.getOutputList().toString());
            log.println("File Table:");
            // TODO - log.println(state.getFileTable().toString());
            log.println();
            log.flush();
        } catch (IOException inner) {
            throw new FileException(logFilePath, inner);
        }
    }
}
