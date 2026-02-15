package org.example.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class CLIReaderTest {

    public static final String FIRST_FILE_NAME = "in1.txt";
    public static final String SECOND_FILE_NAME = "in2.txt";

    @BeforeEach
    void setUp() throws IOException {
        Path firstInputFile = Path.of(FIRST_FILE_NAME);
        Path secondInputFile = Path.of(SECOND_FILE_NAME);

        Files.deleteIfExists(firstInputFile);
        Files.deleteIfExists(secondInputFile);
        Files.createFile(firstInputFile);
        Files.createFile(secondInputFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FIRST_FILE_NAME));
        Files.deleteIfExists(Path.of(SECOND_FILE_NAME));
    }

    @Test
    void constructor_withHelp() {
        String[] args = {"-h"};

        CLIReader cliReader = new CLIReader(args);
    }

    @Test
    void getAppSettings_withCustomPath() {
        String testPath = "/some/test/path";
        String[] args = {"-o", testPath, FIRST_FILE_NAME};

        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();

        Assertions.assertEquals(Path.of(testPath), settings.outputFilesPath());
        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PREFIX, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.DEFAULT_FILE_WRITE_MODE, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.DEFAULT_STATISTICS_LEVEL, settings.statisticsLevel());
    }

    @Test
    void getAppSettings_withFilesPrefix() {
        String testPrefix = "test-prefix_";
        String[] args = {"-p", testPrefix, FIRST_FILE_NAME};

        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();

        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PATH, settings.outputFilesPath());
        Assertions.assertEquals(testPrefix, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.DEFAULT_FILE_WRITE_MODE, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.DEFAULT_STATISTICS_LEVEL, settings.statisticsLevel());
    }

    @Test
    void getAppSettings_withAppendMode() {
        String[] args = {"-a", FIRST_FILE_NAME};

        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();

        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PATH, settings.outputFilesPath());
        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PREFIX, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.FileWriteMode.APPEND, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.DEFAULT_STATISTICS_LEVEL, settings.statisticsLevel());
    }

    @Test
    void getAppSettings_withSummaryStatistics() {
        String[] args = {"-s", FIRST_FILE_NAME};

        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();

        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PATH, settings.outputFilesPath());
        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PREFIX, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.DEFAULT_FILE_WRITE_MODE, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.StatisticsLevel.SUMMARY, settings.statisticsLevel());
    }

    @Test
    void getAppSettings_withFullStatistics() {
        String[] args = {"-f", FIRST_FILE_NAME};

        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();

        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PATH, settings.outputFilesPath());
        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PREFIX, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.DEFAULT_FILE_WRITE_MODE, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.StatisticsLevel.FULL, settings.statisticsLevel());
    }

    @Test
    void getAppSettings_withAllParameters() {
        String testPath = "/some/test/path";
        String testPrefix = "test-prefix_";

        String[] args = {"-o", testPath, "-p", testPrefix, "-a", "-f", FIRST_FILE_NAME};

        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();

        Assertions.assertEquals(Path.of(testPath), settings.outputFilesPath());
        Assertions.assertEquals(testPrefix, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.FileWriteMode.APPEND, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.StatisticsLevel.FULL, settings.statisticsLevel());
    }


    @Test
    void getFiles() {
        String[] args = {FIRST_FILE_NAME, SECOND_FILE_NAME};

        CLIReader cliReader = new CLIReader(args);
        ArrayList<Path> files = cliReader.getFiles();

        Assertions.assertEquals(2, files.size());
        Assertions.assertEquals(Path.of(FIRST_FILE_NAME), files.get(0));
        Assertions.assertEquals(Path.of(SECOND_FILE_NAME), files.get(1));
    }
}