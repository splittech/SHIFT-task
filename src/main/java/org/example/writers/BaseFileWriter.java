package org.example.writers;

import org.example.core.AppSettings;
import org.example.exceptions.FileWriterException;
import org.example.statistics.BaseStatistics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class BaseFileWriter {
    private final AppSettings settings;
    private final BaseStatistics statisticsService;
    private BufferedWriter writer;

    protected BaseFileWriter(AppSettings settings, BaseStatistics statisticsService) {
        this.settings = settings;
        this.statisticsService = statisticsService;
    }

    public boolean tryWriteLine(String value) {
        if (!checkRepresentedType(value)) return false;
        try {
            if (writer == null) writer = open();
        }
        catch (IOException e) {
            throw new FileWriterException(e.getMessage());
        }
        try {
            writer.write(value + System.lineSeparator());
            if (statisticsService != null) {
                statisticsService.updateStatistics(value);
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public void close() {
        if (writer == null) return;
        try {
            writer.flush();
            writer.close();
            writer = null;
        } catch (IOException e) {
            throw new FileWriterException(e.getMessage());
        }
        if (statisticsService != null) {
            statisticsService.printStatistics();
        }
    }

    private BufferedWriter open() throws IOException{
        Path fullPath = getFullOutputFilePath();

        Path parentDir = fullPath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        return switch (settings.fileWriteMode()) {
            case AppSettings.FileWriteMode.APPEND ->
                Files.newBufferedWriter(fullPath,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);

            case AppSettings.FileWriteMode.OVERRIDE ->
                Files.newBufferedWriter(fullPath,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
        };
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
