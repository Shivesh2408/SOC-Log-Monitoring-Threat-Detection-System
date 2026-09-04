# 🏗️ SOC Log Monitoring & Threat Detection System - Architecture Documentation

This document provides a comprehensive technical breakdown of the architecture, components, data flows, and runtime sequences for the **SOC Log Monitoring & Threat Detection System**.

---

## 📐 1. High-Level System Architecture

The system operates as a modular, event-driven pipeline where raw unstructured log streams are converted into strongly typed objects, evaluated against threat detection strategies, written to alert outputs, and synthesized into incident reports.

```text
+-----------------------------------------------------------------------------------+
|                                 INPUT SOURCES                                     |
|                                                                                   |
|   +-----------------------+                    +------------------------------+   |
|   |       logs.txt        |                    |        blacklist.txt         |   |
|   |  (Raw Log Entries)    |                    |  (Malicious Threat Feed)     |   |
|   +-----------+-----------+                    +--------------+---------------+   |
+---------------+-----------------------------------------------+-------------------+
                |                                               |
                v                                               v
+---------------+-----------------------------------------------+-------------------+
|                               PROCESSING PIPELINE                                 |
|                                                                                   |
|   +---------------------------------------------------------------------------+   |
|   |                                LogParser                                  |   |
|   |      (Extracts Timestamp, Level, Username, IP, Action, Request Line)      |   |
|   +------------------------------------+--------------------------------------+   |
|                                        |                                          |
|                                        v                                          |
|   +------------------------------------+--------------------------------------+   |
|   |                              ThreatMonitor                                |   |
|   |                 (Central Orchestrator & Dispatcher Engine)                |   |
|   +----+-------------------+-------------------+-------------------+----------+   |
|        |                   |                   |                   |              |
|        v                   v                   v                   v              |
|  +-----------+       +-----------+       +-----------+       +-----------+        |
|  |BruteForce |       |SQLInjectn |       |DoSDetector|       | Blacklist |        |
|  | Detector  |       | Detector  |       |           |       | Detector  |        |
|  +-----+-----+       +-----+-----+       +-----+-----+       +-----+-----+        |
|        |                   |                   |                   |              |
|        +-------------------+---------+---------+-------------------+              |
|                                      | Returns Optional<Alert>                    |
|                                      v                                            |
|   +----------------------------------+----------------------------------------+   |
|   |                              AlertManager                                 |   |
|   |            (Formats Alert, Console Log & Appends to alerts.txt)           |   |
|   +----------------------------------+----------------------------------------+   |
+--------------------------------------|--------------------------------------------+
                                       |
                                       v
+--------------------------------------v--------------------------------------------+
|                                REPORT GENERATION                                  |
|                                                                                   |
|   +---------------------------------------------------------------------------+   |
|   |                             ReportGenerator                               |   |
|   |       (Java 17 Streams Aggregation by Severity & Top Offending IPs)       |   |
|   +------------------------------------+--------------------------------------+   |
|                                        |                                          |
|                                        v                                          |
|   +------------------------------------+--------------------------------------+   |
|   |                           incident_report.txt                             |   |
|   |                 (Final Executive Security Summary)                        |   |
|   +---------------------------------------------------------------------------+   |
+-----------------------------------------------------------------------------------+
```

---

## 🧩 2. Component Diagram

```text
+------------------------------------------------------------------------------------+
|                                    PACKAGE MODEL                                   |
|                                                                                    |
|   +------------------+         +------------------+         +------------------+   |
|   |     Severity     |         |     LogEntry     |         |      Alert       |   |
|   |      (Enum)      |         |  (Record/Class)  |         |  (Record/Class)  |   |
|   +------------------+         +------------------+         +------------------+   |
+------------------------------------------------------------------------------------+
                                          ^
                                          | Uses Domain Objects
+-----------------------------------------+------------------------------------------+
|                                  PACKAGE DETECTOR                                  |
|                                                                                    |
|                      +---------------------------------------+                     |
|                      |  <<Interface>> ThreatDetector         |                     |
|                      |  + analyze(entry): Optional<Alert>    |                     |
|                      +-------------------+-------------------+                     |
|                                          |                                         |
|         +-------------------+------------+------------+-------------------+        |
|         |                   |                         |                   |        |
|         v                   v                         v                   v        |
|  +--------------+   +--------------+           +--------------+   +--------------+ |
|  |  BruteForce  |   | SQLInjection |           | DoSDetector  |   |  Blacklist   | |
|  |   Detector   |   |   Detector   |           |              |   |   Detector   | |
|  +--------------+   +--------------+           +--------------+   +--------------+ |
+------------------------------------------------------------------------------------+
                                          ^
                                          | Registers Strategy List
+-----------------------------------------+------------------------------------------+
|                                   PACKAGE SERVICE                                  |
|                                                                                    |
|   +-------------------+    +------------------------+    +---------------------+   |
|   |     LogParser     |    |     ThreatMonitor      |    |    AlertManager     |   |
|   | + parseLine()     |    | + registerDetector()   |    | + dispatchAlert()   |   |
|   |                   |    | + processEntry()       |    | + saveToFile()      |   |
|   +-------------------+    +------------------------+    +---------------------+   |
+------------------------------------------------------------------------------------+
                                          ^
                                          | Invokes High Level Pipeline
+-----------------------------------------+------------------------------------------+
|                                  PACKAGE UTIL & MAIN                               |
|                                                                                    |
|   +-------------------+    +------------------------+    +---------------------+   |
|   |     FileUtils     |    |    ReportGenerator     |    |      Main.java      |   |
|   | + readFileLines() |    | + generateReport()     |    |  (Application       |   |
|   | + appendToFile()  |    |                        |    |   Driver Entry)     |   |
|   +-------------------+    +------------------------+    +---------------------+   |
+------------------------------------------------------------------------------------+
```

