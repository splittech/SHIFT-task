package org.example.statistics;

public class SummaryStatistics extends BaseStatistics {
    private final String fileName;
    protected int lineCount;

    public SummaryStatistics(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void updateStatistics(String value) {
        lineCount++;
    }

    @Override
    protected String assembleStatisticsMessage() {
        return String.format("File: %-20s  count: %-5d",
                fileName,
                lineCount
        );
    }
}
