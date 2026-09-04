package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.model.Severity;
import com.socmonitor.util.FileUtils;

import java.util.Optional;
import java.util.Set;

public class BlacklistDetector implements ThreatDetector {
    private final Set<String> blacklistedIps;

    public BlacklistDetector(String blacklistFilePath) {
        this.blacklistedIps = FileUtils.loadBlacklist(blacklistFilePath);
    }

    @Override
    public Optional<Alert> analyze(LogEntry logEntry) {
        String ip = logEntry.getIpAddress();
        if (blacklistedIps.contains(ip)) {
            String description = String.format("Activity detected from blacklisted IP: %s", ip);
            return Optional.of(new Alert("Blacklisted IP Detected", Severity.MEDIUM, logEntry.getTimestamp(), description));
        }
        return Optional.empty();
    }
}
