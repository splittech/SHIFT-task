package org.example.core;

import org.example.writers.BigDecimalFileWriter;
import org.example.writers.BigIntegerFileWriter;
import org.example.writers.StringFileWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {
    public final String FIRST_TEST_FILE_NAME = "in1.txt";
    public final String SECOND_TEST_FILE_NAME = "in2.txt";

    Path firstFile = Path.of(FIRST_TEST_FILE_NAME);
    Path secondFile = Path.of(SECOND_TEST_FILE_NAME);
    Path outputFloatFile = Path.of(BigDecimalFileWriter.BASE_FILE_NAME);
    Path outputIntegerFile = Path.of(BigIntegerFileWriter.BASE_FILE_NAME);
    Path outputStringFile = Path.of(StringFileWriter.BASE_FILE_NAME);

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(firstFile);
        Files.deleteIfExists(secondFile);
        Files.deleteIfExists(outputFloatFile);
        Files.deleteIfExists(outputIntegerFile);
        Files.deleteIfExists(outputStringFile);
    }

    @Test
    void processFile_twoFilesAllTypes() throws IOException {
        String[] firstInputFileLines = {"Lorem ipsum dolor sit amet", "45", "Пример", "3.1415",
                "consectetur adipiscing", "-0.001", "тестовое задание", "100500"};
        String[] secondInputFileLines = {"Нормальная форма числа с плавающей запятой",
                "1.528535047E-25", "Long", "1234567890123456789"};

        String[] expectedFloatFileLines = {"3.1415", "-0.001", "1.528535047E-25"};
        String[] expectedIntegerFileLines = {"45", "100500", "1234567890123456789"};
        String[] expectedStringFileLines = {"Lorem ipsum dolor sit amet", "Пример", "consectetur adipiscing",
                "тестовое задание","Нормальная форма числа с плавающей запятой", "Long"};

        createTestFile(firstFile, firstInputFileLines);
        createTestFile(secondFile, secondInputFileLines);
        FileProcessor fileProcessor = new FileProcessor(AppSettings.defaultSettings());
        ArrayList<Path> files = new ArrayList<>();
        files.add(firstFile);
        files.add(secondFile);

        fileProcessor.processFiles(files);

        assertTrue(Files.exists(outputFloatFile));
        assertTrue(Files.exists(outputIntegerFile));
        assertTrue(Files.exists(outputStringFile));

        String[] outputFloatFileLines = readAllFileLinesAsArray(outputFloatFile);
        String[] outputIntegerFileLines = readAllFileLinesAsArray(outputIntegerFile);
        String[] outputStringFileLines = readAllFileLinesAsArray(outputStringFile);

        Assertions.assertArrayEquals(expectedFloatFileLines, outputFloatFileLines);
        Assertions.assertArrayEquals(expectedIntegerFileLines, outputIntegerFileLines);
        Assertions.assertArrayEquals(expectedStringFileLines, outputStringFileLines);
    }

    String[] readAllFileLinesAsArray(Path file) {
        List<String> lines;
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)){
            lines = reader.readAllLines();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return lines.toArray(new String[0]);
    }

    void createTestFile(Path file, String[] lines) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        }
    }
}