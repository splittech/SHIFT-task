package org.example.statistics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class BigIntegerFullStatisticsTest {
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
        BaseStatistics statisticsService = new BigIntegerFullStatistics("test.txt");
        String[] testValues = {"546547658768980", "-239434875634258", "1234", "0"};
        String expectedString = "[STAT] File: test.txt              count: 4    min: -239434875634258     max: 546547658768980      sum: 307112783135956      avg: 76778195783989" + System.lineSeparator();

        for (String value : testValues) {
            statisticsService.updateStatistics(value);
        }
        statisticsService.printStatistics();
        Assertions.assertEquals(expectedString, outContent.toString());
    }
}