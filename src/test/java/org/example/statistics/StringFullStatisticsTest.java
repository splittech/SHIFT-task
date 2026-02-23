package org.example.statistics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class StringFullStatisticsTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void printStatistics() {
        BaseStatistics statisticsService = new StringFullStatistics("test.txt");
        String[] testValues = {"test", "1236657", "<ABRA>", "=-123s*&(^@!#!#"};
        String expectedString = "[STAT] File: test.txt              count: 4     min length: 4             max length: 15" + System.lineSeparator();

        for (String value : testValues) {
            statisticsService.updateStatistics(value);
        }
        statisticsService.printStatistics();
        Assertions.assertEquals(expectedString, outContent.toString());
    }
}