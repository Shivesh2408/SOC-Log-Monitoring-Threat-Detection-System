package com.socmonitor.service;

import com.socmonitor.model.LogEntry;

public class LogParser {
    
    // Sample Log:
    // 2026-09-01 10:00:10 LOGIN_SUCCESS user=admin ip=192.168.1.5
    // 2026-09-01 10:02:20 SQL_QUERY user=test ip=192.168.1.10 query="DROP TABLE users"
    
    public static LogEntry parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        try {
            String[] parts = line.split(" ", 4); 
            // 0: Date, 1: Time, 2: EventType, 3: Rest
            
            if (parts.length < 4) return null;

            String timestamp = parts[0] + " " + parts[1];
            String eventType = parts[2];
            String rest = parts[3];

            String username = extractField(rest, "user=");
            String ipAddress = extractField(rest, "ip=");
            String query = null;

            if (rest.contains("query=")) {
                query = extractField(rest, "query=\"");
                if (query.endsWith("\"")) {
                    query = query.substring(0, query.length() - 1);
                }
            }

            return new LogEntry(timestamp, eventType, username, ipAddress, query);
            
        } catch (Exception e) {
            System.err.println("Failed to parse log line: " + line);
            return null;
        }
    }

    private static String extractField(String text, String prefix) {
        int startIndex = text.indexOf(prefix);
        if (startIndex == -1) return "UNKNOWN";
        
        startIndex += prefix.length();
        int endIndex = text.indexOf(" ", startIndex);
        
        // If the field is the last one in the string or contains spaces (like query)
        if (endIndex == -1 || text.substring(startIndex).startsWith("\"")) {
            if (text.substring(startIndex).startsWith("\"")) {
                int quoteEndIndex = text.indexOf("\"", startIndex + 1);
                if (quoteEndIndex != -1) {
                    return text.substring(startIndex, quoteEndIndex + 1);
                }
            }
            return text.substring(startIndex);
        }
        
        return text.substring(startIndex, endIndex);
    }
}
