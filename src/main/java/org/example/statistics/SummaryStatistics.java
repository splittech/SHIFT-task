package org.example.statistics;

import java.math.BigInteger;

public class SummaryStatistics extends BaseStatistics {
    protected BigInteger lineCount = BigInteger.ZERO;
    private String fileName;

    public SummaryStatistics(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void updateStatistics(String value) {
        lineCount = lineCount.add(BigInteger.ONE);
    }

    @Override
    protected String assembleStatisticsMessage() {
        return "File: %-20s  count: %-5d".formatted(fileName, lineCount);
    }
}
