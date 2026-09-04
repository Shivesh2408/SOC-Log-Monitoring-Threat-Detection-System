package com.socmonitor.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtils {

    public static Set<String> loadBlacklist(String filePath) {
        Set<String> blacklist = new HashSet<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (!line.trim().isEmpty() && !line.startsWith("#")) {
                    blacklist.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load blacklist from " + filePath + ". File might not exist yet.");
        }
        return blacklist;
    }

    public static void appendLine(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
        }
    }

    public static void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
        }
    }

    public static void clearFile(String filePath) {
        try {
            new FileWriter(filePath, false).close();
        } catch (IOException e) {
            // Ignored
        }
    }
}
