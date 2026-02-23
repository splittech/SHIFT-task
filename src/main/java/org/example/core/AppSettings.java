package org.example.core;

import java.nio.file.Path;



public record AppSettings(
    Path outputFilesPath,
    String outputFilesPrefix,
    FileWriteMode fileWriteMode,
    StatisticsLevel statisticsLevel
) {
    public static final Path DEFAULT_OUTPUT_FILES_PATH = Path.of("");
    public static final String DEFAULT_OUTPUT_FILES_PREFIX = "";
    public static final FileWriteMode DEFAULT_FILE_WRITE_MODE = FileWriteMode.OVERRIDE;
    public static final StatisticsLevel DEFAULT_STATISTICS_LEVEL = StatisticsLevel.NONE;

    public enum FileWriteMode {
        OVERRIDE,
        APPEND
    }

    public enum StatisticsLevel {
        NONE,
        SUMMARY,
        FULL
    }

    public static AppSettings defaultSettings() {
        return new AppSettings(
                AppSettings.DEFAULT_OUTPUT_FILES_PATH,
                AppSettings.DEFAULT_OUTPUT_FILES_PREFIX,
                AppSettings.DEFAULT_FILE_WRITE_MODE,
                AppSettings.DEFAULT_STATISTICS_LEVEL
        );
    }

    public static class Builder {
        private Path outputFilesPath = DEFAULT_OUTPUT_FILES_PATH;
        private String outputFilesPrefix = DEFAULT_OUTPUT_FILES_PREFIX;
        private FileWriteMode fileWriteMode = DEFAULT_FILE_WRITE_MODE;
        private StatisticsLevel statisticsLevel = DEFAULT_STATISTICS_LEVEL;

        public void outputFilesPath(Path path) {
            this.outputFilesPath = path;
        }

        public void outputFilesPrefix(String prefix) {
            this.outputFilesPrefix = prefix;
        }

        public void fileWriteMode(FileWriteMode mode) {
            this.fileWriteMode = mode;
        }

        public void statisticsLevel(StatisticsLevel level) {
            this.statisticsLevel = level;
        }

        public AppSettings build() {
            return new AppSettings(outputFilesPath, outputFilesPrefix, fileWriteMode, statisticsLevel);
        }
    }
}
