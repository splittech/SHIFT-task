package org.example.statistics;

public class StringFullStatistics extends SummaryStatistics {
    private int minLength;
    private int maxLength;

    public StringFullStatistics(String fileName) {
        super(fileName);
        minLength = Integer.MAX_VALUE;
        maxLength = Integer.MIN_VALUE;
    }

    @Override
    public void updateStatistics(String value) {
        super.updateStatistics(value);
        minLength = Integer.min(minLength, value.length());
        maxLength = Integer.max(maxLength, value.length());
    }

    @Override
    protected String assembleStatisticsMessage() {
        return String.format("%s min length: %-13s max length: %s",
                super.assembleStatisticsMessage(),
                formatStringLength(minLength),
                formatStringLength(maxLength)
        );
    }

    private String formatStringLength(int stringLength) {
        if (lineCount == 0) {
            return "NaN";
        }
        return String.valueOf(stringLength);
    }
}
