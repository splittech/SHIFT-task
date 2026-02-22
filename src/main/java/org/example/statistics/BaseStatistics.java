package org.example.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public abstract class BaseStatistics {
    public static final String STATISTICS_LOG_PREFIX = "[STAT]";
    public static final int MAX_FORMAT_LENGTH = 20;

    public void printStatistics() {
        System.out.println(STATISTICS_LOG_PREFIX + " " + assembleStatisticsMessage());
    }

    protected String formatBigInteger(BigInteger value) {
        if (value == null) {
            return "N/A";
        }
        return value.toString();
    }

    protected String formatBigDecimal(BigDecimal value) {
        if (value == null) {
            return "N/A";
        }
        return value.setScale(MAX_FORMAT_LENGTH, RoundingMode.HALF_UP).stripTrailingZeros().toString();
    }

    public abstract void updateStatistics(String value);
    protected abstract String assembleStatisticsMessage();
}
