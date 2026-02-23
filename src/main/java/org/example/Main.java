package org.example;

import org.example.core.AppSettings;
import org.example.core.CLIReader;
import org.example.core.FileProcessor;

import java.nio.file.Path;
import java.util.List;

public class Main {
    static void main(String[] args) {
        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();
        List<Path> files = cliReader.getFiles();

        FileProcessor fileProcessor = new FileProcessor(settings);
        fileProcessor.processFiles(files);
    }
}
