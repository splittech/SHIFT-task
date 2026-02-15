package org.example.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class AppSettingsTest {

    @Test
    public void constructor_WithAllArgs() {
        Path path = Path.of("some/test/path");
        String prefix = "test_prefix_";
        AppSettings.FileWriteMode writeMode = AppSettings.FileWriteMode.APPEND;
        AppSettings.StatisticsLevel statisticsLevel = AppSettings.StatisticsLevel.FULL;

        AppSettings settings = new AppSettings(path, prefix, writeMode, statisticsLevel);

        Assertions.assertEquals(path, settings.outputFilesPath());
        Assertions.assertEquals(prefix, settings.outputFilesPrefix());
        Assertions.assertEquals(writeMode, settings.fileWriteMode());
        Assertions.assertEquals(statisticsLevel, settings.statisticsLevel());
    }
}