# THE SOC PROJECT BIBLE
## ZERO-TO-HERO MASTER GUIDE FOR THE "SOC LOG MONITORING & THREAT DETECTION SYSTEM"

> **Target Audience**: Absolute Beginner to Advanced Security Engineer / SOC Analyst  
> **Mission**: Teach everything from zero in plain, human English first, followed by technical depth, and final interview-ready responses.  
> **Rule**: No prior knowledge assumed. Every technical term is explained with everyday real-world analogies.

---

# TABLE OF CONTENTS
* [Section 1: Project Overview (Beginner Foundations & Analogies)](#section-1-project-overview)
* [Section 2: End-to-End Execution Flow (What Enters & What Leaves)](#section-2-end-to-end-flow)
* [Section 3: Full System Architecture & ASCII Diagrams](#section-3-full-system-architecture)
* [Section 4: Folder Structure & Directory Blueprint](#section-4-folder-structure)
* [Section 5: Every File & Every Line of Code Explained](#section-5-every-file-explained)
* [Section 6: Core Java Concepts Explained from Scratch](#section-6-java-concepts-used)
* [Section 7: Object-Oriented Programming (OOP) in Action](#section-7-oop-concepts)
* [Section 8: SOLID Principles Made Dead Simple](#section-8-solid-principles)
* [Section 9: Design Patterns & Strategy Pattern Deep Dive](#section-9-design-patterns)
* [Section 10: Threat Detection Algorithms & Mechanics](#section-10-threat-detection-deep-dive)
* [Section 11: Enterprise Cybersecurity Concepts from Zero](#section-11-cybersecurity-concepts)
* [Section 12: System Design & Architectural Trade-offs](#section-12-system-design)
* [Section 13: Scalability: Handling 1M to 10M Logs](#section-13-scalability)
* [Section 14: Enterprise SIEM Comparison (Splunk, QRadar, Sentinel, ELK)](#section-14-enterprise-version)
* [Section 15: Resume Elevator Pitches (30s, 1m, 2m, 5m)](#section-15-resume-explanation)
* [Section 16: 300 Interview Questions & Answers (Beginner, Intermediate, Advanced)](#section-16-interview-questions)
* [Section 17: Tough Project Defense Guide (Interviewer Attacks)](#section-17-project-defense)
* [Section 18: 100 Rapid-Fire Viva Questions](#section-18-viva-questions)
* [Section 19: One-Page Master Cheat Sheet](#section-19-cheat-sheet)
* [Section 20: The 15-Minute "Emergency" Crash Course](#section-20-if-i-forget-everything)

---

# SECTION 1: PROJECT OVERVIEW

## 1. What Problem Does This Project Solve?
Imagine a major hospital. Every day, thousands of events occur:
* Patients check in at reception.
* Doctors open pharmacy cabinets.
* Nurses swipe keycards into operating rooms.
* Strangers walk through hallways.

If the hospital has **no security guards and no cameras**, a criminal could walk into the pharmacy, pick a lock 10 times, steal critical medicine, and walk out. Nobody would realize what happened until weeks later when an inventory check is conducted.

Now translate this to the digital computer world:
* **The Hospital** is a company’s web server, customer database, and login portals.
* **The Events** are computer requests: users typing passwords, employees viewing files, and visitors loading web pages.
* **The Problem**: Servers record every single one of these actions into a text file called a **Log File**. A single corporate server generates millions of lines of logs every single day!
* Humans cannot physically read millions of lines of text line-by-line. While security staff are reading page 2, a hacker on page 500 has already guessed an admin password, stolen credit card numbers, or crashed the servers.

**What our project does**:
This project is an **automated, intelligent software security guard**. It reads thousands of server log lines in a fraction of a second, analyzes what each person or computer is doing, spots malicious behavior (hackers guessing passwords, stealing databases, or flooding the system), sounds an immediate alarm, and writes a neat, one-page summary report for company leadership.

---

## 2. The Real-World Airport Security Analogy
Think of an international airport:
1. **The Passengers**: Thousands of travelers entering the terminal (these are web users and server requests).
2. **The Passport Logbook**: Every time a traveler presents their passport, the border agent stamps it and records their name, flight, time, and passport number in a massive database (this is `logs.txt`).
3. **The Automated Scanners & Watchlists**:
   * **Metal Detectors & X-Ray Scanners**: Automatically scan luggage for weapons and explosives without opening every bag manually (this is our `SQLInjectionDetector` looking for dangerous keywords).
   * **Police Most-Wanted Database**: Automatically checks every passport against a list of known criminals (this is our `BlacklistDetector` matching IPs against `blacklist.txt`).
   * **Ticket Turnstiles**: If someone tries to scan a fake ticket 5 times in a row, the turnstile locks and flashes a red light (this is our `BruteForceDetector`).
   * **Crowd Control**: If 100 people try to rush through a single door at the same second, emergency barriers drop (this is our `DoSDetector`).
4. **The Armed Guard Station**: When a scanner beeps, a red alert flashes on the security monitors with the traveler's photo and exact location (this is our `AlertManager` writing to `alerts.txt`).
5. **The Daily Shift Report**: At the end of the day, the Chief of Airport Security receives a document: *"Today 50,000 travelers arrived, 4 weapons were confiscated, 1 wanted person was detained, and Gate 4 had the highest traffic"* (this is our `ReportGenerator` creating `incident_report.txt`).

---

## 3. The Bank Security Analogy
Think of a large commercial bank branch:
* When a customer enters, the security camera timestamps their arrival.
* When someone walks up to an ATM and enters the wrong PIN 5 times in a row, the ATM does not assume they are just clumsy—it assumes someone found a stolen card and is trying to guess the PIN. The ATM swallows the card and alerts bank security.
* If a customer presents a check that has words crossed out or written in red ink with suspicious commands like "PAY ALL MONEY IN VAULT", the teller hits the silent alarm under the desk.
* If 500 people suddenly crowd the teller counter shouting all at once, preventing anyone else from depositing money, the branch manager calls emergency services.

**Our Java application is that entire bank security apparatus running in software.**

---

## 4. Why Do Companies Need It?
1. **Financial Loss Prevention**: The average global cost of a data breach is over $4.4 million (IBM Cost of a Data Breach Report). Catching attacks while they are happening stops data theft before money is lost.
2. **Legal & Compliance Requirements**: Governments and financial authorities (PCI-DSS for credit cards, HIPAA for healthcare, GDPR for privacy) legally mandate that organizations monitor and retain audit logs.
3. **Reputation & Trust**: If a company is hacked and customer data leaks online, customers leave and stock values plummet.
4. **24/7/365 Protection**: Hackers rarely attack at 2:00 PM on a Tuesday; they attack at 3:00 AM on Christmas morning when human engineers are asleep. Automated software never sleeps.

---

## 5. Why SOC Teams Use It & Relation to Entry-Level SOC Jobs
* **What is a SOC?**: A Security Operations Center (SOC) is a physical or virtual command room where cybersecurity analysts sit in front of multi-monitor workstations monitoring live network traffic and alerts.
* **The SOC Analyst Tier 1 Role**:
  * An entry-level SOC Analyst spends 80% of their day reviewing incoming security alerts generated by automated systems (like this project!).
  * When an alert pops up, the analyst investigates: *Is this real? What IP did it come from? What user was targeted? What should we do to block it?*
* **Why this project makes you stand out in an interview**:
  * Most candidates can only talk about tools they clicked in class (like Wireshark or Splunk).
  * **You can say**: *"I didn't just look at alerts—I built the software engine that parses raw logs, calculates correlation thresholds, identifies Indicators of Compromise, and generates the alerts!"*

---

# SECTION 2: END-TO-END FLOW

Here is the exact journey of a single piece of data through our entire project from start to finish.

```
+-----------------------------------------------------------------------------------------+
|                                  THE DATA LIFECYCLE                                     |
+-----------------------------------------------------------------------------------------+

 [ Step 1: Ingestion ]
   logs.txt  --->  Raw String line:
                   "2026-09-01 10:01:15 LOGIN_FAILED user=admin ip=192.168.1.10"
        |
        v
 [ Step 2: Parsing & Normalization ]
   LogParser.java
   - What Enters: Raw String
   - What Happens: Splits line, extracts user, ip, timestamp, query
   - What Leaves: Structured LogEntry Java Object
        |
        v
 [ Step 3: Distribution & Orchestration ]
   ThreatMonitor.java
   - What Enters: LogEntry Object
   - What Happens: Loops through registered detectors
        |
        +-----------------------+-----------------------+-----------------------+
        |                       |                       |                       |
        v                       v                       v                       v
 [ Step 4: Security Inspection by Heuristic Detectors ]
   BruteForceDetector      SQLInjectionDetector    DoSDetector             BlacklistDetector
   Counts failures         Scans for SQL keywords  Counts request volume   Checks blacklist.txt
   (Failed >= 5)           ("DROP", "SELECT", etc) (Requests >= 10)        (IP match in Set)
        |                       |                       |                       |
        +-----------------------+-----------------------+-----------------------+
        |
        v If Threat Found: Emits Optional<Alert>
 [ Step 5: Alert Brokering & Notification ]
   AlertManager.java
   - What Enters: Alert Object (UUID, Type, Severity, Timestamp, Description)
   - What Happens:
       1. Prints live alert with emoji to console (stdout)
       2. Appends formatted alert line to src/main/resources/alerts.txt
       3. Stores alert in in-memory List<Alert>
        |
        v When all logs finish processing:
 [ Step 6: Analytical Aggregation & Reporting ]
   ReportGenerator.java
   - What Enters: List<LogEntry> (all logs) + List<Alert> (all generated alerts)
   - What Happens:
       1. Counts total events
       2. Groups alerts by category using Java Streams
       3. Identifies Top 5 most active IP addresses
       4. Evaluates overall security posture
   - What Leaves: incident_report.txt (Executive Summary Report)
```

### Deep Dive into Each Step:

#### Step 1: Log Creation & Reading
* **What Enters**: The raw file `src/main/resources/logs.txt` sitting on the hard drive.
* **Why it exists**: In the real world, web servers (Apache, Nginx, Tomcat) write one line to a log file every time a client sends an HTTP request.
* **What Leaves**: A collection of raw Java `String` lines loaded via `Files.readAllLines()`.

#### Step 2: Ingestion & Normalization (`LogParser.java`)
* **What Enters**: One unformatted `String` (e.g., `"2026-09-01 10:02:20 SQL_QUERY user=test ip=192.168.1.12 query=\"DROP TABLE users\""`).
* **Why it exists**: Detectors cannot easily work with messy text strings. They need an object where they can simply call `.getIpAddress()` or `.getQuery()`.
* **What Leaves**: A clean, immutable `LogEntry` object with discrete member variables.

#### Step 3: Threat Engine Dispatch (`ThreatMonitor.java`)
* **What Enters**: The `LogEntry` object.
* **Why it exists**: It acts as the central coordinator. Without it, the main program would have to manually call every single detector one by one.
* **What Leaves**: Routes the `LogEntry` to each detector's `analyze()` method.

#### Step 4: Rule Inspection (`ThreatDetector` implementations)
* **What Enters**: `LogEntry`.
* **Why it exists**: Each detector checks for a specific attack signature or threshold:
  * `BruteForceDetector`: Looks for repeated login failures from the same IP.
  * `SQLInjectionDetector`: Looks for malicious database keywords in query strings.
  * `DoSDetector`: Looks for overwhelming traffic volume from a single IP.
  * `BlacklistDetector`: Looks for matches against known malicious threat intel IPs.
* **What Leaves**: An `Optional<Alert>`. If an attack is detected, it contains an `Alert` object. If the log is clean, it returns `Optional.empty()`.

#### Step 5: Alert Dispatch & Auditing (`AlertManager.java`)
* **What Enters**: The triggered `Alert` object.
* **Why it exists**: Separates the detection of an attack from what you *do* with the alert.
* **What Leaves**: 
  1. Live colored terminal notification.
  2. Persistent append to `src/main/resources/alerts.txt`.
  3. Stored in memory for the final report.

#### Step 6: Executive Reporting (`ReportGenerator.java`)
* **What Enters**: The complete history of all logs and all alerts.
* **Why it exists**: Executives, CISOs, and SOC shift managers don't have time to read thousands of alerts. They need high-level metrics: *How many attacks? Who attacked us the most? What is our risk level?*
* **What Leaves**: A clean, human-readable file named `incident_report.txt`.

---

# SECTION 3: FULL SYSTEM ARCHITECTURE

## 1. High-Level Component Diagram
```
+---------------------------------------------------------------------------------------------------------+
|                                           SYSTEM COMPONENTS                                             |
|                                                                                                         |
|   +--------------------------+                                      +-------------------------------+   |
|   |       INPUT FEEDS        |                                      |         DOMAIN MODELS         |   |
|   |                          |                                      |                               |   |
|   |  - logs.txt              |                                      |  - LogEntry.java              |   |
|   |  - blacklist.txt         |                                      |  - Alert.java                 |   |
|   +------------+-------------+                                      |  - Severity.java (Enum)       |   |
|                |                                                    +---------------+---------------+   |
|                v (reads lines)                                                      ^                   |
|   +--------------------------+                                                      |                   |
|   |        LogParser         |--- (creates structured objects)----------------------+                   |
|   +------------+-------------+                                                                          |
|                |                                                                                        |
|                v (passes LogEntry)                                                                      |
|   +-------------------------------------------------------------------------------------------------+   |
|   |                                          ThreatMonitor                                          |   |
|   |  Maintains: List<ThreatDetector>                                                                |   |
|   +--------------------------------------------+----------------------------------------------------+   |
|                                                |                                                        |
|                       +------------------------+------------------------+                               |
|                       |                        |                        |                               |
|                       v                        v                        v                               |
|          +-------------------------+  +------------------+  +-----------------------+                   |
|          |   BruteForceDetector    |  |  SQLInjDetector  |  |      DoSDetector      |                   |
|          +-------------------------+  +------------------+  +-----------------------+                   |
|                       |                        |                        |                               |
|                       +------------------------+------------------------+                               |
|                                                |                                                        |
|                                                v                                                        |
|                                  +---------------------------+                                          |
|                                  |     BlacklistDetector     |                                          |
|                                  +-------------+-------------+                                          |
|                                                |                                                        |
|                                                v (emits Optional<Alert>)                                |
|   +-------------------------------------------------------------------------------------------------+   |
|   |                                          AlertManager                                           |   |
|   |  - Prints alert to terminal stdout                                                              |   |
|   |  - Appends alert to alerts.txt                                                                  |   |
|   |  - Collects alerts in List<Alert>                                                               |   |
|   +--------------------------------------------+----------------------------------------------------+   |
|                                                |                                                        |
|                                                v (passes all logs + alerts)                             |
|   +-------------------------------------------------------------------------------------------------+   |
|   |                                        ReportGenerator                                          |   |
|   |  - Groups alerts by category (Java Streams)                                                     |   |
|   |  - Computes top 5 attacker IPs                                                                  |   |
|   |  - Writes summary to incident_report.txt                                                        |   |
|   +-------------------------------------------------------------------------------------------------+   |
+---------------------------------------------------------------------------------------------------------+
```

---

## 2. Detailed Data Flow Diagram
```
+---------------+
|   logs.txt    |
+-------+-------+
        |
        | [Raw Text String]
        | "2026-09-01 10:01:15 LOGIN_FAILED user=admin ip=192.168.1.10"
        v
+---------------+
|   LogParser   |
+-------+-------+
        |
        | [LogEntry Instance]
        | timestamp : "2026-09-01 10:01:15"
        | eventType : "LOGIN_FAILED"
        | username  : "admin"
        | ipAddress : "192.168.1.10"
        | query     : null
        v
+---------------+
| ThreatMonitor |
+-------+-------+
        |
        +-----------------------------------+-----------------------------------+
        |                                   |                                   |
        v                                   v                                   v
+-----------------------+       +-----------------------+       +-----------------------+
|  BruteForceDetector   |       | SQLInjectionDetector  |       |   BlacklistDetector   |
|                       |       |                       |       |                       |
| Checks: LOGIN_FAILED  |       | Checks: query != null |       | Checks:               |
| Counter: 192.168.1.10 |       | query is null here!   |       | blacklist.contains(ip)|
| attempts = 5 (HIT!)   |       |                       |       | Result: false         |
+-----------+-----------+       +-----------+-----------+       +-----------+-----------+
            |                               |                               |
            | Emits Alert                   | Emits Optional.empty()        | Emits Optional.empty()
            v                               v                               v
+---------------------------------------------------------------------------------------+
|                                    ThreatMonitor                                      |
| Filters: Only forwards present alerts (alertOpt.ifPresent(...))                       |
+-------------------------------------------+-------------------------------------------+
                                            |
                                            | [Alert Instance]
                                            | id          : UUID (fac33671-...)
                                            | alertType   : "Brute Force Attack"
                                            | severity    : Severity.HIGH
                                            | timestamp   : "2026-09-01 10:01:15"
                                            | description : "Multiple failed logins (5) from IP..."
                                            v
+---------------------------------------------------------------------------------------+
|                                     AlertManager                                      |
| 1. System.out.println("🚨 ALERT GENERATED: ...")                                      |
| 2. FileUtils.appendLine("alerts.txt", alert.toString())                               |
| 3. alerts.add(alert)                                                                  |
+---------------------------------------------------------------------------------------+
```

---

## 3. Object-Oriented Class Diagram
```
+-----------------------------------------------------------------------------------+
|                                  com.socmonitor                                   |
+-----------------------------------------------------------------------------------+

   +--------------------------+                   +-------------------------------+
   |        LogEntry          |                   |             Alert             |
   +--------------------------+                   +-------------------------------+
   | - timestamp: String      |                   | - alertId: String (UUID)      |
   | - eventType: String      |                   | - alertType: String           |
   | - username: String       |                   | - severity: Severity          |
   | - ipAddress: String      |                   | - timestamp: String           |
   | - query: String          |                   | - description: String         |
   +--------------------------+                   +-------------------------------+
   | + getTimestamp(): String |                   | + getAlertId(): String        |
   | + getEventType(): String |                   | + getAlertType(): String      |
   | + getUsername(): String  |                   | + getSeverity(): Severity     |
   | + getIpAddress(): String |                   | + getTimestamp(): String      |
   | + getQuery(): String     |                   | + getDescription(): String    |
   | + toString(): String     |                   | + toString(): String          |
   +--------------------------+                   +-------------------------------+

                                                  +-------------------------------+
                                                  |        <<enumeration>>        |
                                                  |           Severity            |
                                                  +-------------------------------+
                                                  | LOW                           |
                                                  | MEDIUM                        |
                                                  | HIGH                          |
                                                  | CRITICAL                      |
                                                  +-------------------------------+

                                  <<interface>>
                         +-----------------------------+
                         |       ThreatDetector        |
                         +-----------------------------+
                         | + analyze(LogEntry):        |
                         |   Optional<Alert>           |
                         +-----------------------------+
                                        ^
                                        | implements
             +--------------------------+--------------------------+
             |                          |                          |
+--------------------------+ +--------------------------+ +-------------------------+
|    BruteForceDetector    | |   SQLInjectionDetector   | |       DoSDetector       |
+--------------------------+ +--------------------------+ +-------------------------+
| - THRESHOLD: int = 5     | | - SQLI_KEYWORDS: List    | | - THRESHOLD: int = 10   |
| - failedAttempts: Map    | +--------------------------+ | - requestCounts: Map    |
+--------------------------+ | + analyze(LogEntry): Opt | +-------------------------+
| + analyze(LogEntry): Opt | +--------------------------+ | + analyze(LogEntry): Opt|
+--------------------------+                              +-------------------------+
             |
             +--------------------------+
                                        |
                         +------------------------------+
                         |      BlacklistDetector       |
                         +------------------------------+
                         | - blacklistedIps: Set<String>|
                         +------------------------------+
                         | + analyze(LogEntry): Opt     |
                         +------------------------------+
```

---

## 4. Complete Execution Sequence Diagram
```
User Terminal        Main             LogParser        ThreatMonitor     Detectors      AlertManager    ReportGen
     |                |                   |                  |               |                |             |
  1. |-- mvn exec: -->|                   |                  |               |                |             |
     |                |-- 2. new AlertMgr("alerts.txt") ---->|               |                |             |
     |                |   (resets alerts.txt)                |               |                |             |
     |                |                                      |               |                |             |
     |                |-- 3. new ThreatMonitor(alertMgr) --->|               |                |             |
     |                |   (loads blacklist & registers rules)|               |                |             |
     |                |                                      |-- 4. new() -->|                |             |
     |                |                                      |               |                |             |
     |                |-- 5. Files.readAllLines(logs.txt)    |               |                |             |
     |                |                                      |               |                |             |
     |                |== 6. FOR EACH LINE IN LOGS ========= |               |                |             |
     |                |                                      |               |                |             |
     |                |-- 7. parse(line) ------------------->|               |                |             |
     |                |<- 8. return LogEntry ----------------|               |                |             |
     |                |                                      |               |                |             |
     |                |-- 9. processLog(entry) ------------->|               |                |             |
     |                |                                      |-- 10. analyze>|                |             |
     |                |                                      |<- 11. Opt<Alert>               |             |
     |                |                                      |                                |             |
     |                |                                      |== 12. IF ALERT PRESENT ======= |             |
     |                |                                      |-- 13. addAlert(Alert) -------->|             |
     |                |                                      |                                |-- 14. stdout|
     |                |                                      |                                |-- 15. append|
     |                |                                      |                                |   alerts.txt|
     |                |== 16. END OF FOR LOOP ============== |               |                |             |
     |                |                                                                       |             |
     |                |-- 17. generateReport(allLogs, alerts, "incident_report.txt") -------->|             |
     |                |   (calculates stream aggregates and writes summary to disk)           |-- 18. write |
     |                |                                                                       |   report.txt|
     |<- 19. Finish --|                                                                       |             |
```

---

# SECTION 4: FOLDER STRUCTURE & DIRECTORY BLUEPRINT

Let us walk through the entire project layout and explain **why** every single folder exists using real-world physical office analogies.

```text
SOC-Monitor/
├── pom.xml                                   <-- The Factory Blueprint
├── README.md                                 <-- The Public Instruction Manual
├── incident_report.txt                       <-- The Daily Executive Shift Summary
├── src/
│   └── main/
│       ├── java/                             <-- The Machinery & Engine Room
│       │   └── com/
│       │       └── socmonitor/
│       │           ├── Main.java             <-- The Master Power Switch
│       │           ├── model/                <-- The Standard Forms & Blank Files
│       │           │   ├── Severity.java
│       │           │   ├── LogEntry.java
│       │           │   └── Alert.java
│       │           ├── service/              <-- The Processing Department
│       │           │   ├── LogParser.java
│       │           │   ├── AlertManager.java
│       │           │   └── ThreatMonitor.java
│       │           ├── detector/             <-- The Security Guard Stations
│       │           │   ├── ThreatDetector.java
│       │           │   ├── BruteForceDetector.java
│       │           │   ├── SQLInjectionDetector.java
│       │           │   ├── DoSDetector.java
│       │           │   └── BlacklistDetector.java
│       │           ├── report/               <-- The Business Intelligence & Stats Desk
│       │           │   └── ReportGenerator.java
│       │           └── util/                 <-- The Filing & Storage Clerks
│       │               └── FileUtils.java
│       └── resources/                        <-- The Warehouse & In/Out Baskets
│           ├── logs.txt
│           ├── blacklist.txt
│           └── alerts.txt
```

### Folder Breakdown & Analogies:

#### 1. `model/` (Data Transfer Objects & Entities)
* **What it contains**: `Severity.java`, `LogEntry.java`, `Alert.java`.
* **Why it exists**: In clean programming, we never mix data storage with business logic. Models have NO logic, NO security checks, and NO file operations. They are pure data holders.
* **Real-World Analogy**: Blank printed forms in a doctor's office (e.g., Patient Intake Form, Prescription Slip). The form itself doesn't heal you or check your heartbeat; it just holds your name, blood pressure, and date.

#### 2. `service/` (Core Orchestration & Business Workflows)
* **What it contains**: `LogParser.java`, `AlertManager.java`, `ThreatMonitor.java`.
* **Why it exists**: Coordinates actions. Takes data from models, sends it to detectors, and forwards results to managers.
* **Real-World Analogy**: The head nurse or shift supervisor who directs incoming patients to the right examination rooms and alerts the doctor.

#### 3. `detector/` (Security Heuristic Rules)
* **What it contains**: `ThreatDetector.java`, `BruteForceDetector.java`, `SQLInjectionDetector.java`, `DoSDetector.java`, `BlacklistDetector.java`.
* **Why it exists**: Keeps all detection logic completely isolated from one another.
* **Real-World Analogy**: Specialized security guards standing at different airport gates. One guard checks metal detectors; one guard inspects passports; one guard checks liquid containers. Each guard has one job.

#### 4. `report/` (Forensic Analytics)
* **What it contains**: `ReportGenerator.java`.
* **Why it exists**: Summarizing historical data should never slow down real-time threat detection. This folder is dedicated to post-incident data science and reporting.
* **Real-World Analogy**: The accounting or auditing department that reviews sales receipts at the end of the month and presents an executive graph to the CEO.

#### 5. `util/` (Reusable Helpers)
* **What it contains**: `FileUtils.java`.
* **Why it exists**: Prevents code duplication. Rather than every class opening file streams, handling exceptions, and closing buffers, all file I/O is centralized here.
* **Real-World Analogy**: The office filing clerk. When anyone needs a folder opened or a page filed, they give it to the filing clerk rather than doing it themselves.

#### 6. `resources/` (Raw Telemetry & Feeds)
* **What it contains**: `logs.txt`, `blacklist.txt`, `alerts.txt`.
* **Why it exists**: In standard Maven projects, all non-Java configuration files, raw inputs, and text feeds belong inside `src/main/resources`.

---

# SECTION 5: EVERY FILE EXPLAINED LINE-BY-LINE

No shortcuts. We will now walk through **every single line of code** in every file of the project.

---

## FILE 1: `Severity.java`
* **Path**: `src/main/java/com/socmonitor/model/Severity.java`
* **Why it exists**: In cybersecurity, not all alarms are equal. A ping from a suspicious IP is an alert, but someone deleting your database is a national emergency! This file defines the priority levels.
* **Analogy**: Hospital triage tags: Green (Minor), Yellow (Delayed), Orange (Urgent), Red (Immediate Life Threat).

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.model;
2: 
3: public enum Severity {
4:     LOW,
5:     MEDIUM,
6:     HIGH,
7:     CRITICAL
8: }
```

* **Line 1 (`package com.socmonitor.model;`)**: Tells Java which folder/package this file belongs to. Packages prevent naming collisions.
* **Line 3 (`public enum Severity {`)**: Declares a public `enum` (enumeration). An enum is a special Java data type that defines a fixed set of constants. You cannot create any other severity value besides the ones listed inside.
* **Line 4 (`LOW,`)**: Represents minor informational warnings (e.g., a single failed login).
* **Line 5 (`MEDIUM,`)**: Represents suspicious activity that requires analyst review within a few hours (e.g., an interaction with a blacklisted IP).
* **Line 6 (`HIGH,`)**: Represents an active, dangerous attack attempt (e.g., Brute Force login flood or DoS traffic).
* **Line 7 (`CRITICAL`)**: Represents an immediate threat of data destruction, unauthorized administrative access, or full compromise (e.g., SQL Injection exploit).
* **Line 8 (`}`)**: Closes the enum block.

---

## FILE 2: `LogEntry.java`
* **Path**: `src/main/java/com/socmonitor/model/LogEntry.java`
* **Why it exists**: Raw text logs look like messy strings. This class packages each piece of information (who, what, when, where) into a clean, immutable object.
* **Analogy**: A printed parking ticket displaying License Plate, Date, Time, and Violation.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.model;
2: 
3: public class LogEntry {
4:     private String timestamp;
5:     private String eventType;
6:     private String username;
7:     private String ipAddress;
8:     private String query;
9: 
10:     public LogEntry(String timestamp, String eventType, String username, String ipAddress, String query) {
11:         this.timestamp = timestamp;
12:         this.eventType = eventType;
13:         this.username = username;
14:         this.ipAddress = ipAddress;
15:         this.query = query;
16:     }
17: 
18:     public String getTimestamp() { return timestamp; }
19:     public String getEventType() { return eventType; }
20:     public String getUsername() { return username; }
21:     public String getIpAddress() { return ipAddress; }
22:     public String getQuery() { return query; }
23: 
24:     @Override
25:     public String toString() {
26:         return "LogEntry{" +
27:                 "timestamp='" + timestamp + '\'' +
28:                 ", eventType='" + eventType + '\'' +
29:                 ", username='" + username + '\'' +
30:                 ", ipAddress='" + ipAddress + '\'' +
31:                 (query != null ? ", query='" + query + '\'' : "") +
32:                 '}';
33:     }
34: }
```

* **Line 1**: Package declaration.
* **Line 3**: Declares the public class `LogEntry`.
* **Lines 4-8**: Declares 5 private member variables (`timestamp`, `eventType`, `username`, `ipAddress`, `query`). They are `private` to enforce **encapsulation**—no outside code can modify them directly.
* **Line 10**: Constructor method. It is called when `new LogEntry(...)` is executed.
* **Lines 11-15**: Assigns incoming parameter values to the object's internal fields using `this.field = param`.
* **Lines 18-22**: Getter methods. These provide read-only access to external classes like detectors. Notice there are **no setters**, making `LogEntry` immutable and thread-safe!
* **Line 24**: `@Override` informs the Java compiler that we are replacing the default `Object.toString()` method.
* **Lines 25-33**: Returns a clean string representation of the log entry for easy debugging and logging. Line 31 conditionally prints the query only if it is not null.

---

## FILE 3: `Alert.java`
* **Path**: `src/main/java/com/socmonitor/model/Alert.java`
* **Why it exists**: When an attack rule matches, we must capture the incident forensic details: What happened? How bad is it? When did it happen? What is its unique tracking number?
* **Analogy**: A 911 police emergency ticket dispatched to patrol cars.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.model;
2: 
3: import java.util.UUID;
4: 
5: public class Alert {
6:     private String alertId;
7:     private String alertType;
8:     private Severity severity;
9:     private String timestamp;
10:     private String description;
11: 
12:     public Alert(String alertType, Severity severity, String timestamp, String description) {
13:         this.alertId = UUID.randomUUID().toString();
14:         this.alertType = alertType;
15:         this.severity = severity;
16:         this.timestamp = timestamp;
17:         this.description = description;
18:     }
19: 
20:     public String getAlertId() { return alertId; }
21:     public String getAlertType() { return alertType; }
22:     public Severity getSeverity() { return severity; }
23:     public String getTimestamp() { return timestamp; }
24:     public String getDescription() { return description; }
25: 
26:     @Override
27:     public String toString() {
28:         return String.format("[%s] [%s] %s - %s (ID: %s)", timestamp, severity, alertType, description, alertId);
29:     }
30: }
```

* **Line 3**: Imports `java.util.UUID` for generating Universally Unique Identifiers.
* **Lines 6-10**: Encapsulated private attributes of a security alert.
* **Line 12**: Constructor accepting alert type, severity enum, timestamp, and forensic explanation.
* **Line 13 (`this.alertId = UUID.randomUUID().toString();`)**: Automatically generates a unique, collision-proof 128-bit ID (e.g., `d7448533-45c9-4b7c-ad26-86bd1ae19f8c`) upon creation. No two alerts will ever share an ID.
* **Lines 14-17**: Initializes properties.
* **Lines 20-24**: Read-only public getters.
* **Lines 26-29**: Overrides `toString()` using `String.format()` to produce an industry-standard SOC console alert line: `[Timestamp] [Severity] AlertType - Description (ID: UUID)`.

---

## FILE 4: `ThreatDetector.java` (Interface)
* **Path**: `src/main/java/com/socmonitor/detector/ThreatDetector.java`
* **Why it exists**: This interface is the master blueprint for all security rules. It defines the contract: *"Any class that wants to be a detector must provide an `analyze` method that takes a `LogEntry` and returns an `Optional<Alert>`."*
* **Analogy**: A universal power outlet. Any appliance can plug in as long as it has the standard 3-prong plug.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.detector;
2: 
3: import com.socmonitor.model.Alert;
4: import com.socmonitor.model.LogEntry;
5: import java.util.Optional;
6: 
7: public interface ThreatDetector {
8:     Optional<Alert> analyze(LogEntry logEntry);
9: }
```

* **Line 5**: Imports `java.util.Optional`.
* **Line 7 (`public interface ThreatDetector {`)**: Declares the interface. An interface has no implementation code; it only declares method signatures.
* **Line 8 (`Optional<Alert> analyze(LogEntry logEntry);`)**: The contract method.
  * **Input**: A `LogEntry` object.
  * **Output**: `Optional<Alert>`. If a threat is detected, it returns `Optional.of(alert)`. If clean, it returns `Optional.empty()`. Using `Optional` eliminates ugly `null` returns and prevents `NullPointerException`!

---

## FILE 5: `BruteForceDetector.java`
* **Path**: `src/main/java/com/socmonitor/detector/BruteForceDetector.java`
* **Why it exists**: Detects credential-guessing attacks. If someone tries to log in and fails 5 times from the same IP, this detector raises a HIGH severity alarm.
* **Analogy**: An ATM locking your card after 3 incorrect PIN entries.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.detector;
2: 
3: import com.socmonitor.model.Alert;
4: import com.socmonitor.model.LogEntry;
5: import com.socmonitor.model.Severity;
6: 
7: import java.util.HashMap;
8: import java.util.Map;
9: import java.util.Optional;
10: 
11: public class BruteForceDetector implements ThreatDetector {
12:     private static final int THRESHOLD = 5;
13:     private final Map<String, Integer> failedAttempts = new HashMap<>();
14: 
15:     @Override
16:     public Optional<Alert> analyze(LogEntry logEntry) {
17:         if ("LOGIN_FAILED".equals(logEntry.getEventType())) {
18:             String ip = logEntry.getIpAddress();
19:             failedAttempts.put(ip, failedAttempts.getOrDefault(ip, 0) + 1);
20: 
21:             if (failedAttempts.get(ip) >= THRESHOLD) {
22:                 int attempts = failedAttempts.get(ip);
23:                 failedAttempts.put(ip, 0); 
24:                 
25:                 String description = String.format("Multiple failed login attempts (%d) detected from IP: %s", attempts, ip);
26:                 return Optional.of(new Alert("Brute Force Attack", Severity.HIGH, logEntry.getTimestamp(), description));
27:             }
28:         }
29:         return Optional.empty();
30:     }
31: }
```

* **Line 11 (`public class BruteForceDetector implements ThreatDetector`)**: Declares the class and commits to fulfilling the `ThreatDetector` interface contract.
* **Line 12 (`private static final int THRESHOLD = 5;`)**: Constant defining that 5 failed attempts trigger an alarm.
* **Line 13 (`private final Map<String, Integer> failedAttempts = new HashMap<>();`)**: In-memory state tracking table. Keys are IP addresses (`"192.168.1.10"`), values are failure counts (`1, 2, 3...`).
* **Line 15**: Overrides the `analyze` method.
* **Line 17 (`if ("LOGIN_FAILED".equals(logEntry.getEventType()))`)**: Checks if this log line is a failed login event. Notice `"LOGIN_FAILED"` is written first—this prevents a crash if `getEventType()` is null.
* **Line 18**: Extracts the source IP address.
* **Line 19 (`failedAttempts.put(ip, failedAttempts.getOrDefault(ip, 0) + 1);`)**: Increments the failure count for that IP. If the IP is new, `getOrDefault` returns `0`, adding `1`.
* **Line 21 (`if (failedAttempts.get(ip) >= THRESHOLD)`)**: Checks if the IP has hit 5 failures.
* **Line 23 (`failedAttempts.put(ip, 0);`)**: **Crucial Line!** Resets the counter back to 0. If we didn't reset it, attempt 6, 7, 8, 9... would each generate duplicate alerts, flooding the analyst's screen (alert fatigue)!
* **Lines 25-26**: Formats the forensic message and returns a `HIGH` severity `Alert` wrapped in `Optional.of()`.
* **Line 29 (`return Optional.empty();`)**: If the event was a successful login or the count is under 5, returns an empty container indicating no threat found.

---

## FILE 6: `SQLInjectionDetector.java`
* **Path**: `src/main/java/com/socmonitor/detector/SQLInjectionDetector.java`
* **Why it exists**: Protects backend databases from unauthorized queries. Scans executed queries for dangerous SQL keywords (`DROP`, `DELETE`, `OR 1=1`, etc.).
* **Analogy**: Airport baggage X-ray machine scanning for shapes resembling firearms or explosives.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.detector;
2: 
3: import com.socmonitor.model.Alert;
4: import com.socmonitor.model.LogEntry;
5: import com.socmonitor.model.Severity;
6: 
7: import java.util.List;
8: import java.util.Optional;
9: 
10: public class SQLInjectionDetector implements ThreatDetector {
11:     private static final List<String> SQLI_KEYWORDS = List.of(
12:             "SELECT", "DROP", "DELETE", "UNION", "OR 1=1", "--"
13:     );
14: 
15:     @Override
16:     public Optional<Alert> analyze(LogEntry logEntry) {
17:         if (logEntry.getQuery() != null) {
18:             String queryUpper = logEntry.getQuery().toUpperCase();
19:             for (String keyword : SQLI_KEYWORDS) {
20:                 if (queryUpper.contains(keyword)) {
21:                     String description = String.format("Suspicious SQL keyword '%s' found in query from IP: %s", keyword, logEntry.getIpAddress());
22:                     return Optional.of(new Alert("SQL Injection Attempt", Severity.CRITICAL, logEntry.getTimestamp(), description));
23:                 }
24:             }
25:         }
26:         return Optional.empty();
27:     }
28: }
```

* **Lines 11-13 (`SQLI_KEYWORDS = List.of(...)`)**: Immutable list of classic SQL injection signature markers:
  * `DROP`: Destroys database tables.
  * `DELETE`: Deletes rows.
  * `UNION`: Combines queries to steal passwords from other tables.
  * `OR 1=1`: Always evaluates to true, bypassing password checks.
  * `--`: SQL comment symbol used to truncate queries.
* **Line 17 (`if (logEntry.getQuery() != null)`)**: Guard clause checking if this log line actually contains an executed query.
* **Line 18 (`String queryUpper = logEntry.getQuery().toUpperCase();`)**: **Evasion defense!** Converts query to uppercase. If a hacker types `DrOp` or `sElEcT` to trick the detector, `toUpperCase()` converts it to `DROP` and `SELECT`, catching the attack!
* **Lines 19-20**: Loops through keywords; checks if query contains the token.
* **Lines 21-22**: Formats warning and returns a **`CRITICAL`** severity alert immediately.
* **Line 26**: Returns `Optional.empty()` if clean.

---

## FILE 7: `DoSDetector.java`
* **Path**: `src/main/java/com/socmonitor/detector/DoSDetector.java`
* **Why it exists**: Detects Denial of Service (DoS) floods. If a single IP sends an abnormal volume of requests in rapid succession, it flags an alert.
* **Analogy**: Someone prank-calling a 911 dispatch center 50 times a minute, blocking real emergencies.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.detector;
2: 
3: import com.socmonitor.model.Alert;
4: import com.socmonitor.model.LogEntry;
5: import com.socmonitor.model.Severity;
6: 
7: import java.util.HashMap;
8: import java.util.Map;
9: import java.util.Optional;
10: 
11: public class DoSDetector implements ThreatDetector {
12:     private static final int THRESHOLD = 10; 
13:     private final Map<String, Integer> requestCounts = new HashMap<>();
14: 
15:     @Override
16:     public Optional<Alert> analyze(LogEntry logEntry) {
17:         String ip = logEntry.getIpAddress();
18:         requestCounts.put(ip, requestCounts.getOrDefault(ip, 0) + 1);
19: 
20:         if (requestCounts.get(ip) >= THRESHOLD) {
21:             int count = requestCounts.get(ip);
22:             requestCounts.put(ip, 0); 
23: 
24:             String description = String.format("Possible DoS Attack! Excessive requests (%d) from IP: %s", count, ip);
25:             return Optional.of(new Alert("Possible DoS Attack", Severity.HIGH, logEntry.getTimestamp(), description));
26:         }
27: 
28:         return Optional.empty();
29:     }
30: }
```

* **Line 12 (`THRESHOLD = 10;`)**: Configured limit for our simulation (in real production, this would be set to 500-1000 requests per minute).
* **Line 13 (`requestCounts = new HashMap<>()`)**: Map tracking total requests per IP address.
* **Line 18**: Increments request count for the incoming IP address.
* **Line 20**: Checks if count exceeds the threshold of 10 requests.
* **Line 22**: Resets the counter to 0 to avoid continuous alerting on every subsequent request.
* **Lines 24-25**: Emits a `HIGH` severity alert.
* **Line 28**: Returns `Optional.empty()` if traffic is normal.

---

## FILE 8: `BlacklistDetector.java`
* **Path**: `src/main/java/com/socmonitor/detector/BlacklistDetector.java`
* **Why it exists**: Compares source IPs against a Threat Intelligence feed of known malicious hacker IPs (`blacklist.txt`).
* **Analogy**: Bouncers at a nightclub checking IDs against a police list of known pickpockets.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.detector;
2: 
3: import com.socmonitor.model.Alert;
4: import com.socmonitor.model.LogEntry;
5: import com.socmonitor.model.Severity;
6: import com.socmonitor.util.FileUtils;
7: 
8: import java.util.Optional;
9: import java.util.Set;
10: 
11: public class BlacklistDetector implements ThreatDetector {
12:     private final Set<String> blacklistedIps;
13: 
14:     public BlacklistDetector(String blacklistFilePath) {
15:         this.blacklistedIps = FileUtils.loadBlacklist(blacklistFilePath);
16:     }
17: 
18:     @Override
19:     public Optional<Alert> analyze(LogEntry logEntry) {
20:         String ip = logEntry.getIpAddress();
21:         if (blacklistedIps.contains(ip)) {
22:             String description = String.format("Activity detected from blacklisted IP: %s", ip);
23:             return Optional.of(new Alert("Blacklisted IP Detected", Severity.MEDIUM, logEntry.getTimestamp(), description));
24:         }
25:         return Optional.empty();
26:     }
27: }
```

* **Line 12 (`private final Set<String> blacklistedIps;`)**: Stores blacklisted IPs inside a `Set` instead of a `List`. In a `HashSet`, looking up an IP using `.contains()` takes constant time ($O(1)$), even if the blacklist has 1,000,000 IPs!
* **Lines 14-16**: Constructor calls `FileUtils.loadBlacklist()` to load IPs from disk during startup.
* **Line 20**: Grabs source IP from the log.
* **Line 21 (`if (blacklistedIps.contains(ip))`)**: Checks if the IP is in the blacklist.
* **Lines 22-23**: Emits a `MEDIUM` severity alert.
* **Line 25**: Returns `Optional.empty()` if the IP is clean.

---

## FILE 9: `LogParser.java`
* **Path**: `src/main/java/com/socmonitor/service/LogParser.java`
* **Why it exists**: Translates raw text strings into structured `LogEntry` objects, safely handling timestamps, event types, usernames, IPs, and quoted database queries.
* **Analogy**: A language translator transcribing foreign radio chatter into standardized English medical charts.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.service;
2: 
3: import com.socmonitor.model.LogEntry;
4: 
5: public class LogParser {
6:     
7:     public static LogEntry parse(String line) {
8:         if (line == null || line.trim().isEmpty()) {
9:             return null;
10:         }
11: 
12:         try {
13:             String[] parts = line.split(" ", 4); 
14:             if (parts.length < 4) return null;
15: 
16:             String timestamp = parts[0] + " " + parts[1];
17:             String eventType = parts[2];
18:             String rest = parts[3];
19: 
20:             String username = extractField(rest, "user=");
21:             String ipAddress = extractField(rest, "ip=");
22:             String query = null;
23: 
24:             if (rest.contains("query=")) {
25:                 query = extractField(rest, "query=\"");
26:                 if (query.endsWith("\"")) {
27:                     query = query.substring(0, query.length() - 1);
28:                 }
29:             }
30: 
31:             return new LogEntry(timestamp, eventType, username, ipAddress, query);
32:             
33:         } catch (Exception e) {
34:             System.err.println("Failed to parse log line: " + line);
35:             return null;
36:         }
37:     }
38: 
39:     private static String extractField(String text, String prefix) {
40:         int startIndex = text.indexOf(prefix);
41:         if (startIndex == -1) return "UNKNOWN";
42:         
43:         startIndex += prefix.length();
44:         int endIndex = text.indexOf(" ", startIndex);
45:         
46:         if (endIndex == -1 || text.substring(startIndex).startsWith("\"")) {
47:             if (text.substring(startIndex).startsWith("\"")) {
48:                 int quoteEndIndex = text.indexOf("\"", startIndex + 1);
49:                 if (quoteEndIndex != -1) {
50:                     return text.substring(startIndex, quoteEndIndex + 1);
51:                 }
52:             }
53:             return text.substring(startIndex);
54:         }
55:         
56:         return text.substring(startIndex, endIndex);
57:     }
58: }
```

* **Lines 8-10**: Guard clause: if the line is blank or null, returns null safely.
* **Line 13 (`String[] parts = line.split(" ", 4);`)**: Splits ONLY the first 3 spaces!
  * `parts[0]` = Date (`"2026-09-01"`)
  * `parts[1]` = Time (`"10:02:20"`)
  * `parts[2]` = Event Type (`"SQL_QUERY"`)
  * `parts[3]` = The entire rest of the line (`"user=test ip=192.168.1.12 query=\"DROP TABLE users\""`)
  * *Why limit 4?* If we didn't use limit 4, spaces inside `"DROP TABLE users"` would split into separate array elements, corrupting the query!
* **Lines 16-18**: Assembles timestamp and event type.
* **Lines 20-21**: Calls helper `extractField()` to grab username and IP.
* **Lines 24-29**: If query exists, extracts everything between quotes and strips trailing quote.
* **Line 31**: Instantiates and returns the populated `LogEntry`.
* **Lines 33-36**: Gracefully catches malformed log lines, logs to `System.err`, and returns null without crashing the application.
* **Lines 39-57 (`extractField`)**: Token extraction helper that finds `user=` or `ip=`, finds the next space, and extracts the substring cleanly.

---

## FILE 10: `AlertManager.java`
* **Path**: `src/main/java/com/socmonitor/service/AlertManager.java`
* **Why it exists**: The central manager that handles all triggered alerts. It prints alerts to the terminal, appends them to disk in `alerts.txt`, and stores them for reporting.
* **Analogy**: A central 911 dispatch operator recording emergencies and broadcasting calls over police radio.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.service;
2: 
3: import com.socmonitor.model.Alert;
4: import com.socmonitor.util.FileUtils;
5: 
6: import java.util.ArrayList;
7: import java.util.List;
8: 
9: public class AlertManager {
10:     private final List<Alert> alerts = new ArrayList<>();
11:     private final String alertFilePath;
12: 
13:     public AlertManager(String alertFilePath) {
14:         this.alertFilePath = alertFilePath;
15:         FileUtils.clearFile(alertFilePath); 
16:     }
17: 
18:     public void addAlert(Alert alert) {
19:         alerts.add(alert);
20:         System.out.println("🚨 ALERT GENERATED: " + alert.toString());
21:         FileUtils.appendLine(alertFilePath, alert.toString());
22:     }
23: 
24:     public List<Alert> getAlerts() {
25:         return alerts;
26:     }
27: }
```

* **Line 10 (`alerts = new ArrayList<>()`)**: In-memory list storing all generated alerts.
* **Line 11 (`alertFilePath`)**: Destination path (`src/main/resources/alerts.txt`).
* **Line 15 (`FileUtils.clearFile(alertFilePath);`)**: Cleans out old alerts from previous test runs so each run starts fresh.
* **Line 18 (`public void addAlert(Alert alert)`)**: Core alert handler:
  * **Line 19**: Saves alert in memory.
  * **Line 20**: Prints live alert with warning emoji to terminal console.
  * **Line 21**: Persists the alert to `alerts.txt` on disk.
* **Line 24**: Returns the list of all collected alerts.

---

## FILE 11: `ThreatMonitor.java`
* **Path**: `src/main/java/com/socmonitor/service/ThreatMonitor.java`
* **Why it exists**: The orchestrator. Holds the list of active threat detectors and feeds each log entry through the detection chain.
* **Analogy**: The airport conveyor belt moving luggage past the X-ray, metal detector, and explosive sniffer.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.service;
2: 
3: import com.socmonitor.detector.*;
4: import com.socmonitor.model.Alert;
5: import com.socmonitor.model.LogEntry;
6: 
7: import java.util.ArrayList;
8: import java.util.List;
9: import java.util.Optional;
10: 
11: public class ThreatMonitor {
12:     private final List<ThreatDetector> detectors = new ArrayList<>();
13:     private final AlertManager alertManager;
14: 
15:     public ThreatMonitor(AlertManager alertManager, String blacklistFilePath) {
16:         this.alertManager = alertManager;
17:         
18:         detectors.add(new BruteForceDetector());
19:         detectors.add(new SQLInjectionDetector());
20:         detectors.add(new DoSDetector());
21:         detectors.add(new BlacklistDetector(blacklistFilePath));
22:     }
23: 
24:     public void processLog(LogEntry logEntry) {
25:         if (logEntry == null) return;
26: 
27:         for (ThreatDetector detector : detectors) {
28:             Optional<Alert> alertOpt = detector.analyze(logEntry);
29:             alertOpt.ifPresent(alertManager::addAlert);
30:         }
31:     }
32: }
```

* **Line 12 (`List<ThreatDetector> detectors`)**: Polymorphic list holding all active detector instances.
* **Lines 15-22**: Constructor accepts `AlertManager` and registers all four detectors into the list.
* **Line 24 (`public void processLog(LogEntry logEntry)`)**: Evaluates a single log entry.
* **Line 27 (`for (ThreatDetector detector : detectors)`)**: Loops through every registered detector.
* **Line 28 (`Optional<Alert> alertOpt = detector.analyze(logEntry);`)**: Executes the detector's rule.
* **Line 29 (`alertOpt.ifPresent(alertManager::addAlert);`)**: If an alert is present, dispatches it to `AlertManager`. If empty, does nothing.

---

## FILE 12: `ReportGenerator.java`
* **Path**: `src/main/java/com/socmonitor/report/ReportGenerator.java`
* **Why it exists**: Post-run analytical reporting. Summarizes log counts, groups alerts by type using Java Streams, ranks top active IPs, and evaluates overall security posture.
* **Analogy**: The police chief's end-of-shift executive summary report.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.report;
2: 
3: import com.socmonitor.model.Alert;
4: import com.socmonitor.model.LogEntry;
5: import com.socmonitor.util.FileUtils;
6: 
7: import java.util.HashMap;
8: import java.util.List;
9: import java.util.Map;
10: import java.util.stream.Collectors;
11: 
12: public class ReportGenerator {
13:     
14:     public static void generateReport(List<LogEntry> logs, List<Alert> alerts, String reportFilePath) {
15:         StringBuilder report = new StringBuilder();
16:         report.append("==================================================\n");
17:         report.append("          SOC INCIDENT REPORT SUMMARY\n");
18:         report.append("==================================================\n\n");
19: 
20:         report.append("1. GENERAL STATISTICS\n");
21:         report.append("--------------------------------------------------\n");
22:         report.append("Total Log Entries Processed : ").append(logs.size()).append("\n");
23:         report.append("Total Alerts Generated      : ").append(alerts.size()).append("\n\n");
24: 
25:         report.append("2. ALERT BREAKDOWN BY TYPE\n");
26:         report.append("--------------------------------------------------\n");
27:         Map<String, Long> alertCounts = alerts.stream()
28:                 .collect(Collectors.groupingBy(Alert::getAlertType, Collectors.counting()));
29:         
30:         for (Map.Entry<String, Long> entry : alertCounts.entrySet()) {
31:             report.append(String.format("- %-25s : %d\n", entry.getKey(), entry.getValue()));
32:         }
33:         report.append("\n");
34: 
35:         report.append("3. MOST ACTIVE IPs\n");
36:         report.append("--------------------------------------------------\n");
37:         Map<String, Long> ipCounts = logs.stream()
38:                 .collect(Collectors.groupingBy(LogEntry::getIpAddress, Collectors.counting()));
39:         
40:         ipCounts.entrySet().stream()
41:                 .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
42:                 .limit(5)
43:                 .forEach(e -> report.append(String.format("- IP: %-15s : %d requests\n", e.getKey(), e.getValue())));
44:         report.append("\n");
45: 
46:         report.append("4. SECURITY SUMMARY\n");
47:         report.append("--------------------------------------------------\n");
48:         if (alerts.isEmpty()) {
49:             report.append("System is Secure. No threats detected.\n");
50:         } else {
51:             report.append("Multiple threats detected! Immediate investigation required.\n");
52:             long criticalAlerts = alerts.stream().filter(a -> a.getSeverity() == com.socmonitor.model.Severity.CRITICAL).count();
53:             if (criticalAlerts > 0) {
54:                 report.append("CRITICAL WARNING: ").append(criticalAlerts).append(" critical alert(s) found.\n");
55:             }
56:         }
57:         report.append("\n==================================================\n");
58: 
59:         FileUtils.writeToFile(reportFilePath, report.toString());
60:         System.out.println("Incident report generated at: " + reportFilePath);
61:     }
62: }
```

* **Line 15 (`StringBuilder report = new StringBuilder();`)**: Creates an efficient mutable string builder to assemble the report text without memory waste.
* **Lines 22-23**: Appends total logs processed and total alerts generated.
* **Lines 27-28 (`alerts.stream().collect(Collectors.groupingBy(...))`)**: **Java Streams in action!** Groups alerts by type (Brute Force, SQLi, etc.) and counts the occurrences of each.
* **Lines 30-32**: Appends alert breakdown lines with clean formatting.
* **Lines 37-43**: Finds the Top 5 most active IPs:
  1. Groups logs by `ipAddress`.
  2. Sorts entries in reverse order (`comparingByValue().reversed()`).
  3. Takes only the top 5 (`limit(5)`).
  4. Appends each IP and request count.
* **Lines 48-56**: Assesses overall risk. If any `CRITICAL` alerts exist, appends an urgent warning flag.
* **Line 59**: Saves the finished report to `incident_report.txt` via `FileUtils`.

---

## FILE 13: `FileUtils.java`
* **Path**: `src/main/java/com/socmonitor/util/FileUtils.java`
* **Why it exists**: Centralizes safe disk I/O (reading files, writing files, appending lines, clearing files). Uses `try-with-resources` to guarantee no file lock leaks.
* **Analogy**: The corporate archivist/records clerk who safely handles all filing cabinet operations.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor.util;
2: 
3: import java.io.*;
4: import java.nio.file.Files;
5: import java.nio.file.Paths;
6: import java.util.HashSet;
7: import java.util.List;
8: import java.util.Set;
9: 
10: public class FileUtils {
11: 
12:     public static Set<String> loadBlacklist(String filePath) {
13:         Set<String> blacklist = new HashSet<>();
14:         try {
15:             List<String> lines = Files.readAllLines(Paths.get(filePath));
16:             for (String line : lines) {
17:                 if (!line.trim().isEmpty() && !line.startsWith("#")) {
18:                     blacklist.add(line.trim());
19:                 }
20:             }
21:         } catch (IOException e) {
22:             System.err.println("Could not load blacklist from " + filePath + ". File might not exist yet.");
23:         }
24:         return blacklist;
25:     }
26: 
27:     public static void appendLine(String filePath, String line) {
28:         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
29:             writer.write(line);
30:             writer.newLine();
31:         } catch (IOException e) {
32:             System.err.println("Failed to write to file: " + filePath);
33:         }
34:     }
35: 
36:     public static void writeToFile(String filePath, String content) {
37:         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
38:             writer.write(content);
39:         } catch (IOException e) {
40:             System.err.println("Failed to write to file: " + filePath);
41:         }
42:     }
43: 
44:     public static void clearFile(String filePath) {
45:         try {
46:             new FileWriter(filePath, false).close();
47:         } catch (IOException e) {
48:             // Ignored
49:         }
50:     }
51: }
```

* **Line 12 (`loadBlacklist`)**: Reads `blacklist.txt` into a `HashSet<String>`.
* **Line 17**: Ignores empty lines and comment lines starting with `#`.
* **Line 28 (`try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)))`)**:
  * `FileWriter(filePath, true)`: The `true` parameter enables **append mode**, so new alerts are added to the bottom without erasing existing ones.
  * Wrapped in `BufferedWriter` for high-speed memory buffering.
  * Wrapped in `try (...)` which guarantees the file is closed automatically.
* **Line 36 (`writeToFile`)**: Overwrites the file completely with the incident report.
* **Line 44 (`clearFile`)**: Truncates the file to 0 bytes by opening `FileWriter` in overwrite mode (`false`) and closing it immediately.

---

## FILE 14: `Main.java`
* **Path**: `src/main/java/com/socmonitor/Main.java`
* **Why it exists**: Application driver and bootstrap entry point. Configures file paths, instantiates services, runs the ingestion loop, and triggers final reporting.
* **Analogy**: The stage director starting the play and signaling actors.

### Complete Code & Line-by-Line Breakdown:
```java
1: package com.socmonitor;
2: 
3: import com.socmonitor.model.LogEntry;
4: import com.socmonitor.report.ReportGenerator;
5: import com.socmonitor.service.AlertManager;
6: import com.socmonitor.service.LogParser;
7: import com.socmonitor.service.ThreatMonitor;
8: 
9: import java.io.IOException;
10: import java.nio.file.Files;
11: import java.nio.file.Paths;
12: import java.util.ArrayList;
13: import java.util.List;
14: 
15: public class Main {
16:     private static final String LOG_FILE = "src/main/resources/logs.txt";
17:     private static final String BLACKLIST_FILE = "src/main/resources/blacklist.txt";
18:     private static final String ALERT_FILE = "src/main/resources/alerts.txt";
19:     private static final String REPORT_FILE = "incident_report.txt";
20: 
21:     public static void main(String[] args) {
22:         System.out.println("Starting SOC Log Monitoring System...");
23: 
24:         AlertManager alertManager = new AlertManager(ALERT_FILE);
25:         ThreatMonitor monitor = new ThreatMonitor(alertManager, BLACKLIST_FILE);
26:         List<LogEntry> allLogs = new ArrayList<>();
27: 
28:         try {
29:             List<String> logLines = Files.readAllLines(Paths.get(LOG_FILE));
30:             
31:             for (String line : logLines) {
32:                 LogEntry entry = LogParser.parse(line);
33:                 if (entry != null) {
34:                     allLogs.add(entry);
35:                     monitor.processLog(entry);
36:                 }
37:             }
38:             
39:             System.out.println("Log processing complete. Generating report...");
40:             ReportGenerator.generateReport(allLogs, alertManager.getAlerts(), REPORT_FILE);
41: 
42:         } catch (IOException e) {
43:             System.err.println("Error reading log file: " + e.getMessage());
44:         }
45:     }
46: }
```

* **Lines 16-19**: File path constants.
* **Line 21 (`public static void main(String[] args)`)**: Java JVM entry point.
* **Lines 24-25**: Initializes `AlertManager` and `ThreatMonitor`.
* **Line 29**: Ingests all lines from `logs.txt`.
* **Lines 31-37**: Loops through every line:
  * Parses line into `LogEntry`.
  * If valid, stores in `allLogs` and passes to `monitor.processLog(entry)`.
* **Line 40**: Compiles and writes `incident_report.txt`.
* **Lines 42-44**: Gracefully catches any I/O errors.

---

# SECTION 6: CORE JAVA CONCEPTS EXPLAINED FROM ZERO

If you have never coded before or struggle with Java theory, this section will make every single concept crystal clear using everyday objects.

---

### 1. Class
* **Everyday Human Definition**: A cookie cutter or architectural blueprint. A blueprint of a house is not a physical house—you cannot sleep in a blueprint! It just describes how many rooms and windows a house will have.
* **Technical Definition**: A user-defined template or prototype from which objects are created. It encapsulates attributes (fields) and behaviors (methods).
* **Where Used in Our Project**: `LogEntry.java`, `Alert.java`, `BruteForceDetector.java`.
* **Interview Answer**: *"A class in Java is a blueprint that defines the structure and behavior of domain entities. In this project, `LogEntry` serves as a class blueprint defining properties like IP address and event type."*

---

### 2. Object
* **Everyday Human Definition**: The actual cookie stamped out of the cutter, or the real brick-and-mortar house built from the blueprint.
* **Technical Definition**: An instance of a class stored in computer memory (the heap) with its own unique state.
* **Where Used in Our Project**: When line 31 of `LogParser` runs: `new LogEntry(...)`, a brand new physical object is allocated in RAM holding `"192.168.1.10"`.
* **Interview Answer**: *"An object is a concrete, runtime instance of a class. When raw log lines are parsed, each line is instantiated as an independent `LogEntry` object in memory."*

---

### 3. Constructor
* **Everyday Human Definition**: The birth certificate clerk or the assembly line robot that puts the initial parts together when a product is manufactured.
* **Technical Definition**: A special method whose name matches the class name and has no return type, invoked automatically when `new` is called to initialize instance variables.
* **Where Used in Our Project**: `public LogEntry(...)` in `LogEntry.java`, and `public Alert(...)` in `Alert.java`.
* **Interview Answer**: *"Constructors initialize an object’s state upon creation. In `Alert.java`, the constructor initializes the alert type, severity, and automatically generates a unique UUID tracking ID."*

---

### 4. Interface
* **Everyday Human Definition**: A legal contract or standard plug socket. A wall socket guarantees: *"If you have 3 round prongs, you can plug in and get electricity."* It doesn't care whether you plug in a refrigerator, TV, or phone charger.
* **Technical Definition**: A reference type in Java that can contain only method signatures (and default/static methods) with no state. It defines a contract that implementing classes must fulfill.
* **Where Used in Our Project**: `ThreatDetector.java`.
* **Interview Answer**: *"An interface defines a common behavioral contract. `ThreatDetector` declares `analyze()`, allowing `ThreatMonitor` to run any detector polymorphically without knowing its internal implementation."*

---

### 5. Enum (Enumeration)
* **Everyday Human Definition**: A drop-down menu with fixed choices. When choosing your shirt size online (S, M, L, XL), you cannot type "Elephant". You must pick one of the allowed options.
* **Technical Definition**: A special data type that enables a variable to be a set of predefined constants.
* **Where Used in Our Project**: `Severity.java` (`LOW`, `MEDIUM`, `HIGH`, `CRITICAL`).
* **Interview Answer**: *"Enums provide compile-time type safety. Using `Severity` prevents invalid strings like 'Highh' or 'urgent' from entering the alert pipeline."*

---

### 6. `Optional<T>`
* **Everyday Human Definition**: A gift box. The box might contain a watch (a value), or it might be completely empty. Before opening it, you know you have a box, so you don't drop dead from shock if it's empty!
* **Technical Definition**: A container object introduced in Java 8 used to represent the presence or absence of a non-null value, eliminating the risk of `NullPointerException`.
* **Where Used in Our Project**: Every detector returns `Optional<Alert>`.
* **Interview Answer**: *"Instead of returning error-prone `null` references when an event is clean, our detectors return `Optional<Alert>`. Callers can safely use functional methods like `.ifPresent()`."*

---

### 7. `ArrayList<T>`
* **Everyday Human Definition**: An expandable binder notebook. You can keep adding pages as you go, and flip directly to page 5 instantly.
* **Technical Definition**: A resizable-array implementation of the `List` interface, offering fast $O(1)$ amortized additions and constant-time random access.
* **Where Used in Our Project**: `AlertManager.java` (`List<Alert> alerts = new ArrayList<>()`) and `ThreatMonitor.java` (`List<ThreatDetector>`).
* **Interview Answer**: *"`ArrayList` provides contiguous memory allocation and high CPU cache locality, making it the ideal collection for storing alerts and ordered detector chains."*

---

### 8. `HashMap<K, V>`
* **Everyday Human Definition**: A coat check room at a theater. When you hand the attendant your coat, they give you ticket #42. When you return, you show ticket #42, and they instantly grab your coat in 1 second without searching through 500 coats.
* **Technical Definition**: A hashtable-based implementation of the `Map` interface. It maps unique Keys to Values with average $O(1)$ insertion and lookup time.
* **Where Used in Our Project**: `BruteForceDetector` (`Map<String, Integer>`) and `DoSDetector` to track counts per IP address.
* **Interview Answer**: *"`HashMap` allows instantaneous $O(1)$ lookups of IP addresses, enabling our detectors to update request and failure counts without scanning through millions of historical records."*

---

### 9. `HashSet<T>`
* **Everyday Human Definition**: A bouncer's guest list of VIP names. The bouncer just checks if your name is written on the single sheet of paper.
* **Technical Definition**: A collection that contains no duplicate elements and is backed by a hash table. Checking `set.contains()` takes $O(1)$ time.
* **Where Used in Our Project**: `FileUtils.loadBlacklist()` to store blacklisted IPs.
* **Interview Answer**: *"Checking whether an IP is blacklisted via `HashSet.contains()` takes $O(1)$ constant time, ensuring the blacklist lookup never becomes a bottleneck even with 100,000 indicators."*

---

### 10. Java Streams API
* **Everyday Human Definition**: A factory conveyor belt. Car frames move down the belt where workers wash them (filter), paint them (map), and box them into shipping containers (collect).
* **Technical Definition**: A pipeline of computational operations (intermediate and terminal) applied to sequences of elements in a declarative, functional style.
* **Where Used in Our Project**: `ReportGenerator.java` to group alerts by type and compute top active IPs.
* **Interview Answer**: *"Java Streams allow declarative, functional data processing. In `ReportGenerator`, I used `Collectors.groupingBy()` and `.sorted()` to aggregate forensic data cleanly without complex nested loops."*

---

### 11. Java NIO Files API (`Files.readAllLines`)
* **Everyday Human Definition**: An automated high-speed document scanner that reads an entire document in one flash.
* **Technical Definition**: Part of Java’s Non-blocking I/O (NIO.2) package offering high-level static utility methods for file reading, writing, and path manipulation.
* **Where Used in Our Project**: `Main.java` and `FileUtils.java`.
* **Interview Answer**: *"Java NIO `Files` provides modern, robust file manipulation methods that replace legacy `java.io.File` and handle platform-independent path resolution."*

---

# SECTION 7: OBJECT-ORIENTED PROGRAMMING (OOP) IN ACTION

OOP is not just interview theory—it is the foundation of how our project operates. Here is how each of the 4 pillars is implemented:

```
+-----------------------------------------------------------------------------------------+
|                                    THE 4 OOP PILLARS                                    |
+-----------------------------------------------------------------------------------------+
|                                                                                         |
|   1. ENCAPSULATION                                2. ABSTRACTION                        |
|   - Private fields in LogEntry & Alert            - ThreatDetector interface            |
|   - Public getters only (No setters!)             - Hides complex detection logic       |
|   - Prevents unauthorized state mutation          - Exposes only analyze()              |
|                                                                                         |
|   3. INHERITANCE / REALIZATION                    4. POLYMORPHISM                       |
|   - BruteForceDetector implements ThreatDetector  - ThreatMonitor stores List<ThreatDet>|
|   - SQLInjectionDetector implements ThreatDetector- Loops and calls detector.analyze()  |
|   - Reuses standard behavioral contract           - Executes child logic at runtime     |
|                                                                                         |
+-----------------------------------------------------------------------------------------+
```

### 1. Encapsulation (Data Hiding)
* **What it means**: Keep variables private so outside code cannot directly tamper with an object's internal memory.
* **Where it is in our code**:
  In `LogEntry.java`:
  ```java
  private String timestamp;
  private String ipAddress;
  public String getIpAddress() { return ipAddress; }
  ```
  Because there is no `setIpAddress()` method, once a `LogEntry` is created by `LogParser`, it is **immutable**. A rogue detector cannot change the IP address or tamper with the forensic evidence!

### 2. Abstraction (Hiding Complexity)
* **What it means**: Show only what an object does, not the messy details of how it does it.
* **Where it is in our code**:
  In `ThreatDetector.java`:
  ```java
  public interface ThreatDetector {
      Optional<Alert> analyze(LogEntry logEntry);
  }
  ```
  `ThreatMonitor` only knows that calling `analyze()` returns an alert if something is wrong. It has no idea whether the detector uses regex, hash lookups, or keyword scanning.

### 3. Inheritance / Interface Realization
* **What it means**: Classes sharing a common identity and behavior.
* **Where it is in our code**:
  All 4 detectors implement `ThreatDetector`:
  ```java
  public class BruteForceDetector implements ThreatDetector { ... }
  public class SQLInjectionDetector implements ThreatDetector { ... }
  ```

### 4. Polymorphism (Many Forms)
* **What it means**: Treating different objects as if they were the same parent type, while Java automatically runs the correct child method at runtime.
* **Where it is in our code**:
  In `ThreatMonitor.java`:
  ```java
  List<ThreatDetector> detectors = new ArrayList<>();
  detectors.add(new BruteForceDetector());
  detectors.add(new SQLInjectionDetector());

  for (ThreatDetector detector : detectors) {
      detector.analyze(logEntry); // Dynamic method dispatch!
  }
  ```
  At runtime, when `detector` is a `BruteForceDetector`, Java runs brute force counting. When `detector` is a `SQLInjectionDetector`, Java runs SQL keyword scanning!

---

# SECTION 8: SOLID PRINCIPLES MADE DEAD SIMPLE

SOLID is the gold standard for writing clean, enterprise-grade software. Here is how our project implements every single one:

### 1. S - Single Responsibility Principle (SRP)
* **Rule**: A class should have one, and only one, reason to change.
* **In Our Project**:
  * If the format of `logs.txt` changes, we **only** edit `LogParser.java`.
  * If the brute force threshold changes from 5 to 10, we **only** edit `BruteForceDetector.java`.
  * If we want alerts sent via email or Slack, we **only** edit `AlertManager.java`.
  * If the boss wants a PDF report instead of text, we **only** edit `ReportGenerator.java`.

### 2. O - Open/Closed Principle (OCP)
* **Rule**: Open for extension, closed for modification. You should be able to add new features without modifying existing code.
* **In Our Project**:
  * Suppose your company wants to detect **Ransomware file-renaming attacks**.
  * **Bad Design**: Opening a 1000-line `Main.java` and adding another messy `else if` block.
  * **Our SOLID Design**: Create a brand-new file `RansomwareDetector.java` that `implements ThreatDetector`. Register it in `ThreatMonitor`. You never touch or risk breaking the existing Brute Force or SQL Injection classes!

### 3. L - Liskov Substitution Principle (LSP)
* **Rule**: You should be able to substitute any child class wherever the parent/interface is expected without breaking the system.
* **In Our Project**:
  * `ThreatMonitor` accepts any object implementing `ThreatDetector`. If you pass `BruteForceDetector` or `DoSDetector`, the engine works seamlessly without throwing unexpected exceptions.

### 4. I - Interface Segregation Principle (ISP)
* **Rule**: Don't force classes to implement methods they don't need.
* **In Our Project**:
  * Our `ThreatDetector` interface has only **one single method**: `analyze(LogEntry)`.
  * We did not pollute it with `generateReport()` or `parseLog()`, which would force detectors to implement useless methods.

### 5. D - Dependency Inversion Principle (DIP)
* **Rule**: High-level modules should depend on abstractions (interfaces), not concrete classes.
* **In Our Project**:
  * `ThreatMonitor` holds a `List<ThreatDetector>` (the interface abstraction). It does not have hardcoded variables like `private BruteForceDetector bfd;`.

---

# SECTION 9: DESIGN PATTERNS & THE STRATEGY PATTERN

## 1. What is the Strategy Design Pattern?
The Strategy Pattern enables selecting an algorithm's behavior at runtime. Instead of hardcoding all algorithms in one giant method, algorithms are extracted into separate classes implementing a shared interface.

## 2. Comparison: Without vs. With Strategy Pattern

### The Ugly Way (WITHOUT Strategy Pattern):
```java
// Terrible, amateur code!
public class BigGodClass {
    public void process(String line) {
        if (line.contains("FAILED")) {
            // 50 lines of brute force code
        } else if (line.contains("SELECT") || line.contains("DROP")) {
            // 40 lines of SQL injection code
        } else if (line.contains("10.0.0.5")) {
            // 30 lines of blacklist code
        }
    }
}
```
* **Why this is awful**:
  * Merge conflicts if 2 engineers edit rules at the same time.
  * A typo in SQL injection code breaks login monitoring.
  * Impossible to write automated unit tests for individual rules.

### The Clean Way (WITH Strategy Pattern in our project):
```java
public interface ThreatDetector {
    Optional<Alert> analyze(LogEntry logEntry);
}

// Every rule is a clean, isolated Strategy:
public class BruteForceDetector implements ThreatDetector { ... }
public class SQLInjectionDetector implements ThreatDetector { ... }
public class DoSDetector implements ThreatDetector { ... }
public class BlacklistDetector implements ThreatDetector { ... }
```

```
                     +----------------------+
                     |    ThreatMonitor     |
                     +----------+-----------+
                                |
                                v maintains
                     +----------------------+
                     |   <<interface>>      |
                     |   ThreatDetector     |
                     +----------+-----------+
                                |
          +---------------------+---------------------+
          |                     |                     |
+---------+--------+  +---------+--------+  +---------+--------+
|BruteForceDetector|  |SQLInjectDetector |  |   DoSDetector    |
+------------------+  +------------------+  +------------------+
```

---

# SECTION 10: THREAT DETECTION ALGORITHMS DEEP DIVE

---

## 1. Brute Force Detection
* **Attack Goal**: Guessing user passwords via automated dictionary attacks.
* **Algorithm Step-by-Step**:
  ```
  Step 1: Receive LogEntry.
  Step 2: Check: Is eventType equal to "LOGIN_FAILED"?
          - If NO: Return empty (Ignore benign logins).
          - If YES: Proceed to Step 3.
  Step 3: Extract source IP address (e.g., "192.168.1.10").
  Step 4: Look up IP in failedAttempts HashMap.
          - If IP not seen before: Count = 1.
          - If IP seen before: Count = Existing Count + 1.
  Step 5: Check: Is Count >= 5?
          - If NO: Return empty.
          - If YES: 
              a. Reset Count to 0 (Mitigate alert fatigue).
              b. Create new Alert(Type="Brute Force", Severity=HIGH).
              c. Return Optional.of(alert).
  ```

### Diagram:
```
[ Incoming Event ] ---> "LOGIN_FAILED" from 192.168.1.10
                              |
                              v
                   [ Check HashMap Counter ]
                   Current Failures for IP = 4
                              |
                              v
                   [ Increment Counter: 4 + 1 = 5 ]
                              |
                     Is count >= 5?
                     /            \
                   YES            NO
                   /                \
        [ TRIGGER ALERT! ]     [ Wait for next event ]
        - Severity: HIGH
        - Reset count to 0
```

---

## 2. SQL Injection Detection
* **Attack Goal**: Tricking the database into executing unauthorized queries to dump user tables or bypass logins.
* **Algorithm Step-by-Step**:
  ```
  Step 1: Receive LogEntry.
  Step 2: Check: Does this log contain a query? (query != null)
          - If NO: Return empty.
          - If YES: Proceed to Step 3.
  Step 3: Normalize query to uppercase: query.toUpperCase()
          (Protects against hacker evasion like "sElEcT" or "dRoP").
  Step 4: Iterate through signature keyword list:
          ["SELECT", "DROP", "DELETE", "UNION", "OR 1=1", "--"]
  Step 5: Does queryUpper contain the keyword?
          - If YES:
              Create Alert(Type="SQL Injection", Severity=CRITICAL)
              Return immediately!
  Step 6: If loop finishes with no match: Return empty.
  ```

### Diagram:
```
[ Query Payload ]: 'SELECT * FROM users WHERE 1=1 OR 1=1'
                           |
                           v
              [ Normalize to Uppercase ]
                           |
                           v
      [ Scan for Signatures: DROP, UNION, OR 1=1 ]
                           |
                     Found "OR 1=1"!
                           |
                           v
               [ TRIGGER ALERT! ]
               - Severity: CRITICAL
               - Reason: Boolean Auth Bypass Attempt
```

---

## 3. Denial of Service (DoS) Detection
* **Attack Goal**: Overwhelming server memory and CPU with thousands of requests, crashing the service for legitimate users.
* **Algorithm Step-by-Step**:
  ```
  Step 1: Receive LogEntry.
  Step 2: Extract client IP address.
  Step 3: Increment total requests in requestCounts HashMap.
  Step 4: Is count >= 10?
          - If YES:
              a. Reset counter to 0.
              b. Create Alert(Type="Possible DoS Attack", Severity=HIGH).
              c. Return Optional.of(alert).
          - If NO:
              Return empty.
  ```

---

## 4. Blacklist Detection (Threat Intelligence Matching)
* **Attack Goal**: Catching known compromised computers, botnets, and ransomware command-and-control servers interacting with our network.
* **Algorithm Step-by-Step**:
  ```
  Startup:
  Read blacklist.txt into an in-memory HashSet<String>.

  Runtime:
  Step 1: Receive LogEntry.
  Step 2: Extract IP address.
  Step 3: Query: blacklistedIps.contains(ip)
          - Average time complexity: O(1) instantaneous lookup!
  Step 4: Is IP present in Set?
          - If YES:
              Create Alert(Type="Blacklisted IP Detected", Severity=MEDIUM).
              Return Optional.of(alert).
          - If NO:
              Return empty.
  ```

---

# SECTION 11: CYBERSECURITY CONCEPTS FROM ZERO

---

### 1. SOC (Security Operations Center)
* **Simple English**: The physical security control room of an enterprise, like the CCTV monitoring room of a mega-mall.
* **Technical Definition**: A centralized command unit that monitors, analyzes, and defends an organization's security posture across endpoints, networks, and cloud assets.
* **Interview Response**: *"A SOC is the central operational unit where security analysts monitor real-time telemetry, triage automated alerts, contain active breaches, and conduct threat hunting."*

### 2. SIEM (Security Information & Event Management)
* **Simple English**: The massive super-brain software that collects security recordings from every computer in a company, organizes them, and looks for crime patterns.
* **Technical Definition**: An enterprise software solution that aggregates log data from across an entire network, provides real-time event correlation, alerts on anomalies, and enables compliance auditing.
* **Interview Response**: *"A SIEM provides centralized log aggregation, event normalization, real-time correlation rules, and compliance reporting. My project simulates the core engine of a SIEM."*

### 3. Incident Response (IR)
* **Simple English**: The digital firefighters. When a fire (hack) starts, they rush in, put out the fire, rescue the data, and investigate how the fire started.
* **Technical Definition**: The structured, 6-stage lifecycle (NIST SP 800-61): Preparation, Detection & Analysis, Containment, Eradication, Recovery, and Post-Incident Activity.
* **Interview Response**: *"Incident Response is the formal operational framework for handling security breaches. My project focuses on the Detection and Analysis phase and assists Containment via automated alerting."*

### 4. Threat Hunting
* **Simple English**: A detective walking around a bank looking for suspicious people *before* the alarm rings.
* **Technical Definition**: The proactive, human-driven practice of searching through networks and datasets to detect malicious actors that have evaded automated security defenses.
* **Interview Response**: *"While log monitoring is reactive to triggered alerts, threat hunting is proactive and hypothesis-driven, looking for stealthy persistence and lateral movement."*

### 5. Indicator of Compromise (IoC)
* **Simple English**: Fingerprints, muddy shoe marks, or a broken window left behind by a burglar.
* **Technical Definition**: Forensic digital evidence pointing to potential intrusion, such as known malicious IP addresses, domain names, file hashes, or registry keys.
* **Interview Response**: *"IoCs are actionable forensic artifacts. Our `BlacklistDetector` directly uses IP-based IoCs from threat intelligence feeds to spot malicious actors."*

### 6. IDS (Intrusion Detection System) vs. IPS (Intrusion Prevention System)
* **Simple English**:
  * **IDS**: A security camera with an alarm. It screams when someone breaks in, but does not block the door.
  * **IPS**: An armed security guard. When someone tries to break in, the guard physically tackles them and locks the gate.
* **Technical Definition**: IDS is passive (monitors and alerts). IPS is active (sits inline and drops malicious packets).
* **Interview Response**: *"An IDS detects and alerts on malicious traffic out-of-band, whereas an IPS sits inline to actively drop or reset malicious network sessions."*

### 7. Firewall
* **Simple English**: The security checkpoint gate at the entrance to a military base. Only cars with valid security passes can drive through.
* **Technical Definition**: A network security device that monitors and filters incoming and outgoing network traffic based on established security rule sets (IPs, Ports, Protocols).
* **Interview Response**: *"Firewalls enforce perimeter access control at Layers 3 and 4, blocking unauthorized network ports, while Next-Gen Firewalls (NGFW) inspect Layer 7 application traffic."*

### 8. EDR (Endpoint Detection & Response)
* **Simple English**: A body camera worn by every individual computer in the company, recording every program running, every file opened, and every USB plugged in.
* **Technical Definition**: Integrated endpoint security software that records system-level activities, uses behavioral AI to detect malware, and enables remote host isolation.
* **Interview Response**: *"EDR provides deep, host-level forensic visibility into processes, memory, and registry changes on workstations and servers (e.g., CrowdStrike Falcon, Microsoft Defender for Endpoint)."*

### 9. Nmap (Network Mapper)
* **Simple English**: A flashlight that shines on a building to see which windows and doors are unlocked.
* **Technical Definition**: An open-source network scanner used for network discovery, vulnerability scanning, and service/port enumeration.
* **Interview Response**: *"Nmap is the industry-standard tool for port scanning and network reconnaissance, used by penetration testers and defenders to identify exposed services."*

### 10. Wireshark
* **Simple English**: A microscope that lets you inspect the exact words and numbers inside every electronic letter traveling across a wire.
* **Technical Definition**: A GUI-based packet analyzer used for network troubleshooting, protocol analysis, and deep packet inspection (DPI).
* **Interview Response**: *"Wireshark captures raw Layer 2 through Layer 7 network packets in transit, enabling deep protocol inspection and forensic analysis of malicious network traffic."*

### 11. Kali Linux
* **Simple English**: A digital Swiss Army knife pre-loaded with hundreds of cybersecurity and penetration testing tools.
* **Technical Definition**: A Debian-derived Linux distribution tailored for security auditing, digital forensics, and penetration testing.
* **Interview Response**: *"Kali Linux is a specialized security operating system maintained by Offensive Security, packaging hundreds of offensive and defensive security tools."*

---

# SECTION 12: SYSTEM DESIGN EXPLANATIONS

---

## 1. Why Is `LogParser` Separated?
* **The Reason**: The Single Responsibility Principle (SRP).
* In real-world enterprise architectures, logs come in dozens of different formats: Apache Combined Log Format, JSON, Windows Event XML (EVTX), CEF (Common Event Format), and Syslog RFC 5424.
* By isolating `LogParser.java`, we can easily modify regex or string splitting logic without touching a single detector! Detectors should never know how a log was parsed; they only care about receiving a clean `LogEntry`.

---

## 2. Why Is `ThreatMonitor` Separated?
* **The Reason**: The Orchestrator Pattern.
* `ThreatMonitor` acts as the traffic controller. It holds the collection of active detectors and handles the loop. If tomorrow we want to run detectors concurrently across a multi-core thread pool, we change `ThreatMonitor` without touching the detectors.

---

## 3. Why Are Detectors Separated from One Another?
* **The Reason**: Modularity and Isolation.
* If all 4 detectors were in one file, a developer updating the SQL Injection keyword list might accidentally introduce a bug into the Brute Force login counter!
* Individual detector classes allow independent unit testing, independent tuning, and collaborative development across multiple engineers simultaneously.

---

## 4. Why Is `ReportGenerator` Separated?
* **The Reason**: Separation of Streaming Runtime from Batch Analytics.
* Log parsing and detection occur in real-time as logs stream in. Reporting is an expensive post-mortem aggregation across the entire dataset. Keeping them separate guarantees that heavy stream sorting and grouping never slow down real-time threat detection.

---

## 5. What Disasters Happen If ALL Logic Is Put Inside `Main.java`?
If an amateur developer puts everything in `Main.java`:
1. **The "God Class" Anti-Pattern**: You end up with a 1500-line unmaintainable spaghetti file.
2. **Merge Conflicts**: Two security engineers trying to add different rules will constantly overwrite each other's code in Git.
3. **Zero Testability**: You cannot write JUnit tests for brute force logic without triggering file reading and reporting.
4. **Code Rigidity**: Changing from reading `logs.txt` to listening to an Apache Kafka streaming queue requires rewriting the entire application.

---

# SECTION 13: SCALABILITY: HANDLING 1 MILLION TO 10 MILLION LOGS

---

## 1. Current Architecture Limitations
In our standalone simulation project:
1. `Files.readAllLines()` loads all log lines into JVM heap memory at once. For a 20GB log file, this causes `java.lang.OutOfMemoryError: Java heap space`.
2. A single CPU thread processes logs one by one sequentially.
3. IP counters are stored in an in-memory `HashMap`, meaning if the server restarts, all counters are lost.

---

## 2. How to Scale to 1 Million Logs: `BufferedReader` & Streaming
* **The Fix**: Replace `Files.readAllLines(path)` with streaming:
  ```java
  try (BufferedReader reader = Files.newBufferedReader(Paths.get("logs.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
          LogEntry entry = LogParser.parse(line);
          monitor.processLog(entry);
      }
  }
  ```
* **Why this works**: `BufferedReader` reads only one line into memory at a time. The garbage collector immediately frees the memory after detection. **Memory usage stays under 50 MB whether the file has 10 lines or 10 million lines!**

---

## 3. How to Scale to 10 Million Logs: Multithreading (Producer-Consumer)
```
       [ Log File ]
            |
            v
   [ Reader Thread (Producer) ]
            |
            v puts into
   [ ArrayBlockingQueue<LogEntry> ]
            |
   +--------+--------+--------+ (Consumers take from queue)
   |                 |                 |
   v                 v                 v
[ Worker 1 ]      [ Worker 2 ]      [ Worker 3 ]
(Evaluates rules) (Evaluates rules) (Evaluates rules)
```
* **Why this works**: Utilizes all 8 or 16 CPU cores simultaneously, processing 100,000+ logs per second!
* **Thread Safety Requirement**: Replace `HashMap` with `ConcurrentHashMap` and integer counters with `AtomicInteger` to prevent race conditions.

---

## 4. How Enterprise Scales: Apache Kafka + Distributed SIEM
* **Log Shipper (Beats / Fluentd)**: Installed on servers; ships logs to Apache Kafka.
* **Apache Kafka**: Distributed message broker that buffers millions of logs per second across clusters without dropping data.
* **Elasticsearch / OpenSearch**: Distributed search engine that indexes logs into inverted indexes for sub-second search.
* **Splunk / Kibana**: Web dashboards allowing SOC analysts to query petabytes of data visually.

---

# SECTION 14: ENTERPRISE SIEM COMPARISON

Here is a side-by-side comparison showing how our project's architecture directly mirrors enterprise SIEM platforms:

```
+-------------------------------------------------------------------------------------------------------------+
|                                        SIEM ARCHITECTURAL COMPARISON                                        |
+-------------------------------------------------------------------------------------------------------------+
| Capability             | My Java SOC Engine          | Splunk Enterprise          | Microsoft Sentinel      |
+------------------------+-----------------------------+----------------------------+-------------------------+
| Log Forwarding         | Manual file read / NIO      | Splunk Universal Forwarder | Azure Monitor Agent     |
| Data Normalization     | LogParser.java (POJO)       | Splunk CIM (Common Info)   | ASIM (Advanced Schema)  |
| Correlation Engine     | ThreatDetector (Java OOP)   | SPL Scheduled Searches     | KQL Analytics Rules     |
| Threat Intelligence    | blacklist.txt (HashSet O(1))| Enterprise Security (ES)   | Sentinel Threat Intel   |
| Incident Ticketing     | AlertManager (UUIDv4)       | Notable Events / SOAR      | Incidents / ServiceNow  |
| Forensic Reporting     | ReportGenerator.java        | Scheduled Dashboards / PDF | Sentinel Workbooks      |
+-------------------------------------------------------------------------------------------------------------+
```

### Key Differences & Similarities:
1. **Splunk**: Uses its own query language called **SPL (Search Processing Language)**. In Splunk, our brute force rule is written as:
   `index=auth action=failure | stats count by src_ip | where count >= 5`
2. **Microsoft Sentinel**: Cloud-native SIEM hosted on Microsoft Azure. Uses **KQL (Kusto Query Language)**:
   `SecurityEvent | where EventID == 4625 | summarize count() by IpAddress | where count_ >= 5`
3. **Our Java Project**: Implements this exact same logic in **raw Java code**, proving that you understand the underlying mathematics and algorithms that power these multi-million dollar enterprise platforms!

---

# SECTION 15: RESUME ELEVATOR PITCHES

Memorize these word-for-word based on interview time!

---

### The 30-Second Elevator Pitch (Recruiter Phone Screen)
> *"I designed and developed a Java 17 Security Operations Center (SOC) Log Monitoring and Threat Detection Engine. It automates the detection of Brute Force credential attacks, SQL Injections, Denial of Service floods, and Blacklisted Threat Actor IPs from raw server logs. It normalizes telemetry, applies the Strategy Design Pattern for modular detection, generates real-time alerts with UUID tracking, and compiles executive incident summaries. It demonstrates solid OOP design, clean data structures, and foundational SIEM engineering."*

---

### The 1-Minute Pitch (Technical Interview Opening)
> *"On my resume, you'll find my 'SOC Log Monitoring & Threat Detection System'. I built this project to simulate how an enterprise SIEM like Splunk ingests, normalizes, and correlates security logs.*
> 
> *The system is written in Java 17. Raw audit logs are tokenized into immutable `LogEntry` objects by `LogParser`. A central `ThreatMonitor` engine then dispatches these events through a polymorphic detector pipeline implementing the Strategy Pattern.*
> 
> *I built heuristic rules for four attack classes: Brute Force credential attacks using stateful hash counters, SQL Injection via signature scanning, DoS traffic via volumetric thresholding, and Threat Intelligence matching against blacklisted IPs using constant-time `HashSet` lookups.*
> 
> *Alerts are assigned unique UUIDs and severity ratings, logged to console, written to disk, and aggregated into an executive summary report using Java Streams. This project demonstrates my proficiency in Java OOP, data structures, and defensive SOC engineering."*

---

### The 2-Minute Pitch (Deep Technical Screen)
> *(Deliver the 1-minute pitch above, then continue without pause:)*
> 
> *"...In architecting the system, I prioritized Big-O algorithmic efficiency and clean SOLID principles. For example, in the `BlacklistDetector`, I loaded threat indicators into a Java `HashSet` rather than a `List` to guarantee $O(1)$ constant-time lookups during high-volume stream ingestion.*
> 
> *For stateful rules like `BruteForceDetector` and `DoSDetector`, I used `HashMap` frequency counters with automated counter resets upon alert generation to mitigate alert fatigue. I used `java.util.Optional` across all detector interfaces to guarantee null-safety.*
> 
* **Minute 4: Alert Broker & Reporting**: Explain UUID tracking, disk persistence, and Java Streams aggregations.
* **Minute 5: Scalability & Enterprise Roadmap**: Discuss `BufferedReader` streaming, multithreaded producer-consumer queues, and how to scale to Kafka and Elasticsearch in production.

---

# SECTION 16: 300 INTERVIEW QUESTIONS & ANSWERS

---

## PART 1: 100 BEGINNER QUESTIONS & ANSWERS

#### B1: What does SOC stand for?
* **Answer**: Security Operations Center.
* **Follow-up**: What is its core mission?
* **Answer**: To continuously monitor, detect, analyze, and respond to cybersecurity threats across an organization.

#### B2: What does SIEM stand for?
* **Answer**: Security Information and Event Management.
* **Follow-up**: Name two commercial SIEM tools.
* **Answer**: Splunk Enterprise and IBM QRadar.

#### B3: What language and version did you use?
* **Answer**: Java 17 LTS (Long-Term Support).
* **Follow-up**: Why not Java 8?
* **Answer**: Java 17 provides modern features (`List.of()`, `Optional`, Streams), enhanced performance, and current enterprise support.

#### B4: What build tool did you use?
* **Answer**: Apache Maven.
* **Follow-up**: Where are build configurations stored?
* **Answer**: In `pom.xml` (Project Object Model).

#### B5: What command compiles and runs the project?
* **Answer**: `mvn clean compile exec:java "-Dexec.mainClass=com.socmonitor.Main"`.
* **Follow-up**: What does `mvn clean` do?
* **Answer**: Deletes the `target/` directory containing previous build artifacts.

#### B6: What is a log file?
* **Answer**: A time-stamped text record generated by operating systems, servers, or applications detailing events that occurred.
* **Follow-up**: Why are logs critical for security?
* **Answer**: Logs provide the forensic audit trail required to detect attacks and investigate breaches.

#### B7: What is an IP address?
* **Answer**: A unique numerical label assigned to every device connected to a computer network (e.g., `192.168.1.10`).
* **Follow-up**: What is the difference between IPv4 and IPv6?
* **Answer**: IPv4 uses 32-bit addresses (4.3 billion IPs); IPv6 uses 128-bit addresses to overcome IPv4 exhaustion.

#### B8: What is `LogEntry.java`?
* **Answer**: A domain model representing a structured, parsed log line in memory.
* **Follow-up**: What fields does it contain?
* **Answer**: `timestamp`, `eventType`, `username`, `ipAddress`, and `query`.

#### B9: What is `Alert.java`?
* **Answer**: A model representing a triggered security incident ticket.
* **Follow-up**: How is each alert uniquely identified?
* **Answer**: Via a randomly generated UUIDv4 string.

#### B10: What is an enum in Java?
* **Answer**: A special class representing a fixed group of constants.
* **Follow-up**: What enum did you create?
* **Answer**: `Severity.java` with values `LOW`, `MEDIUM`, `HIGH`, and `CRITICAL`.

#### B11: What is a Brute Force attack?
* **Answer**: An automated attack attempting to guess user passwords through repeated trial and error.
* **Follow-up**: What threshold did you choose?
* **Answer**: 5 failed login attempts from the same IP.

#### B12: What severity is assigned to Brute Force attacks?
* **Answer**: HIGH severity.
* **Follow-up**: Why not Low?
* **Answer**: Repeated failed logins indicate active malicious credential guessing, requiring rapid analyst triage.

#### B13: What is SQL Injection (SQLi)?
* **Answer**: An attack that inserts malicious SQL statements into entry fields to manipulate backend databases.
* **Follow-up**: What severity is assigned to SQLi?
* **Answer**: CRITICAL severity.

#### B14: Why is SQL Injection CRITICAL severity?
* **Answer**: Successful SQL injection can allow attackers to dump customer databases, alter records, or destroy entire tables.
* **Follow-up**: Name two SQL injection signatures your project catches.
* **Answer**: `' OR 1=1` and `DROP TABLE`.

#### B15: What is a Denial of Service (DoS) attack?
* **Answer**: An attack that floods a server with requests to exhaust resources and make it unavailable to legitimate users.
* **Follow-up**: How does your project detect it?
* **Answer**: By tracking request volume per IP and alerting when an IP exceeds 10 requests.

#### B16: What is a blacklist in cybersecurity?
* **Answer**: A list of known malicious entities (IPs, domains, hashes) blocked or flagged on sight.
* **Follow-up**: Where does your project store blacklisted IPs?
* **Answer**: In `src/main/resources/blacklist.txt`.

#### B17: What severity is assigned to Blacklisted IP interactions?
* **Answer**: MEDIUM severity.
* **Follow-up**: Why Medium and not Critical?
* **Answer**: Contact from a blacklisted IP could be benign background scanning or web crawling; it requires investigation but isn't necessarily an active compromise.

#### B18: What is `LogParser.java`?
* **Answer**: A utility class that converts raw log text strings into structured `LogEntry` objects.
* **Follow-up**: What happens if a log line is malformed?
* **Answer**: It catches the exception, prints an error to `System.err`, and returns `null` safely.

#### B19: What is `ThreatMonitor.java`?
* **Answer**: The orchestrator engine that holds all active detectors and passes each log entry to them.
* **Follow-up**: What design pattern does it use?
* **Answer**: The Strategy Design Pattern.

#### B20: What is `ThreatDetector.java`?
* **Answer**: The interface defining the common contract `Optional<Alert> analyze(LogEntry logEntry)`.
* **Follow-up**: Why use an interface?
* **Answer**: To decouple the engine from specific detector rules, adhering to the Open/Closed Principle.

#### B21: What is `AlertManager.java`?
* **Answer**: The service managing alert lifecycle: console printing, disk persistence, and memory caching.
* **Follow-up**: Where does it write alerts?
* **Answer**: `src/main/resources/alerts.txt`.

#### B22: What is `ReportGenerator.java`?
* **Answer**: A reporting service that aggregates log and alert data into `incident_report.txt`.
* **Follow-up**: What key metrics does it summarize?
* **Answer**: Total logs, total alerts, alert breakdown by type, top 5 attacker IPs, and security posture.

#### B23: What is `FileUtils.java`?
* **Answer**: A static utility class centralizing safe file reading, writing, appending, and clearing.
* **Follow-up**: Why centralize file operations?
* **Answer**: To follow the DRY (Don't Repeat Yourself) principle and ensure proper stream closing.

#### B24: What is `Main.java`?
* **Answer**: The main entry point containing `public static void main(String[] args)` that bootstraps and runs the application.
* **Follow-up**: What is the first thing it does?
* **Answer**: Initializes `AlertManager` and `ThreatMonitor`.

#### B25: What is an Indicator of Compromise (IoC)?
* **Answer**: Forensic digital evidence pointing to a potential security breach.
* **Follow-up**: Give an example from the project.
* **Answer**: A malicious IP address matching `blacklist.txt`.

#### B26: What is a False Positive?
* **Answer**: A security alarm triggered by benign, legitimate user activity.
* **Follow-up**: Give an example.
* **Answer**: A developer running a legitimate `SELECT` query triggering our SQL injection detector.

#### B27: What is a False Negative?
* **Answer**: An actual cyberattack that bypasses detection without raising an alarm.
* **Follow-up**: Why are false negatives worse than false positives?
* **Answer**: False negatives leave active attackers inside the network completely undetected.

#### B28: What is alert fatigue?
* **Answer**: Mental exhaustion experienced by SOC analysts from being overwhelmed by too many false or duplicate alarms.
* **Follow-up**: How does your project mitigate alert fatigue?
* **Answer**: By resetting IP failure counters after triggering an alert.

#### B29: What is encapsulation in Java?
* **Answer**: Restricting direct access to object fields using `private` variables and public getters.
* **Follow-up**: Where is it used?
* **Answer**: In `LogEntry.java` and `Alert.java`.

#### B30: What is abstraction in Java?
* **Answer**: Hiding complex internal implementation details and exposing only what is necessary through an interface.
* **Follow-up**: Where is it used?
* **Answer**: In the `ThreatDetector` interface.

#### B31: What is inheritance in Java?
* **Answer**: The mechanism where a class adopts properties and behaviors from a parent class or interface.
* **Follow-up**: Where is it used?
* **Answer**: Detectors implementing `ThreatDetector`.

#### B32: What is polymorphism in Java?
* **Answer**: The ability of different classes to respond to the same interface call in their own unique way.
* **Follow-up**: Where is it used?
* **Answer**: `ThreatMonitor` executing `detector.analyze(logEntry)` dynamically across all detector types.

#### B33: What is `Optional` in Java?
* **Answer**: A container object that may or may not contain a non-null value, avoiding `NullPointerException`.
* **Follow-up**: Where is it used?
* **Answer**: The return type of `ThreatDetector.analyze()`.

#### B34: What is `HashMap`?
* **Answer**: A key-value collection providing average $O(1)$ lookup and insertion time.
* **Follow-up**: Where is it used?
* **Answer**: In `BruteForceDetector` and `DoSDetector` to track counts per IP.

#### B35: What is `HashSet`?
* **Answer**: A collection of unique elements with $O(1)$ constant-time `contains()` checks.
* **Follow-up**: Where is it used?
* **Answer**: In `BlacklistDetector` to store blacklisted IPs.

#### B36: What is `ArrayList`?
* **Answer**: A dynamically resizing array implementation of the `List` interface.
* **Follow-up**: Where is it used?
* **Answer**: In `AlertManager` to collect triggered alerts.

#### B37: What is `try-with-resources`?
* **Answer**: A statement that automatically closes resources (like file streams) when the block completes.
* **Follow-up**: What interface must the resource implement?
* **Answer**: `java.lang.AutoCloseable`.

#### B38: What does `UUID.randomUUID()` produce?
* **Answer**: A 128-bit universally unique identifier string.
* **Follow-up**: Why use it?
* **Answer**: To give every alert a collision-proof tracking ticket number.

#### B39: What does `String.split(" ", 4)` do?
* **Answer**: Splits a string on spaces up to a maximum of 4 array pieces.
* **Follow-up**: Why limit to 4?
* **Answer**: To keep multi-word SQL queries in `parts[3]` from being fragmented.

#### B40: What is the difference between `==` and `.equals()` for Strings?
* **Answer**: `==` compares memory addresses; `.equals()` compares the actual text characters.
* **Follow-up**: Which should always be used for Strings?
* **Answer**: `.equals()`.

#### B41: What is a Yoda condition?
* **Answer**: Writing literal values first in equality checks, e.g., `"LOGIN_FAILED".equals(eventType)`.
* **Follow-up**: What is the benefit?
* **Answer**: It prevents `NullPointerException` if `eventType` is null.

#### B42: What does `toUpperCase()` do in `SQLInjectionDetector`?
* **Answer**: Converts the entire query string to capital letters.
* **Follow-up**: Why is this a security defense?
* **Answer**: It defeats case-evasion bypasses like `dRoP` or `sElEcT`.

#### B43: What does `Collectors.groupingBy()` do?
* **Answer**: A Stream collector that groups elements by a given property and stores results in a Map.
* **Follow-up**: Where is it used?
* **Answer**: In `ReportGenerator` to count alerts by type and logs by IP.

#### B44: What is `StringBuilder`?
* **Answer**: A mutable sequence of characters used to concatenate strings efficiently.
* **Follow-up**: Why not use `+` in loops?
* **Answer**: String concatenation with `+` creates new objects in memory repeatedly, causing GC thrashing.

#### B45: What is a checked exception?
* **Answer**: An exception checked at compile time that must be caught or declared (e.g., `IOException`).
* **Follow-up**: What is an unchecked exception?
* **Answer**: A runtime exception that doesn't require explicit handling (e.g., `NullPointerException`).

#### B46: What is the difference between `System.out` and `System.err`?
* **Answer**: `System.out` is standard output; `System.err` is standard error.
* **Follow-up**: Why separate them?
* **Answer**: To allow administrators to redirect error logs to dedicated log files independently of console output.

#### B47: What is the difference between Low and Critical severity?
* **Answer**: Low is informational; Critical is an active breach requiring immediate emergency containment.
* **Follow-up**: Which detector emits Critical?
* **Answer**: `SQLInjectionDetector`.

#### B48: What is a Tier 1 SOC Analyst?
* **Answer**: The frontline security analyst responsible for monitoring queues, triaging alerts, and escalating real incidents.
* **Follow-up**: What is a Tier 2 SOC Analyst?
* **Answer**: An incident responder who performs deep forensic investigations and root-cause analysis.

#### B49: What is incident containment?
* **Answer**: Actions taken to prevent an attack from spreading (e.g., disconnecting a server from the network or blocking an IP).
* **Follow-up**: How does this project help containment?
* **Answer**: By providing immediate forensic data (offending IP, timestamp, attack payload) to execute blocks quickly.

#### B50: What is GitHub?
* **Answer**: A cloud-based platform for version control and software collaboration using Git.
* **Follow-up**: Why is this project GitHub ready?
* **Answer**: It has clean Maven structure, standardized directories, `.gitignore`-ready artifacts, and complete documentation.

*(Questions B51 through B100 continue foundational checks across basic networking, protocols, and Java syntax:)*
#### B51: What port does HTTP use? (Port 80)
#### B52: What port does HTTPS use? (Port 443)
#### B53: What is TLS/SSL? (Cryptographic protocol encrypting web communication)
#### B54: What is DNS? (Domain Name System: translates domain names to IP addresses)
#### B55: What is DHCP? (Dynamic Host Configuration Protocol: assigns IPs automatically)
#### B56: What is a subnet mask? (Defines network vs. host portions of an IP address)
#### B57: What is `127.0.0.1`? (Loopback localhost address)
#### B58: What is a private IP range? (`10.0.0.0/8`, `172.16.0.0/12`, `192.168.0.0/16`)
#### B59: What is a public IP? (Globally routable Internet IP address)
#### B60: What is a MAC address? (Unique hardware identifier for network interfaces at Layer 2)
#### B61: What is TCP? (Connection-oriented, reliable transport protocol with 3-way handshake)
#### B62: What is UDP? (Connectionless, lightweight, unreliable transport protocol)
#### B63: What is ICMP? (Internet Control Message Protocol, used by `ping` and `traceroute`)
#### B64: What is a SYN packet? (Initial synchronization request in TCP handshake)
#### B65: What is an ACK packet? (Acknowledgment packet in TCP handshake)
#### B66: What is a FIN packet? (Graceful connection termination packet in TCP)
#### B67: What is an RST packet? (Immediate connection reset packet in TCP)
#### B68: What is an API? (Application Programming Interface: rules for software communication)
#### B69: What is JSON? (JavaScript Object Notation: lightweight data-interchange format)
#### B70: What is CSV? (Comma-Separated Values format)
#### B71: What is XML? (Extensible Markup Language)
#### B72: What is a byte? (8 bits of computer memory)
#### B73: What is a bit? (Smallest unit of computer data: 0 or 1)
#### B74: What is JVM? (Java Virtual Machine that executes compiled bytecode)
#### B75: What is JRE? (Java Runtime Environment: JVM + core libraries)
#### B76: What is JDK? (Java Development Kit: JRE + compiler + developer tools)
#### B77: What is bytecode? (Platform-independent `.class` file executed by JVM)
#### B78: What is `javac`? (The Java compiler translating source code to bytecode)
#### B79: What is `java` command? (The JVM launcher executing compiled classes)
#### B80: What is a package in Java? (Namespace grouping related classes into directories)
#### B81: What is `import` keyword? (Makes classes from other packages available)
#### B82: What is `public` access modifier? (Accessible from any other class)
#### B83: What is `private` access modifier? (Accessible only within the same class)
#### B84: What is `protected` access modifier? (Accessible within same package and subclasses)
#### B85: What is default package-private? (Accessible only within the same package)
#### B86: What is `static` keyword? (Belongs to the class itself, not individual instances)
#### B87: What is `final` variable? (Constant whose reference cannot be changed once assigned)
#### B88: What is `final` class? (Class that cannot be subclassed/extended)
#### B89: What is `final` method? (Method that cannot be overridden in child classes)
#### B90: What is a null pointer? (A variable referencing no memory location)
#### B91: What is garbage collection? (Automatic memory reclamation in Java)
#### B92: What is the heap memory? (JVM memory where all objects are allocated)
#### B93: What is stack memory? (JVM memory storing method frames and local variables)
#### B94: What is an infinite loop? (A loop that never terminates, locking up execution)
#### B95: What is an off-by-one error? (Index mistake checking `<=` instead of `<`)
#### B96: What is an array? (Fixed-size collection of identical data types)
#### B97: What is a list? (Ordered, dynamic collection allowing duplicate elements)
#### B98: What is a set? (Unordered collection that strictly forbids duplicate elements)
#### B99: What is a map? (Collection storing key-value associations)
#### B100: What is clean code? (Code that is readable, modular, well-tested, and maintainable)

---

## PART 2: 100 INTERMEDIATE QUESTIONS & ANSWERS

#### I1: What is the Strategy Pattern and how is it used in your project?
* **Answer**: A behavioral design pattern that defines a family of interchangeable algorithms.
* **Follow-up**: Which classes represent the Strategy?
* **Answer**: `ThreatDetector` is the strategy interface; `BruteForceDetector`, `SQLInjectionDetector`, etc., are concrete strategies; `ThreatMonitor` is the context.

#### I2: How does `BruteForceDetector` state tracking work?
* **Answer**: It stores IP failure counts inside a `Map<String, Integer>`.
* **Follow-up**: What is the Big-O time complexity?
* **Answer**: $O(1)$ amortized lookup and update.

#### I3: What is the purpose of `map.getOrDefault(key, 0) + 1`?
* **Answer**: It returns the current count if present or 0 if absent, adding 1 in a single atomic expression.
* **Follow-up**: Why is this better than `containsKey`?
* **Answer**: It avoids executing two separate hash lookups on the map.

#### I4: Why does `BruteForceDetector` reset its counter to 0 after alerting?
* **Answer**: To avoid alert fatigue by preventing an alert on every single subsequent failed attempt.
* **Follow-up**: What is the vulnerability of this approach?
* **Answer**: An attacker failing 9 times only triggers 1 alert instead of 2.

#### I5: How does `SQLInjectionDetector` handle inline comments like `--`?
* **Answer**: It inspects whether the query string contains `"--"`.
* **Follow-up**: Why do attackers inject `--`?
* **Answer**: To truncate the developer's SQL query, commenting out remaining password checks.

#### I6: Why convert queries to uppercase before scanning for SQL injection?
* **Answer**: To defeat case-variation evasion techniques like `uNiOn sElEcT`.
* **Follow-up**: What is this called in security?
* **Answer**: Input canonicalization / normalization.

#### I7: Why use `HashSet` instead of `ArrayList` for blacklisted IPs?
* **Answer**: `HashSet.contains()` is $O(1)$ constant time, while `ArrayList.contains()` is $O(N)$ linear time.
* **Follow-up**: What is the impact with 100,000 blacklisted IPs?
* **Answer**: `HashSet` checks in nanoseconds; `ArrayList` requires scanning up to 100,000 entries per log line.

#### I8: What is the difference between stateful and stateless threat detection?
* **Answer**: Stateless evaluates single events in isolation (SQLi). Stateful tracks historical context across multiple events over time (Brute Force).
* **Follow-up**: Which detectors in your project are stateful?
* **Answer**: `BruteForceDetector` and `DoSDetector`.

#### I9: How does `ReportGenerator` rank the Top 5 most active IPs?
* **Answer**: It groups logs by IP, converts entries to a stream, sorts by value descending, and applies `.limit(5)`.
* **Follow-up**: What stream method reverses sort order?
* **Answer**: `Map.Entry.<String, Long>comparingByValue().reversed()`.

#### I10: What does `Collectors.counting()` return?
* **Answer**: A downstream collector that returns a `Long` representing element count.
* **Follow-up**: Why `Long` and not `Integer`?
* **Answer**: To prevent integer overflow on datasets exceeding 2 billion records.

*(Questions I11 through I100 continue intermediate checks across design patterns, Big-O, concurrency, and security protocols:)*
#### I11: What is the Open/Closed Principle? (Classes open for extension, closed for modification)
#### I12: What is the Single Responsibility Principle? (One class, one reason to change)
#### I13: What is Liskov Substitution Principle? (Subtypes must be substitutable for base types)
#### I14: What is Interface Segregation Principle? (Clients shouldn't depend on unused methods)
#### I15: What is Dependency Inversion Principle? (Depend on abstractions, not concretions)
#### I16: What is method reference syntax in Java? (`ClassName::methodName`)
#### I17: What does `Optional.ifPresent()` do? (Executes consumer lambda only if value is present)
#### I18: What is the difference between `Optional.of()` and `Optional.ofNullable()`? (`of()` throws NPE on null; `ofNullable()` returns empty)
#### I19: What is `UUID.randomUUID()` version? (UUID Version 4: pseudorandom)
#### I20: How many bits is a UUID? (128 bits)
#### I21: What is defensive copying? (Copying collections before returning to prevent mutation)
#### I22: What is an immutable class? (Class whose state cannot change after instantiation)
#### I23: How do you make a class immutable? (Private final fields, no setters, deep copy collections)
#### I24: Why is `LogEntry` immutable? (To prevent forensic log tampering during detection)
#### I25: What is a race condition? (Output depends on unpredictable thread execution timing)
#### I26: Is `HashMap` thread-safe? (No, concurrent writes corrupt internal bucket lists)
#### I27: What is `ConcurrentHashMap`? (Thread-safe map using bucket-level striping)
#### I28: What is `AtomicInteger`? (Lock-free thread-safe integer using hardware CAS)
#### I29: What is Compare-And-Swap (CAS)? (Atomic CPU instruction comparing and swapping memory)
#### I30: What is the volatile keyword? (Guarantees variable visibility across CPU caches)
#### I31: What is thread contention? (Multiple threads fighting for the same lock)
#### I32: What is a deadlock? (Two threads waiting indefinitely for locks held by each other)
#### I33: What is thread starvation? (A thread perpetually denied CPU time by greedy threads)
#### I34: What is an ExecutorService? (Java framework managing thread pools and task execution)
#### I35: What is a thread pool? (Pre-allocated pool of reusable worker threads)
#### I36: What is a daemon thread? (Background thread that doesn't prevent JVM shutdown)
#### I37: What is `BufferedReader`? (Buffers characters for efficient stream reading)
#### I38: What is `BufferedWriter`? (Buffers character output to minimize disk I/O)
#### I39: What is Java NIO? (Non-blocking I/O introduced in Java 1.4/7)
#### I40: What is `Path` in Java NIO? (Modern programmatic representation of file paths)
#### I41: What is `Paths.get()`? (Factory method creating a `Path` from string paths)
#### I42: What is `Files.readAllLines()` limitation? (Loads entire file into heap memory)
#### I43: What is `Files.lines()`? (Streams lines lazily from a file without heap exhaustion)
#### I44: What is the default heap size in Java? (Typically 25% of physical RAM)
#### I45: How do you increase heap size? (`-Xmx4g` flag)
#### I46: What is OutOfMemoryError? (JVM cannot allocate memory for a new object)
#### I47: What is StackOverflowError? (Call stack exceeds memory, usually infinite recursion)
#### I48: What is a memory leak in Java? (Unused objects remaining referenced and uncollected)
#### I49: What is String Constant Pool? (Special heap area caching string literals)
#### I50: What is `String.intern()`? (Forces a dynamically created string into the string pool)
#### I51: What is SQL Prepared Statement? (Pre-compiled SQL query using parameterized inputs)
#### I52: Why do Prepared Statements stop SQLi? (Treats input as pure data, never executable code)
#### I53: What is an ORM? (Object-Relational Mapping, e.g., Hibernate)
#### I54: What is XSS? (Cross-Site Scripting: injecting malicious JavaScript into web apps)
#### I55: What is Stored XSS? (Malicious script permanently saved in database)
#### I56: What is Reflected XSS? (Malicious script reflected off server in URL query)
#### I57: What is DOM-based XSS? (Vulnerability in client-side JavaScript execution)
#### I58: What is CSRF? (Cross-Site Request Forgery: tricking user browser into sending actions)
#### I59: How do anti-CSRF tokens work? (Unpredictable secret token validated on each POST)
#### I60: What is SSRF? (Server-Side Request Forgery: forcing server to make requests)
#### I61: What is Path Traversal? (Using `../` to access unauthorized files on host)
#### I62: What is Command Injection? (Injecting OS shell commands via user input)
#### I63: What is a buffer overflow? (Writing data beyond allocated memory buffer boundaries)
#### I64: Can buffer overflows happen in pure Java? (No, JVM enforces strict array bounds checks)
#### I65: What is MITRE ATT&CK? (Knowledge base of adversary tactics and techniques)
#### I66: What is a TTP? (Tactics, Techniques, and Procedures used by threat actors)
#### I67: What is MITRE ATT&CK T1110? (Brute Force credential attacks)
#### I68: What is MITRE ATT&CK T1190? (Exploit Public-Facing Application, e.g., SQLi)
#### I69: What is MITRE ATT&CK T1498? (Network Denial of Service)
#### I70: What is a CVE? (Common Vulnerabilities and Exposures identifier, e.g., CVE-2021-44228)
#### I71: What is CVSS? (Common Vulnerability Scoring System: score from 0.0 to 10.0)
#### I72: What is a Zero-Day? (A flaw exploited before the software vendor releases a patch)
#### I73: What is Threat Intelligence? (Evidence-based data about cyber adversaries and threats)
#### I74: What is STIX? (Structured Threat Information Expression format)
#### I75: What is TAXII? (Trusted Automated eXchange of Intelligence Information protocol)
#### I76: What is an allowlist / whitelist? (List of explicitly approved entities; all else blocked)
#### I77: What is DNS Sinkholing? (Spoofing DNS responses for bad domains to block malware C2)
#### I78: What is C2 (Command & Control)? (Server used by attackers to control compromised hosts)
#### I79: What is lateral movement? (Techniques used to move through internal network post-breach)
#### I80: What is privilege escalation? (Gaining higher permission levels than authorized)
#### I81: What is credential dumping? (Extracting plaintext passwords/hashes from RAM, e.g., Mimikatz)
#### I82: What is Pass-the-Hash? (Authenticating to servers using password hash without cracking)
#### I83: What is Kerberoasting? (Extracting Kerberos ticket hashes for offline cracking)
#### I84: What is Golden Ticket? (Forging Kerberos TGT to achieve full domain control)
#### I85: What is Active Directory? (Microsoft enterprise directory service managing users and machines)
#### I86: What is LDAP? (Lightweight Directory Access Protocol)
#### I87: What is Kerberos? (Ticket-based network authentication protocol using symmetric crypto)
#### I88: What is NTLM? (Legacy Microsoft authentication protocol vulnerable to relay attacks)
#### I89: What is syslog RFC 5424? (Modern standard protocol format for network logs)
#### I90: What port does syslog use? (UDP/TCP Port 514)
#### I91: What is CEF (Common Event Format)? (Standard log format developed by ArcSight)
#### I92: What is Logstash? (Open-source data processing pipeline in ELK stack)
#### I93: What is Filebeat? (Lightweight log shipper forwarding files to Logstash/Elasticsearch)
#### I94: What is Elasticsearch? (Distributed RESTful search and analytics engine)
#### I95: What is Kibana? (Visualization and dashboard frontend for Elasticsearch)
#### I96: What is a WAF? (Web Application Firewall filtering Layer 7 HTTP traffic)
#### I97: What is ModSecurity? (Open-source WAF engine using Core Rule Set)
#### I98: What is rate limiting? (Restricting number of requests allowed within a time window)
#### I99: What is an SLA in SOC? (Service Level Agreement defining maximum alert response times)
#### I100: What is MTTR? (Mean Time To Remediate/Respond: average time to contain an attack)

---

## PART 3: 100 ADVANCED QUESTIONS & ANSWERS

#### A1: How does a sliding window algorithm improve upon your current brute force detector?
* **Answer**: Current detector counts failures indefinitely until 5. A sliding window tracks timestamps in a queue (e.g., `Deque<Instant>`) and only alerts if 5 failures occur within a rolling 60-second window.
* **Follow-up**: What evasion does this defeat?
* **Answer**: It defeats slow-and-low brute force attacks while preventing false alarms on users who fail once per week over 5 weeks.

#### A2: How would you prevent race conditions if multiple threads call `AlertManager.addAlert()`?
* **Answer**: Synchronize the method or wrap `alerts` in a `CopyOnWriteArrayList` / concurrent queue, and serialize file writes using a `ReentrantLock`.
* **Follow-up**: Why not use `Vector`?
* **Answer**: `Vector` is a legacy Java 1.0 class with coarse-grained locking; modern concurrent collections offer superior throughput.

#### A3: How can attackers evade your `SQLInjectionDetector`?
* **Answer**: Via URL encoding (`%27%20OR%201=1`), hexadecimal encoding (`0x27`), inline comments (`UN/**/ION SEL/**/ECT`), or concatenation (`'O'||'R' 1=1`).
* **Follow-up**: How do you defeat this evasion?
* **Answer**: Decode strings to canonical form before scanning, and parse queries using an Abstract Syntax Tree (AST) SQL lexer like JSqlParser.

#### A4: What is an Abstract Syntax Tree (AST) in security parsing?
* **Answer**: A structural tree representation of query syntax that evaluates semantic logic rather than plain character substrings.
* **Follow-up**: Why is AST superior to keyword matching?
* **Answer**: It eliminates false positives on legitimate queries containing "SELECT" while detecting actual syntax alterations in `WHERE` clauses.

#### A5: How would you implement IP subnet matching (CIDR) in `BlacklistDetector`?
* **Answer**: Convert IP strings and CIDR blocks (`10.0.0.0/24`) into 32-bit integer representations and apply bitwise subnet masking: `(ipInt & mask) == (subnetInt & mask)`.
* **Follow-up**: What is the performance complexity?
* **Answer**: Constant $O(1)$ integer arithmetic operations.

#### A6: Explain the Java Memory Model (JMM) in multithreaded log processing.
* **Answer**: JMM governs how CPU caches and memory interact, defining visibility, atomicity, and instruction reordering across threads.
* **Follow-up**: What happens without memory barriers in our detectors?
* **Answer**: Counter updates on Core 1 remain in L1 cache, causing Core 2 to read stale counters and fail to trigger alerts.

#### A7: How does Java 17 Sealed Classes enhance security architecture?
* **Answer**: By restricting which classes can implement an interface using the `permits` keyword.
* **Follow-up**: How would you apply it here?
* **Answer**: `public sealed interface ThreatDetector permits BruteForceDetector, SQLInjectionDetector...` prevents malicious third-party plugins from injecting unauthorized detector code.

#### A8: How would you scale this system to ingest 500,000 logs per second?
* **Answer**: Deploy an Apache Kafka cluster for distributed log partitioning, consume partitions across Kubernetes worker pods running our Java detection engine, and use a Redis cluster for distributed state tracking.
* **Follow-up**: What is the primary bottleneck?
* **Answer**: Disk I/O. Decoupling ingestion from disk writing via Kafka queues removes the I/O bottleneck entirely.

#### A9: What is backpressure in streaming reactive pipelines?
* **Answer**: A flow-control mechanism where overwhelmed consumers signal upstream producers to buffer or slow down event emission.
* **Follow-up**: What happens without backpressure?
* **Answer**: In-memory queues expand until exhausting JVM heap, throwing `OutOfMemoryError`.

#### A10: What is log tampering and how do you achieve log non-repudiation?
* **Answer**: When attackers alter audit logs to erase evidence. Prevented by streaming logs immediately to WORM (Write Once Read Many) storage and calculating cryptographic SHA-256 hash chains across lines.
* **Follow-up**: What is a hash chain?
* **Answer**: Each log entry hashes the previous line's hash with its own content ($H_n = \text{SHA256}(H_{n-1} + \text{Log}_n)$). Modifying any historical line invalidates all subsequent hashes.

*(Questions A11 through A100 continue advanced architectural, algorithmic, and defensive security mastery:)*
#### A11: What is zero-copy networking in Java? (`FileChannel.transferTo()` bypassing user memory)
#### A12: What is ReDoS? (Catastrophic regex backtracking exhausting CPU)
#### A13: How did we avoid ReDoS? (Used deterministic string boundary index matching)
#### A14: What is escape analysis in JIT? (Allocates non-escaping objects on the CPU stack instead of heap)
#### A15: What is JIT hot-spot compilation? (Compiling frequently executed bytecode into native machine code)
#### A16: What is the G1 Garbage Collector? (Region-based collector balancing latency and throughput)
#### A17: What is ZGC in Java 17? (Sub-millisecond low-latency garbage collector)
#### A18: What is a Dead Letter Queue (DLQ)? (Buffer storing malformed or unprocessable messages for manual audit)
#### A19: What is circuit breaker pattern? (Stops calling failing downstream services to prevent cascade failures)
#### A20: What is Canary deployment? (Deploying new detection rules to a tiny traffic fraction to test false-positive rates)
#### A21: What is SOAR? (Security Orchestration, Automation, and Response)
#### A22: How would SOAR integrate here? (Webhook in `AlertManager` triggering firewall IP block automatically)
#### A23: What is an AS-REP Roasting attack? (Exploiting accounts without Kerberos pre-authentication)
#### A24: What is DCSync attack? (Pretending to be a Domain Controller to replicate password hashes)
#### A25: What is Shadow Copy deletion? (Ransomware deleting Volume Shadow Copies via `vssadmin` to prevent restore)
#### A26: How would you write a RansomwareDetector? (Detect massive velocity of file modification and extension changes)
#### A27: What is DNS Tunneling? (Exfiltrating data encoded inside DNS query subdomains)
#### A28: How do you detect DNS Tunneling? (Alert on high Shannon entropy and excessive length in DNS queries)
#### A29: What is Shannon Entropy in security? (Mathematical measure of randomness, used to spot encrypted C2 traffic)
#### A30: What is DGA (Domain Generation Algorithm)? (Malware generating pseudo-random domains to bypass static blacklists)
#### A31: What is JA3 / JA3S fingerprinting? (Fingerprinting TLS client/server negotiation handshakes)
#### A32: What is NetFlow / IPFIX? (Cisco protocol exporting summarized IP traffic flow metrics)
#### A33: What is BGP Hijacking? (Illegitimate routing of IP address prefixes to intercept traffic)
#### A34: What is ARP Spoofing? (Linking attacker MAC to legitimate default gateway IP at Layer 2)
#### A35: What is a Smurf Attack? (Broadcast ping flood using victim's spoofed source IP)
#### A36: What is an NTP Amplification attack? (Abusing `monlist` command on NTP servers for massive DDoS reflection)
#### A37: What is an amplification factor? (Ratio of response size to request size in reflection DDoS)
#### A38: What is DNSSEC? (Cryptographically signing DNS records to prevent cache poisoning)
#### A39: What is HSTS? (HTTP Strict Transport Security: enforces HTTPS-only browser connections)
#### A40: What is Certificate Pinning? (Hardcoding trusted server certificate public keys inside apps)
#### A41: What is mTLS (Mutual TLS)? (Both client and server authenticate each other via digital certificates)
#### A42: What is JWT (JSON Web Token)? (Base64URL encoded cryptographically signed identity token)
#### A43: What is the `alg: none` JWT attack? (Exploiting servers that accept unsigned tokens)
#### A44: What is OAuth 2.0? (Industry-standard authorization framework using access tokens)
#### A45: What is OpenID Connect (OIDC)? (Identity layer built on top of OAuth 2.0 for authentication)
#### A46: What is SAML? (XML-based standard for exchanging authentication data across domains)
#### A47: What is Zero Trust Architecture? ("Never trust, always verify" continuous authentication model)
#### A48: What is Microsegmentation? (Isolating network segments to contain lateral movement)
#### A49: What is CASB? (Cloud Access Security Broker enforcing enterprise policies across SaaS)
#### A50: What is SASE? (Secure Access Service Edge combining SD-WAN with cloud security services)
#### A51: What is CSP (Content Security Policy)? (HTTP response header restricting script execution sources)
#### A52: What is SRI (Subresource Integrity)? (Validating CDN script hashes to prevent supply chain tampering)
#### A53: What is SolarWinds supply chain attack? (Trojanized Orion software update compromising enterprise networks)
#### A54: What is Log4Shell (CVE-2021-44228)? (JNDI injection flaw in Log4j allowing unauthenticated remote code execution)
#### A55: How would our system detect Log4Shell? (Scan queries for `${jndi:ldap://` patterns)
#### A56: What is deserialization vulnerability? (Executing malicious code during object deserialization via `readObject`)
#### A57: What is XXE (XML External Entity)? (Exploiting XML parsers to read internal server files via external DTDs)
#### A58: What is Insecure Direct Object Reference (IDOR)? (Accessing unauthorized user IDs via URL parameters)
#### A59: What is BOLA? (Broken Object Level Authorization: the API equivalent of IDOR)
#### A60: What is BFLA? (Broken Function Level Authorization: standard user calling admin API endpoints)
#### A61: What is Mass Assignment vulnerability? (Client modifying administrative database attributes via JSON binding)
#### A62: What is Data Exfiltration? (Unauthorized electronic transfer of sensitive corporate data)
#### A63: What is Steganography? (Concealing secret data within ordinary image or audio files)
#### A64: What is Memory Injection (Process Hollowing)? (Injecting malicious code into the memory space of legitimate processes)
#### A65: What is DLL Sideloading? (Tricking signed executables into loading malicious DLLs from working directories)
#### A66: What is Living-off-the-Land (LotL)? (Attackers using legitimate built-in tools like PowerShell, WMI, and Certutil)
#### A67: What is AMSI (Antimalware Scan Interface)? (Microsoft API allowing security tools to inspect obfuscated scripts)
#### A68: How do attackers bypass AMSI? (Patching AMSI memory addresses in PowerShell process space)
#### A69: What is Sysmon? (Microsoft system monitoring tool logging detailed process creation, network, and file events)
#### A70: What is Sysmon Event ID 1? (Process Creation: captures parent-child processes and command-line arguments)
#### A71: What is Sysmon Event ID 3? (Network Connection: records IP and port connections per process)
#### A72: What is Sysmon Event ID 11? (File Creation: detects ransomware dropping executable payloads)
#### A73: What is Windows Security Event ID 4624? (Successful Account Logon)
#### A74: What is Windows Security Event ID 4625? (Failed Account Logon: monitored by Brute Force rules)
#### A75: What is Windows Security Event ID 4672? (Special Privileges Assigned to New Logon, e.g., Admin)
#### A76: What is Windows Security Event ID 4720? (User Account Created)
#### A77: What is Windows Security Event ID 1102? (Audit Log Cleared: severe indicator of attacker cover-up)
#### A78: What is Linux `/var/log/auth.log`? (Records SSH logins, `sudo` usage, and authentication events on Debian/Ubuntu)
#### A79: What is Linux auditd? (Kernel-level auditing subsystem tracking syscalls and file modifications)
#### A80: What is Sigma rule? (Open-source generic signature format translated into Splunk, QRadar, or Sentinel queries)
#### A81: What is YARA rule? (Pattern-matching rule identifying malware files based on strings and byte patterns)
#### A82: What is Snort / Suricata? (Open-source network intrusion detection engines using packet rule sets)
#### A83: What is Zeek (Bro)? (Powerful network security monitoring framework providing structured transaction logs)
#### A84: What is Honeypot canary token? (Decoy database record or file; accessing it triggers a guaranteed critical alarm)
#### A85: What is Cyber Kill Chain phase 1? (Reconnaissance: harvesting emails, scanning ports)
#### A86: What is Cyber Kill Chain phase 2? (Weaponization: coupling exploit with payload)
#### A87: What is Cyber Kill Chain phase 3? (Delivery: phishing email, malicious USB)
#### A88: What is Cyber Kill Chain phase 4? (Exploitation: executing code on victim system)
#### A89: What is Cyber Kill Chain phase 5? (Installation: establishing malware persistence)
#### A90: What is Cyber Kill Chain phase 6? (Command & Control: remote attacker communication)
#### A91: What is Cyber Kill Chain phase 7? (Actions on Objectives: ransomware encryption, data exfiltration)
#### A92: What is Diamond Model of Intrusion Analysis? (Adversary, Capability, Infrastructure, Victim relationship model)
#### A93: What is Pyramid of Pain? (David Bianco's model ranking IoC types by difficulty for attackers to change)
#### A94: What is at the base of Pyramid of Pain? (Hash values: trivial for attackers to modify)
#### A95: What is in the middle of Pyramid of Pain? (IP addresses and Domain names: simple to change)
#### A96: What is at the apex of Pyramid of Pain? (TTPs: extremely painful and costly for attackers to change)
#### A97: Where does our SQLi detector sit on Pyramid of Pain? (At the TTP layer: detects attacker techniques directly!)
#### A98: What is DevSecOps? (Integrating automated security scanning into every phase of CI/CD software pipelines)
#### A99: What is SAST vs. DAST? (SAST scans static source code; DAST tests running applications dynamically)
#### A100: What is the defining trait of an elite SOC Engineer? (Understanding foundational system architecture so deeply that no abstraction, evasion, or interview challenge can shake your confidence!)

---

# SECTION 17: PROJECT DEFENSE GUIDE (INTERVIEWER ATTACKS)

Here is how to answer when an interviewer aggressively challenges your design decisions:

---

### Attack 1: "Why did you use an in-memory `HashMap` instead of an actual database like PostgreSQL or MongoDB to store IP attempt counts?"
* **Interviewer's Trap**: They think you don't know databases, or they want to see if you understand caching and write latency.
* **The Winning Response**:
  > *"Using a database for high-throughput stream counting introduces severe I/O bottlenecks. In a busy SOC environment processing 10,000 log events per second, executing a relational SQL query (`SELECT count FROM logins WHERE ip = ...` followed by `UPDATE ...`) for every single log line would saturate the database connection pool and introduce 5 to 15 milliseconds of latency per event.
  > 
  > I deliberately used an in-memory `HashMap` because hash lookups and increments occur in sub-microsecond $O(1)$ time in memory. In an enterprise version, this in-memory structure would be moved to a distributed in-memory cache like **Redis**, providing sub-millisecond lookups while maintaining persistence and cross-worker synchronization."*

---

### Attack 2: "Why did you use `Optional<Alert>` instead of just returning `null` when there's no alert? Isn't `Optional` just extra object allocation overhead?"
* **Interviewer's Trap**: They want to test your understanding of Java best practices vs. performance trade-offs.
* **The Winning Response**:
  > *"Returning `null` is famously known as the 'Billion-Dollar Mistake'. In a pipeline where multiple detectors are executed across millions of logs, returning `null` forces callers to write defensive `if (alert != null)` checks everywhere. If a single check is missed, it triggers an unhandled `NullPointerException`, crashing the entire monitoring pipeline.
  > 
  > `Optional<Alert>` makes the absence of a value an explicit, type-safe API contract. It allows expressive, functional chaining such as `alertOpt.ifPresent(alertManager::addAlert)`. Modern JVM JIT compilers optimize away short-lived `Optional` allocations through escape analysis, so the safety benefits far outweigh the negligible allocation cost."*

---

### Attack 3: "Why did you use an interface and separate detector classes instead of a simple, fast `switch-case` statement inside a loop?"
* **Interviewer's Trap**: They want to see if you understand clean architecture and the Open/Closed Principle vs. quick-and-dirty scripting.
* **The Winning Response**:
  > *"A `switch-case` statement violates the **Open/Closed Principle** and the **Single Responsibility Principle**. If you have 50 detection rules in a `switch-case`, that file becomes thousands of lines long. Every time a security engineer adds or modifies a rule, they risk introducing regression bugs into other rules, and multiple developers cannot work on different rules concurrently without Git merge conflicts.
  > 
  > By using the **Strategy Pattern** with the `ThreatDetector` interface, every rule is isolated in its own independent class. Rules can be unit-tested in isolation, toggled on or off via configuration, and new rules can be added dynamically at runtime without modifying a single line of existing code."*

---

### Attack 4: "Your SQL injection detector just looks for strings like 'DROP' and 'SELECT'. What happens when a legitimate user named 'Selectra' signs up, or a developer runs a legitimate report query? Isn't your false-positive rate absurdly high?"
* **Interviewer's Trap**: Testing if you understand the difference between basic regex matching and real Web Application Firewalls (WAF).
* **The Winning Response**:
  > *"You are completely right. A naive substring search for 'SELECT' in a raw production environment would trigger intolerable false positives on legitimate administrative queries or text fields. 
  > 
  > In this simulation, I implemented keyword matching to demonstrate basic signature-based detection principles. However, in a hardened production version, I would implement two key defenses:
  > 1. **Contextual Tokenization / AST Analysis**: Using an SQL parser like JSqlParser to inspect the Abstract Syntax Tree. This verifies whether the input alters the boolean logic of a `WHERE` clause (such as `OR 1=1`), rather than simply checking if a keyword exists.
  > 2. **Role & Endpoint Whitelisting**: Suppressing SQL query alerts originating from authenticated internal backend services, while applying strict inspection only to untrusted public-facing HTTP parameters."*

---

### Attack 5: "How does your system detect Zero-Day attacks that have no known signatures or keywords?"
* **Interviewer's Trap**: Testing your understanding of signature-based vs. anomaly-based / machine learning detection.
* **The Winning Response**:
  > *"Signature-based detectors—like our current SQL injection keyword scanner—cannot catch zero-day exploits because the signature is not yet known. 
  > 
  > To catch zero-days, I would implement **Behavioral Anomaly Detection (UEBA - User and Entity Behavior Analytics)**:
  > 1. **Baseline Profiling**: Establish a statistical baseline of normal behavior for each user and IP (e.g., normal request rate, usual working hours, typical query payload lengths).
  > 2. **Deviation Scoring**: Calculate standard deviations or Z-scores on incoming events. If a user account suddenly downloads 500 records at 3:00 AM from a new country, an anomaly alert triggers regardless of whether any known malicious keywords were used."*

---

# SECTION 18: 100 RAPID-FIRE VIVA QUESTIONS

*(Essential for college lab vivas, technical certifications, and rapid-fire screening rounds!)*

 1. What does SOC mean? -> Security Operations Center.
 2. What does SIEM mean? -> Security Information & Event Management.
 3. What is an IoC? -> Indicator of Compromise.
 4. What is a DoS attack? -> Denial of Service.
 5. What is DDoS? -> Distributed Denial of Service.
 6. What is Brute Force? -> Automated credential guessing.
 7. What is SQL Injection? -> Malicious SQL passed into inputs.
 8. What is a Blacklist? -> List of banned or malicious entities.
 9. What is an Allowlist? -> List of approved entities (all else blocked).
10. What is an Alert? -> A notification of detected security anomaly.
11. What is Severity? -> Priority classification of an alert.
12. Name our four severities -> LOW, MEDIUM, HIGH, CRITICAL.
13. What is Java 17? -> A Long-Term Support release of Java.
14. What is Maven? -> Build automation and dependency tool.
15. What file does Maven use? -> pom.xml.
16. What is target/ folder? -> Folder storing compiled .class files.
17. What is src/main/java? -> Directory storing application source code.
18. What is src/main/resources? -> Directory storing data and config files.
19. What is a class? -> A blueprint for creating objects.
20. What is an object? -> A runtime instance of a class.
21. What is an interface? -> A contract declaring abstract methods.
22. What is an enum? -> A data type representing fixed constants.
23. What is a constructor? -> Method initializing object state on creation.
24. What is encapsulation? -> Hiding internal fields using private variables.
25. What is abstraction? -> Showing functionality while hiding details.
26. What is inheritance? -> Subclass adopting behavior from superclass/interface.
27. What is polymorphism? -> Dynamic execution of child methods via parent reference.
28. What is an ArrayList? -> A dynamically resizable array.
29. What is a HashMap? -> Key-value collection with O(1) average lookup.
30. What is a HashSet? -> Collection of unique items with O(1) contains check.
31. What is Optional? -> Container avoiding null and NullPointerException.
32. What is UUID? -> Universally Unique Identifier (128 bits).
33. What is Java Streams? -> Declarative functional data pipeline.
34. What is Collectors.groupingBy? -> Stream collector grouping elements into a Map.
35. What is try-with-resources? -> Automatically closes AutoCloseable resources.
36. What is a checked exception? -> Exception verified at compile-time (IOException).
37. What is an unchecked exception? -> Runtime exception (NullPointerException).
38. What is StringBuilder? -> Mutable character sequence for fast concatenation.
39. What is LogParser? -> Converts raw text strings to LogEntry objects.
40. What is LogEntry? -> Normalized domain model of a log event.
41. What is ThreatMonitor? -> Central engine running detectors over logs.
42. What is ThreatDetector? -> Common interface for all security rules.
43. What is AlertManager? -> Handles alert console output, disk saving, and memory.
44. What is ReportGenerator? -> Generates incident_report.txt.
45. What is FileUtils? -> Utility class centralizing safe disk I/O.
46. What is logs.txt? -> Input text file containing simulated server logs.
47. What is blacklist.txt? -> Input text file containing malicious IP addresses.
48. What is alerts.txt? -> Output text file where alerts are appended.
49. What is incident_report.txt? -> Executive summary report generated at end.
50. What threshold triggers Brute Force? -> 5 failed logins from same IP.
51. What threshold triggers DoS? -> 10 requests from same IP (in simulation).
52. What keywords trigger SQLi? -> SELECT, DROP, DELETE, UNION, OR 1=1, --.
53. Why uppercase queries in SQLi? -> Defeats case evasion like "sElEcT".
54. What is Strategy Pattern? -> Makes algorithms interchangeable at runtime.
55. What is Single Responsibility Principle? -> One class, one reason to change.
56. What is Open/Closed Principle? -> Open for extension, closed for modification.
57. What is Liskov Substitution? -> Child classes substitutable for base types.
58. What is Interface Segregation? -> Don't force unused interface methods.
59. What is Dependency Inversion? -> Depend on abstractions, not concrete classes.
60. What is alert fatigue? -> Analyst exhaustion from too many false alarms.
61. How does our code fight alert fatigue? -> Resets IP counters after alerting.
62. What is False Positive? -> Benign event flagged as an attack.
63. What is False Negative? -> Real attack that went undetected.
64. What is IDS? -> Intrusion Detection System (passive alerting).
65. What is IPS? -> Intrusion Prevention System (active packet blocking).
66. What is Firewall? -> Network device filtering ports and IP traffic.
67. What is WAF? -> Web Application Firewall inspecting Layer 7 HTTP requests.
68. What is EDR? -> Endpoint Detection and Response monitoring host processes.
69. What is Nmap? -> Open-source network and port scanner.
70. What is Wireshark? -> Network packet capture and protocol analyzer.
71. What is Kali Linux? -> Debian Linux distro preloaded with security tools.
72. What is Port 80? -> Unencrypted HTTP traffic.
73. What is Port 443? -> Encrypted HTTPS traffic.
74. What is Port 514? -> Syslog network logging port.
75. What is Port 22? -> SSH (Secure Shell) remote login.
76. What is Port 53? -> DNS (Domain Name System).
77. What is TCP? -> Reliable connection-oriented transport protocol.
78. What is UDP? -> Fast, connectionless transport protocol.
79. What is an IPv4 address? -> 32-bit address written in dotted-decimal format.
80. What is an IPv6 address? -> 128-bit address written in hexadecimal format.
81. What is private IP? -> Non-routable internal IP (e.g., 192.168.1.1).
82. What is public IP? -> Routable global Internet address.
83. What is localhost IP? -> 127.0.0.1.
84. What is DNS? -> Translates google.com to IP addresses.
85. What is MITRE ATT&CK? -> Knowledge base of adversary tactics and techniques.
86. What is T1110? -> MITRE ATT&CK technique for Brute Force.
87. What is T1190? -> MITRE ATT&CK technique for Exploit Public-Facing App.
88. What is T1498? -> MITRE ATT&CK technique for Network Denial of Service.
89. What is CVE? -> Common Vulnerabilities and Exposures database ID.
90. What is CVSS? -> Common Vulnerability Scoring System (0-10).
91. What is a Zero-Day? -> Flaw exploited before vendor releases patch.
92. What is Logstash? -> Data collection engine in ELK stack.
93. What is Elasticsearch? -> Search and analytics engine in ELK stack.
94. What is Kibana? -> Visualization dashboard in ELK stack.
95. What is Splunk? -> Enterprise SIEM platform using SPL query language.
96. What is Microsoft Sentinel? -> Cloud-native SIEM on Microsoft Azure.
97. What is Kafka? -> Distributed streaming event broker.
98. What is Redis? -> In-memory key-value data store used for caching.
99. What is Zero Trust? -> "Never trust, always verify" security model.
100. What is the goal of a SOC Analyst? -> Protect organization assets by detecting and stopping breaches!

---

# SECTION 19: ONE-PAGE MASTER CHEAT SHEET

*(Review this sheet 10 minutes before your interview!)*

### 1. Key Project Stats & Architecture
* **Project Name**: SOC Log Monitoring & Threat Detection System
* **Tech Stack**: Java 17 LTS, Apache Maven, Java NIO, Java Streams API, Collections Framework.
* **Architecture Pattern**: Strategy Design Pattern (`ThreatDetector` interface), Service-Oriented Ingestion Pipeline.
* **Data Flow**: `logs.txt` $\to$ `LogParser` $\to$ `LogEntry` DTO $\to$ `ThreatMonitor` $\to$ 4 Detectors $\to$ `AlertManager` (`alerts.txt`) $\to$ `ReportGenerator` (`incident_report.txt`).

---

### 2. The 4 Threat Detectors at a Glance
| Detector | Threat Detected | Algorithm / Heuristic | Threshold | Severity | Data Structure | Big-O |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **BruteForceDetector** | Credential Guessing | Counts consecutive `LOGIN_FAILED` per IP | $\ge 5$ fails | **HIGH** | `HashMap<String, Integer>` | $O(1)$ |
| **SQLInjectionDetector**| Database Exploitation | Scans uppercase queries for SQL signatures | `DROP`, `SELECT`, `OR 1=1` | **CRITICAL**| `List<String>` | $O(K \times M)$ |
| **DoSDetector** | Volumetric Traffic Flood| Counts total HTTP requests per IP | $\ge 10$ requests | **HIGH** | `HashMap<String, Integer>` | $O(1)$ |
| **BlacklistDetector** | Known Malicious Actors | Matches IP against Threat Intelligence feed| Exact match in feed | **MEDIUM** | `HashSet<String>` | $O(1)$ |

---

### 3. Core Software Engineering Concepts to Recite
* **Encapsulation**: `LogEntry` and `Alert` have `private` fields and getters only (immutable state).
* **Polymorphism**: `ThreatMonitor` holds `List<ThreatDetector>` and iterates via dynamic method dispatch.
* **Strategy Pattern**: Decouples detection rules from the engine, satisfying the Open/Closed Principle.
* **Null-Safety**: All detectors return `Optional<Alert>` instead of error-prone `null` references.
* **Resource Safety**: `FileUtils` uses `try-with-resources` to guarantee zero file handle leaks.
* **Stream Analytics**: `ReportGenerator` uses `Collectors.groupingBy()` and reverse sorting to generate executive summaries.

---

# SECTION 20: THE 15-MINUTE "EMERGENCY" CRASH COURSE

*(If your interview is in 15 minutes and your mind goes blank, read this section and breathe!)*

---

### Minute 1 to 3: The 30-Second Elevator Pitch
If the interviewer says: *"Tell me about this SOC Log Monitoring project on your resume."*
* Say this with confidence:
  > *"I built a Java 17 Security Operations Center (SOC) Log Monitoring and Threat Detection Engine. It simulates how enterprise SIEM platforms like Splunk ingest, normalize, and correlate security logs in real time.*
  > 
  > *Raw server logs are parsed into structured immutable objects by `LogParser`. A central `ThreatMonitor` engine then runs each event through a polymorphic detector pipeline implementing the Strategy Pattern.*
  > 
  > *It detects Brute Force login spikes, SQL Injection payloads, DoS traffic, and Blacklisted Threat Actor IPs. Generated alerts receive unique UUIDs and severity ratings, and an executive summary report is produced using Java Streams."*

---

### Minute 4 to 6: Explain the 4 Detectors
If they ask: *"How does your threat detection actually work?"*
* Say:
  1. **Brute Force**: *"I track failed logins per IP in a `HashMap`. If an IP hits 5 failed attempts, it triggers a HIGH severity alert and resets the counter to prevent alert fatigue."*
  2. **SQL Injection**: *"I extract executed database queries, normalize them to uppercase to defeat case evasion, and scan for malicious signatures like `DROP`, `UNION`, and `' OR 1=1`. This triggers a CRITICAL alert."*
  3. **DoS**: *"I count total request volume per client IP using a `HashMap`. If an IP exceeds 10 requests, it triggers a HIGH severity DoS alert."*
  4. **Blacklist**: *"During startup, I load known bad IPs from a threat intelligence file into a `HashSet`. Because `HashSet.contains()` runs in constant $O(1)$ time, checking each log line is lightning fast."*

---

### Minute 7 to 9: Explain the Java Architecture & OOP
If they ask: *"Why did you design the classes this way?"*
* Say:
  1. **Encapsulation**: *"All domain models (`LogEntry`, `Alert`) have private fields with getters only. Once a log is parsed, it is immutable so forensic data cannot be tampered with."*
  2. **Strategy Pattern**: *"All detectors implement the `ThreatDetector` interface. `ThreatMonitor` depends on this interface rather than concrete classes. This adheres to the Open/Closed Principle—I can add a new Ransomware detector without touching existing code."*
  3. **`Optional`**: *"Detectors return `Optional<Alert>` instead of `null`, preventing `NullPointerException`."*

---

### Minute 10 to 12: Handle the Toughest Interviewer Challenge
If the interviewer says: *"What is the biggest flaw in your project and how would you fix it?"*
* Say:
  > *"Currently, `Files.readAllLines()` loads the entire log file into JVM heap memory at once, which would cause an `OutOfMemoryError` on a 20GB production log file.*
  > 
  > *To fix it, I would replace it with streaming via `BufferedReader.readLine()` or `Files.lines()`, which processes lines one by one in constant $O(1)$ memory. In an enterprise environment, I would connect this engine to an **Apache Kafka** streaming topic and use **Redis** for distributed IP state tracking."*

---

### Minute 13 to 15: The Final Golden Advice
* **Speak slowly and clearly**.
* **Own your code**: You wrote it, you tested it, and you know how every line works.
* **Think like a defender**: Remember that the goal of every security tool is to protect human beings and organizations from real-world cybercrime.

**You are now completely prepared. Walk into your interview with your head held high and crush it!**
