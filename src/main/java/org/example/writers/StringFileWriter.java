package org.example.writers;

import org.example.core.AppSettings;
import org.example.statistics.BaseStatistics;
import org.example.statistics.StringFullStatistics;
import org.example.statistics.SummaryStatistics;

public class StringFileWriter extends BaseTypedFileWriter {
    public static final String BASE_FILE_NAME = "strings.txt";

    public StringFileWriter(AppSettings settings) {
        String fileName = settings.outputFilesPrefix() + BASE_FILE_NAME;
        BaseStatistics statisticsService = switch (settings.statisticsLevel()) {
            case FULL -> new StringFullStatistics(fileName);
            case SUMMARY -> new SummaryStatistics(fileName);
            case NONE -> null;
        };
        super(settings, statisticsService);
    }

    @Override
    protected boolean checkRepresentedType(String value) {
        return true;
    }

    @Override
    protected String getBaseFileName() {
        return BASE_FILE_NAME;
    }
}
