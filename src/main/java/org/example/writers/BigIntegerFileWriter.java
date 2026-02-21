package org.example.writers;

import org.example.core.AppSettings;

import java.math.BigInteger;

public class BigIntegerFileWriter extends BaseFileWriter {
    public static final String BASE_FILE_NAME = "integers.txt";

    public BigIntegerFileWriter(AppSettings settings) {
        super(settings);
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
