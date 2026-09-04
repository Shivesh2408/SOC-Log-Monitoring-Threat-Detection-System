package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.model.Severity;

import java.util.List;
import java.util.Optional;

public class SQLInjectionDetector implements ThreatDetector {
    private static final List<String> SQLI_KEYWORDS = List.of(
            "SELECT", "DROP", "DELETE", "UNION", "OR 1=1", "--"
    );

    @Override
    public Optional<Alert> analyze(LogEntry logEntry) {
        if (logEntry.getQuery() != null) {
            String queryUpper = logEntry.getQuery().toUpperCase();
            for (String keyword : SQLI_KEYWORDS) {
                if (queryUpper.contains(keyword)) {
                    String description = String.format("Suspicious SQL keyword '%s' found in query from IP: %s", keyword, logEntry.getIpAddress());
                    return Optional.of(new Alert("SQL Injection Attempt", Severity.CRITICAL, logEntry.getTimestamp(), description));
                }
            }
        }
        return Optional.empty();
    }
}
