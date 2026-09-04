package com.socmonitor;

import com.socmonitor.model.LogEntry;
import com.socmonitor.report.ReportGenerator;
import com.socmonitor.service.AlertManager;
import com.socmonitor.service.LogParser;
import com.socmonitor.service.ThreatMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String LOG_FILE = "src/main/resources/logs.txt";
    private static final String BLACKLIST_FILE = "src/main/resources/blacklist.txt";
    private static final String ALERT_FILE = "src/main/resources/alerts.txt";
    private static final String REPORT_FILE = "incident_report.txt";

    public static void main(String[] args) {
        System.out.println("Starting SOC Log Monitoring System...");

        AlertManager alertManager = new AlertManager(ALERT_FILE);
        ThreatMonitor monitor = new ThreatMonitor(alertManager, BLACKLIST_FILE);
        List<LogEntry> allLogs = new ArrayList<>();

        try {
            List<String> logLines = Files.readAllLines(Paths.get(LOG_FILE));
            
            for (String line : logLines) {
                LogEntry entry = LogParser.parse(line);
                if (entry != null) {
                    allLogs.add(entry);
                    monitor.processLog(entry);
                }
            }
            
            System.out.println("Log processing complete. Generating report...");
            ReportGenerator.generateReport(allLogs, alertManager.getAlerts(), REPORT_FILE);

        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }
}
