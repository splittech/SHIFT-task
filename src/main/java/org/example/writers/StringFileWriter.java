package org.example.writers;

import org.example.core.AppSettings;

public class StringFileWriter extends BaseFileWriter {
    public static final String BASE_FILE_NAME = "strings.txt";

    public StringFileWriter(AppSettings settings) {
        super(settings);
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
