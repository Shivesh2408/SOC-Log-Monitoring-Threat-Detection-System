# 🛡️ SOC Log Monitoring & Threat Detection System

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-blue.svg)](https://maven.apache.org/)
[![Design Pattern](https://img.shields.io/badge/Pattern-Strategy%20Pattern-green.svg)]()
[![Cybersecurity](https://img.shields.io/badge/Domain-SOC%20%2F%20SIEM-red.svg)]()
[![License](https://img.shields.io/badge/License-MIT-brightgreen.svg)]()

> A high-performance **Security Operations Center (SOC) Log Monitoring and Automated Threat Detection Engine** built with Java 17. The system ingests raw server audit logs, parses structured events, evaluates threat detection rules using the **Strategy Pattern**, triggers prioritized alerts, and generates executive security incident reports.

---

## 📌 Project Overview

Enterprise servers generate millions of raw audit logs daily. Manually reviewing log lines to spot ongoing attacks is impossible for human analysts.

This project mimics an enterprise **SIEM (Security Information and Event Management)** engine. It acts as an automated 24/7 security agent that ingests raw server log streams, applies heuristic threat detection rules, generates real-time security alerts (`alerts.txt`), and synthesizes executive incident reports (`incident_report.txt`).

---

## ⚡ Key Features

- 🔐 **Brute Force Detection**: Tracks repeated failed login attempts (`FAILED_LOGIN`) per username within a rolling window (Threshold: $\ge 5$ attempts).
- 💉 **SQL Injection (SQLi) Detection**: Scans request payloads and URL parameters for OWASP malicious SQL injection signatures (e.g., `' OR '1'='1`, `UNION SELECT`, `DROP TABLE`).
- 🌊 **Denial of Service (DoS) Detection**: Monitors IP-level request frequency to flag volumetric endpoint flooding (Threshold: $\ge 10$ requests per IP).
- 🚫 **Blacklisted IP Detection**: Matches incoming source IP addresses against a Threat Intelligence feed (`blacklist.txt`) using $O(1)$ fast set lookups.
- 🚨 **Multi-Channel Alert Dispatcher**: Emits formatted alerts to the console and persists them to `alerts.txt`.
- 📊 **Executive Summary Reporter**: Utilizes Java 17 Streams to aggregate alerts by severity (`CRITICAL`, `HIGH`, `MEDIUM`, `LOW`), identify top offending IPs, and export `incident_report.txt`.

---

## 🛠️ Technologies Used

- **Language**: Java 17 (LTS) — OOP, Enums, `Optional`, Java Streams API, NIO File I/O.
- **Build Tool**: Apache Maven.
- **Design Pattern**: Strategy Pattern (Decoupled detection strategies behind `ThreatDetector` interface).
- **Data Structures**: `HashMap` (stateful failed login counters) and `HashSet` ($O(1)$ constant time threat intel lookup).

---

## 🏗️ Architecture Overview

```text
[ Raw Log Entry ] ──> LogParser ──> LogEntry Object
                                        │
                                        ▼
                                  ThreatMonitor
                                        │
        ┌───────────────────┬───────────┴───────┬───────────────────┐
        ▼                   ▼                   ▼                   ▼
BruteForceDetector  SQLInjectionDetector   DoSDetector     BlacklistDetector
        │                   │                   │                   │
        └───────────────────┴───────────┬───────┴───────────────────┘
                                        │ Returns Optional<Alert>
                                        ▼
                                  AlertManager ──> console output & alerts.txt
                                        │
                                        ▼
                                 ReportGenerator ──> incident_report.txt
```

---

## 🔍 Threat Detection Modules

| Module | Target Threat | Severity | Heuristic / Rule Logic |
| :--- | :--- | :--- | :--- |
| **`BruteForceDetector`** | Credential Stuffing / Password Guessing | `HIGH` | Tracks consecutive `FAILED_LOGIN` events per user ($\ge 5$ threshold). |
| **`SQLInjectionDetector`** | Database Theft / Payload Injection | `CRITICAL` | Scans query strings for OWASP SQL patterns (`UNION SELECT`, `' OR '1'='1`). |
| **`DoSDetector`** | Volumetric Traffic / Bot Flooding | `MEDIUM` | Tracks request density per source IP ($\ge 10$ threshold). |
| **`BlacklistDetector`** | Malicious Actor Traffic | `CRITICAL` | $O(1)$ hash set lookup against `blacklist.txt` threat intel feed. |

---

## 🚀 How To Run

### Prerequisites
- **Java JDK 17** or higher
- **Apache Maven 3.8+**

### Execution Commands

```bash
# 1. Navigate to project root
cd SOC-Monitor

# 2. Compile project
mvn clean compile

# 3. Run application
mvn exec:java "-Dexec.mainClass=com.socmonitor.Main"
```

---

## 💻 Sample Output

### Console & File Output (`alerts.txt`):
```text
[ALERT] [HIGH] Possible Brute Force Attack detected for user: admin (Failed attempts: 5) | IP: 192.168.1.50
[ALERT] [CRITICAL] SQL Injection Attempt Detected: GET /login?user=' OR '1'='1 | IP: 10.0.0.99
[ALERT] [CRITICAL] Blacklisted IP Activity Detected! IP: 198.51.100.4
[ALERT] [MEDIUM] High Traffic / DoS Warning: 192.168.1.50 (Total requests: 10)
```

### Executive Summary Report (`incident_report.txt`):
```text
==================================================
         SOC THREAT DETECTION SUMMARY REPORT
==================================================
Total Alerts Generated: 4

ALERTS BY SEVERITY:
- CRITICAL: 2
- HIGH: 1
- MEDIUM: 1
- LOW: 0

TOP OFFENDING IP ADDRESSES:
- 192.168.1.50 (2 alerts)
- 10.0.0.99 (1 alert)
- 198.51.100.4 (1 alert)
==================================================
```

---

## 🎓 Skills Demonstrated

- **Software Engineering**: Clean architecture, SOLID principles (SRP, OCP), Strategy Design Pattern.
- **Performance Optimization**: $O(1)$ set lookups for threat intelligence feeds and streaming file reads via Java NIO `BufferedReader`.
- **Cybersecurity Core**: SOC workflows, SIEM rule development, OWASP Top 10 vulnerabilities, Indicators of Compromise (IoC), incident reporting.

---

## 🔮 Future Enhancements

- 🔄 **Real-Time Continuous Tailing**: Java NIO `WatchService` integration for continuous live log monitoring.
- 🧵 **Multithreaded Ingestion**: Concurrent log processing using Java `ExecutorService` and `ConcurrentHashMap`.
- 🐳 **Dockerization**: Package application with multi-stage Docker builds and CI/CD pipelines.

---
*Maintained by Shivesh | Cybersecurity & Java Development Portfolio.*
