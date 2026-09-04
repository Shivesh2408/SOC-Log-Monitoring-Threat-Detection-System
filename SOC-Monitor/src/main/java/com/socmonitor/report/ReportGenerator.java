package com.socmonitor.report;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.util.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    
    public static void generateReport(List<LogEntry> logs, List<Alert> alerts, String reportFilePath) {
        StringBuilder report = new StringBuilder();
        report.append("==================================================\n");
        report.append("          SOC INCIDENT REPORT SUMMARY\n");
        report.append("==================================================\n\n");

        report.append("1. GENERAL STATISTICS\n");
        report.append("--------------------------------------------------\n");
        report.append("Total Log Entries Processed : ").append(logs.size()).append("\n");
        report.append("Total Alerts Generated      : ").append(alerts.size()).append("\n\n");

        report.append("2. ALERT BREAKDOWN BY TYPE\n");
        report.append("--------------------------------------------------\n");
        Map<String, Long> alertCounts = alerts.stream()
                .collect(Collectors.groupingBy(Alert::getAlertType, Collectors.counting()));
        
        for (Map.Entry<String, Long> entry : alertCounts.entrySet()) {
            report.append(String.format("- %-25s : %d\n", entry.getKey(), entry.getValue()));
        }
        report.append("\n");

        report.append("3. MOST ACTIVE IPs\n");
        report.append("--------------------------------------------------\n");
        Map<String, Long> ipCounts = logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getIpAddress, Collectors.counting()));
        
        ipCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> report.append(String.format("- IP: %-15s : %d requests\n", e.getKey(), e.getValue())));
        report.append("\n");

        report.append("4. SECURITY SUMMARY\n");
        report.append("--------------------------------------------------\n");
        if (alerts.isEmpty()) {
            report.append("System is Secure. No threats detected.\n");
        } else {
            report.append("Multiple threats detected! Immediate investigation required.\n");
            long criticalAlerts = alerts.stream().filter(a -> a.getSeverity() == com.socmonitor.model.Severity.CRITICAL).count();
            if (criticalAlerts > 0) {
                report.append("CRITICAL WARNING: ").append(criticalAlerts).append(" critical alert(s) found.\n");
            }
        }
        report.append("\n==================================================\n");

        FileUtils.writeToFile(reportFilePath, report.toString());
        System.out.println("Incident report generated at: " + reportFilePath);
    }
}
