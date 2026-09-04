package com.socmonitor.model;

public class LogEntry {
    private String timestamp;
    private String eventType;
    private String username;
    private String ipAddress;
    private String query;

    public LogEntry(String timestamp, String eventType, String username, String ipAddress, String query) {
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.username = username;
        this.ipAddress = ipAddress;
        this.query = query;
    }

    public String getTimestamp() { return timestamp; }
    public String getEventType() { return eventType; }
    public String getUsername() { return username; }
    public String getIpAddress() { return ipAddress; }
    public String getQuery() { return query; }

    @Override
    public String toString() {
        return "LogEntry{" +
                "timestamp='" + timestamp + '\'' +
                ", eventType='" + eventType + '\'' +
                ", username='" + username + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                (query != null ? ", query='" + query + '\'' : "") +
                '}';
    }
}
