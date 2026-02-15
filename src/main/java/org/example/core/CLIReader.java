package org.example.core;

import org.apache.commons.cli.*;
import org.example.exceptions.CLIProcessingException;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class CLIReader {
    public static final String APP_NAME = "uXl";

    public static final Option ARG_HELP = Option.builder()
            .option("h")
            .longOpt("help")
            .hasArg(false)
            .desc("All usage information.")
            .build();

    public static final Option ARG_OUTPUT_PATH = Option.builder()
            .option("o")
            .longOpt("output")
            .hasArg(true)
            .desc("Output files path.")
            .build();

    public static final Option ARG_OUTPUT_PREFIX = Option.builder()
            .option("p")
            .longOpt("prefix")
            .hasArg(true)
            .desc("Output files prefix.")
            .build();

    public static final Option ARG_APPEND_MODE = Option.builder()
            .option("a")
            .longOpt("append")
            .hasArg(false)
            .desc("Enables append write mode.")
            .build();

    public static final Option ARG_STATISTICS_SUMMARY = Option.builder()
            .option("s")
            .longOpt("summary")
            .hasArg(false)
            .desc("Show summary statistics.")
            .build();

    public static final Option ARG_STATISTICS_FULL = Option.builder()
            .option("f")
            .longOpt("full")
            .hasArg(false)
            .desc("Show full statistics.")
            .build();

    private final AppSettings appSettings;
    private final ArrayList<Path> files;

    public CLIReader(String[] args) {
        Options options = createOptions();
        CommandLine commandLine = setUpCommandLine(args, options);

        appSettings = readSettings(commandLine, options);
        files = readFiles(commandLine);
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption(ARG_HELP);
        options.addOption(ARG_OUTPUT_PATH);
        options.addOption(ARG_OUTPUT_PREFIX);
        options.addOption(ARG_APPEND_MODE);
        options.addOption(ARG_STATISTICS_SUMMARY);
        options.addOption(ARG_STATISTICS_FULL);
        return options;
    }

    private CommandLine setUpCommandLine(String[] args, Options options) {
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            return  commandLineParser.parse(options, args);
        }
        catch (ParseException e) {
            throw new CLIProcessingException(e.getMessage());
        }
    }

    private AppSettings readSettings(CommandLine commandLine, Options options) {
        AppSettings.Builder settingsBuilder = new AppSettings.Builder();

        if (commandLine.hasOption(ARG_HELP.getLongOpt())) {
            printHelp(options);
        }
        if(commandLine.hasOption(ARG_OUTPUT_PATH.getLongOpt())) {
            Path path = Path.of(commandLine.getOptionValue(ARG_OUTPUT_PATH));
            settingsBuilder.outputFilesPath(path);
        }
        if(commandLine.hasOption(ARG_OUTPUT_PREFIX.getLongOpt())) {
            String prefix = commandLine.getOptionValue(ARG_OUTPUT_PREFIX);
            settingsBuilder.outputFilesPrefix(prefix);
        }
        if(commandLine.hasOption(ARG_APPEND_MODE.getLongOpt())) {
            settingsBuilder.fileWriteMode(AppSettings.FileWriteMode.APPEND);
        }

        if(commandLine.hasOption(ARG_STATISTICS_SUMMARY.getLongOpt()) &&
           commandLine.hasOption(ARG_STATISTICS_FULL.getLongOpt())) {
            throw new CLIProcessingException("full and summary statistics cannot be enabled simultaneously.");
        } else if(commandLine.hasOption(ARG_STATISTICS_SUMMARY.getLongOpt())) {
            settingsBuilder.statisticsLevel(AppSettings.StatisticsLevel.SUMMARY);
        } else if(commandLine.hasOption(ARG_STATISTICS_FULL.getLongOpt())) {
            settingsBuilder.statisticsLevel(AppSettings.StatisticsLevel.FULL);
        }

        return settingsBuilder.build();
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);

        String appUsage = "java -jar " + APP_NAME + ".jar [options] [files]";

        printWriter.println();
        formatter.printUsage(printWriter, 100, appUsage);
        formatter.printOptions(printWriter, 100, options, 2, 5);

        printWriter.flush();
    }

    private ArrayList<Path> readFiles(CommandLine commandLine) {
        if (commandLine.hasOption(ARG_HELP.getLongOpt())) {
            return new ArrayList<>();
        }

        if (commandLine.getArgList().isEmpty()) {
            throw new CLIProcessingException("no files provided.");
        }

        ArrayList<Path> files = new ArrayList<>();
        ArrayList<String> invalidFiles = new ArrayList<>();

        for(String arg : commandLine.getArgList()) {
            Path file = Path.of(arg);
            if (Files.notExists(file)) {
                invalidFiles.add(arg + " (not found)");
                continue;
            }
            if (!Files.isRegularFile(file)) {
                invalidFiles.add(arg + " (not a regular file)");
                continue;
            }
            files.add(file);
        }

        if (!invalidFiles.isEmpty()) {
            throw new CLIProcessingException(String.join(", ", invalidFiles) + ".");
        }

        return files;
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public ArrayList<Path> getFiles() {
        return files;
    }
}
