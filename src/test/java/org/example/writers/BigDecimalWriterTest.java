package org.example.writers;

import org.example.core.AppSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class BigDecimalWriterTest {
    @Test
    void write_manyFloatValues() throws IOException {
        String[] testValues = {"3.1415", "-0.001", "1.528535047E-25"};
        BaseFileWriter writer = new BigDecimalFileWriter(AppSettings.defaultSettings());
        Path createdFile = Path.of(writer.getBaseFileName());
        Files.deleteIfExists(createdFile);

        for (String testValue : testValues) {
            writer.tryWriteLine(testValue);
        }
        writer.close();
        Assertions.assertTrue(Files.exists(createdFile));

        List<String> fileValuesList = Files.readAllLines(createdFile);
        String[] fileValues = fileValuesList.toArray(new String[0]);
        Assertions.assertArrayEquals(testValues, fileValues);
        Files.deleteIfExists(createdFile);
    }
}