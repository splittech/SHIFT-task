package org.example.writers;

import org.example.core.AppSettings;

import java.math.BigDecimal;

public class BigDecimalFileWriter extends BaseFileWriter {
    public static final String BASE_FILE_NAME = "floats.txt";

    public BigDecimalFileWriter(AppSettings settings) {
        super(settings);
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
