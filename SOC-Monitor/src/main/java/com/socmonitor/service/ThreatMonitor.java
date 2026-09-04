package com.socmonitor.service;

import com.socmonitor.detector.*;
import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThreatMonitor {
    private final List<ThreatDetector> detectors = new ArrayList<>();
    private final AlertManager alertManager;

    public ThreatMonitor(AlertManager alertManager, String blacklistFilePath) {
        this.alertManager = alertManager;
        
        // Register all detectors
        detectors.add(new BruteForceDetector());
        detectors.add(new SQLInjectionDetector());
        detectors.add(new DoSDetector());
        detectors.add(new BlacklistDetector(blacklistFilePath));
    }

    public void processLog(LogEntry logEntry) {
        if (logEntry == null) return;

        for (ThreatDetector detector : detectors) {
            Optional<Alert> alertOpt = detector.analyze(logEntry);
            alertOpt.ifPresent(alertManager::addAlert);
        }
    }
}
