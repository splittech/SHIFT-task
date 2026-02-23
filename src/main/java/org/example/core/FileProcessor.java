package org.example.core;

import org.example.writers.BaseTypedFileWriter;
import org.example.writers.BigDecimalFileWriter;
import org.example.writers.BigIntegerFileWriter;
import org.example.writers.StringFileWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileProcessor {
    private final AppSettings settings;

    public FileProcessor(AppSettings settings) {
        this.settings = settings;
    }

    public void processFiles(List<Path> files) {
        List<BaseTypedFileWriter> fileWriters = arrangeFileWriters();

        for (Path file : files) {
            try {
                processFile(file, fileWriters);
            }
            catch (IOException e) {
                System.err.printf("Failed to process file %s: %s%n",
                        file,
                        e.getMessage()
                );
            }
        }
        for (BaseTypedFileWriter fileWriter : fileWriters) {
            fileWriter.close();
        }
    }

    void processFile(Path file, List<BaseTypedFileWriter> fileWriters) throws IOException{
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String nextLine = reader.readLine();
            while (nextLine != null) {
                for (BaseTypedFileWriter fileWriter : fileWriters) {
                    if (fileWriter.tryWriteTypedString(nextLine)) break;
                }
                nextLine = reader.readLine();
            }
        }
    }

    private List<BaseTypedFileWriter> arrangeFileWriters() {
        return List.of(
                new BigIntegerFileWriter(settings),
                new BigDecimalFileWriter(settings),
                new StringFileWriter(settings)
        );
    }
}
