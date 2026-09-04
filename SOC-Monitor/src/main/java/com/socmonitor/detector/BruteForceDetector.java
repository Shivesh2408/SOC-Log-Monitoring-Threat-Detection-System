package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.model.Severity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BruteForceDetector implements ThreatDetector {
    private static final int THRESHOLD = 5;
    private final Map<String, Integer> failedAttempts = new HashMap<>();

    @Override
    public Optional<Alert> analyze(LogEntry logEntry) {
        if ("LOGIN_FAILED".equals(logEntry.getEventType())) {
            String ip = logEntry.getIpAddress();
            failedAttempts.put(ip, failedAttempts.getOrDefault(ip, 0) + 1);

            if (failedAttempts.get(ip) >= THRESHOLD) {
                // Reset to avoid spamming alerts for every subsequent failure, or keep it depending on requirements.
                // For this simulation, let's reset it so we alert again if 5 more happen.
                int attempts = failedAttempts.get(ip);
                failedAttempts.put(ip, 0); 
                
                String description = String.format("Multiple failed login attempts (%d) detected from IP: %s", attempts, ip);
                return Optional.of(new Alert("Brute Force Attack", Severity.HIGH, logEntry.getTimestamp(), description));
            }
        }
        return Optional.empty();
    }
}
