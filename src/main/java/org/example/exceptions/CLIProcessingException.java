package org.example.exceptions;

public class CLIProcessingException extends RuntimeException {
    public CLIProcessingException(String message) {
        String suffixMessage = "\nUse --help to see all options.";
        super(message + suffixMessage);
    }
}
