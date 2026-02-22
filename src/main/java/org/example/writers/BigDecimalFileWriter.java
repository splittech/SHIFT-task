package org.example.writers;

import org.example.core.AppSettings;
import org.example.statistics.BaseStatistics;
import org.example.statistics.BigDecimalFullStatistics;
import org.example.statistics.SummaryStatistics;

import java.math.BigDecimal;

public class BigDecimalFileWriter extends BaseFileWriter {
    public static final String BASE_FILE_NAME = "floats.txt";

    public BigDecimalFileWriter(AppSettings settings) {
        String fileName = settings.outputFilesPrefix() + BASE_FILE_NAME;
        BaseStatistics statisticsService = switch (settings.statisticsLevel()){
            case FULL -> new BigDecimalFullStatistics(fileName);
            case SUMMARY -> new SummaryStatistics(fileName);
            case NONE -> null;
        };
        super(settings, statisticsService);
    }

    @Override
    protected boolean checkRepresentedType(String value) {
        try {
            new BigDecimal(value);
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
