package com.socmonitor.service;

import com.socmonitor.model.Alert;
import com.socmonitor.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class AlertManager {
    private final List<Alert> alerts = new ArrayList<>();
    private final String alertFilePath;

    public AlertManager(String alertFilePath) {
        this.alertFilePath = alertFilePath;
        FileUtils.clearFile(alertFilePath); // Clear old alerts on startup
    }

    public void addAlert(Alert alert) {
        alerts.add(alert);
        System.out.println("🚨 ALERT GENERATED: " + alert.toString());
        FileUtils.appendLine(alertFilePath, alert.toString());
    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}
