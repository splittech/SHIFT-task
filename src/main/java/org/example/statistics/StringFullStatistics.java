package org.example.statistics;

public class StringFullStatistics extends SummaryStatistics {
    int minLength = Integer.MAX_VALUE;
    int maxLength = Integer.MIN_VALUE;

    public StringFullStatistics(String fileName) {
        super(fileName);
    }

    @Override
    public void updateStatistics(String value) {
        super.updateStatistics(value);
        minLength = Integer.min(minLength, value.length());
        maxLength = Integer.max(maxLength, value.length());
    }

    @Override
    protected String assembleStatisticsMessage() {
        String additionalInfo = "min length: %-13d max length: %d".formatted(minLength, maxLength);
        return super.assembleStatisticsMessage() + additionalInfo;
    }
}
