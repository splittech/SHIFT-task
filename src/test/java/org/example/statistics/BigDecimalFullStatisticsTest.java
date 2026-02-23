package org.example.statistics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class BigDecimalFullStatisticsTest {
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
        BaseStatistics statisticsService = new BigDecimalFullStatistics("test.txt");
        String[] testValues = {"12323546", "-123904732894572", "0.213723904573456", "-0.43058743985789"};
        String expectedString = "[STAT] File: test.txt              count: 4     min: -123904732894572" +
                "     max: 12323546             sum: -123904720571026.216863535284434 " +
                "avg: -30976180142756.5542158838211085" + System.lineSeparator();

        for (String value : testValues) {
            statisticsService.updateStatistics(value);
        }
        statisticsService.printStatistics();
        Assertions.assertEquals(expectedString, outContent.toString());
    }
}