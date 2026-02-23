package org.example.writers;

import org.example.core.AppSettings;
import org.example.statistics.BaseStatistics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class BaseTypedFileWriter {
    private final AppSettings settings;
    private final BaseStatistics statisticsService;
    private BufferedWriter writer;

    protected BaseTypedFileWriter(AppSettings settings, BaseStatistics statisticsService) {
        this.settings = settings;
        this.statisticsService = statisticsService;
    }

    public boolean tryWriteTypedString(String value) {
        if (!checkRepresentedType(value)) return false;
        try {
            if (writer == null) {
                writer = open();
            }
            writer.write(value + System.lineSeparator());
            if (statisticsService != null) {
                statisticsService.updateStatistics(value);
            }
        } catch (IOException e) {
            System.err.println("File writing error: " + e.getMessage());
            return false;
        }
        return true;
    }

    private BufferedWriter open() throws IOException {
        Path fullPath = getFullOutputFilePath();
        createNonExistingParentDirectories(fullPath);

        return switch (settings.fileWriteMode()) {
            case AppSettings.FileWriteMode.APPEND -> Files.newBufferedWriter(
                    fullPath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            case AppSettings.FileWriteMode.OVERRIDE -> Files.newBufferedWriter(
                    fullPath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        };
    }

    public void close() {
        if (writer == null) {
            return;
        }
        try {
            writer.flush();
            writer.close();
            writer = null;
        } catch (IOException e) {
            System.err.println("Failed to close writer stream: " + e.getMessage());
        }
        if (statisticsService != null) {
            statisticsService.printStatistics();
        }
    }

    private void createNonExistingParentDirectories(Path fullPath) throws IOException {
        Path parentDir = fullPath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
    }

    private Path getFullOutputFilePath() {
        return settings.outputFilesPath().resolve(getFileNameWithPrefix());
    }

    protected String getFileNameWithPrefix() {
        return settings.outputFilesPrefix() + getBaseFileName();
    }

    protected abstract boolean checkRepresentedType(String value);

    protected abstract String getBaseFileName();
}
