package org.example.writers;

import org.example.core.AppSettings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class BaseFileWriter {
    private final AppSettings settings;
    private BufferedWriter writer;

    protected BaseFileWriter(AppSettings settings) {
        this.settings = settings;
    }

    public boolean tryWriteLine(String value) {
        if (!checkRepresentedType(value)) return false;
        try {
            if (writer == null) writer = open();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.write(value + System.lineSeparator());
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
            throw new RuntimeException(e);
        }
    }

    private BufferedWriter open() throws IOException{
        Path fullPath = getFullOutputFilePath();
        Files.createDirectories(fullPath.getParent());

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
        return settings.outputFilesPath().resolve(settings.outputFilesPrefix() + getBaseFileName());
    }

    protected abstract boolean checkRepresentedType(String value);

    protected abstract String getBaseFileName();
}
