package org.example.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class AppSettingsBuilderTest {
    private AppSettings.Builder settingsBuilder;

    @BeforeEach
    public void setUp() {
        settingsBuilder = new AppSettings.Builder();
    }

    @Test
    public void outputFilesPath() {
        Path filePath = Path.of("/some/test/path");

        settingsBuilder.outputFilesPath(filePath);
        AppSettings settings = settingsBuilder.build();

        Assertions.assertEquals(filePath, settings.outputFilesPath());
        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PREFIX, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.DEFAULT_FILE_WRITE_MODE, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.DEFAULT_STATISTICS_LEVEL, settings.statisticsLevel());
    }

    @Test
    public void outputFilesPrefix() {
        String filePrefix = "some-prefix_";

        settingsBuilder.outputFilesPrefix(filePrefix);
        AppSettings settings = settingsBuilder.build();

        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PATH, settings.outputFilesPath());
        Assertions.assertEquals(filePrefix, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.DEFAULT_FILE_WRITE_MODE, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.DEFAULT_STATISTICS_LEVEL, settings.statisticsLevel());
    }

    @Test
    public void fileWriteMode() {
        AppSettings.FileWriteMode fileWriteMode = AppSettings.FileWriteMode.APPEND;

        settingsBuilder.fileWriteMode(fileWriteMode);
        AppSettings settings = settingsBuilder.build();

        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PATH, settings.outputFilesPath());
        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PREFIX, settings.outputFilesPrefix());
        Assertions.assertEquals(fileWriteMode, settings.fileWriteMode());
        Assertions.assertEquals(AppSettings.DEFAULT_STATISTICS_LEVEL, settings.statisticsLevel());
    }

    @Test
    public void statisticsLevel() {
        AppSettings.StatisticsLevel statisticsLevel = AppSettings.StatisticsLevel.FULL;

        settingsBuilder.statisticsLevel(statisticsLevel);
        AppSettings settings = settingsBuilder.build();

        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PATH, settings.outputFilesPath());
        Assertions.assertEquals(AppSettings.DEFAULT_OUTPUT_FILES_PREFIX, settings.outputFilesPrefix());
        Assertions.assertEquals(AppSettings.DEFAULT_FILE_WRITE_MODE, settings.fileWriteMode());
        Assertions.assertEquals(statisticsLevel, settings.statisticsLevel());
    }
}