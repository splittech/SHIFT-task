package org.example.core;

import org.example.writers.BaseFileWriter;
import org.example.writers.BigDecimalFileWriter;
import org.example.writers.BigIntegerFileWriter;
import org.example.writers.StringFileWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileProcessor {
    private final AppSettings settings;

    public FileProcessor(AppSettings settings) {
        this.settings = settings;
    }

    public void processFiles(ArrayList<Path> files) {
        ArrayList<BaseFileWriter> fileWriters = arrangeFileWriters();

        for (Path file : files) {
            processFile(file, fileWriters);
        }
        for (BaseFileWriter fileWriter : fileWriters) {
            fileWriter.close();
        }
    }

    void processFile(Path file, ArrayList<BaseFileWriter> fileWriters) {
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String nextLine = reader.readLine();
            while (nextLine != null) {
                for (BaseFileWriter fileWriter : fileWriters) {
                    if (fileWriter.tryWriteLine(nextLine)) break;
                }
                nextLine = reader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<BaseFileWriter> arrangeFileWriters() {
        ArrayList<BaseFileWriter> fileWriters = new ArrayList<>();
        fileWriters.add(new BigIntegerFileWriter(settings));
        fileWriters.add(new BigDecimalFileWriter(settings));
        fileWriters.add(new StringFileWriter(settings));
        return fileWriters;
    }
}
