package org.example.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BigIntegerFullStatistics extends SummaryStatistics {
    private BigInteger min;
    private BigInteger max;
    private BigInteger sum = BigInteger.ZERO;

    public BigIntegerFullStatistics(String fileName) {
        super(fileName);
    }

    @Override
    public void updateStatistics(String value) {
        super.updateStatistics(value);

        BigInteger integerValue = new BigInteger(value);
        if (min == null || integerValue.compareTo(min) < 0)
            min = integerValue;
        if (max == null || integerValue.compareTo(max) > 0)
            max = integerValue;
        sum = sum.add(integerValue);
    }

    @Override
    protected String assembleStatisticsMessage() {
        BigDecimal avg = null;

        if (!lineCount.equals(BigInteger.ZERO)) {
            avg = new BigDecimal(sum).divide(new BigDecimal(lineCount), 100, RoundingMode.HALF_UP);
        }

        String additionalInfo = "min: %-20s max: %-20s sum: %-20s avg: %s".formatted(
                formatBigInteger(min),
                formatBigInteger(max),
                formatBigInteger(sum),
                formatBigDecimal(avg)
        );
        return super.assembleStatisticsMessage() + additionalInfo;
    }
}
