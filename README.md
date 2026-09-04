# 🛡️ SOC Log Monitoring & Threat Detection System

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Build Tool](https://img.shields.io/badge/Maven-3.8%2B-blue.svg)](https://maven.apache.org/)
[![Security](https://img.shields.io/badge/SOC-Analyst%20Portfolio-red.svg)]()
[![Design Pattern](https://img.shields.io/badge/Pattern-Strategy%20Pattern-green.svg)]()
[![License](https://img.shields.io/badge/License-MIT-brightgreen.svg)]()

> An industry-style, high-performance **Security Operations Center (SOC) Log Monitoring and Automated Threat Detection Engine** written in modern Java 17. The system ingests raw server audit logs, parses structured events, evaluates threat detection rules using the **Strategy Pattern**, triggers real-time security alerts, and generates executive incident reports.

---

## 📋 Table of Contents
- [1. Project Overview](#1-project-overview)
- [2. Problem Statement](#2-problem-statement)
- [3. Features](#3-features)
- [4. Technologies Used](#4-technologies-used)
- [5. Project Architecture](#5-project-architecture)
- [6. Folder Structure](#6-folder-structure)
- [7. Threat Detection Modules](#7-threat-detection-modules)
- [8. How It Works](#8-how-it-works)
- [9. Sample Execution Flow](#9-sample-execution-flow)
- [10. Future Enhancements](#10-future-enhancements)
- [11. Skills Demonstrated](#11-skills-demonstrated)
- [12. Interview Talking Points](#12-interview-talking-points)
- [Quick Start Guide](#quick-start-guide)

---

## 1. Project Overview

In enterprise environments, servers, firewalls, and applications generate millions of log lines daily. Manually reviewing raw logs to identify cyberattacks is physically impossible for human operators.

The **SOC Log Monitoring & Threat Detection System** mimics a real-world **SIEM (Security Information and Event Management)** engine. It acts as an automated, 24/7 digital security guard that constantly monitors incoming log files, detects suspicious indicators of compromise (IoCs), writes prioritized alerts to `alerts.txt`, and builds an executive `incident_report.txt`.

---

## 2. Problem Statement

Modern organizations face severe cyber threats:
* **Credential Stuffing & Brute Force**: Automated bots guessing passwords repeatedly until access is gained.
* **Database Compromise (SQL Injection)**: Malicious SQL queries injected through web input forms to leak or alter sensitive data.
* **Service Disruption (Denial of Service)**: High-frequency request spikes overwhelming web servers.
* **Malicious Infrastructure Traffic**: Connections originating from known bad actors, botnets, or blacklisted IP addresses.

Without an automated detection system, breaches remain undetected for weeks (average dwell time is ~200 days). This project solves that problem by processing logs instantly and enforcing automated rule sets.

---

## 3. Features

- 🔐 **Brute Force Detection**: Tracks repeated failed login attempts (`FAILED_LOGIN`) per username within a rolling window (Threshold: $\ge 5$ attempts).
- 💉 **SQL Injection (SQLi) Detection**: Scans request payloads and URL parameters for OWASP-top-10 malicious SQL patterns (e.g., `' OR '1'='1`, `UNION SELECT`, `DROP TABLE`).
- ⚡ **Denial of Service (DoS) Detection**: Monitors IP-level request frequency to flag volumetric flooding (Threshold: $\ge 10$ requests per IP).
- 🚫 **Blacklisted IP Detection**: Matches incoming IP addresses against an external Threat Intelligence feed (`blacklist.txt`) using $O(1)$ fast set lookups.
- 🚨 **Multi-Channel Alert Management**: Emits formatted alert notifications to the standard output console and persists formatted entries to `alerts.txt`.
- 📊 **Executive Report Generation**: Utilizes Java 17 Streams to group alerts by severity (`CRITICAL`, `HIGH`, `MEDIUM`, `LOW`), identify top offending IPs, and export `incident_report.txt`.

---

## 4. Technologies Used

| Category | Technology | Usage in Project |
| :--- | :--- | :--- |
| **Language** | **Java 17 (LTS)** | Core runtime, Records/Enums, modern switch expressions, and Streams API. |
| **Build & Dependency Tool** | **Apache Maven** | Project lifecycle management, dependency resolution, and build automation. |
| **Design Pattern** | **Strategy Pattern** | Decoupled threat detection logic behind a clean `ThreatDetector` interface. |
| **Data Structures** | **`HashMap` & `HashSet`** | Stateful counter tracking ($O(1)$ time complexity) and threat intel lookup tables. |
| **I/O & File Handling** | **Java NIO & `BufferedReader`** | Efficient memory streaming and safe file reads (`try-with-resources`). |
| **Functional Programming** | **Java Streams API** | Declarative grouping, sorting, filtering, and alert aggregation. |
| **Core Architecture** | **Object-Oriented Programming (OOP)** | Clean separation of concerns across Models, Detectors, Services, and Utilities. |

---

## 5. Project Architecture

```text
               +-----------------------+
               |   src/main/resources  |
               |  logs.txt  blacklist  |
               +-----------+-----------+
                           |
                           v
                 +-------------------+
                 |    LogParser      |
                 +---------+---------+
                           | Reads raw text & converts to LogEntry objects
                           v
                 +-------------------+
                 |   ThreatMonitor   |
                 +---------+---------+
                           | Dispatches LogEntry to pluggable detectors
        +------------------+------------------+------------------+
        |                  |                  |                  |
        v                  v                  v                  v
+---------------+  +---------------+  +---------------+  +---------------+
|  BruteForce   |  | SQLInjection  |  |  DoSDetector  |  |  Blacklist    |
|   Detector    |  |   Detector    |  |               |  |   Detector    |
+-------+-------+  +-------+-------+  +-------+-------+  +-------+-------+
        |                  |                  |                  |
        +------------------+------------------+------------------+
                           | Returns Optional<Alert>
                           v
                 +-------------------+
                 |   AlertManager    |
                 +---------+---------+
                           | Console output & appends to alerts.txt
                           v
                 +-------------------+
                 |  ReportGenerator  |
                 +---------+---------+
                           | Aggregates stats via Java Streams
                           v
               +-----------------------+
               |  incident_report.txt  |
               +-----------------------+
```

---

## 6. Folder Structure

```text
SOC-Monitor/
├── pom.xml                               # Maven build configuration
├── .gitignore                            # Git ignore rules for build & IDE artifacts
├── README.md                             # Project documentation
├── ARCHITECTURE.md                       # Comprehensive architectural diagrams & flows
├── GITHUB_IMPROVEMENTS.md                # Roadmap & architectural improvement guide
├── PROJECT_MASTERY_GUIDE.md              # Deep-dive study guide for interview prep
├── SOC_PROJECT_BIBLE.md                  # Comprehensive zero-to-hero project reference
├── logs.txt                              # Sample raw security log input file
├── blacklist.txt                         # Threat intelligence feed (known malicious IPs)
├── alerts.txt                            # Output file for triggered security alerts
├── incident_report.txt                   # Output summary report generated by ReportGenerator
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── socmonitor/
        │           ├── Main.java                 # Entry point application driver
        │           ├── model/
        │           │   ├── LogEntry.java         # Encapsulated log event model
        │           │   ├── Alert.java            # Formatted security alert model
        │           │   └── Severity.java         # Threat severity Enum (LOW, MEDIUM, HIGH, CRITICAL)
        │           ├── service/
        │           │   ├── LogParser.java        # String tokenization & raw line parsing
        │           │   ├── ThreatMonitor.java    # Central orchestrator & pipeline coordinator
        │           │   └── AlertManager.java     # Alert persistence & alert dispatching
        │           ├── detector/
        │           │   ├── ThreatDetector.java   # Strategy Pattern interface
        │           │   ├── BruteForceDetector.java # Stateful failed login correlation rule
        │           │   ├── SQLInjectionDetector.java # OWASP SQL signature scanning rule
        │           │   ├── DoSDetector.java      # Volumetric IP frequency threshold rule
        │           │   └── BlacklistDetector.java# Threat intel IP set matching rule
        │           └── util/
        │               ├── FileUtils.java        # Safe NIO file reading & writing utilities
        │               └── ReportGenerator.java  # Stream-based aggregation & metrics reporter
        └── resources/
            ├── logs.txt                          # Bundled classpath sample logs
            └── blacklist.txt                     # Bundled classpath threat intel list
```

---

## 7. Threat Detection Modules

### 1️⃣ BruteForceDetector
- **Target Threat**: Password guessing and credential stuffing attacks.
- **Logic**: Tracks consecutive `FAILED_LOGIN` events grouped by `username` using `HashMap<String, Integer>`.
- **Threshold**: $\ge 5$ failures. Once triggered, emits a `HIGH` severity alert and resets the counter to prevent alert fatigue.

### 2️⃣ SQLInjectionDetector
- **Target Threat**: Unauthorized database access and data exfiltration.
- **Logic**: Inspects incoming request endpoints and payloads against signature lists (`' OR '1'='1`, `UNION SELECT`, `DROP TABLE`, `--`).
- **Severity**: `CRITICAL`. Instant rule trigger upon pattern match.

### 3️⃣ DoSDetector
- **Target Threat**: Denial of Service and volumetric endpoint flooding.
- **Logic**: Tracks total requests per `ipAddress` using `HashMap<String, Integer>`.
- **Threshold**: $\ge 10$ total requests from a single source IP. Emits a `MEDIUM` severity alert and resets counter.

### 4️⃣ BlacklistDetector
- **Target Threat**: Traffic from known malicious actors and bad reputation IPs.
- **Logic**: Loads bad IP addresses into a `HashSet<String>`. Matches every log entry's IP in $O(1)$ time complexity.
- **Severity**: `CRITICAL`. Instant rule trigger upon matching blacklist feed.

---

## 8. How It Works

1. **Initialization**: `Main` loads `logs.txt` and `blacklist.txt`.
2. **Parsing**: `LogParser.parseLine()` tokenizes each raw line into a strongly typed `LogEntry` object (extracting Timestamp, Level, Username, IP, Action, and Request line).
3. **Pipeline Orchestration**: `ThreatMonitor` feeds each `LogEntry` to all registered `ThreatDetector` strategies.
4. **Strategy Evaluation**: Each detector evaluates the entry against its specific heuristic or threshold.
5. **Alert Emission**: If a rule triggers, an `Alert` object wrapped in `Optional<Alert>` is returned, processed by `AlertManager`, printed to console, and appended to `alerts.txt`.
6. **Report Generation**: `ReportGenerator` processes collected alerts, groups them by severity using Java Streams, identifies offending IPs, and exports `incident_report.txt`.

---

## 9. Sample Execution Flow

### Input (`logs.txt` sample):
```text
2026-09-04 10:00:01 INFO admin 192.168.1.50 LOGIN FAILED_LOGIN
2026-09-04 10:00:02 INFO admin 192.168.1.50 LOGIN FAILED_LOGIN
2026-09-04 10:00:03 INFO admin 192.168.1.50 LOGIN FAILED_LOGIN
2026-09-04 10:00:04 INFO admin 192.168.1.50 LOGIN FAILED_LOGIN
2026-09-04 10:00:05 INFO admin 192.168.1.50 LOGIN FAILED_LOGIN
2026-09-04 10:01:00 INFO attacker 10.0.0.99 WEB "GET /login?user=' OR '1'='1"
```

### Console & File Output (`alerts.txt`):
```text
[ALERT] [HIGH] Possible Brute Force Attack detected for user: admin (Failed attempts: 5) | IP: 192.168.1.50
[ALERT] [CRITICAL] SQL Injection Attempt Detected: GET /login?user=' OR '1'='1 | IP: 10.0.0.99
```

---

## 10. Future Enhancements

- 🔄 **Real-Time Continuous File Tailing**: Integrate Java NIO `WatchService` to monitor `logs.txt` in real-time as new lines are appended by live web servers.
- ⚡ **Multithreaded Processing**: Use Java `ExecutorService` and `ConcurrentHashMap` for concurrent, high-throughput multi-worker parsing.
- 📊 **Interactive Dashboard**: Build a modern UI dashboard (Spring Boot + React or JavaFX) with interactive charts.
- 🗄️ **Database Persistence**: Store alerts and logs in PostgreSQL / MongoDB for historical querying and threat hunting.
- 🐳 **Dockerization & CI/CD**: Package the application with Docker and implement GitHub Actions workflow for automated testing and container deployment.

---

## 11. Skills Demonstrated

- **Core Java Mastery**: Object-Oriented Design, Strategy Pattern, Generics, Enums, `Optional`, Java Streams API.
- **Data Structure Optimization**: Utilizing `HashMap` for stateful tracking and `HashSet` for $O(1)$ set lookups.
- **Robust Error Handling & I/O**: Safe string tokenization, boundary checking, and defensive file operations (`try-with-resources`).
- **Cybersecurity Fundamentals**: Understanding SOC operations, SIEM workflows, OWASP top 10 vulnerabilities, Indicators of Compromise (IoC), and incident response reporting.

---

## 12. Interview Talking Points

- **Strategy Pattern Scalability**: *"I decoupled detection logic using the Strategy Pattern (`ThreatDetector`). Adding a new threat detector (e.g., Ransomware or XSS detector) requires zero changes to the core orchestrator (`ThreatMonitor`), adhering strictly to the Open/Closed Principle."*
- **Time Complexity Optimization**: *"For blacklist verification, I chose a `HashSet<String>` instead of an `ArrayList` to achieve $O(1)$ constant time lookup per log entry rather than $O(N)$ linear lookup, ensuring performance stays fast even with 100,000 blacklisted IPs."*
- **Defensive String Parsing**: *"Log entries can contain irregular whitespace or spaces within HTTP request quotes. My `LogParser` uses bounded split scanning to handle arbitrary query strings safely without raising `ArrayIndexOutOfBoundsException` or succumbing to ReDoS."*

---

## 🚀 Quick Start Guide

### Prerequisites
- Java JDK 17 or higher
- Apache Maven 3.8+

### Compilation & Execution
```bash
# 1. Clone the repository
git clone https://github.com/your-username/SOC-Monitor.git
cd SOC-Monitor

# 2. Compile using Maven
mvn clean compile

# 3. Execute the system
mvn exec:java "-Dexec.mainClass=com.socmonitor.Main"
```

---

