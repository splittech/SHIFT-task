package org.example.writers;

import org.example.core.AppSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class BigIntegerWriterTest {
    @Test
    void write_manyIntegerValues() throws IOException {
        String[] testValues = {"45", "100500", "1234567890123456789"};
        BaseTypedFileWriter writer = new StringFileWriter(AppSettings.defaultSettings());
        Path createdFile = Path.of(writer.getBaseFileName());
        Files.deleteIfExists(createdFile);

        for (String testValue : testValues) {
            writer.tryWriteTypedString(testValue);
        }
        writer.close();
        Assertions.assertTrue(Files.exists(createdFile));

        List<String> fileValuesList = Files.readAllLines(createdFile);
        String[] fileValues = fileValuesList.toArray(new String[0]);
        Assertions.assertArrayEquals(testValues, fileValues);
        Files.deleteIfExists(createdFile);
    }
}