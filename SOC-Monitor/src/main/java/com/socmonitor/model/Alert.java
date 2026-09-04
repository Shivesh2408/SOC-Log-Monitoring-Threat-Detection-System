package com.socmonitor.model;

import java.util.UUID;

public class Alert {
    private String alertId;
    private String alertType;
    private Severity severity;
    private String timestamp;
    private String description;

    public Alert(String alertType, Severity severity, String timestamp, String description) {
        this.alertId = UUID.randomUUID().toString();
        this.alertType = alertType;
        this.severity = severity;
        this.timestamp = timestamp;
        this.description = description;
    }

    public String getAlertId() { return alertId; }
    public String getAlertType() { return alertType; }
    public Severity getSeverity() { return severity; }
    public String getTimestamp() { return timestamp; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("[%s] [%s] %s - %s (ID: %s)", timestamp, severity, alertType, description, alertId);
    }
}
