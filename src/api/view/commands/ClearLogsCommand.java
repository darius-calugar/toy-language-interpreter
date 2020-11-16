package api.view.commands;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 Command that clears all logs matching a cer
 */
public class ClearLogsCommand extends Command {
    private final String logDirectoryPath;
    private final String logFileFormat;

    public ClearLogsCommand(String key, String description, String logDirectoryPath, String logFileFormat) {
        super(key, description);
        this.logDirectoryPath = logDirectoryPath;
        this.logFileFormat    = logFileFormat;
    }

    @Override
    public void execute() {
        var files = new File(logDirectoryPath).listFiles();
        if (files != null) {
            System.out.println(Arrays.stream(files)
                    .filter(File::isFile)
                    .filter(file -> file.getPath().matches(logFileFormat))
                    .filter(File::delete)
                    .map(File::getPath)
                    .collect(Collectors.joining("\n  ", "Deleted logs:\n  ", "")));
        } else
            System.out.println("Could not find dir");
    }
}
