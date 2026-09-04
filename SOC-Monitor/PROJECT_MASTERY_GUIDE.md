# SOC LOG MONITORING & THREAT DETECTION SYSTEM
## THE ULTIMATE MASTER PROJECT & INTERVIEW GUIDE

> **Author**: Created for SOC Analyst & Java Security Engineering Interviews  
> **Level**: Beginner-Friendly to Advanced SOC Analyst / Security Architect  
> **Goal**: Complete Mastery of Architecture, Code, Security Concepts, and Interview Defense  

---

# TABLE OF CONTENTS
1. [Section 1: Project Overview & Industry Context](#section-1-project-overview)
2. [Section 2: Complete System Architecture & Data Flow](#section-2-complete-system-architecture)
3. [Section 3: Project Structure & Directory Breakdown](#section-3-complete-project-structure)
4. [Section 4: File-by-File & Line-by-Line Code Breakdown](#section-4-file-by-file-explanation)
5. [Section 5: Complete Execution Flow & Sequence Diagrams](#section-5-execution-flow)
6. [Section 6: Core Cybersecurity Concepts & SOC Foundations](#section-6-cybersecurity-concepts)
7. [Section 7: Core Java Concepts & OOP In Action](#section-7-java-concepts-used)
8. [Section 8: Software Engineering & System Design Decisions](#section-8-system-design-explanation)
9. [Section 9: Deep Dive into Threat Detectors](#section-9-detector-deep-dive)
10. [Section 10: Real-World Enterprise SOC & SIEM Comparison](#section-10-real-world-soc-comparison)
11. [Section 11: 150 Interview Questions & Model Answers](#section-11-interview-preparation)
12. [Section 12: Behavioral & Project Experience Questions](#section-12-hr--project-questions)
13. [Section 13: Elevator Pitches (30s, 1m, 2m, 5m)](#section-13-resume-explanation)
14. [Section 14: Tough Interviewer Grillings & Project Defense](#section-14-project-defense-guide)
15. [Section 15: Visual Architecture & Diagram Catalog](#section-15-visual-architecture)
16. [Section 16: One-Page Mastery & Quick Revision Cheat Sheet](#section-16-mastery-notes)

---

# SECTION 1: PROJECT OVERVIEW

## 1. Simple English Explanation (Layman's Terms)
Imagine a massive airport. Thousands of people pass through the entrance doors every minute. Some are regular travelers carrying suitcases, some forget their boarding pass, and some might be carrying prohibited items or attempting to sneak into restricted security zones. 

If security guards had to personally inspect every single person's history and behavior manually, lines would stretch for miles, and dangerous people would slip through unnoticed. Instead, airports use automated scanners, metal detectors, ticket verification turnstiles, and facial recognition cameras linked to a police watch-list. When an alarm triggers, armed security officers intervene immediately.

In the digital world:
* **The Airport** is your organization's servers, databases, and websites.
* **The People** are incoming web requests and user login attempts.
* **The Security Logbook** is `logs.txt` (recording who arrived, what time, what they clicked, and what IP address they came from).
* **This Project** is the **automated airport scanner system**. It continuously reads the logbook, instantly catches malicious hackers attempting to break in (guessing passwords, injecting database exploits, flooding the servers, or coming from blacklisted attacker IPs), raises an instant alarm (`alerts.txt`), and produces an executive summary for management (`incident_report.txt`).

---

## 2. Technical Explanation (Engineering Language)
Modern enterprise infrastructures generate gigabytes to terabytes of audit logs daily across edge routers, web servers, identity providers (Active Directory/Okta), databases, and firewall appliances. Security Operations Center (SOC) Tier 1 and Tier 2 analysts are responsible for real-time triage, event correlation, and initial containment of Indicators of Compromise (IoCs).

This project implements a modular, event-driven **Log Monitoring and Threat Detection Engine** written in Java 17. The engine consumes unstructured/semi-structured textual audit telemetry, tokenizes and normalizes the log stream into structured POJO domain entities (`LogEntry`), and broadcasts those events through a polymorphic chain of heuristic rule detectors (`ThreatDetector`). 

The engine implements automated detection algorithms for four major attack classes:
1. **Authentication Attacks**: Distributed & volumetric Brute Force login attempts.
2. **Application-Layer Exploits**: SQL Injection (SQLi) attacks leveraging input-handling vulnerabilities.
3. **Availability Attacks**: Volumetric Denial of Service (DoS) flood attacks.
4. **Threat Intelligence / Reputational Attacks**: Interactions originating from known hostile external IP addresses (Blacklist Matching).

The alert output is enriched with severity categorizations (Low, Medium, High, Critical), globally unique tracking IDs (UUIDv4), chronological timestamps, and forensic descriptions. The lifecycle terminates with an automated post-run analytical report summarizing incident distribution, attacker IP frequency, and executive security posture.

---

## 3. How to Answer in an Interview
> *"I designed and built a standalone Security Operations Center (SOC) Log Monitoring and Threat Detection Engine using Java 17. The system simulates how enterprise SIEM platforms like Splunk or Microsoft Sentinel ingest raw server telemetry, parse and normalize disparate event streams, correlate suspicious patterns against behavioral thresholds and threat intelligence blacklists, and trigger prioritized security alerts. I engineered the system using Object-Oriented Principles, applying the Strategy and Factory patterns to decouple detection heuristics from log parsing and reporting."*

---

## 4. Real-World Use Cases & Why SOC Teams Need It
1. **Preventing Credential Stuffing & Password Spraying**: Attackers write automated Python scripts to try millions of common passwords against corporate portals. Without automated threshold detection, hundreds of failed logins go unnoticed until an account is breached.
2. **Defending Database Integrity from Web Application Exploits**: Attackers test URL parameters and search bars with malicious SQL payloads (`' OR 1=1 --`, `UNION SELECT`). Real-time detection stops data exfiltration before tables are dumped.
3. **Mitigating Service Outages from Flood Attacks**: Bots and compromised IoT devices bombard servers with HTTP requests. Detecting volumetric spikes from single IPs allows upstream firewalls to apply rate-limiting or null-route traffic.
4. **Leveraging Threat Intelligence Feeds**: Cyber threat intelligence organizations (like CISA, AlienVault OTX, Talos) publish daily feeds of malicious command-and-control (C2) IP addresses. Comparing internal traffic against these feeds immediately reveals compromised internal machines calling home.

---

# SECTION 2: COMPLETE SYSTEM ARCHITECTURE

## 1. ASCII End-to-End System Architecture

```
+-----------------------------------------------------------------------------------------+
|                                    INPUT DATA LAYER                                     |
|                                                                                         |
|      +--------------------------------+         +--------------------------------+      |
|      |    Raw Security Logs File      |         |     Threat Intel Blacklist     |      |
|      |  (src/main/resources/logs.txt) |         | (src/main/resources/blacklist) |      |
|      +--------------------------------+         +--------------------------------+      |
+-----------------------|-----------------------------------------|-----------------------+
                        |                                         |
                        v (Line-by-line read)                     |
+-------------------------------------------------------------+   |
|                 INGESTION & PARSING LAYER                   |   |
|                                                             |   |
|   +-----------------------------------------------------+   |   |
|   |                  LogParser.java                     |   |   |
|   |  - Tokenizes raw log string into discrete fields    |   |   |
|   |  - Handles variable whitespace & quoted SQL strings |   |   |
|   +-----------------------------------------------------+   |   |
+-----------------------|-------------------------------------+   |
                        | Instantiates                            |
                        v                                         |
+-------------------------------------------------------------+   |
|                  DOMAIN DATA MODEL LAYER                    |   |
|                                                             |   |
|   +-----------------------------------------------------+   |   |
|   |                  LogEntry.java                      |   |   |
|   |  (timestamp, eventType, username, ipAddress, query) |   |   |
|   +-----------------------------------------------------+   |   |
+-----------------------|-------------------------------------+   |
                        | Passes to Engine                        |
                        v                                         |
+-------------------------------------------------------------+   |
|              THREAT DETECTION & CORRELATION ENGINE          |   |
|                                                             |   |
|   +-----------------------------------------------------+   |   |
|   |                ThreatMonitor.java                   |   |   |
|   |   Coordinates and iterates registered detectors     |   |   |
|   +-----------------------------------------------------+   |   |
|                              |                                  |
|           +------------------+------------------+               |
|           |                  |                  |               |
|           v                  v                  v               v
|   +---------------+  +---------------+  +---------------+  +---------------+
|   |  BruteForce   |  | SQLInjection  |  |      DoS      |  |   Blacklist   |
|   |   Detector    |  |   Detector    |  |   Detector    |  |   Detector    |
|   | (Failed >= 5) |  | (Bad SQL keys)|  | (Reqs >= 10)  |  |(IP match set) |
|   +---------------+  +---------------+  +---------------+  +---------------+
|           |                  |                  |                  |
|           +------------------+------------------+------------------+
|                              | Emits Optional<Alert>
|                              v
+-----------------------------------------------------------------------------------------+
|                               ALERT MANAGEMENT LAYER                                    |
|                                                                                         |
|   +---------------------------------------------------------------------------------+   |
|   |                               AlertManager.java                                 |   |
|   |  - Central In-Memory Store: List<Alert>                                         |   |
|   |  - Real-Time Console Logging: System.out.println("🚨 ALERT GENERATED: ...")      |   |
|   |  - Disk Persistence: Appends to src/main/resources/alerts.txt                  |   |
|   +---------------------------------------------------------------------------------+   |
+--------------------------------------|--------------------------------------------------+
                                       |
                                       | All Logs & Triggered Alerts
                                       v
+-----------------------------------------------------------------------------------------+
|                           ANALYTICS & REPORTING LAYER                                   |
|                                                                                         |
|   +---------------------------------------------------------------------------------+   |
|   |                             ReportGenerator.java                                |   |
|   |  - Java Streams aggregation (groupingBy alertType, counting)                    |   |
|   |  - IP Frequency profiling (sorted by request volume descending)                 |   |
|   |  - Overall threat posture assessment                                            |   |
|   +---------------------------------------------------------------------------------+   |
+--------------------------------------|--------------------------------------------------+
                                       |
                                       v Generates
+-----------------------------------------------------------------------------------------+
|                                    OUTPUT ARTIFACTS                                     |
|                                                                                         |
|     +----------------------------------+     +------------------------------------+     |
|     |            alerts.txt            |     |        incident_report.txt         |     |
|     |  Chronological list of all high, |     | Executive summary of logs, alerts, |     |
|     |  critical, and medium alerts     |     | top malicious IPs, and posture     |     |
|     +----------------------------------+     +------------------------------------+     |
+-----------------------------------------------------------------------------------------+
```

---

## 2. Step-by-Step Data Flow
1. **Bootstrapping**: `Main.java` executes. It instantiates `AlertManager` (which wipes/resets `alerts.txt` for clean session logging) and `ThreatMonitor`.
2. **Rule Registration**: When `ThreatMonitor` is instantiated, it loads `blacklist.txt` into an in-memory `HashSet<String>` via `FileUtils`, then instantiates and registers the four detectors (`BruteForceDetector`, `SQLInjectionDetector`, `DoSDetector`, `BlacklistDetector`).
3. **Ingestion**: `Main` reads all lines from `logs.txt` using Java NIO `Files.readAllLines()`.
4. **Tokenization & Normalization**: Each raw string line is passed to `LogParser.parse(line)`. The parser extracts timestamps, action types, usernames, source IP addresses, and any queries into a structured immutable `LogEntry`.
5. **Detection Pipeline**: `ThreatMonitor.processLog(entry)` routes the `LogEntry` to every registered detector in sequence.
6. **Heuristic Evaluation**:
   * If an attack condition matches, the detector returns `Optional.of(new Alert(...))`.
   * If the event is benign, it returns `Optional.empty()`.
7. **Alert Dispatch**: Any generated `Alert` is passed to `AlertManager.addAlert()`, which logs it to standard output with an alert emoji and appends it to `src/main/resources/alerts.txt`.
8. **Forensic Aggregation & Reporting**: When all lines are consumed, `ReportGenerator.generateReport()` processes all collected `LogEntry` objects and generated `Alert` objects using Java Streams to write a forensic executive summary into `incident_report.txt`.

---

## 3. Why the Architecture Was Designed This Way
* **Separation of Concerns (SoC)**: `LogParser` knows nothing about security rules. `ThreatDetector` knows nothing about file I/O. `AlertManager` knows nothing about how alerts were generated. If you change the log format tomorrow (e.g., from plaintext to JSON), you only edit `LogParser`.
* **Open/Closed Principle (OCP)**: New detection rules (e.g., Port Scan Detector, Ransomware Extension Detector) can be added simply by creating a class that implements `ThreatDetector` without changing a single line of code in the existing detectors or the parser.
* **Loose Coupling**: Components communicate via standard interfaces and immutable domain models.

---

## 4. Alternative Architectural Patterns & Trade-offs
| Architecture Pattern | How It Differs | Pros | Cons / Trade-offs |
| :--- | :--- | :--- | :--- |
| **Current Monolithic In-Memory Pipeline** | Single JVM, reads file into memory, processes sequentially. | Simple, zero external dependencies, rapid execution, crystal clear for interviews. | Memory consumption scales with log file size; single-threaded throughput limit. |
| **Producer-Consumer (BlockingQueue / Reactive)** | Thread 1 tails log file and writes to `ArrayBlockingQueue`; Pool of worker threads consumes and evaluates rules. | Real-time streaming capability; non-blocking; higher throughput. | Requires thread synchronization, concurrent data structures, and graceful shutdown handling. |
| **Microservices / Event-Driven (Kafka + Flink)** | Logs published to Apache Kafka topic; Apache Flink or Kafka Streams processes sliding windows. | Infinite horizontal scalability across distributed clusters; enterprise standard. | Massive infrastructure overhead, requires Docker, Zookeeper/Kafka, and complex configuration. |

---

# SECTION 3: COMPLETE PROJECT STRUCTURE

```text
SOC-Monitor/
├── pom.xml                                    <-- Maven Project Object Model (Dependencies, Compiler configs)
├── README.md                                  <-- Project documentation, setup guide, and portfolio summary
├── incident_report.txt                        <-- Generated post-run executive incident report
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── socmonitor/
│       │           ├── Main.java              <-- System Entry Point & Orchestration Driver
│       │           ├── model/                 <-- Domain Data Transfer Objects (DTOs) & Enums
│       │           │   ├── Severity.java      <-- Priority classification enum (LOW, MEDIUM, HIGH, CRITICAL)
│       │           │   ├── LogEntry.java      <-- Normalized structured representation of a single log event
│       │           │   └── Alert.java         <-- Security alert entity containing forensic details & UUID
│       │           ├── service/               <-- Business logic & engine orchestration
│       │           │   ├── LogParser.java     <-- String tokenization & raw text extraction engine
│       │           │   ├── AlertManager.java  <-- Alert lifecycle manager, terminal notifier & file writer
│       │           │   └── ThreatMonitor.java <-- Rule dispatcher & detection coordinator
│       │           ├── detector/              <-- Polymorphic security detection rules
│       │           │   ├── ThreatDetector.java<-- Common interface defining the analyze() contract
│       │           │   ├── BruteForceDetector.java     <-- Tracks failed authentication velocity per IP
│       │           │   ├── SQLInjectionDetector.java   <-- Scans payload queries for SQL syntax tokens
│       │           │   ├── DoSDetector.java            <-- Tracks volumetric request frequency per IP
│       │           │   └── BlacklistDetector.java      <-- Matches source IP against known malicious feeds
│       │           ├── report/                <-- Forensic aggregation & analytical reporting
│       │           │   └── ReportGenerator.java<-- Stream-based analytics generating incident_report.txt
│       │           └── util/                  <-- Reusable lower-level helpers
│       │               └── FileUtils.java     <-- Safe file reading, writing, clearing, and line appending
│       └── resources/                         <-- Non-code assets, configuration, and data feeds
│           ├── logs.txt                       <-- Simulated enterprise raw event stream
│           ├── blacklist.txt                  <-- Curated list of malicious IP addresses (Threat Intel)
│           └── alerts.txt                     <-- Output log where generated alerts are recorded
```

### Folder Responsibilities:
1. **`model/`**: Contains pure data containers. No business logic, no file operations, no security checks. Only attributes, getters, and `toString()` formatters.
2. **`service/`**: Coordinates workflows. `LogParser` turns text into models; `ThreatMonitor` loops models through detectors; `AlertManager` routes generated alerts.
3. **`detector/`**: Contains all cybersecurity detection logic. Every detector is self-contained and isolated.
4. **`report/`**: Contains statistical calculation logic, grouping, sorting, and reporting.
5. **`util/`**: Reusable static helper functions for reading files, preventing code duplication across services.
6. **`resources/`**: Input and output files loaded at runtime.

---

# SECTION 4: FILE BY FILE EXPLANATION

Here is an exhaustive, line-by-line breakdown of every single file in the project.

---

## FILE 1: `pom.xml`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\pom.xml`
* **Purpose**: Defines project configuration, Java version targets (Java 17), and build tool lifecycle settings for Apache Maven.
* **Why it exists**: Maven needs this file to understand how to compile the source code, what Java SDK version to target, and how to execute the application from the command line without requiring manual `javac` commands.
* **Real-World Analogy**: The `pom.xml` is like the blueprint and ingredients list for a manufacturing plant. It tells the automated machinery exactly what materials are needed and how to assemble the final product.

### Complete Code:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.socmonitor</groupId>
    <artifactId>SOC-Monitor</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
</project>
```

### Line-by-Line Explanation:
* Lines 1-4: XML declaration and Maven Project schema headers.
* Line 5: `<modelVersion>4.0.0</modelVersion>` indicates this POM complies with the Maven 2 & 3 object model.
* Lines 7-9: Identifies the project coordinates. `groupId` is `com.socmonitor`, `artifactId` is `SOC-Monitor`, and `version` is `1.0-SNAPSHOT` (indicating a build in active development).
* Lines 11-15: `<properties>` sets compiler source and target flags to `17` (Java 17 LTS), enabling modern language features (like `List.of()`, `Optional`, and enhanced switch expressions) and enforces UTF-8 character encoding.

### Interview Response:
> *"I used Apache Maven as the dependency management and build automation tool. The `pom.xml` is configured for Java 17 LTS to leverage modern Java language features while maintaining enterprise stability."*

---

## FILE 2: `Severity.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\model\Severity.java`
* **Purpose**: Defines the standardized priority levels for security alerts.
* **Why it exists**: In cybersecurity, alerts must be triaged according to urgency. Using an `enum` prevents typos (like writing `"critcal"` or `"High"` as loose strings) and enforces type safety.
* **Real-World Analogy**: In a hospital emergency room, patients are tagged with triage colors: Green (Non-urgent), Yellow (Moderate), Red (Emergency), Black (Critical). `Severity` is the digital ER triage system.

### Complete Code:
```java
package com.socmonitor.model;

public enum Severity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
```

### Line-by-Line Explanation:
* Line 1: Declares the package `com.socmonitor.model`.
* Line 3: Declares an `enum` named `Severity`.
* Lines 4-7: Declares four distinct enum constants:
  * `LOW`: Informational or minor deviation.
  * `MEDIUM`: Suspicious activity requiring analyst review (e.g., Blacklisted IP pinging an endpoint).
  * `HIGH`: High probability of active compromise attempt (e.g., Brute force login threshold hit, DoS attack).
  * `CRITICAL`: Immediate threat to data confidentiality and infrastructure integrity (e.g., active SQL Injection exploit).

### Interview Response:
> *"I implemented `Severity` as a Java enum to enforce type-safety across the detection pipeline. In a real SOC, alerts are categorized by severity so analysts can prioritize responding to Critical and High alerts before handling Medium or Low informational events."*

---

## FILE 3: `LogEntry.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\model\LogEntry.java`
* **Purpose**: Represents a single, normalized, structured security log event in memory.
* **Why it exists**: Raw text logs are messy and difficult to evaluate. Wrapping the extracted fields into a dedicated Java object allows detectors to query properties like `entry.getIpAddress()` or `entry.getEventType()` cleanly and reliably.
* **Real-World Analogy**: When a police officer stops someone, they take the driver's messy verbal statements and transcribe them into a standardized ticket format: Date, Name, License Plate, Violation. `LogEntry` is that standardized ticket.

### Complete Code:
```java
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
```

### Line-by-Line Explanation:
* Lines 4-8: Private encapsulated fields storing normalized log details.
* Lines 10-16: Constructor initializing all five attributes when parsed from text.
* Lines 18-22: Public getter methods providing read-only access to encapsulate state.
* Lines 24-33: Overridden `toString()` method for debugging and readable console logging.

### Interview Response:
> *"The `LogEntry` class represents our domain model for ingested events. It encapsulates all normalized telemetry fields—such as source IP, event type, username, and query payloads—following OOP encapsulation principles."*

---

## FILE 4: `Alert.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\model\Alert.java`
* **Purpose**: Encapsulates a triggered security incident with forensic metadata.
* **Why it exists**: When an attack rule fires, SOC analysts need complete situational context: what happened, when did it happen, how severe is it, and what is its unique incident tracking ticket? `Alert` models this exact artifact.
* **Real-World Analogy**: When a bank's silent alarm goes off, an electronic ticket is generated for the 911 dispatcher with an Incident ID, Threat Type (Armed Robbery), Severity (Critical), Time, and Description.

### Complete Code:
```java
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
```

### Line-by-Line Explanation:
* Line 3: Imports `java.util.UUID` for generating universally unique identifiers.
* Lines 6-10: Encapsulated fields defining an alert's lifecycle properties.
* Line 13: `UUID.randomUUID().toString()` automatically generates a collision-free unique 128-bit identifier (e.g., `fac33671-13b6-4bda-a2cd-4134953ed54e`) upon creation.
* Lines 14-17: Assigns alert category, severity enum, timestamp, and contextual forensic description.
* Lines 20-24: Standard getters.
* Lines 26-29: Formatted string representation for clean console and file logging.

### Interview Response:
> *"The `Alert` class encapsulates triggered incidents. It uses Java's `UUID.randomUUID()` to generate an immutable, globally unique ticket ID for every detected threat, mimicking enterprise case management systems like ServiceNow or Jira Service Desk."*

---

## FILE 5: `ThreatDetector.java` (Interface)
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\detector\ThreatDetector.java`
* **Purpose**: Defines the common abstraction contract that all detection rules must implement.
* **Why it exists**: This is the heart of the **Strategy Design Pattern**. By requiring all detectors to implement `Optional<Alert> analyze(LogEntry logEntry)`, `ThreatMonitor` can treat all detectors polymorphically.
* **Real-World Analogy**: In an electrical power strip, any appliance can plug in as long as it has a standard 3-prong plug. `ThreatDetector` is the standard 3-prong plug contract.

### Complete Code:
```java
package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import java.util.Optional;

public interface ThreatDetector {
    Optional<Alert> analyze(LogEntry logEntry);
}
```

### Line-by-Line Explanation:
* Line 5: Imports `java.util.Optional` to handle absent values cleanly without risking `NullPointerException`.
* Line 7: Defines the interface `ThreatDetector`.
* Line 8: Declares the contract method `analyze(LogEntry logEntry)`. If a threat is detected, it returns `Optional.of(alert)`. If no threat is found, it returns `Optional.empty()`.

### Interview Response:
> *"I designed `ThreatDetector` as an interface to adhere to the Open/Closed Principle and the Strategy Pattern. It enables loose coupling: our threat monitoring engine can iterate over any collection of detectors without knowing their internal detection algorithms."*

---

## FILE 6: `BruteForceDetector.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\detector\BruteForceDetector.java`
* **Purpose**: Detects automated credential-guessing attacks by monitoring failed login frequencies per IP.
* **Why it exists**: Humans occasionally type the wrong password once or twice. Bots try hundreds of passwords in seconds. Tracking failed logins by IP catches bots while tolerating normal user mistakes.
* **Real-World Analogy**: If an ATM card PIN is entered incorrectly 3 times, the ATM locks the card and snaps a photo of the user. This detector triggers after 5 failed attempts from the same IP.

### Complete Code:
```java
package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.model.Severity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BruteForceDetector implements ThreatDetector {
    private static final int THRESHOLD = 5;
    private final Map<String, Integer> failedAttempts = new HashMap<>();

    @Override
    public Optional<Alert> analyze(LogEntry logEntry) {
        if ("LOGIN_FAILED".equals(logEntry.getEventType())) {
            String ip = logEntry.getIpAddress();
            failedAttempts.put(ip, failedAttempts.getOrDefault(ip, 0) + 1);

            if (failedAttempts.get(ip) >= THRESHOLD) {
                int attempts = failedAttempts.get(ip);
                failedAttempts.put(ip, 0); 
                
                String description = String.format("Multiple failed login attempts (%d) detected from IP: %s", attempts, ip);
                return Optional.of(new Alert("Brute Force Attack", Severity.HIGH, logEntry.getTimestamp(), description));
            }
        }
        return Optional.empty();
    }
}
```

### Line-by-Line Explanation:
* Line 11: Implements the `ThreatDetector` interface contract.
* Line 12: `THRESHOLD = 5`: Constant defining the maximum allowable failed login attempts before raising an alert.
* Line 13: `private final Map<String, Integer> failedAttempts = new HashMap<>()`: State-tracking memory structure. Keys are IP addresses, values are failed attempt counts.
* Line 16: Safe string comparison `"LOGIN_FAILED".equals(...)` prevents `NullPointerException` if event type is null.
* Line 18: `failedAttempts.getOrDefault(ip, 0) + 1`: Increments the failure counter for that specific IP.
* Lines 20-27: If count reaches 5:
  * Resets counter to 0 to avoid continuous alerting on every single subsequent failure.
  * Formats a descriptive warning message.
  * Instantiates and returns a `HIGH` severity `Alert` wrapped in `Optional.of()`.
* Line 29: If benign or below threshold, returns `Optional.empty()`.

### Interview Response:
> *"The `BruteForceDetector` maintains state using an internal `HashMap<String, Integer>`. When a `LOGIN_FAILED` event occurs, it increments the failure count for that source IP. When the threshold of 5 is breached, it generates a HIGH severity alert and resets the counter to prevent alert fatigue."*

---

## FILE 7: `SQLInjectionDetector.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\detector\SQLInjectionDetector.java`
* **Purpose**: Detects attempts to compromise relational databases through unauthorized query manipulation.
* **Why it exists**: SQL Injection remains on the OWASP Top 10. Attackers inject SQL operators into user fields to bypass authentication or dump database schemas. This detector scans database query logs for signature tokens.
* **Real-World Analogy**: Airport baggage X-ray machines are programmed to sound an alarm if an object shaped like a knife or firearm is spotted inside a suitcase. This detector inspects query strings for dangerous SQL keywords.

### Complete Code:
```java
package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.model.Severity;

import java.util.List;
import java.util.Optional;

public class SQLInjectionDetector implements ThreatDetector {
    private static final List<String> SQLI_KEYWORDS = List.of(
            "SELECT", "DROP", "DELETE", "UNION", "OR 1=1", "--"
    );

    @Override
    public Optional<Alert> analyze(LogEntry logEntry) {
        if (logEntry.getQuery() != null) {
            String queryUpper = logEntry.getQuery().toUpperCase();
            for (String keyword : SQLI_KEYWORDS) {
                if (queryUpper.contains(keyword)) {
                    String description = String.format("Suspicious SQL keyword '%s' found in query from IP: %s", keyword, logEntry.getIpAddress());
                    return Optional.of(new Alert("SQL Injection Attempt", Severity.CRITICAL, logEntry.getTimestamp(), description));
                }
            }
        }
        return Optional.empty();
    }
}
```

### Line-by-Line Explanation:
* Lines 12-14: Immutable list of common SQL Injection signature patterns (`SELECT`, `DROP`, `DELETE`, `UNION`, boolean bypass `OR 1=1`, and comment operator `--`).
* Line 17: Checks if the log entry actually contains an executed query payload (`query != null`).
* Line 18: Normalizes the query string to uppercase (`query.toUpperCase()`) to defeat case-manipulation evasion tactics (like `dRoP` or `sElEcT`).
* Lines 19-24: Iterates through signature keywords. If matched, immediately returns a `CRITICAL` severity `Alert` containing the offending token and originating IP.
* Line 26: Returns `Optional.empty()` if the query is clean.

### Interview Response:
> *"The `SQLInjectionDetector` performs signature-based threat detection. It normalizes queries to uppercase to prevent case-evasion bypasses and inspects strings against OWASP-derived SQL injection markers such as `OR 1=1`, `DROP`, and comment markers `--`. Because successful SQL injection can lead to complete database compromise, it triggers a CRITICAL alert."*

---

## FILE 8: `DoSDetector.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\detector\DoSDetector.java`
* **Purpose**: Identifies volumetric Denial of Service traffic spikes originating from single source IP addresses.
* **Why it exists**: Web servers can crash if overwhelmed with excessive requests. Tracking request volume per client IP allows security systems to identify and block abusive clients before service degrades.
* **Real-World Analogy**: If someone dials your home telephone 100 times in 1 minute, they are not having a normal conversation; they are harassing you and tying up your phone line so nobody else can call.

### Complete Code:
```java
package com.socmonitor.detector;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.model.Severity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DoSDetector implements ThreatDetector {
    private static final int THRESHOLD = 10; // Scaled down for simulation demonstration
    private final Map<String, Integer> requestCounts = new HashMap<>();

    @Override
    public Optional<Alert> analyze(LogEntry logEntry) {
        String ip = logEntry.getIpAddress();
        requestCounts.put(ip, requestCounts.getOrDefault(ip, 0) + 1);

        if (requestCounts.get(ip) >= THRESHOLD) {
            int count = requestCounts.get(ip);
            requestCounts.put(ip, 0); // Reset after alerting

            String description = String.format("Possible DoS Attack! Excessive requests (%d) from IP: %s", count, ip);
            return Optional.of(new Alert("Possible DoS Attack", Severity.HIGH, logEntry.getTimestamp(), description));
        }

        return Optional.empty();
    }
}
```

### Line-by-Line Explanation:
* Line 11: Implements `ThreatDetector`.
* Line 12: `THRESHOLD = 10`: Request limit configured for the simulation dataset (in real enterprise production, this would be 500-1000 requests per minute).
* Line 13: `requestCounts`: State-tracking `HashMap` recording total hits per IP.
* Lines 17-18: Increments request counter for every incoming log entry from that IP.
* Lines 20-25: When count reaches the threshold:
  * Resets the counter.
  * Emits a `HIGH` severity `Possible DoS Attack` alert.

### Interview Response:
> *"The `DoSDetector` monitors request velocity per client IP using a frequency counter backed by a `HashMap`. In high-volume enterprise architectures, this concept forms the basis for Web Application Firewall (WAF) rate-limiting rules and DDoS scrubbing."*

---

## FILE 9: `BlacklistDetector.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\detector\BlacklistDetector.java`
* **Purpose**: Correlates network activity against a known Threat Intelligence list of malicious IP addresses.
* **Why it exists**: Security organizations maintain dynamic feeds of compromised computers, botnet controllers, and phishing servers. Even if a blacklisted IP generates a normal-looking request, their presence alone is suspicious.
* **Real-World Analogy**: Building security checking visitor IDs against an active FBI Wanted List. Even if a wanted criminal politely asks to enter the lobby, security is immediately notified.

### Complete Code:
```java
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
```

### Line-by-Line Explanation:
* Line 12: `private final Set<String> blacklistedIps`: Stores bad IPs inside a `Set` rather than a `List` to guarantee $O(1)$ constant-time lookup performance.
* Lines 14-16: Constructor delegates to `FileUtils.loadBlacklist()` to load IPs from disk during initialization.
* Lines 20-24: Checks if `blacklistedIps.contains(ip)` evaluates to true. If so, emits a `MEDIUM` severity alert.

### Interview Response:
> *"The `BlacklistDetector` implements Threat Intelligence correlation. I deliberately used a Java `HashSet` for storing blacklisted IPs because `HashSet.contains()` offers $O(1)$ lookup complexity, ensuring lightning-fast evaluations regardless of how large the threat intel list grows."*

---

## FILE 10: `LogParser.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\service\LogParser.java`
* **Purpose**: Extracts raw, unformatted text strings into clean, structured `LogEntry` domain objects.
* **Why it exists**: Raw logs contain dates, spaces, key-value pairs (`user=admin`), and quoted strings (`query="DROP TABLE users"`). `LogParser` handles all the string slicing and normalization.
* **Real-World Analogy**: An international translator at a conference who listens to speeches in different languages and translates them into a single, standardized document format.

### Complete Code:
```java
package com.socmonitor.service;

import com.socmonitor.model.LogEntry;

public class LogParser {
    
    public static LogEntry parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        try {
            String[] parts = line.split(" ", 4); 
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
```

### Line-by-Line Explanation:
* Lines 8-10: Guard clause checking for null or empty lines.
* Line 13: `line.split(" ", 4)` splits only the first 3 spaces, preventing queries containing spaces from being accidentally split apart!
* Lines 16-18: Reassembles Date (`parts[0]`) and Time (`parts[1]`) into a clean timestamp and grabs `eventType`.
* Lines 20-21: Calls helper `extractField` to cleanly extract `user=` and `ip=`.
* Lines 24-29: Handles quoted query payloads, stripping outer quotation marks.
* Lines 37-54: `extractField()` handles variable-length fields and quoted strings safely without throwing index out-of-bounds exceptions.

### Interview Response:
> *"The `LogParser` handles data ingestion and normalization. It extracts key-value attributes and uses safe string boundary tokenization to ensure database queries containing spaces and quotes do not corrupt the parsing pipeline."*

---

## FILE 11: `AlertManager.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\service\AlertManager.java`
* **Purpose**: Coordinates the alert lifecycle: storing in memory, broadcasting to the console, and appending to disk.
* **Why it exists**: If detectors wrote directly to files, you would have multiple classes locking the same file concurrently. `AlertManager` acts as the single authoritative manager for all alerts.
* **Real-World Analogy**: A central 911 dispatch office. Individual police officers on patrol call the central dispatcher, who logs the incident, informs the team over the radio, and saves the record in the police database.

### Complete Code:
```java
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
```

### Line-by-Line Explanation:
* Line 9: In-memory `ArrayList<Alert>` holding all triggered alerts for downstream analytics.
* Lines 12-15: Constructor initializes target output path and clears stale alerts from previous application runs.
* Lines 17-21: `addAlert(alert)`:
  * Adds the alert to in-memory list.
  * Prints a formatted warning to standard output.
  * Appends the alert line to `src/main/resources/alerts.txt` for persistent auditing.
* Line 23: Returns unmodifiable/readable access to the collected alert list.

### Interview Response:
> *"The `AlertManager` acts as our central alert broker. It decouples alert consumption from alert detection, handling both real-time console notification and persistent disk auditing into `alerts.txt`."*

---

## FILE 12: `ThreatMonitor.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\service\ThreatMonitor.java`
* **Purpose**: Orchestrates the threat detection pipeline by dispatching log events to registered detectors.
* **Why it exists**: Rather than having `Main` manually call 4 different detector classes, `ThreatMonitor` registers them into a dynamic list and executes them polymorphically.
* **Real-World Analogy**: The conveyor belt at an airport security checkpoint that moves bags past the X-ray, the metal detector, and the chemical sniffer in sequence.

### Complete Code:
```java
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
```

### Line-by-Line Explanation:
* Line 11: Holds a polymorphic `List<ThreatDetector>`.
* Lines 14-22: Constructor injects `AlertManager` and registers all four detector implementations into the detection pipeline.
* Lines 24-32: `processLog(LogEntry logEntry)`:
  * Iterates through every detector in the list.
  * Evaluates `detector.analyze(logEntry)`.
  * Uses Java 8 `Optional.ifPresent(alertManager::addAlert)` with a clean method reference to dispatch the alert if detected.

### Interview Response:
> *"The `ThreatMonitor` class is the orchestrator of our detection engine. It maintains a collection of `ThreatDetector` instances and dispatches incoming logs across the detector chain, forwarding any resulting alerts to the `AlertManager`."*

---

## FILE 13: `ReportGenerator.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\report\ReportGenerator.java`
* **Purpose**: Generates an executive-level post-incident forensic report summarizing log activity, alert breakdowns, and top attacker IPs.
* **Why it exists**: SOC management does not want to read hundreds of raw logs. They need a clean, structured executive summary for compliance, shift handover, and decision making.
* **Real-World Analogy**: At the end of a police shift, the sergeant writes a 1-page shift report: "Total calls: 45. Robberies: 2. Domestic disputes: 10. Neighborhood with most activity: Downtown."

### Complete Code:
```java
package com.socmonitor.report;

import com.socmonitor.model.Alert;
import com.socmonitor.model.LogEntry;
import com.socmonitor.util.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    
    public static void generateReport(List<LogEntry> logs, List<Alert> alerts, String reportFilePath) {
        StringBuilder report = new StringBuilder();
        report.append("==================================================\n");
        report.append("          SOC INCIDENT REPORT SUMMARY\n");
        report.append("==================================================\n\n");

        report.append("1. GENERAL STATISTICS\n");
        report.append("--------------------------------------------------\n");
        report.append("Total Log Entries Processed : ").append(logs.size()).append("\n");
        report.append("Total Alerts Generated      : ").append(alerts.size()).append("\n\n");

        report.append("2. ALERT BREAKDOWN BY TYPE\n");
        report.append("--------------------------------------------------\n");
        Map<String, Long> alertCounts = alerts.stream()
                .collect(Collectors.groupingBy(Alert::getAlertType, Collectors.counting()));
        
        for (Map.Entry<String, Long> entry : alertCounts.entrySet()) {
            report.append(String.format("- %-25s : %d\n", entry.getKey(), entry.getValue()));
        }
        report.append("\n");

        report.append("3. MOST ACTIVE IPs\n");
        report.append("--------------------------------------------------\n");
        Map<String, Long> ipCounts = logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getIpAddress, Collectors.counting()));
        
        ipCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> report.append(String.format("- IP: %-15s : %d requests\n", e.getKey(), e.getValue())));
        report.append("\n");

        report.append("4. SECURITY SUMMARY\n");
        report.append("--------------------------------------------------\n");
        if (alerts.isEmpty()) {
            report.append("System is Secure. No threats detected.\n");
        } else {
            report.append("Multiple threats detected! Immediate investigation required.\n");
            long criticalAlerts = alerts.stream().filter(a -> a.getSeverity() == com.socmonitor.model.Severity.CRITICAL).count();
            if (criticalAlerts > 0) {
                report.append("CRITICAL WARNING: ").append(criticalAlerts).append(" critical alert(s) found.\n");
            }
        }
        report.append("\n==================================================\n");

        FileUtils.writeToFile(reportFilePath, report.toString());
        System.out.println("Incident report generated at: " + reportFilePath);
    }
}
```

### Line-by-Line Explanation:
* Lines 15-23: Formats general counts using `StringBuilder`.
* Lines 25-32: Uses **Java Streams API** (`Collectors.groupingBy` and `Collectors.counting()`) to calculate the exact breakdown of alerts by attack type.
* Lines 34-42: Computes the Top 5 most active IP addresses across all ingested logs by grouping by IP, sorting values in descending order, and limiting the stream to 5 results.
* Lines 44-54: Generates the Executive Posture Summary. If any `CRITICAL` alerts exist, flags an urgent warning.
* Line 57: Persists the summary to `incident_report.txt` via `FileUtils`.

### Interview Response:
> *"The `ReportGenerator` leverages modern Java Streams (`Collectors.groupingBy`, sorting, and filtering) to perform forensic aggregations on logs and alerts. It outputs a standardized incident summary detailing general metrics, alert categorizations, and top offending IP addresses."*

---

## FILE 14: `FileUtils.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\util\FileUtils.java`
* **Purpose**: Centralizes safe disk I/O operations (file writing, appending, reading, clearing).
* **Why it exists**: Avoids repeating `try-with-resources` and `BufferedWriter` boilerplate code across multiple classes.
* **Real-World Analogy**: A dedicated filing clerk in an office. Whenever an employee needs to read a file, add a page to a folder, or shred a document, they ask the filing clerk rather than wandering into the records room themselves.

### Complete Code:
```java
package com.socmonitor.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtils {

    public static Set<String> loadBlacklist(String filePath) {
        Set<String> blacklist = new HashSet<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (!line.trim().isEmpty() && !line.startsWith("#")) {
                    blacklist.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load blacklist from " + filePath + ". File might not exist yet.");
        }
        return blacklist;
    }

    public static void appendLine(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
        }
    }

    public static void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
        }
    }

    public static void clearFile(String filePath) {
        try {
            new FileWriter(filePath, false).close();
        } catch (IOException e) {
            // Ignored
        }
    }
}
```

### Line-by-Line Explanation:
* Lines 12-25: `loadBlacklist()` reads all lines using Java NIO `Files.readAllLines()`, trims whitespace, ignores comment lines starting with `#`, and returns a `HashSet<String>`.
* Lines 27-34: `appendLine()` uses `try-with-resources` wrapping a `BufferedWriter` around `new FileWriter(filePath, true)` (the `true` flag enables append mode instead of overwrite mode).
* Lines 36-42: `writeToFile()` overwrites a file with complete report content.
* Lines 44-50: `clearFile()` truncates an existing file to 0 bytes by opening a `FileWriter` with append set to `false` and immediately closing it.

### Interview Response:
> *"I implemented `FileUtils` as a utility class with static helper methods to encapsulate safe Java File I/O. It utilizes `try-with-resources` to guarantee that file handles and buffers are automatically closed even if an exception occurs, preventing resource leaks."*

---

## FILE 15: `Main.java`
* **Path**: `c:\Users\shive\Desktop\SOC\SOC-Monitor\src\main\java\com\socmonitor\Main.java`
* **Purpose**: System entry point; bootstraps services, coordinates the execution loop, and terminates.
* **Why it exists**: Every Java application requires a `public static void main(String[] args)` method to start execution.
* **Real-World Analogy**: The director of an orchestra who signals the musicians when to start, coordinates their playing, and signals the grand finale.

### Complete Code:
```java
package com.socmonitor;

import com.socmonitor.model.LogEntry;
import com.socmonitor.report.ReportGenerator;
import com.socmonitor.service.AlertManager;
import com.socmonitor.service.LogParser;
import com.socmonitor.service.ThreatMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String LOG_FILE = "src/main/resources/logs.txt";
    private static final String BLACKLIST_FILE = "src/main/resources/blacklist.txt";
    private static final String ALERT_FILE = "src/main/resources/alerts.txt";
    private static final String REPORT_FILE = "incident_report.txt";

    public static void main(String[] args) {
        System.out.println("Starting SOC Log Monitoring System...");

        AlertManager alertManager = new AlertManager(ALERT_FILE);
        ThreatMonitor monitor = new ThreatMonitor(alertManager, BLACKLIST_FILE);
        List<LogEntry> allLogs = new ArrayList<>();

        try {
            List<String> logLines = Files.readAllLines(Paths.get(LOG_FILE));
            
            for (String line : logLines) {
                LogEntry entry = LogParser.parse(line);
                if (entry != null) {
                    allLogs.add(entry);
                    monitor.processLog(entry);
                }
            }
            
            System.out.println("Log processing complete. Generating report...");
            ReportGenerator.generateReport(allLogs, alertManager.getAlerts(), REPORT_FILE);

        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }
}
```

### Line-by-Line Explanation:
* Lines 16-19: Constants defining paths for logs, blacklist, alerts, and report.
* Lines 24-25: Instantiates `AlertManager` and `ThreatMonitor`.
* Line 29: Ingests the entire log stream into a list of strings.
* Lines 31-37: For each line, parses it into a `LogEntry`, stores it in `allLogs`, and passes it to `monitor.processLog(entry)`.
* Line 40: Triggers `ReportGenerator.generateReport()`.

### Interview Response:
> *"`Main.java` serves as the driver class. It sets up file path configurations, instantiates our services, iterates through the ingested telemetry stream, and initiates final reporting upon completion."*

---

# SECTION 5: EXECUTION FLOW & SEQUENCE DIAGRAMS

## 1. ASCII Sequence Diagram of Full Execution

```
User/Terminal         Main.java        LogParser       ThreatMonitor     Detectors     AlertManager    ReportGenerator
      |                   |                |                 |               |               |                |
   1. | -- mvn exec:java ->                |                 |               |               |                |
      |                   |                |                 |               |               |                |
      |                   | 2. Instantiates AlertManager & ThreatMonitor     |               |                |
      |                   |--------------------------------->|               |               |                |
      |                   |                                  | 3. Loads Blacklist.txt        |                |
      |                   |                                  | 4. Registers 4 Detectors      |                |
      |                   |                                  |-------------->|               |                |
      |                   |                                  |               |               |                |
      |                   | 5. Files.readAllLines(logs.txt)  |               |               |                |
      |                   |                                  |               |               |                |
      |                   | 6. parse(line) |                 |               |               |                |
      |                   |--------------->|                 |               |               |                |
      |                   |<- LogEntry ----|                 |               |               |                |
      |                   |                                  |               |               |                |
      |                   | 7. processLog(LogEntry)          |               |               |                |
      |                   |--------------------------------->|               |               |                |
      |                   |                                  | 8. analyze(entry)             |                |
      |                   |                                  |-------------->|               |                |
      |                   |                                  |<- Opt<Alert> -|               |                |
      |                   |                                  |                               |                |
      |                   |                                  | 9. If Alert present:          |                |
      |                   |                                  |    addAlert(Alert)            |                |
      |                   |                                  |------------------------------>|                |
      |                   |                                  |                               | 10. Print to Console
      |                   |                                  |                               | 11. Append to alerts.txt
      |                   |                                  |                               |                |
      |                   | 12. End of lines: generateReport(allLogs, alerts)                |                |
      |                   |---------------------------------------------------------------------------------->|
      |                   |                                                                  |                | 13. Java Streams
      |                   |                                                                  |                | 14. Write incident_report.txt
      |                   |<- Complete -----------------------------------------------------------------------|
      | 15. Program exits |
      v                   v
```

---

# SECTION 6: CYBERSECURITY CONCEPTS & SOC FOUNDATIONS

### 1. SOC (Security Operations Center)
* **Definition**: A centralized department within an enterprise where security analysts continuously monitor, assess, and defend computer systems, networks, and databases against cyberattacks.
* **Project Connection**: This project simulates the automated detection engine of a Tier 1 SOC Analyst workstation.

### 2. SIEM (Security Information and Event Management)
* **Definition**: A software platform that aggregates log and event data from across an organization's applications, servers, and network hardware, correlates the events, and flags anomalous activity.
* **Project Connection**: Our system performs the two core functions of a SIEM: **Log Ingestion/Parsing** and **Rule-Based Correlation**.

### 3. Incident Response (IR)
* **Definition**: The structured process an organization follows when handling a cybersecurity breach (Identification, Containment, Eradication, Recovery, Lessons Learned).
* **Project Connection**: Our system automates the **Identification** phase by generating structured alerts and the **Documentation** phase by producing the `incident_report.txt`.

### 4. Indicators of Compromise (IoC)
* **Definition**: Forensic digital evidence indicating that a system has been compromised or targeted by attackers (e.g., specific bad IP addresses, malicious hashes, unusual query strings).
* **Project Connection**: Our `BlacklistDetector` uses malicious IP addresses as IoCs; our `SQLInjectionDetector` uses attack payload strings as IoCs.

### 5. Threat Intelligence (TI)
* **Definition**: Evidence-based cyber threat knowledge, including context, mechanisms, indicators, and actionable advice about existing or emerging hazards.
* **Project Connection**: Represented by `blacklist.txt`, simulating an external threat intelligence feed of known attacker IPs.

### 6. Brute Force Attack
* **Definition**: A trial-and-error method used by automated bots to guess login credentials (usernames and passwords).
* **Project Connection**: `BruteForceDetector` counts consecutive `LOGIN_FAILED` events by IP and triggers a `HIGH` alert at 5 failures.

### 7. SQL Injection (SQLi)
* **Definition**: An attack that exploits insecure input handling to inject malicious SQL commands into database queries, allowing attackers to view or destroy data.
* **Project Connection**: `SQLInjectionDetector` detects tokens like `DROP`, `UNION`, `SELECT`, and `OR 1=1` and triggers a `CRITICAL` alert.

### 8. Denial of Service (DoS)
* **Definition**: An attack designed to overwhelm a server or network with excessive traffic, making it unavailable to legitimate users.
* **Project Connection**: `DoSDetector` monitors request frequency and flags an alert when an IP exceeds 10 requests.

### 9. Alert Fatigue & Threshold Tuning
* **Definition**: The mental exhaustion experienced by SOC analysts when overwhelmed by too many false alarms, causing them to miss real attacks.
* **Project Connection**: Our detectors **reset their counters** once an alert triggers, preventing hundreds of duplicate alerts for the same incident.

---

# SECTION 7: JAVA CONCEPTS USED

### 1. Object-Oriented Programming (OOP) Pillars
* **Encapsulation**: 
  * *Concept*: Bundling data (state) and methods (behavior) within a single unit and restricting direct access to internal fields using the `private` modifier and exposing public getters.
  * *In Project*: In `LogEntry.java` and `Alert.java`, fields like `timestamp`, `ipAddress`, and `alertId` are `private`. External classes cannot corrupt the state; they can only read values through immutable getters.
  * *Interview Answer*: *"I applied strict encapsulation across domain models like `LogEntry` and `Alert`. All member fields are private, making instances effectively immutable once instantiated, preventing thread contention or unintended mutation."*
* **Abstraction**: 
  * *Concept*: Hiding complex implementation details and showing only the essential public interface.
  * *In Project*: The `ThreatDetector` interface exposes a single method: `Optional<Alert> analyze(LogEntry logEntry)`. `ThreatMonitor` does not care if the detector does string searches, database lookups, or counts in a map.
  * *Interview Answer*: *"Abstraction is achieved through the `ThreatDetector` interface. It defines what a detector does (`analyze`), not how it does it, decoupling the engine from specific detection algorithms."*
* **Polymorphism**: 
  * *Concept*: The ability of an object to take on many forms. In Java, this allows a parent interface reference to invoke overridden methods in child classes at runtime (dynamic method dispatch).
  * *In Project*: `ThreatMonitor` maintains a `List<ThreatDetector>`. At runtime, when `detector.analyze(logEntry)` is called in the loop, Java dynamically executes the specific implementation for `BruteForceDetector`, `SQLInjectionDetector`, etc.
  * *Interview Answer*: *"I utilized subtype polymorphism to register heterogeneous threat detection rules in a single collection. Adding new rules requires zero changes to the dispatcher loop."*
* **Inheritance & Interface Realization**:
  * *Concept*: Creating new classes built upon existing interfaces or base classes.
  * *In Project*: All four detector classes use `implements ThreatDetector`, adhering to a common contractual behavior.

---

### 2. SOLID Principles in Practice
* **Single Responsibility Principle (SRP)**:
  * Every class has one, and only one, reason to change.
  * `LogParser`: Only changes if log format changes.
  * `BruteForceDetector`: Only changes if brute-force criteria change.
  * `AlertManager`: Only changes if alert storage/notification changes.
  * `ReportGenerator`: Only changes if report layout/metrics change.
* **Open/Closed Principle (OCP)**:
  * Software entities should be open for extension, but closed for modification.
  * To add a "RansomwareDetector", we don't modify existing detectors. We simply create a new class implementing `ThreatDetector` and add it to the detector list in `ThreatMonitor`.
* **Liskov Substitution Principle (LSP)**:
  * Subtypes must be substitutable for their base types without altering program correctness.
  * Any instance of `ThreatDetector` can be substituted into `ThreatMonitor` and it will behave predictably without unexpected exceptions.
* **Interface Segregation Principle (ISP)**:
  * Clients should not be forced to depend on methods they do not use.
  * `ThreatDetector` defines only one clean method (`analyze`). It doesn't force detectors to implement reporting, parsing, or logging methods.
* **Dependency Inversion Principle (DIP)**:
  * High-level modules should not depend on low-level modules; both should depend on abstractions.
  * `ThreatMonitor` depends on the abstraction `ThreatDetector`, not concrete detector classes.

---

### 3. Java Collections Framework (JCF)
* **`List<T>` & `ArrayList<T>`**:
  * Used in `ThreatMonitor` to store ordered detector chains and in `AlertManager` to collect triggered alerts dynamically.
  * Provides $O(1)$ amortized insertion and fast iteration.
* **`Set<T>` & `HashSet<T>`**:
  * Used in `FileUtils.loadBlacklist()` to hold blacklisted IPs.
  * Backed by a hash table; provides $O(1)$ constant time complexity for `contains(ip)`, which is critical when matching against tens of thousands of threat intel indicators.
* **`Map<K,V>` & `HashMap<K,V>`**:
  * Used in `BruteForceDetector` (`Map<String, Integer>`) and `DoSDetector` to track per-IP state across log streams.
  * Enables instantaneous key-based counter increments using `map.getOrDefault(key, 0) + 1`.

---

### 4. Modern Java Features (Java 8 - 17)
* **`java.util.Optional<T>`**:
  * Avoids returning error-prone `null` references when an event is clean.
  * Allows elegant functional chaining: `detector.analyze(logEntry).ifPresent(alertManager::addAlert)`.
* **Java Streams API & Collectors**:
  * Used in `ReportGenerator` for declarative forensic aggregation:
    ```java
    Map<String, Long> alertCounts = alerts.stream()
        .collect(Collectors.groupingBy(Alert::getAlertType, Collectors.counting()));
    ```
  * Replaces clunky nested `for` loops with concise, readable, and parallelizable processing.
* **Method References (`Class::method`)**:
  * `alertManager::addAlert` acts as a concise lambda shorthand for `alert -> alertManager.addAlert(alert)`.
* **`UUID.randomUUID()`**:
  * Generates cryptographically strong, collision-resistant 128-bit identifiers for audit ticket tracking.
* **Java NIO (`java.nio.file.Files`) & `try-with-resources`**:
  * `Files.readAllLines()` provides high-level modern file reading.
  * `try (BufferedWriter writer = ...)` guarantees deterministic automatic closure of file handles, preventing memory and file descriptor leaks.

---

# SECTION 8: SYSTEM DESIGN EXPLANATION

## 1. Why Detectors are Separated
In early prototype software, developers often write one massive "God Class" containing a 500-line `if/else` block checking for failed logins, SQL keywords, and IP lookups all mixed together. 

**Why this fails in enterprise SOC engineering:**
1. **Merge Conflicts**: Multiple security engineers cannot work on different detection rules simultaneously.
2. **Regression Risks**: Modifying SQL detection code could accidentally break login detection.
3. **Testing Nightmare**: Unit testing individual rules becomes impossible without feeding complex multi-faceted logs.
4. **Modularity**: By separating detectors into standalone classes implementing `ThreatDetector`, each rule is isolated, unit-testable, and independently tuneable.

---

## 2. Why the `ThreatDetector` Interface Was Used (Strategy Pattern)
The Strategy Pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. In this project:
* The Strategy is `ThreatDetector`.
* The Concrete Strategies are `BruteForceDetector`, `SQLInjectionDetector`, `DoSDetector`, and `BlacklistDetector`.
* The Context is `ThreatMonitor`.

```
                  +----------------------+
                  |   <<interface>>      |
                  |   ThreatDetector     |
                  +----------------------+
                  | +analyze(): Optional |
                  +----------------------+
                             ^
                             | implements
         +-------------------+-------------------+
         |                   |                   |
+------------------+ +------------------+ +------------------+
| BruteForceDet... | | SQLInjectionDet. | |  BlacklistDet... |
+------------------+ +------------------+ +------------------+
```

---

## 3. Why `AlertManager` Exists (Broker & Facade Pattern)
If individual detectors wrote directly to `alerts.txt`:
1. File locking conflicts would emerge as multiple threads write concurrently.
2. Detectors would become tightly coupled to the local file system, violating Single Responsibility.
3. Adding a new alert destination (e.g., Slack webhook, email notification, Syslog) would require editing every single detector!
`AlertManager` centralizes dispatching. If tomorrow you want alerts sent via HTTP to an AWS SNS topic, you only modify `AlertManager.addAlert()`.

---

## 4. Why `ReportGenerator` is Separated
Log parsing and threat detection happen *in-stream* (line-by-line). Incident reporting happens *post-run* across the aggregated historical dataset. Keeping `ReportGenerator` distinct ensures the runtime detection pipeline is not weighed down by heavy statistical aggregations until the stream concludes.

---

## 5. Scalability, Maintainability, and Extensibility
| Dimension | How It Is Addressed in This System | Enterprise Evolution Path |
| :--- | :--- | :--- |
| **Maintainability** | Clean package layout, standard naming conventions, zero third-party dependencies beyond standard JDK. | Package as an internal corporate library / Maven dependency. |
| **Extensibility** | New rules added by implementing `ThreatDetector` interface; registered in `ThreatMonitor`. | Load detection rules dynamically at runtime via Java SPI (`ServiceLoader`) or Spring `@Component` scanning. |
| **Scalability** | Constant-time $O(1)$ Hash lookups; fast in-memory string scanning. | Replace `Files.readAllLines` with streaming `BufferedReader` or reactive Kafka consumers for multi-gigabyte log streams. |

---

# SECTION 9: DETECTOR DEEP DIVE

---

## 1. `BruteForceDetector`
* **Detection Goal**: Detect automated credential-guessing attacks before accounts are breached.
* **Core Algorithm**:
  ```
  1. Check if eventType == "LOGIN_FAILED". If false, return Empty.
  2. Extract source IP.
  3. Look up IP in failedAttempts map.
  4. Increment count by 1.
  5. If count >= 5:
        Reset count to 0.
        Emit HIGH Severity Alert.
     Else:
        Return Empty.
  ```
* **Data Structure**: `Map<String, Integer>` (`HashMap`).
* **Time Complexity**: $O(1)$ amortized per log entry (instantaneous hash lookup).
* **Space Complexity**: $O(U)$ where $U$ is the number of unique IP addresses failing logins.
* **Real SOC Equivalent**: Splunk correlation search:
  `index=auth action="failure" | stats count by src_ip | where count >= 5`

---

## 2. `SQLInjectionDetector`
* **Detection Goal**: Prevent unauthorized exfiltration or manipulation of relational database data.
* **Core Algorithm**:
  ```
  1. Check if logEntry has an executed SQL query (query != null).
  2. Normalize query to uppercase: queryUpper = query.toUpperCase().
  3. For each keyword in ["SELECT", "DROP", "DELETE", "UNION", "OR 1=1", "--"]:
        If queryUpper.contains(keyword):
           Emit CRITICAL Severity Alert with offending token.
  4. If loop completes with no match, return Empty.
  ```
* **Data Structure**: Immutable `List<String>` for signature tokens.
* **Time Complexity**: $O(K \times M)$ where $K$ is number of keywords (6) and $M$ is query length. In practice, virtually instantaneous.
* **Space Complexity**: $O(1)$ constant memory overhead.
* **Real SOC Equivalent**: Web Application Firewall (WAF) ModSecurity Core Rule Set (CRS) inspecting HTTP GET/POST parameters for SQL meta-characters.

---

## 3. `DoSDetector`
* **Detection Goal**: Identify volumetric packet/request floods targeting web services.
* **Core Algorithm**:
  ```
  1. Extract source IP.
  2. Increment total requests counter in requestCounts map.
  3. If count >= 10 (simulated threshold):
        Reset count to 0.
        Emit HIGH Severity Alert.
  4. Else, return Empty.
  ```
* **Data Structure**: `HashMap<String, Integer>`.
* **Time Complexity**: $O(1)$ constant time lookup.
* **Space Complexity**: $O(U)$ where $U$ is total distinct client IPs.
* **Real SOC Equivalent**: Cloudflare / AWS Shield Rate Limiting Rules (e.g., block any IP generating > 100 requests per 10-second sliding window).

---

## 4. `BlacklistDetector`
* **Detection Goal**: Detect command-and-control (C2) beaconing, botnet nodes, or known hostile threat actors.
* **Core Algorithm**:
  ```
  1. In constructor, read blacklist.txt into HashSet<String>.
  2. Extract source IP from logEntry.
  3. If blacklistedIps.contains(ip):
        Emit MEDIUM Severity Alert.
  4. Else, return Empty.
  ```
* **Data Structure**: `HashSet<String>`.
* **Time Complexity**: $O(1)$ constant-time lookup. (Using a `List` would have been $O(N)$ linear time).
* **Space Complexity**: $O(B)$ where $B$ is the number of blacklisted indicators.
* **Real SOC Equivalent**: Cisco Talos, AlienVault OTX, or Palo Alto Networks Dynamic Block Lists (DBLs).

---

# SECTION 10: REAL WORLD SOC & SIEM COMPARISON

| Feature / Capability | My Java SOC System | Splunk Enterprise | IBM QRadar | ELK Stack (Elasticsearch) | Microsoft Sentinel |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Log Ingestion** | Custom `LogParser` text tokenization | Universal Forwarders, Syslog, HTTP Event Collector (HEC) | WinCollect, Syslog, QRadar Log Forwarder | Logstash / Elastic Beats (Filebeat) | Azure Monitor Agent, Data Collection Rules (DCR) |
| **Storage Layer** | Local filesystem (`alerts.txt`) | Proprietary hot/warm index buckets | Aerial database (Ariel Query Language) | Inverted Index (Lucene documents) | Azure Log Analytics Workspace (Kusto DB) |
| **Correlation Engine** | Java Strategy Pattern (`ThreatDetector`) | Search Processing Language (SPL) scheduled alerts | Custom Rules Engine (CRE) | ElastAlert / Kibana Alerting Engine | Kusto Query Language (KQL) Analytics Rules |
| **Threat Intel** | Flat file `blacklist.txt` | Enterprise Security (ES) Threat Intel Framework | Threat Intelligence App (TAXII / STIX feeds) | Elastic Threat Intelligence Integration | Sentinel Threat Intelligence (STIX/TAXII, Microsoft Graph) |
| **Incident Reporting** | `ReportGenerator` (`incident_report.txt`) | Scheduled Dashboard PDF Exports | Automated Compliance & Executive Reports | Kibana Canvas / PDF Reporting | PowerBI Dashboards & Sentinel Workbooks |
| **Scale Target** | Single JVM / Embedded / Local Testing | Terabytes/Day across Distributed Clusters | Enterprise On-Prem / Cloud hybrid | Petabyte-scale distributed cluster | Cloud-native serverless elastic scale |

---

# SECTION 11: 150 INTERVIEW QUESTIONS & MODEL ANSWERS

---

## PART 1: 50 BEGINNER QUESTIONS

#### Q1: What is the primary objective of this project?
* **Answer**: To build an automated Security Operations Center (SOC) log monitoring system in Java that reads server logs, detects security threats (Brute Force, SQLi, DoS, Blacklisted IPs), raises alerts, and generates an executive incident report.
* **Follow-up**: Why not just read the logs manually?
* **Best Interview Response**: *"Manual log review is impossible in modern enterprise environments where servers generate thousands of events per second. Automated detection systems are essential to catch cyberattacks in real time before damage occurs."*

#### Q2: What version of Java did you use and why?
* **Answer**: Java 17 LTS (Long-Term Support).
* **Follow-up**: What features of Java 17 did you benefit from?
* **Best Interview Response**: *"Java 17 provides long-term enterprise stability, enhanced performance in garbage collection, `List.of()` factory methods, `Optional`, and strong typing."*

#### Q3: What is Maven and why did you use it?
* **Answer**: Apache Maven is a build automation and dependency management tool.
* **Follow-up**: What happens when you run `mvn clean compile`?
* **Best Interview Response**: *"Maven deletes previous build artifacts in the `target/` directory and compiles the `.java` source files in `src/main/java` into `.class` bytecode."*

#### Q4: What is a SOC Analyst?
* **Answer**: A cybersecurity professional who monitors an organization's IT infrastructure, detects unauthorized activity, triages security alerts, and participates in incident containment.
* **Follow-up**: What tier does this system emulate?
* **Best Interview Response**: *"This project simulates Tier 1 automated alert triage and forensic reporting, which frees human analysts to focus on Tier 2 in-depth threat hunting."*

#### Q5: What is a SIEM?
* **Answer**: Security Information and Event Management platform that aggregates, correlates, and analyzes security logs from multiple network devices.
* **Follow-up**: How does your project act like a SIEM?
* **Best Interview Response**: *"My project implements the two foundational pillars of a SIEM: ingestion/normalization via `LogParser` and rule-based correlation via `ThreatMonitor`."*

#### Q6: What is a `LogEntry` in your project?
* **Answer**: A domain model class representing a single structured log line containing timestamp, event type, username, IP, and query.
* **Follow-up**: Why did you make its fields private?
* **Best Interview Response**: *"To enforce encapsulation and immutability. Once a log entry is created, its contents should not be modified by any detector."*

#### Q7: What is an `Alert`?
* **Answer**: An entity generated when a threat detector flags an attack, containing an Alert ID, type, severity, timestamp, and description.
* **Follow-up**: How do you uniquely identify alerts?
* **Best Interview Response**: *"I used Java's `UUID.randomUUID()` to generate collision-resistant 128-bit unique ticket IDs for incident tracking."*

#### Q8: What are the four severity levels in your `Severity` enum?
* **Answer**: LOW, MEDIUM, HIGH, and CRITICAL.
* **Follow-up**: Why use an `enum` instead of string constants?
* **Best Interview Response**: *"Enums provide compile-time type safety, eliminate typographical errors, and allow fast equality checks."*

#### Q9: What is Brute Force attack detection?
* **Answer**: Identifying when an attacker repeatedly tries guessing login passwords.
* **Follow-up**: What is your detection rule?
* **Best Interview Response**: *"If 5 or more `LOGIN_FAILED` events originate from the same IP address, a HIGH severity Brute Force alert is triggered."*

#### Q10: What is SQL Injection (SQLi)?
* **Answer**: An injection vulnerability where an attacker passes malicious SQL operators into application input fields to manipulate backend database queries.
* **Follow-up**: What severity did you assign to SQL Injection?
* **Best Interview Response**: *"CRITICAL severity, because a successful SQL injection attack can lead to total database theft, authentication bypass, or data destruction."*

#### Q11: What is a Denial of Service (DoS) attack?
* **Answer**: An attack that attempts to make a machine or network resource unavailable by flooding it with excessive requests.
* **Follow-up**: How does your project detect it?
* **Best Interview Response**: *"By counting the total requests received per IP address and triggering an alert when an IP breaches a volumetric threshold."*

#### Q12: What is an Indicator of Compromise (IoC)?
* **Answer**: A forensic digital artifact that points to malicious computer activity, such as a known bad IP or malicious file hash.
* **Follow-up**: Give an example from your project.
* **Best Interview Response**: *"The IP addresses listed in `blacklist.txt` serve as reputational IoCs; any internal event matching those IPs generates an alert."*

#### Q13: What does `ThreatDetector` interface do?
* **Answer**: It defines the common contract `Optional<Alert> analyze(LogEntry logEntry)` implemented by all security detection classes.
* **Follow-up**: Why use an interface here?
* **Best Interview Response**: *"It applies the Strategy Design Pattern and Open/Closed Principle, allowing `ThreatMonitor` to run any detector without knowing its internal implementation."*

#### Q14: What is `LogParser` responsible for?
* **Answer**: Reading raw text strings from logs and splitting them into structured `LogEntry` objects.
* **Follow-up**: What happens if a log line is malformed?
* **Best Interview Response**: *"The parser catches the exception, logs an error to `System.err`, and returns `null`, preventing the entire application from crashing."*

#### Q15: What is `AlertManager`?
* **Answer**: A centralized service that stores generated alerts in memory, prints them live to the console, and appends them to `alerts.txt`.
* **Follow-up**: Why clear `alerts.txt` on startup?
* **Best Interview Response**: *"To ensure each execution run starts with a clean slate for debugging, while `incident_report.txt` provides the permanent post-run summary."*

#### Q16: What is `ReportGenerator`?
* **Answer**: A service that compiles all processed logs and generated alerts into an executive summary text report named `incident_report.txt`.
* **Follow-up**: What key metrics does it display?
* **Best Interview Response**: *"Total logs processed, total alerts generated, alert breakdown by type, top 5 most active IP addresses, and an overall security posture evaluation."*

#### Q17: What does `FileUtils` do?
* **Answer**: A utility class with static helper methods for file reading, writing, appending, and clearing.
* **Follow-up**: Why create a utility class?
* **Best Interview Response**: *"To follow the DRY (Don't Repeat Yourself) principle, encapsulating Java I/O boilerplate and `try-with-resources` logic in one place."*

#### Q18: What is `Main.java`?
* **Answer**: The application entry point containing the `main()` method that coordinates the workflow.
* **Follow-up**: What is the first thing `Main` does?
* **Best Interview Response**: *"It instantiates `AlertManager` and `ThreatMonitor`, which initializes the detection rules and loads the threat intel blacklist."*

#### Q19: What is `Optional<T>` in Java?
* **Answer**: A container object introduced in Java 8 used to represent the presence or absence of a value.
* **Follow-up**: Why use it instead of returning `null`?
* **Best Interview Response**: *"Returning `null` frequently causes `NullPointerException`. `Optional` explicitly forces callers to handle the empty case safely."*

#### Q20: What is a `HashMap`?
* **Answer**: A key-value collection that uses hashing to provide $O(1)$ average time complexity for insertions and lookups.
* **Follow-up**: Where is it used in your project?
* **Best Interview Response**: *"In `BruteForceDetector` and `DoSDetector` to track request and failure counts associated with each source IP."*

#### Q21: What is a `HashSet`?
* **Answer**: A collection of unique elements backed by a hash table that offers constant-time $O(1)$ lookup.
* **Follow-up**: Why use `HashSet` for the blacklist?
* **Best Interview Response**: *"Because checking whether an IP is blacklisted via `contains()` takes $O(1)$ time in a `HashSet`, compared to $O(N)$ linear time in an `ArrayList`."*

#### Q22: What is encapsulation?
* **Answer**: Restricting direct access to an object's internal fields using `private` variables and exposing access through public methods.
* **Follow-up**: How does `Alert` demonstrate encapsulation?
* **Best Interview Response**: *"Its fields like `alertId` and `severity` are private with only getters provided, guaranteeing that alert data cannot be tampered with after creation."*

#### Q23: What is polymorphism?
* **Answer**: The ability of different classes to respond to the same interface method in their own unique way.
* **Follow-up**: How does your project use polymorphism?
* **Best Interview Response**: *"`ThreatMonitor` calls `detector.analyze(logEntry)` on a collection of `ThreatDetector` references. At runtime, Java executes the specific detection logic for each detector class."*

#### Q24: What is abstraction?
* **Answer**: Hiding complex internal implementation details and exposing only what is necessary to the user.
* **Follow-up**: Where is abstraction visible?
* **Best Interview Response**: *"In `ThreatDetector.java`. It declares `analyze()` without exposing how brute force counting or regex keyword checking is implemented."*

#### Q25: What is the purpose of `try-with-resources`?
* **Answer**: A statement that automatically closes resources (like file streams and writers) at the end of the block.
* **Follow-up**: What interface must a resource implement?
* **Best Interview Response**: *"`AutoCloseable`. Using it in `FileUtils` prevents file lock leaks and memory leaks even if an I/O exception is thrown."*

#### Q26: What is a UUID?
* **Answer**: Universally Unique Identifier, a 128-bit number used to uniquely identify information in computer systems.
* **Follow-up**: How is it created in Java?
* **Best Interview Response**: *"Via `UUID.randomUUID().toString()`, generating standard RFC 4122 v4 pseudorandom UUIDs."*

#### Q27: How does `String.split()` work in `LogParser`?
* **Answer**: It splits a string around matches of a given delimiter.
* **Follow-up**: Why did you write `line.split(" ", 4)` with limit 4?
* **Best Interview Response**: *"The limit parameter prevents spaces inside database query strings from splitting into extra array elements, preserving quoted SQL payloads intact."*

#### Q28: How do you check string equality in Java?
* **Answer**: Using `.equals()` or `.equalsIgnoreCase()`, never `==`.
* **Follow-up**: Why is `==` incorrect for strings?
* **Best Interview Response**: *"`==` compares memory references, whereas `.equals()` compares the actual character contents of the strings."*

#### Q29: What does `"LOGIN_FAILED".equals(eventType)` accomplish?
* **Answer**: It checks if `eventType` equals `"LOGIN_FAILED"` without risking a `NullPointerException` if `eventType` happens to be `null`.
* **Follow-up**: What is this pattern called?
* **Best Interview Response**: *"It is called a 'Yoda Condition' or null-safe literal comparison."*

#### Q30: What is alert fatigue?
* **Answer**: The condition where security analysts receive so many low-value or duplicate alerts that they become desensitized and miss real attacks.
* **Follow-up**: How does your project combat alert fatigue?
* **Best Interview Response**: *"By resetting counters once a threshold is met and categorizing alerts by severity so critical issues are investigated first."*

#### Q31: What is a False Positive?
* **Answer**: An alert that incorrectly indicates an attack is occurring when the activity is actually benign.
* **Follow-up**: Give an example in this project.
* **Best Interview Response**: *"A database developer executing a legitimate `SELECT * FROM users` query might trigger an alert if the detector simply checks for the word `SELECT`."*

#### Q32: What is a False Negative?
* **Answer**: When an actual cyberattack occurs, but the security monitoring system fails to detect or alert on it.
* **Follow-up**: Why are false negatives dangerous?
* **Best Interview Response**: *"False negatives mean an active breach is underway without the SOC team's knowledge, allowing attackers to exfiltrate data undetected."*

#### Q33: What is the Java Streams API?
* **Answer**: A functional programming feature introduced in Java 8 for processing sequences of elements with operations like filter, map, sorted, and collect.
* **Follow-up**: Where is it used in your project?
* **Best Interview Response**: *"In `ReportGenerator` to aggregate alert categories and calculate the top 5 most active IP addresses."*

#### Q34: What does `Collectors.groupingBy()` do?
* **Answer**: It groups stream elements according to a classification function and returns a `Map`.
* **Follow-up**: How is it used for alerts?
* **Best Interview Response**: *"`alerts.stream().collect(Collectors.groupingBy(Alert::getAlertType, Collectors.counting()))` produces a map of alert names to their frequency."*

#### Q35: What is method reference syntax in Java?
* **Answer**: A shorthand notation for a lambda expression calling a specific method, written as `ClassName::methodName`.
* **Follow-up**: Give an example from your project.
* **Best Interview Response**: *"`alertOpt.ifPresent(alertManager::addAlert)` cleanly passes the alert to the alert manager."*

#### Q36: What is a blacklist in cybersecurity?
* **Answer**: A list of entities (such as IP addresses, domain names, or file hashes) that are explicitly blocked or flagged due to known malicious reputation.
* **Follow-up**: What is the opposite of a blacklist?
* **Best Interview Response**: *"A whitelist (or allowlist), where only explicitly approved entities are permitted and all others are blocked by default."*

#### Q37: What is the difference between a high and critical severity alert?
* **Answer**: High severity indicates a probable attack attempt (like brute force). Critical severity indicates an active exploit targeting core assets (like SQL injection against a database).
* **Follow-up**: How should a SOC analyst respond differently?
* **Best Interview Response**: *"Critical alerts require immediate incident containment and potential host isolation, whereas High alerts require priority investigation within standard SLA windows."*

#### Q38: What does `BufferedWriter` provide over `FileWriter`?
* **Answer**: It buffers characters in memory before writing to disk, significantly reducing expensive disk I/O operations.
* **Follow-up**: Why is buffering important?
* **Best Interview Response**: *"Writing to disk byte-by-byte creates heavy I/O overhead. Buffering batches writes into memory blocks, maximizing throughput."*

#### Q39: What is `StringBuilder`?
* **Answer**: A mutable sequence of characters used to concatenate strings efficiently in a single thread.
* **Follow-up**: Why not use the `+` operator in a loop?
* **Best Interview Response**: *"Strings are immutable in Java. Using `+` in loops creates numerous short-lived `String` objects, causing memory churn and garbage collection pauses."*

#### Q40: What happens if `logs.txt` does not exist?
* **Answer**: Java throws an `IOException`, which is caught in `Main.java`, printing an error message to `System.err` without crashing ungracefully.
* **Follow-up**: What type of exception is `IOException`?
* **Best Interview Response**: *"It is a checked exception, meaning the Java compiler forces developers to either catch it or declare it in the method signature."*

#### Q41: What is a checked vs. unchecked exception in Java?
* **Answer**: Checked exceptions inherit from `Exception` and must be handled at compile time. Unchecked exceptions inherit from `RuntimeException` and occur during execution (e.g., `NullPointerException`).
* **Follow-up**: Which did you handle in file operations?
* **Best Interview Response**: *"Checked exceptions (`IOException`) are handled with try-catch blocks in `FileUtils` and `Main`."*

#### Q42: What is the Single Responsibility Principle?
* **Answer**: A principle stating that a class should have only one reason to change.
* **Follow-up**: How does `LogParser` follow this?
* **Best Interview Response**: *"`LogParser` is only responsible for parsing text into domain models. It does not perform detection, write files, or print alerts."*

#### Q43: How do you run your project from the terminal?
* **Answer**: Using `mvn clean compile exec:java "-Dexec.mainClass=com.socmonitor.Main"`.
* **Follow-up**: Why are quotation marks necessary around `-Dexec.mainClass` on Windows?
* **Best Interview Response**: *"In PowerShell, unquoted `-D` arguments can be misinterpreted by the shell parser as PowerShell parameters."*

#### Q44: Where are generated alerts stored?
* **Answer**: In `src/main/resources/alerts.txt`.
* **Follow-up**: Can external tools read this file?
* **Best Interview Response**: *"Yes, standard syslog forwarders or SIEM agents (like Filebeat) can easily ingest `alerts.txt` into enterprise dashboards."*

#### Q45: What is in `incident_report.txt`?
* **Answer**: An executive summary with general statistics, alert breakdown, top active IPs, and a security posture summary.
* **Follow-up**: Who is the target audience for this file?
* **Best Interview Response**: *"SOC Managers, CISOs, and incident response team leads during shift handovers."*

#### Q46: What is a brute force password spraying attack?
* **Answer**: Trying a single common password (like `Summer2026!`) across hundreds of usernames to avoid account lockout thresholds.
* **Follow-up**: How would you modify your detector to catch password spraying?
* **Best Interview Response**: *"Instead of grouping by IP, I would group failed logins across different usernames originating from the same subnet or geographic block within a specific time window."*

#### Q47: What does `queryUpper.contains(keyword)` do?
* **Answer**: Checks if the uppercase query contains a specific SQL injection keyword substring.
* **Follow-up**: Why convert to uppercase first?
* **Best Interview Response**: *"To defeat case-variation evasion techniques, such as an attacker submitting `sElEcT` or `uNiOn`."*

#### Q48: What is `git` and how is this project GitHub ready?
* **Answer**: Git is a distributed version control system. The project has a clean folder structure, `.gitignore`-ready output artifacts, a `pom.xml`, and a comprehensive `README.md`.
* **Follow-up**: Why is a README important?
* **Best Interview Response**: *"A clean README provides setup instructions, architectural overviews, and usage guidelines, demonstrating professional engineering discipline to recruiters."*

#### Q49: What is the time complexity of looking up an IP in `HashSet`?
* **Answer**: $O(1)$ constant time on average.
* **Follow-up**: What is worst-case time complexity?
* **Best Interview Response**: *"$O(N)$ in the rare event of extreme hash collisions, though Java 8+ converts buckets with many collisions into Red-Black trees ($O(\log N)$)."*

#### Q50: What would you improve if given one more week?
* **Answer**: Add real-time log file tailing using multithreading, integrate a Java Swing visual dashboard, and output alerts in JSON format.
* **Follow-up**: How would multithreading help?
* **Best Interview Response**: *"It would decouple log ingestion from detection, allowing the system to process high-throughput log streams asynchronously without blocking."*

---

## PART 2: 50 INTERMEDIATE QUESTIONS

#### Q51: How did you implement the Strategy Pattern in this project?
* **Answer**: `ThreatDetector` serves as the Strategy interface. Each concrete detector (`BruteForceDetector`, `SQLInjectionDetector`, etc.) implements `analyze()`. `ThreatMonitor` holds a collection of strategies and executes them polymorphically.
* **Follow-up**: What is the primary benefit of this pattern here?
* **Best Interview Response**: *"It decouples the detection execution engine from rule algorithms, enabling new detection rules to be added without modifying existing code, fulfilling the Open/Closed Principle."*

#### Q52: Why did you choose `Map.getOrDefault()` in your detectors?
* **Answer**: `map.getOrDefault(key, 0)` returns the existing count if present, or `0` if the key is missing, in a single step.
* **Follow-up**: What would the alternative look like?
* **Best Interview Response**: *"The alternative would require an explicit `if (map.containsKey(key))` check followed by `map.get(key)`, which causes two hash lookups instead of one."*

#### Q53: How does your brute force detector prevent repeated alert spamming?
* **Answer**: Once the counter hits 5, it triggers the alert and immediately resets `failedAttempts.put(ip, 0)`.
* **Follow-up**: What is the security trade-off of resetting to 0?
* **Best Interview Response**: *"Resetting prevents alert fatigue, but if an attacker continues guessing 4 more times, those 4 attempts aren't flagged until they hit another 5. A sliding time window would be a more sophisticated real-world alternative."*

#### Q54: Explain the difference between DoS and DDoS.
* **Answer**: DoS (Denial of Service) originates from a single IP or machine. DDoS (Distributed Denial of Service) originates from thousands of compromised distributed machines (a botnet).
* **Follow-up**: Can your current `DoSDetector` catch a DDoS attack?
* **Best Interview Response**: *"No, because `DoSDetector` tracks request volume per individual IP. In a DDoS attack, each bot might only send 2 requests, bypassing the per-IP threshold. Detecting DDoS requires tracking total aggregate server request volume regardless of IP."*

#### Q55: How does `SQLInjectionDetector` handle inline SQL comments like `--`?
* **Answer**: It checks if the query contains the string `"--"`.
* **Follow-up**: Why do attackers use `--` in SQL injection?
* **Best Interview Response**: *"In SQL, `--` denotes the start of a single-line comment. Attackers use it to truncate the remainder of a developer's query, neutralizing password or permission checks."*

#### Q56: How does the system handle quotes in SQL queries during parsing?
* **Answer**: In `LogParser.java`, `extractField()` detects if the value begins with a quotation mark `"` and scans for the matching closing quotation mark.
* **Follow-up**: Why not just split by spaces?
* **Best Interview Response**: *"Because SQL queries contain spaces between keywords (e.g., `DROP TABLE users`). Splitting purely on spaces would shatter the query across multiple array indices."*

#### Q57: What is the purpose of `Collectors.counting()` in the reporting stream?
* **Answer**: It is a downstream collector that counts the number of elements in each grouped category.
* **Follow-up**: What type does `Collectors.counting()` return?
* **Best Interview Response**: *"It returns a `Long`, resulting in a `Map<String, Long>`."*

#### Q58: How does `ReportGenerator` sort the most active IPs?
* **Answer**: It converts the entry set into a stream, uses `.sorted(Map.Entry.<String, Long>comparingByValue().reversed())`, and applies `.limit(5)`.
* **Follow-up**: What does `comparingByValue().reversed()` do?
* **Best Interview Response**: *"It sorts the map entries by request count in descending order (highest volume IP first)."*

#### Q59: Why did you separate `FileUtils` rather than using standard Java I/O inside each class?
* **Answer**: To centralize file access policies, avoid duplicate stream-handling code, and ensure consistent character encoding and exception logging.
* **Follow-up**: How does this improve testability?
* **Best Interview Response**: *"It allows us to mock file operations during unit testing or swap the persistence layer to a database without touching the detectors or parser."*

#### Q60: What happens if `blacklist.txt` contains comment lines starting with `#`?
* **Answer**: `FileUtils.loadBlacklist()` explicitly checks `!line.startsWith("#")` and ignores those lines.
* **Follow-up**: Why is this feature useful?
* **Best Interview Response**: *"Industry threat intelligence feeds often include metadata, source references, and comments preceded by `#` at the top of the file."*

#### Q61: What is the memory footprint of `Files.readAllLines()`?
* **Answer**: It loads the entire file into memory as a `List<String>`.
* **Follow-up**: At what file size does this become dangerous?
* **Best Interview Response**: *"When the log file size approaches the allocated JVM heap space (e.g., a 2GB file on a 1GB heap), it triggers an `OutOfMemoryError: Java heap space`."*

#### Q62: How would you refactor `Main` to avoid loading the entire file into memory?
* **Answer**: Use `Files.lines(Path)` or `BufferedReader.readLine()` to stream lines one by one.
* **Follow-up**: How does that change garbage collection?
* **Best Interview Response**: *"Streaming lines allows the JVM to garbage-collect each line and `LogEntry` immediately after detection rules run, keeping memory usage constant ($O(1)$) regardless of file size."*

#### Q63: Why did you use `final` for instance fields in detectors?
* **Answer**: In `BruteForceDetector`, `private final Map<String, Integer> failedAttempts` ensures the map reference cannot be reassigned to another object.
* **Follow-up**: Does `final` prevent modifying map entries?
* **Best Interview Response**: *"No, `final` makes the variable reference immutable, but the internal contents of the `HashMap` can still be modified via `.put()`."*

#### Q64: Explain the difference between `Optional.of()` and `Optional.ofNullable()`.
* **Answer**: `Optional.of(value)` throws a `NullPointerException` if the value is null. `Optional.ofNullable(value)` returns `Optional.empty()` if null.
* **Follow-up**: Which did you use in `ThreatDetector`?
* **Best Interview Response**: *"I used `Optional.of(new Alert(...))` because the alert is guaranteed to be non-null when instantiated, and `Optional.empty()` when benign."*

#### Q65: What is the difference between `ArrayList` and `LinkedList` in Java?
* **Answer**: `ArrayList` is backed by a dynamically resizing array, providing $O(1)$ random access. `LinkedList` is a doubly-linked list with $O(1)$ insertion at ends but $O(N)$ random access.
* **Follow-up**: Why is `ArrayList` preferred for storing alerts?
* **Best Interview Response**: *"`ArrayList` has much better CPU cache locality and lower memory overhead per element since it doesn't need to store node pointers."*

#### Q66: How does the system handle timestamps?
* **Answer**: Timestamps are extracted as formatted strings (`YYYY-MM-DD HH:MM:SS`) and stored in `LogEntry` and `Alert`.
* **Follow-up**: How would you handle timezones in production?
* **Best Interview Response**: *"I would parse timestamps into `java.time.Instant` or `java.time.ZonedDateTime` and normalize all events to UTC (Coordinated Universal Time) to accurately correlate cross-datacenter logs."*

#### Q67: What is the purpose of `@Override` annotation?
* **Answer**: It informs the compiler that the method is intended to override a method declared in a superclass or interface.
* **Follow-up**: What happens if the method signature doesn't match?
* **Best Interview Response**: *"The compiler generates an error, catching typographical mistakes or signature mismatches early at compile time."*

#### Q68: What is the difference between `System.out` and `System.err`?
* **Answer**: `System.out` is the standard output stream for normal execution messages. `System.err` is the standard error stream dedicated to error logging and diagnostics.
* **Follow-up**: Why separate them in `LogParser`?
* **Best Interview Response**: *"In production, terminal operators can redirect `System.err` to a dedicated error log file while piping `System.out` to a dashboard or monitoring console."*

#### Q69: Explain the difference between `Comparable` and `Comparator` in Java.
* **Answer**: `Comparable` defines a natural ordering for a class via `compareTo()`. `Comparator` defines external, custom ordering rules via `compare()`.
* **Follow-up**: Which was used in `ReportGenerator`?
* **Best Interview Response**: *"`Comparator` was used via `Map.Entry.<String, Long>comparingByValue().reversed()` to sort map entries by value dynamically."*

#### Q70: How does Java manage memory for strings?
* **Answer**: Java uses the **String Constant Pool** in the heap to store literal string values. If an identical literal is encountered, Java reuses the existing reference.
* **Follow-up**: Does `new String(...)` use the pool?
* **Best Interview Response**: *"No, `new String(...)` explicitly allocates a new object on the heap, bypassing the pool unless `.intern()` is called."*

#### Q71: What is a MITRE ATT&CK Framework?
* **Answer**: A globally accessible knowledge base of adversary tactics, techniques, and procedures (TTPs) based on real-world observations.
* **Follow-up**: Map your detectors to MITRE ATT&CK technique IDs.
* **Best Interview Response**: *"`BruteForceDetector` maps to T1110 (Brute Force). `SQLInjectionDetector` maps to T1190 (Exploit Public-Facing Application). `DoSDetector` maps to T1498 (Network Denial of Service)."*

#### Q72: What is an SLA in incident response?
* **Answer**: Service Level Agreement: the agreed timeframe within which a security alert must be acknowledged, triaged, and contained.
* **Follow-up**: How does severity tie to SLAs?
* **Best Interview Response**: *"Critical severity alerts typically carry a 15-minute SLA for Tier 1 triage, while Medium severity alerts may have a 4 to 8 hour SLA."*

#### Q73: What is the Principle of Least Privilege (PoLP)?
* **Answer**: Giving users and software components only the minimum access rights necessary to perform their functions.
* **Follow-up**: How does this relate to SQL Injection?
* **Best Interview Response**: *"If a web application's database account has read-only access to specific tables, an attacker exploiting SQL Injection cannot execute `DROP TABLE` or dump admin credential tables."*

#### Q74: Why is SQL Injection still prevalent despite being well understood?
* **Answer**: Legacy codebases, dynamic query concatenation by inexperienced developers, and third-party plugin vulnerabilities.
* **Follow-up**: What is the primary coding defense against SQLi?
* **Best Interview Response**: *"Parameterized queries (PreparedStatements) and Object-Relational Mappers (ORMs), which treat user inputs strictly as parameters, never as executable code."*

#### Q75: How does `PreparedStatement` prevent SQL Injection in Java?
* **Answer**: The database compiles the SQL query structure first. User parameters are sent separately and treated purely as literal data, preventing input from altering the query structure.
* **Follow-up**: Does our project use `PreparedStatement`?
* **Best Interview Response**: *"Our project simulates a monitoring engine that detects SQLi attempts from external audit logs; it does not connect directly to a database."*

#### Q76: What is a Web Application Firewall (WAF)?
* **Answer**: A security appliance that monitors, filters, and blocks HTTP traffic to and from a web service.
* **Follow-up**: How does `SQLInjectionDetector` mirror a WAF?
* **Best Interview Response**: *"Like a WAF inspection engine, our detector evaluates query parameters against attack signatures and flags requests before execution."*

#### Q77: What is DNS Sinkholing?
* **Answer**: Redirecting DNS requests for known malicious domains to a controlled IP address to prevent compromised hosts from communicating with command-and-control servers.
* **Follow-up**: How does this connect to `BlacklistDetector`?
* **Best Interview Response**: *"When `BlacklistDetector` flags a connection to a blacklisted IP, a SOC analyst's remediation playbook may involve sinkholing the domain at the edge router."*

#### Q78: What is syslog?
* **Answer**: A standard protocol used by network devices, servers, and firewalls to send system logs and event notifications over UDP/TCP port 514.
* **Follow-up**: How does our log format compare to RFC 5424 syslog?
* **Best Interview Response**: *"Our mock logs follow the same fundamental structure: timestamp, facility/event type, and key-value payload parameters."*

#### Q79: What is log correlation?
* **Answer**: Analyzing multiple disparate log events across different systems to identify related sequences that indicate an attack pattern.
* **Follow-up**: Give an example of correlation.
* **Best Interview Response**: *"Correlating 5 failed logins from an IP followed by a successful login, followed immediately by an administrative privilege change from that same IP."*

#### Q80: What is the difference between stateful and stateless detection?
* **Answer**: Stateless detection evaluates each event in isolation (e.g., SQLi keyword search). Stateful detection tracks historical context across multiple events over time (e.g., failed login counts in brute force).
* **Follow-up**: Which detectors in your project are stateful?
* **Best Interview Response**: *"`BruteForceDetector` and `DoSDetector` are stateful because they maintain state across events using `HashMap` counters."*

#### Q81: What happens if an IP in `logs.txt` has invalid formatting (e.g., `999.999.999.999`)?
* **Answer**: Currently, `LogParser` extracts the string as-is without semantic validation.
* **Follow-up**: How would you validate IP addresses in Java?
* **Best Interview Response**: *"I would use Apache Commons Validator `InetAddressValidator.isValidInet4Address(ip)` or a regular expression matching IPv4 and IPv6 patterns."*

#### Q82: What is the difference between horizontal and vertical scaling?
* **Answer**: Vertical scaling means adding more CPU/RAM to a single machine. Horizontal scaling means adding more machines/nodes to a distributed cluster.
* **Follow-up**: How would you scale this SOC system horizontally?
* **Best Interview Response**: *"Partition log streams across an Apache Kafka topic and run multiple parallel instances of `ThreatMonitor` worker pods consuming partitions."*

#### Q83: What is the diamond problem in Java?
* **Answer**: Ambiguity arising when a class inherits from two superclasses that define the same method.
* **Follow-up**: How does Java avoid it?
* **Best Interview Response**: *"Java does not support multiple class inheritance. A class can implement multiple interfaces, but default method conflicts must be explicitly resolved."*

#### Q84: What is garbage collection in Java?
* **Answer**: The automatic process of identifying and deallocating memory occupied by objects that are no longer referenced by the program.
* **Follow-up**: What garbage collector does Java 17 use by default?
* **Best Interview Response**: *"The G1 (Garbage-First) Garbage Collector, designed for multi-processor machines with large memory spaces."*

#### Q85: What is a memory leak in Java?
* **Answer**: When objects that are no longer needed remain referenced, preventing the garbage collector from reclaiming their memory.
* **Follow-up**: Can our `BruteForceDetector` cause a memory leak?
* **Best Interview Response**: *"Yes. If millions of unique IP addresses generate failed logins over months, the `failedAttempts` `HashMap` will continuously grow. An LRU cache or expiring map (like Google Guava Cache) is needed to evict stale IPs."*

#### Q86: What is a WeakHashMap?
* **Answer**: A `Map` implementation where keys are stored as weak references. If a key is no longer referenced elsewhere, its entry is automatically garbage-collected.
* **Follow-up**: When is it useful?
* **Best Interview Response**: *"For building memory-sensitive caches where cache entries should be discarded when memory becomes low."*

#### Q87: What is defensive copying?
* **Answer**: Creating a clone or copy of an object before storing it or returning it from a getter, ensuring the original internal state cannot be modified externally.
* **Follow-up**: How can defensive copying improve `AlertManager`?
* **Best Interview Response**: *"`getAlerts()` should return `Collections.unmodifiableList(alerts)` or `new ArrayList<>(alerts)` to prevent external code from tampering with the collected alerts."*

#### Q88: What is the Open/Closed Principle?
* **Answer**: Software entities should be open for extension, but closed for modification.
* **Follow-up**: How does your detector architecture prove this?
* **Best Interview Response**: *"To add a new detector, I simply create a new class implementing `ThreatDetector` and register it; I never modify existing detector classes."*

#### Q89: What is the Liskov Substitution Principle?
* **Answer**: Derived classes must be completely substitutable for their base types without altering system behavior or throwing unexpected exceptions.
* **Follow-up**: How does `ThreatDetector` satisfy LSP?
* **Best Interview Response**: *"Every detector adheres strictly to the contract: consuming a `LogEntry` and returning an `Optional<Alert>`, allowing `ThreatMonitor` to run them interchangeably."*

#### Q90: What is Dependency Injection?
* **Answer**: A design pattern where an object receives its dependencies from an external source rather than creating them internally.
* **Follow-up**: Where is Dependency Injection used?
* **Best Interview Response**: *"`ThreatMonitor` receives `AlertManager` and the blacklist path through its constructor, rather than instantiating them itself."*

#### Q91: What is the difference between synchronized and concurrent collections?
* **Answer**: Synchronized collections (e.g., `Collections.synchronizedMap`) lock the entire collection for every operation. Concurrent collections (e.g., `ConcurrentHashMap`) lock smaller segments/buckets, allowing concurrent reads and writes.
* **Follow-up**: Why use `ConcurrentHashMap` in a multithreaded detector?
* **Best Interview Response**: *"To allow multiple worker threads to evaluate logs and update IP counters simultaneously without causing lock contention or thread bottlenecks."*

#### Q92: What is the volatile keyword in Java?
* **Answer**: A modifier that guarantees changes to a variable are immediately visible across all CPU caches and threads.
* **Follow-up**: Does `volatile` guarantee thread safety for counter increments?
* **Best Interview Response**: *"No, because `count++` is a non-atomic read-modify-write operation. `AtomicInteger` is required for atomic increments."*

#### Q93: What is an AtomicInteger?
* **Answer**: A thread-safe integer class that uses hardware-level Compare-And-Swap (CAS) instructions to perform atomic increments without locking.
* **Follow-up**: Where would you use it?
* **Best Interview Response**: *"In a multithreaded `DoSDetector` to increment request counts safely across parallel worker threads."*

#### Q94: What is packet sniffing vs. log monitoring?
* **Answer**: Packet sniffing captures raw network packets in transit (e.g., Wireshark). Log monitoring analyzes higher-level event records written by software applications and operating systems.
* **Follow-up**: Which does this project perform?
* **Best Interview Response**: *"Log monitoring. We analyze application-layer event logs rather than inspecting raw Layer 2/3 network packets."*

#### Q95: What is lateral movement in cyberattacks?
* **Answer**: Techniques used by an attacker after breaching an initial system to explore and gain access to other internal network systems.
* **Follow-up**: How would log monitoring detect lateral movement?
* **Best Interview Response**: *"By monitoring internal SSH or RDP login logs between internal IP addresses that normally do not communicate."*

#### Q96: What is a credential stuffing attack?
* **Answer**: An automated attack where lists of leaked username/password pairs from previous data breaches are tested across multiple other websites.
* **Follow-up**: How does it differ from traditional brute force?
* **Best Interview Response**: *"Traditional brute force guesses many passwords against one account. Credential stuffing tries thousands of known account credentials once or twice across many accounts."*

#### Q97: What is port scanning?
* **Answer**: Probing a host's network ports to discover open services and vulnerabilities (e.g., using Nmap).
* **Follow-up**: How would you write a `PortScanDetector`?
* **Best Interview Response**: *"By tracking firewall reject logs and alerting if a single IP attempts connections to more than 20 distinct destination ports within 10 seconds."*

#### Q98: What is honeypot technology?
* **Answer**: A decoy computer system designed to lure cyberattackers, detect intrusion attempts, and study attack methodologies.
* **Follow-up**: How would you integrate a honeypot into this system?
* **Best Interview Response**: *"Any activity recorded in `logs.txt` targeting a honeypot IP or URL endpoint would immediately trigger a `CRITICAL` alert with zero false-positive tolerance."*

#### Q99: What is the difference between authentication and authorization?
* **Answer**: Authentication verifies *who you are* (e.g., username/password). Authorization determines *what you are allowed to do* (permissions/roles).
* **Follow-up**: Which detector monitors authentication?
* **Best Interview Response**: *"`BruteForceDetector` monitors authentication by evaluating `LOGIN_FAILED` and `LOGIN_SUCCESS` events."*

#### Q100: What is SIEM rule tuning?
* **Answer**: The ongoing process of adjusting correlation rule thresholds and filters to minimize false positives while maintaining high detection fidelity.
* **Follow-up**: What happens if thresholds are too strict?
* **Best Interview Response**: *"If thresholds are too strict, false negatives increase, allowing attacks to slip through. If too loose, analysts suffer from alert fatigue."*

---

## PART 3: 50 ADVANCED QUESTIONS

#### Q101: How would you re-architect this system to handle 50,000 log events per second?
* **Answer**: Replace synchronous file reading with a distributed streaming pipeline. Use Apache Kafka as the distributed log ingestion buffer, deploy worker pods running our Java detection logic as Kafka consumers, and use Redis for distributed IP state tracking.
* **Follow-up**: What is the bottleneck in the current architecture?
* **Best Interview Response**: *"The current architecture has three bottlenecks: single-threaded execution, sequential disk I/O with `BufferedWriter`, and in-memory `HashMap` storage limited to a single JVM."*

#### Q102: How does a sliding window algorithm improve on our current brute force threshold?
* **Answer**: Our current algorithm counts failed attempts indefinitely until reaching 5, regardless of how much time elapses. A sliding window only counts attempts within a rolling time window (e.g., 5 failures within 60 seconds).
* **Follow-up**: How would you implement a sliding window in Java?
* **Best Interview Response**: *"I would store timestamps of failed attempts inside a `Map<String, Deque<Instant>>`. For each new failure, I would purge entries older than 60 seconds using `deque.removeIf(...)` and check if `deque.size() >= 5`."*

#### Q103: What happens if two threads call `addAlert()` concurrently in `AlertManager`?
* **Answer**: `alerts.add(alert)` is not thread-safe in an `ArrayList`. Concurrent insertions can cause race conditions, lost updates, or `ArrayIndexOutOfBoundsException`.
* **Follow-up**: How would you make `AlertManager` thread-safe?
* **Best Interview Response**: *"I would replace `ArrayList` with `CopyOnWriteArrayList` or synchronize the `addAlert` method, and wrap file writing in a reentrant lock or dedicated consumer thread."*

#### Q104: How can an attacker bypass our `SQLInjectionDetector`?
* **Answer**: By using encoding evasion (e.g., URL encoding `%27%20OR%201=1`, hexadecimal encoding `0x27`, or Unicode homoglyphs), concatenation (`'O'||'R' 1=1`), or inline comments (`UN/**/ION SEL/**/ECT`).
* **Follow-up**: How would you harden the detector against evasion?
* **Best Interview Response**: *"I would decode all input (URL decoding, hex decoding) to a normalized canonical form before running signatures, or utilize an Abstract Syntax Tree (AST) SQL lexer like JSqlParser to detect query structural changes."*

#### Q105: What is an Abstract Syntax Tree (AST) parser in security?
* **Answer**: A parser that breaks down code or queries into a grammatical tree structure, analyzing the semantic intent rather than raw character strings.
* **Follow-up**: Why is AST-based detection superior to keyword matching?
* **Best Interview Response**: *"Keyword matching creates false positives on legitimate queries containing the word 'SELECT'. An AST parser verifies if user input changed the mathematical grammar and logic of the SQL statement."*

#### Q106: How would you implement continuous, real-time log monitoring?
* **Answer**: Using Java NIO `WatchService` to monitor the directory for `ENTRY_MODIFY` events, combined with a background thread tailing the file using `RandomAccessFile.seek()`.
* **Follow-up**: What Linux utility does this emulate?
* **Best Interview Response**: *"It emulates `tail -f /var/log/syslog`, processing new lines as they are appended to disk in real time."*

#### Q107: Explain the Java Memory Model (JMM) in the context of multithreaded threat detection.
* **Answer**: The JMM defines how threads interact through memory, governing visibility, ordering, and atomicity across CPU caches and main RAM.
* **Follow-up**: How does it impact state tracking?
* **Best Interview Response**: *"Without synchronization or memory barriers (like `volatile` or `ConcurrentHashMap`), updates to IP counters made by Core 1 may remain in L1 cache and be invisible to Core 2, causing undercounting."*

#### Q108: What is the difference between `synchronized` and `ReentrantLock`?
* **Answer**: `synchronized` is a built-in Java keyword with automatic lock acquisition and release. `ReentrantLock` provides advanced features: fairness policies, interruptible lock acquisition, timed lock attempts, and multiple condition variables.
* **Follow-up**: When would you use `ReentrantLock` here?
* **Best Interview Response**: *"When writing alerts to disk, `tryLock(500, TimeUnit.MILLISECONDS)` allows a thread to fall back to an in-memory queue if disk I/O is temporarily blocked."*

#### Q109: What is the Producer-Consumer pattern and how would you apply it here?
* **Answer**: A concurrency pattern where producers generate data and push to a buffer, while consumers pull data and process it independently.
* **Follow-up**: How would you build it in Java?
* **Best Interview Response**: *"A log reader thread (Producer) reads lines and inserts `LogEntry` objects into a `BlockingQueue<LogEntry>`. A thread pool of detection workers (Consumers) takes entries from the queue and runs detectors in parallel."*

#### Q110: What is backpressure in streaming systems?
* **Answer**: A mechanism where a downstream consumer that is overwhelmed with data signals the upstream producer to slow down or buffer incoming traffic.
* **Follow-up**: What happens without backpressure?
* **Best Interview Response**: *"If logs arrive faster than detectors can process them, in-memory queues grow unbounded, eventually exhausting the JVM heap and causing an `OutOfMemoryError`."*

#### Q111: How would you export alerts to a remote SIEM like Splunk or Elasticsearch?
* **Answer**: Add an HTTP/REST dispatcher inside `AlertManager` that formats `Alert` objects as JSON payloads and sends them via an asynchronous `HttpClient` to the SIEM's HTTP Event Collector (HEC).
* **Follow-up**: What happens if the network goes down?
* **Best Interview Response**: *"Implement an in-memory or on-disk dead-letter queue (DLQ) with exponential backoff retries to guarantee zero alert loss."*

#### Q112: What is the difference between symmetric and asymmetric encryption?
* **Answer**: Symmetric encryption uses a single shared key for encryption and decryption (e.g., AES). Asymmetric encryption uses a public key to encrypt and a private key to decrypt (e.g., RSA, ECC).
* **Follow-up**: How does this relate to log security?
* **Best Interview Response**: *"Log files containing sensitive PII should be encrypted at rest using AES-256 and transmitted over TLS 1.3 to prevent eavesdropping."*

#### Q113: What is log tampering and how do you protect log integrity?
* **Answer**: When an attacker deletes or edits log files to erase evidence of intrusion.
* **Follow-up**: How do you prevent it?
* **Best Interview Response**: *"Stream logs immediately to an immutable, write-once-read-many (WORM) remote SIEM server, and compute cryptographic hash chains (SHA-256) across consecutive log entries."*

#### Q114: What is a hash chain in audit logs?
* **Answer**: Each log entry includes a cryptographic hash of the previous log entry along with its own data: $H_n = \text{SHA256}(H_{n-1} + \text{Log}_n)$.
* **Follow-up**: What does this achieve?
* **Best Interview Response**: *"If an attacker deletes or alters any historical log line, the hash chain breaks, providing mathematical proof of log tampering."*

#### Q115: How does Java 17 Sealed Classes benefit security modeling?
* **Answer**: Sealed classes restrict which other classes can extend or implement them using the `permits` keyword.
* **Follow-up**: How could `ThreatDetector` use sealing?
* **Best Interview Response**: *"`public sealed interface ThreatDetector permits BruteForceDetector, SQLInjectionDetector...` prevents malicious third-party plugins from injecting unauthorized detector implementations into the engine."*

#### Q116: What is reflection in Java and what are its security risks?
* **Answer**: An API that allows inspecting and modifying classes, methods, and fields at runtime, even private ones.
* **Follow-up**: Why is reflection dangerous?
* **Best Interview Response**: *"Attackers can use reflection to bypass encapsulation, alter security flags, or instantiate unauthorized classes. Java 9 Modules (JPMS) restrict deep reflection by default."*

#### Q117: What is the Java Module System (JPMS)?
* **Answer**: Introduced in Java 9, JPMS packages code into modules with explicit `module-info.java` definitions detailing exports and dependencies.
* **Follow-up**: How does it improve security?
* **Best Interview Response**: *"Strong encapsulation prevents external modules from accessing internal implementation packages via reflection unless explicitly exported."*

#### Q118: How would you implement IP subnet matching (CIDR blocks) in `BlacklistDetector`?
* **Answer**: Instead of matching exact string IPs, convert both the incoming IP and the blacklisted CIDR (e.g., `10.0.0.0/24`) into 32-bit integers and apply a bitwise subnet mask: `(ipInt & mask) == (subnetInt & mask)`.
* **Follow-up**: Why not use string comparisons for CIDRs?
* **Best Interview Response**: *"String comparison cannot evaluate whether an IP belongs inside a network range; bitwise integer arithmetic provides mathematically exact, $O(1)$ range verification."*

#### Q119: What is the difference between CPU-bound and I/O-bound tasks in our system?
* **Answer**: I/O-bound tasks wait for disk or network operations (reading `logs.txt`, writing `alerts.txt`). CPU-bound tasks execute computations in memory (string parsing, hash lookups, stream sorting).
* **Follow-up**: How do you tune thread pools for each?
* **Best Interview Response**: *"For CPU-bound tasks, set pool size to $N_{\text{threads}} = N_{\text{CPU cores}}$. For I/O-bound tasks, allocate a larger thread pool ($2 \times \text{cores}$ or higher) to keep the CPU utilized while threads wait on I/O."*

#### Q120: How does `ForkJoinPool` work with Java Parallel Streams?
* **Answer**: Parallel streams break collections into sub-tasks and execute them across worker threads using work-stealing algorithms.
* **Follow-up**: Why didn't we use `.parallelStream()` in `ReportGenerator`?
* **Best Interview Response**: *"Parallel streams introduce thread coordination overhead that outweighs benefits on small datasets. For millions of logs, parallel streams significantly accelerate aggregation."*

#### Q121: What is cache locality and how does it affect Java collections?
* **Answer**: The CPU property where accessing memory locations near recently accessed data is much faster due to L1/L2 cache prefetching.
* **Follow-up**: Why is `ArrayList` more cache-friendly than `LinkedList`?
* **Best Interview Response**: *"`ArrayList` stores elements in contiguous memory blocks, maximizing CPU cache hits. `LinkedList` scatters nodes randomly across the heap, causing frequent cache misses."*

#### Q122: What is the difference between optimistic and pessimistic locking?
* **Answer**: Pessimistic locking assumes conflicts will happen and locks resources upfront. Optimistic locking assumes conflicts are rare, proceeds without locking, and checks for collisions before committing.
* **Follow-up**: Which is better for IP counters?
* **Best Interview Response**: *"Optimistic locking via `AtomicInteger` (Compare-And-Swap) provides much higher throughput for counter increments than pessimistic `synchronized` blocks."*

#### Q123: What is a Race Condition? Give an example that could occur in threat detection.
* **Answer**: When multiple threads access and modify shared data concurrently, and the final outcome depends on thread scheduling order.
* **Follow-up**: Example in our project?
* **Best Interview Response**: *"If two threads process failed logins from the same IP at count 4 simultaneously, both might read count 4 and increment to 5, generating two duplicate brute force alerts instead of one."*

#### Q124: How does `String.intern()` work in Java?
* **Answer**: It returns the canonical representation of a string from the String Constant Pool.
* **Follow-up**: Could `String.intern()` help optimize memory in `LogEntry`?
* **Best Interview Response**: *"Yes. Since event types (like `LOGIN_SUCCESS`, `LOGIN_FAILED`) repeat thousands of times, interning them ensures all `LogEntry` instances share the exact same string reference in memory."*

#### Q125: What is Zero-Copy in high-performance networking?
* **Answer**: A technique where the CPU avoids copying data between user space and kernel space memory buffers during file and network transfers.
* **Follow-up**: How would that apply to log ingestion?
* **Best Interview Response**: *"Using Java NIO `FileChannel.transferTo()`, logs can be transferred directly from network sockets to disk without passing through JVM application memory."*

#### Q126: What is a Distributed Denial of Service (DDoS) SYN Flood?
* **Answer**: An attack that exploits the TCP 3-way handshake by sending thousands of SYN packets with spoofed IPs without sending the final ACK, exhausting server connection tables.
* **Follow-up**: Can application log monitoring catch a SYN flood?
* **Best Interview Response**: *"No. SYN floods operate at Layer 4 (Transport). The connection never completes, so the application layer never logs an event. SYN floods must be detected via NetFlow or firewall telemetry."*

#### Q127: What is Cross-Site Scripting (XSS)?
* **Answer**: An injection vulnerability where malicious JavaScript scripts are injected into trusted web applications and executed in a victim's browser.
* **Follow-up**: How would you build an `XSSDetector`?
* **Best Interview Response**: *"Scan web query strings and HTTP parameters for script markers like `<script>`, `onerror=`, `javascript:`, and encoded HTML entity equivalents."*

#### Q128: What is Cross-Site Request Forgery (CSRF)?
* **Answer**: An attack that forces an authenticated end user to execute unwanted actions on a web application they are currently logged into.
* **Follow-up**: What is the primary defense against CSRF?
* **Best Interview Response**: *"Anti-CSRF tokens (unique, unpredictable tokens validated on state-changing requests) and `SameSite=Strict` cookie attributes."*

#### Q129: What is Server-Side Request Forgery (SSRF)?
* **Answer**: A vulnerability where an attacker abuses server functionality to force the server to send requests to unauthorized internal destinations (e.g., AWS metadata endpoint `http://169.254.169.254`).
* **Follow-up**: How would our system detect SSRF?
* **Best Interview Response**: *"Flag any outgoing HTTP request logs targeting private IP ranges (`10.0.0.0/8`, `192.168.0.0/16`, `127.0.0.1`, or cloud metadata addresses)."*

#### Q130: What is Command Injection?
* **Answer**: An attack where an attacker executes arbitrary operating system commands on the host server through vulnerable application inputs.
* **Follow-up**: What signatures would a `CommandInjectionDetector` look for?
* **Best Interview Response**: *"Shell command separators and operators such as `;`, `&&`, `|`, `` ` ``, `$()`, and commands like `cat /etc/passwd` or `whoami`."*

#### Q131: What is a Zero-Day vulnerability?
* **Answer**: A security flaw in software or hardware that is unknown to the vendor and has no available security patch.
* **Follow-up**: Can signature-based detectors like our SQLi detector catch zero-days?
* **Best Interview Response**: *"Rarely. Signature detectors rely on known patterns. Catching zero-days requires behavioral anomaly detection, machine learning, and baseline deviation models."*

#### Q132: What is the difference between Signature-Based and Anomaly-Based detection?
* **Answer**: Signature-based detection matches events against known malicious patterns. Anomaly-based detection creates a baseline of normal behavior and flags significant statistical deviations.
* **Follow-up**: Which is our `BruteForceDetector`?
* **Best Interview Response**: *"`BruteForceDetector` is threshold-based behavioral anomaly detection, whereas `SQLInjectionDetector` is purely signature-based."*

#### Q133: What is SOAR (Security Orchestration, Automation, and Response)?
* **Answer**: Platforms that connect security tools and automate incident response workflows via predefined playbooks (e.g., auto-blocking an IP at the firewall when an alert triggers).
* **Follow-up**: How could this project connect to a SOAR?
* **Best Interview Response**: *"When `AlertManager` generates an alert, a webhook could invoke a SOAR playbook in Palo Alto Cortex XSOAR to block the offending IP automatically."*

#### Q134: What is EDR (Endpoint Detection and Response)?
* **Answer**: Software installed on end-user workstations and servers that continuously records system activities, file modifications, and process executions to detect threats.
* **Follow-up**: How does SIEM log monitoring differ from EDR?
* **Best Interview Response**: *"EDR focuses deeply on host-level forensics (memory, registry, processes). SIEM log monitoring aggregates telemetry across network devices, firewalls, and applications for holistic enterprise correlation."*

#### Q135: What is the MITRE D3FEND framework?
* **Answer**: A complementary matrix to MITRE ATT&CK focusing on defensive cybersecurity techniques and countermeasures.
* **Follow-up**: How does our project relate?
* **Best Interview Response**: *"Our project implements D3FEND countermeasures: Decoupled Log Analysis (D3-DLA), Threat Intel Mapping, and Credential Spray Detection."*

#### Q136: How does the Java Garbage Collector handle `OutOfMemoryError`?
* **Answer**: When the heap is exhausted and the GC cannot reclaim sufficient memory to allocate a new object, the JVM throws an `OutOfMemoryError` and terminates normal execution.
* **Follow-up**: What flag allows post-mortem heap analysis?
* **Best Interview Response**: *"`-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/dumps` writes a snapshot of heap memory to disk for analysis in Eclipse Memory Analyzer (MAT)."*

#### Q137: What is JIT (Just-In-Time) compilation in Java?
* **Answer**: The JVM component that dynamically translates frequently executed bytecode ("hot spots") into native machine code at runtime.
* **Follow-up**: How does JIT benefit our threat detectors?
* **Best Interview Response**: *"The detector loop in `ThreatMonitor` executes millions of times. The JIT compiler optimizes and inlines method calls, achieving near C++ execution speeds."*

#### Q138: What is escape analysis in the JVM?
* **Answer**: An optimization technique where the JIT compiler determines whether an object allocated inside a method escapes outside the method's scope.
* **Follow-up**: What optimization does it enable?
* **Best Interview Response**: *"If an object does not escape (like short-lived string builders in `LogParser`), the JVM can allocate it directly on the CPU stack instead of the heap, eliminating GC overhead."*

#### Q139: How do you secure database credentials in enterprise Java applications?
* **Answer**: Never hardcode credentials in source code. Store them in secret vaults (HashiCorp Vault, AWS Secrets Manager) and inject them at runtime via encrypted environment variables.
* **Follow-up**: Why is committing passwords to GitHub dangerous?
* **Best Interview Response**: *"Automated threat actor bots continuously scan public GitHub repositories for API keys and database credentials, often compromising exposed assets within minutes."*

#### Q140: What is a Time-of-Check to Time-of-Use (TOCTOU) race condition?
* **Answer**: A concurrency bug where a system checks the state of a resource, but the resource's state changes before the system performs an action on it.
* **Follow-up**: How could this happen in file loading?
* **Best Interview Response**: *"Checking `file.exists()` before `Files.readAllLines(path)`. An external process could delete the file between the check and the read, throwing an exception."*

#### Q141: What is the difference between Horizontal and Vertical Privilege Escalation?
* **Answer**: Horizontal privilege escalation occurs when an attacker gains access to data belonging to another user with identical permissions. Vertical escalation occurs when a standard user gains administrative/root privileges.
* **Follow-up**: How would you detect vertical privilege escalation in logs?
* **Best Interview Response**: *"Alert on events where a standard user account executes administrative actions (e.g., `sudo`, role modifications, or accessing `/admin` endpoints)."*

#### Q142: What is the Golden Ticket attack in Active Directory?
* **Answer**: An attack where an adversary compromises the Active Directory Key Distribution Service account (`KRBTGT`) to forge Kerberos Ticket Granting Tickets (TGTs), granting unlimited domain access.
* **Follow-up**: What event ID flags Kerberos anomalies in Windows logs?
* **Best Interview Response**: *"Windows Security Event ID 4768 (TGT Request) and Event ID 4769 (Service Ticket Request) with unusual encryption types or non-existent usernames."*

#### Q143: What is the Pass-the-Hash attack?
* **Answer**: An attack where an adversary captures a password hash and uses it to authenticate to remote servers without ever cracking the plaintext password.
* **Follow-up**: How do modern operating systems defend against it?
* **Best Interview Response**: *"Credential Guard (virtualization-based security), disabling NTLM in favor of Kerberos, and restricting Local Administrator passwords via LAPS."*

#### Q144: What is the cyber kill chain?
* **Answer**: A 7-phase model developed by Lockheed Martin describing cyberattack stages: Reconnaissance, Weaponization, Delivery, Exploitation, Installation, Command & Control (C2), Actions on Objectives.
* **Follow-up**: What phases does our project detect?
* **Best Interview Response**: *"`DoSDetector` and `BlacklistDetector` catch Reconnaissance and C2 activity; `SQLInjectionDetector` catches Exploitation; `BruteForceDetector` catches Initial Access."*

#### Q145: How would you implement distributed caching for blacklisted IPs across 10 instances?
* **Answer**: Use a distributed in-memory data store like Redis or Hazelcast. All 10 instances query the shared Redis cache via Redis `SISMEMBER` in sub-millisecond time.
* **Follow-up**: What if the Redis cache is unreachable?
* **Best Interview Response**: *"Implement a local fallback cache (e.g., Caffeine cache) with a 10-minute TTL to ensure detection continues even during network partitions."*

#### Q146: What is a ReDoS (Regular Expression Denial of Service) attack?
* **Answer**: An algorithmic complexity attack where an attacker submits a specially crafted string that causes a poorly written regular expression to execute catastrophic backtracking, pegging CPU at 100%.
* **Follow-up**: How did we avoid ReDoS in `LogParser`?
* **Best Interview Response**: *"We used fast, deterministic string boundary methods (`indexOf`, `substring`, `split`) instead of complex nested greedy regular expressions."*

#### Q147: What is Canary deployment?
* **Answer**: A deployment strategy where a software update is rolled out to a tiny fraction of servers first to verify stability and error rates before full production rollout.
* **Follow-up**: How would you deploy a new detection rule safely?
* **Best Interview Response**: *"Deploy the rule in 'Audit / Shadow Mode' where it evaluates real logs and logs metrics, but does not trigger active alerts to analysts until false-positive rates are proven low."*

#### Q148: What is alert correlation using graph databases?
* **Answer**: Representing security events, users, IP addresses, and assets as nodes and edges in a graph database (like Neo4j) to uncover multi-hop attack paths.
* **Follow-up**: Why is graph correlation superior to relational SQL tables?
* **Best Interview Response**: *"Graph traversals execute relationship queries across complex multi-step attack chains exponentially faster than multi-table SQL joins."*

#### Q149: What is Threat Hunting?
* **Answer**: The proactive, hypothesis-driven search through networks and endpoints to detect malicious activity that has evaded existing automated security defenses.
* **Follow-up**: How does a SOC Analyst transition from monitoring to hunting?
* **Best Interview Response**: *"Monitoring is reactive to triggered alerts. Hunting begins with a hypothesis (e.g., 'Has an attacker abused PowerShell on our domain?') and queries historical logs for subtle anomalies."*

#### Q150: What is the most important quality of a top-tier SOC Analyst?
* **Answer**: Relentless curiosity, rigorous analytical discipline, and the refusal to close an alert without understanding the true root cause of the anomaly.
* **Follow-up**: How does your software engineering background make you a better security analyst?
* **Best Interview Response**: *"Because I know how software is built, how logs are generated, and how databases execute queries, I don't just memorize attack signatures—I understand the underlying system mechanics, allowing me to detect evasions, communicate effectively with developers, and automate repetitive tasks."*

---

# SECTION 12: HR + BEHAVIORAL PROJECT QUESTIONS

---

### Q1: "Why did you choose to build a SOC Log Monitoring System instead of a generic web or CRUD application?"
* **Why HR/Hiring Managers Ask This**: They want to see if you have genuine passion for cybersecurity and if you understand real-world business risk rather than just following generic tutorials.
* **The Perfect Answer**:
  > *"Most software engineering students build another e-commerce clone or todo list. While those teach basic CRUD operations, they don't reflect how enterprise systems actually survive in hostile digital environments. Modern enterprises are breached not because of database design, but because unauthorized activity goes undetected for months.*
  > 
  > *I wanted to build something directly relevant to security operations. I wanted to understand how security telemetry is generated, how threat actors evade detection, and how automated engines identify attacks in real time. Building this project allowed me to bridge the gap between core Java software architecture and defensive cybersecurity engineering, which is the exact intersection needed for a modern SOC Analyst or Security Engineer."*

---

### Q2: "What was the biggest technical challenge you faced while developing this project, and how did you resolve it?"
* **Why HR/Hiring Managers Ask This**: They want to evaluate your problem-solving process, resilience, and debugging methodology.
* **The Perfect Answer**:
  > *"The biggest challenge was handling unstructured log tokens without corrupting payload data—specifically in database query logs. When parsing lines using standard delimiter splitting (`line.split(" ")`), SQL queries containing spaces like `DROP TABLE users` or `' OR 1=1 --` were split across multiple arbitrary array indices, breaking field mapping.*
  > 
  > *I initially tried complex regex, but realized nested regex could introduce catastrophic backtracking vulnerabilities (ReDoS) on high-throughput log streams. I solved this by redesigning `LogParser` with bounded splitting (`split(" ", 4)`) combined with a safe boundary tokenizer in `extractField()` that inspects quotation markers. This guaranteed that multi-word payloads remained intact while keeping string parsing deterministic and lightning fast."*

---

### Q3: "What did you learn from this project both technically and in terms of cybersecurity mindset?"
* **The Perfect Answer**:
  > *"Technically, I mastered the practical application of the Strategy Design Pattern, immutable domain modeling, Java NIO, and functional Stream analytics. But more importantly, from a cybersecurity mindset, I learned the critical trade-off between detection sensitivity and alert fatigue.*
  > 
  > *If detection thresholds are set too low, an analyst is flooded with thousands of false alarms and burns out. If thresholds are too high, real attackers slip through unnoticed. Real-world security isn't just about writing a rule; it's about continuously tuning indicators, managing state safely, and producing structured, actionable intelligence."*

---

### Q4: "If you had an enterprise budget and 6 months to take this to production, what would your roadmap look like?"
* **The Perfect Answer**:
  > *"My roadmap would focus on three major pillars: Scale, Intelligence, and Automation:*
  > 1. ***Distributed Ingestion (Scale)***: *Replace local disk reading with an Apache Kafka streaming pipeline backed by Redis for distributed IP state tracking, allowing horizontal scaling across Kubernetes worker pods.*
  > 2. ***Dynamic Threat Intelligence (Intelligence)***: *Integrate automated STIX/TAXII threat feeds and real-time IP reputation lookups via the VirusTotal / AbuseIPDB API instead of a static text file.*
  > 3. ***SOAR Orchestration (Automation)***: *Build automated containment playbooks using webhooks to trigger immediate firewall null-routing (AWS Security Group / Cloudflare IP ban) when CRITICAL alerts occur."*

---

# SECTION 13: RESUME EXPLANATIONS & ELEVATOR PITCHES

Use these scripted responses based on the amount of time the interviewer gives you.

---

## 1. The 30-Second Elevator Pitch (Quick Screening Call)
> *"I built a Java 17 Security Operations Center (SOC) Log Monitoring and Threat Detection Engine. It ingests raw server audit logs, parses and normalizes disparate event streams, and evaluates them across a modular suite of threat detectors. It automatically catches Brute Force login spikes, SQL Injection payloads, DoS traffic, and blacklisted threat actors, generating real-time prioritized alerts and an executive forensic incident report. It demonstrates core OOP design patterns, clean Java I/O, and real-world SIEM correlation mechanics."*

---

## 2. The 1-Minute Pitch (Standard Interview Intro)
> *"On my resume, you'll see my 'SOC Log Monitoring & Threat Detection System'. I built this project to simulate the automated detection pipeline of an enterprise SIEM like Splunk or QRadar.*
> 
> *The engine is written in Java 17 and follows a decoupled, service-oriented architecture. Raw server logs are tokenized into structured domain objects by `LogParser`. A central `ThreatMonitor` engine then dispatches each event through a polymorphic detector pipeline implementing the Strategy Pattern.*
> 
> *I implemented heuristic detectors for four critical attack classes: Brute Force credential guessing via stateful IP tracking, SQL Injection via signature scanning, Denial of Service via volumetric thresholding, and Threat Intelligence matching against a blacklisted IP feed.*
> 
> *Alerts are assigned unique UUID tracking tickets and severity ratings, persisted to disk, and aggregated into an executive summary report using Java Streams. This project showcases my proficiency in Java OOP, data structures, and foundational SOC analysis."*

---

## 3. The 2-Minute Pitch (Technical Deep Dive Opening)
> *(Use the 1-minute pitch above, then seamlessly transition to design decisions:)*
> 
> *"...In designing the system, I paid strict attention to software architecture and Big-O efficiency. For example, in the `BlacklistDetector`, I loaded thousands of threat indicators into a Java `HashSet` rather than an `ArrayList` to ensure constant-time $O(1)$ lookups during stream processing.*
> 
> *For stateful rules like `BruteForceDetector` and `DoSDetector`, I used `HashMap` frequency counters with automatic counter resets upon alert generation to mitigate alert fatigue. I used `java.util.Optional` across all detector interfaces to guarantee null-safety.*
> 
> *Finally, the `ReportGenerator` utilizes modern Java Streams and `Collectors.groupingBy` to compile an executive forensic audit without cluttering the runtime detection loop. It gave me deep, hands-on experience in both defensive security principles and clean software engineering."*

---

## 4. The 5-Minute Master Presentation (Senior Panel / Portfolio Defense)
* **Minute 1: The Problem & Vision**: Explain why modern enterprises suffer from log volume explosion and alert fatigue.
* **Minute 2: System Architecture & Ingestion**: Walk through the architectural diagram: `logs.txt` $\to$ `LogParser` $\to$ `LogEntry` DTO $\to$ `ThreatMonitor`.
* **Minute 3: The Threat Detection Suite**: Detail the exact algorithms:
  * Brute Force: Threshold tracking with state reset.
  * SQL Injection: Uppercase token normalization and signature scanning.
  * DoS: Request velocity tracking per client IP.
  * Blacklist: Threat intel correlation backed by $O(1)$ `HashSet`.
* **Minute 4: Alert Management & Reporting**: Explain `AlertManager` (UUID tracking, terminal notification, file auditing) and `ReportGenerator` (Java Streams grouping, sorting, security posture scoring).
* **Minute 5: Enterprise Scaling & Lessons**: Discuss how this architecture scales to Kafka/Redis in production, how to handle evasion (AST parsing), and how it prepared you to step into a SOC Analyst Tier 1 role immediately.

---

# SECTION 14: PROJECT DEFENSE GUIDE (TOUGH INTERVIEWER QUESTIONS)

Here is how to answer when an interviewer tries to aggressively challenge your project:

---

### Grill 1: *"Your SQL injection detector just looks for strings like 'DROP' and 'SELECT'. What happens when a legitimate user named 'Selectra' signs up, or a developer runs a legitimate report query? Isn't your false-positive rate absurdly high?"*
* **The Winning Response**:
  > *"You are completely right. A naive substring search for 'SELECT' in a raw production environment would trigger intolerable false positives on legitimate administrative queries or text fields. 
  > 
  > In this simulation, I implemented keyword matching to demonstrate basic signature-based detection principles. However, in a hardened production version, I would implement two key defenses:
  > 1. **Contextual Tokenization / AST Analysis**: Using an SQL parser like JSqlParser to inspect the Abstract Syntax Tree. This verifies whether the input alters the boolean logic of a `WHERE` clause (such as `OR 1=1`), rather than simply checking if a keyword exists.
  > 2. **Role & Endpoint Whitelisting**: Suppressing SQL query alerts originating from authenticated internal backend services, while applying strict inspection only to untrusted public-facing HTTP parameters."*

---

### Grill 2: *"Your BruteForceDetector resets the counter to 0 after 5 failures. If an attacker knows this, can't they just fail 4 times, wait a second, fail 4 times again, and brute force passwords forever without triggering an alert?"*
* **The Winning Response**:
  > *"That is an acute observation regarding threshold evasion. Resetting to 0 was a deliberate trade-off in this project to prevent a single attacker from generating 500 duplicate alerts on 505 attempts, which causes alert fatigue.
  > 
  > To defeat slow-and-low evasion where an attacker paces their attempts, enterprise SIEMs implement **sliding time windows**. Instead of an integer counter, each IP would maintain a queue of failure timestamps. We would evaluate: 'Did 5 failures occur within any rolling 10-minute window?' Furthermore, we would add exponential lockout penalties or account-level lockouts regardless of source IP to defeat distributed brute force."*

---

### Grill 3: *"Why did you write this in Java? Python or Go has way more cybersecurity scripting libraries."*
* **The Winning Response**:
  > *"Python is fantastic for quick proof-of-concept scripts and ad-hoc log parsing. However, enterprise-scale security data pipelines—such as Apache Kafka, Apache Flink, Elasticsearch, and Apache NiFi—are built primarily on the Java Virtual Machine (JVM).
  > 
  > Building this in Java allowed me to demonstrate strong object-oriented design, compile-time type safety, memory efficiency, and concurrency paradigms that are essential when building high-throughput, carrier-grade enterprise security systems."*

---

### Grill 4: *"Your project reads the file with `Files.readAllLines()`. If I throw a 20GB server log at your app, it will crash immediately with `OutOfMemoryError`. How can you call this industry-style?"*
* **The Winning Response**:
  > *"I acknowledge that limitation in the batch demonstration driver. `Files.readAllLines()` was chosen for simplicity in running local test datasets.
  > 
  > However, transitioning this system to handle 20GB+ logs requires changing only a single line in `Main.java`: replacing `Files.readAllLines()` with `Files.lines(Path)` or a `BufferedReader`. Because Java Streams process lines lazily on-demand, each line is read, parsed, analyzed by detectors, and immediately made eligible for garbage collection. The memory footprint drops from 20GB to less than 50 megabytes, regardless of file size."*

---

# SECTION 15: VISUAL ARCHITECTURE & DIAGRAM CATALOG

---

## 1. Comprehensive Component Diagram
```
+------------------------------------------------------------------------------------+
|                                    SOC-MONITOR                                     |
|                                                                                    |
|  +------------------------+                        +----------------------------+  |
|  |       RESOURCES        |                        |           MODELS           |  |
|  | - logs.txt             |                        | - LogEntry                 |  |
|  | - blacklist.txt        |                        | - Alert                    |  |
|  | - alerts.txt           |                        | - Severity                 |  |
|  +-----------|------------+                        +-------------^--------------+  |
|              |                                                   |                 |
|              v                                                   |                 |
|  +------------------------+      creates LogEntry                |                 |
|  |       LogParser        |--------------------------------------+                 |
|  +-----------|------------+                                                        |
|              |                                                                     |
|              v passes LogEntry                                                     |
|  +------------------------+                                                        |
|  |     ThreatMonitor      |                                                        |
|  +-----------|------------+                                                        |
|              |                                                                     |
|              | dispatches polymorphically                                          |
|              v                                                                     |
|  +------------------------------------------------------------------------------+  |
|  |                             DETECTOR LAYER                                   |  |
|  |                                                                              |  |
|  |   +---------------------+   +---------------------+   +------------------+   |  |
|  |   | BruteForceDetector  |   |SQLInjectionDetector |   |   DoSDetector    |   |  |
|  |   +---------------------+   +---------------------+   +------------------+   |  |
|  |              |                         |                        |            |  |
|  |              +-------------------------+------------------------+            |  |
|  |                                        |                                     |  |
|  |                                        v                                     |  |
|  |                            +-----------------------+                         |  |
|  |                            |   BlacklistDetector   |                         |  |
|  |                            +-----------------------+                         |  |
|  +----------------------------------------|-------------------------------------+  |
|                                           |                                        |
|                                           v emits Optional<Alert>                  |
|  +------------------------+               |                                        |
|  |      AlertManager      |<--------------+                                        |
|  +-----------|------------+                                                        |
|              |                                                                     |
|              v generates post-run forensic summary                                 |
|  +------------------------+                        +----------------------------+  |
|  |    ReportGenerator     |----------------------->|    incident_report.txt     |  |
|  +------------------------+                        +----------------------------+  |
+------------------------------------------------------------------------------------+
```

---

## 2. Object-Oriented Class Diagram
```
+-------------------------------------------------------------------+
|                           LogEntry                                |
+-------------------------------------------------------------------+
| - timestamp: String                                               |
| - eventType: String                                               |
| - username: String                                                |
| - ipAddress: String                                               |
| - query: String                                                   |
+-------------------------------------------------------------------+
| + getTimestamp(): String                                          |
| + getEventType(): String                                          |
| + getUsername(): String                                           |
| + getIpAddress(): String                                          |
| + getQuery(): String                                              |
+-------------------------------------------------------------------+

+-------------------------------------------------------------------+
|                            Alert                                  |
+-------------------------------------------------------------------+
| - alertId: String (UUID)                                          |
| - alertType: String                                               |
| - severity: Severity                                              |
| - timestamp: String                                               |
| - description: String                                             |
+-------------------------------------------------------------------+
| + getAlertId(): String                                            |
| + getAlertType(): String                                          |
| + getSeverity(): Severity                                         |
| + getTimestamp(): String                                          |
| + getDescription(): String                                        |
+-------------------------------------------------------------------+

                           <<interface>>
                   +---------------------------+
                   |      ThreatDetector       |
                   +---------------------------+
                   | + analyze(LogEntry): Opt  |
                   +---------------------------+
                                 ^
                                 | implements
        +------------------------+------------------------+
        |                        |                        |
+-------------------+   +--------------------+   +-------------------+
| BruteForceDetector|   |SQLInjectionDetector|   |    DoSDetector    |
+-------------------+   +--------------------+   +-------------------+
| - THRESHOLD: 5    |   | - SQLI_KEYWORDS    |   | - THRESHOLD: 10   |
| - failedAttempts  |   |                    |   | - requestCounts   |
+-------------------+   +--------------------+   +-------------------+
        |
        +------------------------+
                                 |
                        +--------------------+
                        | BlacklistDetector  |
                        +--------------------+
                        | - blacklistedIps   |
                        +--------------------+
```

---

## 3. Threat Detection Flow Diagram
```
   Incoming Log Line
           |
           v
   Is eventType == "LOGIN_FAILED"?
     /               \
   YES               NO
   /                   \
Increment IP count    Contains SQL query?
Is count >= 5?          /             \
 /         \          YES             NO
YES        NO         /                 \
 |          |    Contains SQL       Is IP in
Emit       Pass  Keywords?          blacklist?
Brute            /        \          /      \
Force          YES        NO       YES      NO
Alert           |          |        |        |
              Emit        Pass    Emit      Pass
              SQLi                Blacklist
              Alert               Alert
```

---

# SECTION 16: ONE-PAGE MASTERY & QUICK REVISION CHEAT SHEET

*(Read this 10 minutes before walking into your interview!)*

### 1. Key Elevator Numbers & Facts
* **Language & Build**: Java 17 LTS, Apache Maven.
* **Architecture Pattern**: Strategy Pattern (`ThreatDetector` interface), Service-Oriented Ingestion Pipeline.
* **Data Structures**: `HashSet` (Blacklist - $O(1)$), `HashMap` (Brute Force/DoS counters - $O(1)$), `ArrayList` (Ordered alert storage).
* **Alert Categories**:
  1. `Brute Force Attack` (Severity: **HIGH**, Threshold: 5 failed logins).
  2. `SQL Injection Attempt` (Severity: **CRITICAL**, Tokens: `DROP`, `SELECT`, `UNION`, `OR 1=1`, `--`).
  3. `Possible DoS Attack` (Severity: **HIGH**, Threshold: 10 volumetric requests).
  4. `Blacklisted IP Detected` (Severity: **MEDIUM**, Source: Threat Intel feed).

---

### 2. Top 5 Concepts to Recite with Confidence
1. **Separation of Concerns**: `LogParser` handles tokenization; `ThreatDetector` handles security rules; `AlertManager` handles persistence; `ReportGenerator` handles analytics.
2. **Strategy Pattern**: `ThreatMonitor` executes detectors polymorphically via `Optional<Alert> analyze(LogEntry)`. We can add 10 new detectors without changing existing code (Open/Closed Principle).
3. **Alert Fatigue Prevention**: Counters reset after triggering an alert to avoid spamming analysts with repetitive notifications.
4. **Java 8+ Modernity**: Employs `Optional` for null-safety, Streams API (`Collectors.groupingBy`) for forensic summaries, and `UUIDv4` for unique incident ticket IDs.
5. **Real-World Alignment**: Maps directly to Tier 1 SIEM triage operations seen in Splunk, QRadar, and Microsoft Sentinel.

---

### 3. Golden Rule for Any Unexpected Question
If the interviewer asks about a scenario you haven't implemented (e.g., *"How do you detect ransomware?"* or *"How do you handle distributed Kafka streams?"*):
> *"In my current Java architecture, that would be implemented as a new class adhering to the `ThreatDetector` interface. I would ingest the specific telemetry (e.g., file extension renaming events or Kafka partition streams), apply sliding window correlation, and emit a prioritized alert to `AlertManager`."*
