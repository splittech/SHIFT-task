package org.example.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BigDecimalFullStatistics extends SummaryStatistics {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal sum = BigDecimal.ZERO;

    public BigDecimalFullStatistics(String fileName) {
        super(fileName);
    }

    @Override
    public void updateStatistics(String value) {
        super.updateStatistics(value);

        BigDecimal decimalValue = new BigDecimal(value);
        if (min == null || decimalValue.compareTo(min) < 0)
            min = decimalValue;
        if (max == null || decimalValue.compareTo(max) > 0)
            max = decimalValue;
        sum = sum.add(decimalValue);
    }

    @Override
    protected String assembleStatisticsMessage() {
        BigDecimal avg = null;

        if (!lineCount.equals(BigInteger.ZERO)) {
            avg = sum.divide(new BigDecimal(lineCount), 100, RoundingMode.HALF_UP);
        }

        String additionalInfo = "min: %-20s max: %-20s sum: %-20s avg: %s".formatted(
                formatBigDecimal(min),
                formatBigDecimal(max),
                formatBigDecimal(sum),
                formatBigDecimal(avg)
        );
        return super.assembleStatisticsMessage() + additionalInfo;
    }
}
