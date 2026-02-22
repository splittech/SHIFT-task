package org.example.writers;

import org.example.core.AppSettings;
import org.example.statistics.BaseStatistics;
import org.example.statistics.BigIntegerFullStatistics;
import org.example.statistics.SummaryStatistics;

import java.math.BigInteger;

public class BigIntegerFileWriter extends BaseFileWriter {
    public static final String BASE_FILE_NAME = "integers.txt";

    public BigIntegerFileWriter(AppSettings settings) {
        String fileName = settings.outputFilesPrefix() + BASE_FILE_NAME;
        BaseStatistics statisticsService = switch (settings.statisticsLevel()){
            case FULL -> new BigIntegerFullStatistics(fileName);
            case SUMMARY -> new SummaryStatistics(fileName);
            case NONE -> null;
        };
        super(settings, statisticsService);
    }

    @Override
    protected boolean checkRepresentedType(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getBaseFileName() {
        return BASE_FILE_NAME;
    }
}
