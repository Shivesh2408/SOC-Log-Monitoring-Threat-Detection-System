package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.model.Severity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DoSDetector implements ThreatDetector {
    private static final int THRESHOLD = 10; // Reduced for simulation purposes (e.g., 100 in real world)
    private final Map<String, Integer> requestCounts = new HashMap<>();

    @Override
    public Optional<Alert> analyze(LogEntry logEntry) {
        String ip = logEntry.getIpAddress();
        requestCounts.put(ip, requestCounts.getOrDefault(ip, 0) + 1);

        if (requestCounts.get(ip) >= THRESHOLD) {
            int count = requestCounts.get(ip);
            requestCounts.put(ip, 0); // Reset after alerting

            String description = String.format("Possible DoS Attack! Excessive requests (%d) from IP: %s", count, ip);
            return Optional.of(new Alert("Possible DoS Attack", Severity.HIGH, logEntry.getTimestamp(), description));
        }

        return Optional.empty();
    }
}
