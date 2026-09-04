package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import java.util.Optional;

public interface ThreatDetector {
    Optional<Alert> analyze(LogEntry logEntry);
}
