package org.example;

import org.example.core.AppSettings;
import org.example.core.CLIReader;

import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    static void main(String[] args) {
        CLIReader cliReader = new CLIReader(args);
        AppSettings settings = cliReader.getAppSettings();
        ArrayList<Path> files = cliReader.getFiles();
    }
}