---

## 🔀 3. Data Flow Diagram (DFD)

```text
[Raw Log Line] ---> (1. LogParser) ---> [LogEntry Object]
                                               |
                                               v
                                     (2. ThreatMonitor)
                                               |
                     +-------------------------+-------------------------+
                     |                         |                         |
                     v                         v                         v
          (BruteForceDetector)       (SQLInjectionDetector)        (DoSDetector & Blacklist)
                     |                         |                         |
                     +-------------------------+-------------------------+
                                               |
                                    [Optional<Alert>]
                                               |
                                               v (If Present)
                                      (3. AlertManager)
                                               |
                                 +-------------+-------------+
                                 |                           |
                                 v                           v
                          [Console Output]           [alerts.txt File]
                                                             |
                                                             v
                                                   (4. ReportGenerator)
                                                             |
                                                             v
                                                  [incident_report.txt]
```

---

## ⚡ 4. Runtime Execution Sequence Diagram

```text
Main            FileUtils           LogParser        ThreatMonitor       Detector Strategy       AlertManager      ReportGenerator
  |                 |                   |                  |                     |                    |                   |
  |--readFileLines->|                   |                  |                     |                    |                   |
  |<--List<String>--|                   |                  |                     |                    |                   |
  |                                     |                  |                     |                    |                   |
  |=== Loop for each raw log line ========================================================================================|
  |--------------------parseLine------->|                  |                     |                    |                   |
  |<-------------------LogEntry---------|                  |                     |                    |                   |
  |                                                        |                     |                    |                   |
  |--------------------processEntry----------------------->|                     |                    |                   |
  |                                                        |---analyze(entry)--->|                    |                   |
  |                                                        |<--Optional<Alert>---|                    |                   |
  |                                                        |                                          |                   |
  |                                                        |=== If Alert Present =====================|                   |
  |                                                        |---dispatchAlert------------------------->|                   |
  |                                                        |                                          |--appendToFile---->|
  |                                                        |-----------------------------------------+|<--ack-------------|
  |=======================================================================================================================|
  |                                                                                                                       |
  |--------------------generateReport------------------------------------------------------------------------------------>|
  |                                                                                                                       |--writeReport->
  |<-------------------completed------------------------------------------------------------------------------------------|
```

---

## 💡 5. Detailed Component Breakdown (Beginner Friendly)

### 1. `LogEntry.java` (Domain Model)
- **What it is**: A clean blueprint object representing a single event in the log file.
- **Human Analogy**: Think of it as a **digital passport stamp** or a security guestbook entry containing *Who*, *When*, *Where*, and *What action was attempted*.

### 2. `LogParser.java` (Service Layer)
- **What it is**: Converts a raw string line like `"2026-09-04 10:00:01 INFO admin 192.168.1.50 LOGIN FAILED_LOGIN"` into a structured `LogEntry` object.
- **Human Analogy**: A **translator** who reads messy hand-written security forms and types them cleanly into computer database fields.

### 3. `ThreatDetector.java` (Interface / Strategy)
- **What it is**: An interface defining a contract (`analyze(LogEntry entry)`).
- **Human Analogy**: A **job description for a security guard standing at a gate**. Every guard must follow the rule: *"Examine the visitor, and sound an alarm if you spot suspicious activity."*

### 4. `BruteForceDetector.java` (Stateful Strategy)
- **What it is**: Monitors consecutive failed login attempts per username using a `HashMap<String, Integer>`.
- **Human Analogy**: An ATM lock mechanism that keeps count of wrong PIN attempts and freezes the card after 5 failures.

### 5. `SQLInjectionDetector.java` (Signature Strategy)
- **What it is**: Scans incoming HTTP requests for malicious database command strings.
- **Human Analogy**: An airport baggage X-ray scanner checking luggage for dangerous contraband items.

### 6. `DoSDetector.java` (Rate-Limiting Strategy)
- **What it is**: Tracks the total volume of requests per IP address to spot computer bots overwhelming the system.
- **Human Analogy**: A turnstile counter at a stadium gate that locks if a single person tries to push through 10 tickets at the exact same second.

### 7. `BlacklistDetector.java` (Threat Intel Strategy)
- **What it is**: Matches source IP addresses against a pre-loaded `HashSet<String>` containing known bad IP addresses.
- **Human Analogy**: A security guard at a high-profile venue holding a **"Most Wanted" photo list** and comparing every face at the door.

### 8. `ThreatMonitor.java` (Pipeline Orchestrator)
- **What it is**: Holds a collection of active `ThreatDetector` strategies and passes incoming `LogEntry` objects through every detector.
- **Human Analogy**: The **Chief Security Officer in the Security Command Room** who coordinates all specialized guards and instructs them on what to check.

### 9. `AlertManager.java` (Alert Sink)
- **What it is**: Handles formatting, logging to terminal, and persisting security alerts to `alerts.txt`.
- **Human Analogy**: The **emergency siren and dispatch system** that sounds the alarm and logs official incident records in the master binder.

### 10. `ReportGenerator.java` (Analytics Engine)
- **What it is**: Uses Java 17 Streams to count, group, and sort alert statistics, producing an executive `incident_report.txt`.
- **Human Analogy**: An **executive assistant** who reviews all emergency alarms at 5:00 PM and prints a neat one-page briefing summary for the CEO.
