📘 RevWorkForce – Console Based HRM System
📌 Application Overview

RevWorkForce is a Java console-based Human Resource Management (HRM) system designed to manage employees, leaves, performance reviews, notifications, and system configurations.

The application follows a layered architecture:

DAO Layer – Database operations

Service Layer – Business logic

Menu Layer – User interaction

Model Layer – Entity objects

Utility Layer – DB connection & input handling

The system supports three roles:

✔ Employee
✔ Manager
✔ Admin

🛠️ Technologies Used
Technology	Purpose
Java 7	Core application
JDBC	Database connectivity
Oracle 10g	Database
JUnit 4	Unit testing
Log4j 2	Logging
Eclipse Indigo	IDE


📂 Project Structure
RevWorkForce
│
├── src
│   └── com.revworkforce
│       ├── dao
│       │   ├── EmployeeDAO.java
│       │   ├── LeaveDAO.java
│       │   ├── AuditDAO.java
│       │   └── SystemConfigDAO.java
│       │
│       ├── main
│       │   └── RevWorkForceApp.java
│       │
│       ├── menu
│       │   ├── AdminMenu.java
│       │   ├── EmployeeMenu.java
│       │   └── ManagerMenu.java
│       │
│       ├── model
│       │   └── Employee.java
│       │
│       ├── service
│       │   ├── AdminService.java
│       │   ├── AuthService.java
│       │   ├── EmployeeService.java
│       │   ├── ManagerService.java
│       │   └── SystemConfigService.java
│       │
│       └── util
│           ├── DBConnection.java
│           └── InputUtil.java
│
├── test
│   └── com.revworkforce.test
│       ├── EmployeeDAOTest.java
│       ├── LeaveDAOTest.java
│       └── EmployeeServiceTest.java
│
├── resources
│   └── log4j2.xml
│
├── logs
│   ├── app.log
│   ├── ERD.png
│   └── README.md
│
└── JUnit 4

👨‍💼 Employee Features
🔐 Authentication & Profile

Login using Employee ID or Email + Password

Edit profile (phone, address, emergency contact)

View reporting manager details

Change password

Forgot password using security question

📅 Leave Management

View leave balance (CL, SL, PL, PRL)

Apply leave with reason & date range

View applied leaves with status

Cancel pending leave

View holiday calendar

Receive leave approval/rejection notifications

📈 Performance Management

Submit performance review

Add yearly goals

Update goal progress

View manager feedback

🔔 Notifications

In-app notifications stored in DB

Unread count shown at login

Notifications include:

Leave status

Performance feedback

Announcements

👔 Manager Features

(All employee features +)

View direct reportees

Approve/Reject leave requests

View team leave calendar

Review performance documents

Provide feedback & ratings

Track team goals

Team reports

🛡️ Admin Features
👥 Employee Management

Add employee

Update employee details

Activate/Deactivate accounts

Change reporting managers

Search employees

📋 Leave Management

Configure leave policies

Assign leave quotas

Adjust balances

Revoke leaves

Generate reports

⚙️ System Configuration

Departments & designations

Performance cycles

Policies

Audit logs

🔐 Security & Session

✔ Login validation
✔ Password change
✔ Forgot password via security Q&A
✔ Status based access (ACTIVE/INACTIVE)

📊 Logging

Implemented using Log4j 2

Logs stored in logs/app.log

Tracks:

Logins

Actions

Errors

System events

🧪 Testing

JUnit 4 test cases:

EmployeeDAOTest

LeaveDAOTest

EmployeeServiceTest

✔ Core methods tested
✔ Functional validations

📈 Database

Oracle 10g used with tables including:

EMPLOYEE

LEAVE_BALANCE

LEAVE_REQUEST

NOTIFICATION

PERFORMANCE_REVIEW

GOALS

HOLIDAY

LEAVE_POLICY

AUDIT_LOG

(Refer ERD.png for relationships)

▶ How to Run

Open project in Eclipse

Ensure:

Oracle DB running

JDBC jar added

Log4j jars added

Run:

RevWorkForceApp.java

📎 Deliverables

✔ Complete Source Code
✔ ERD Diagram (ERD.png)
✔ README.md
✔ Unit Tests
✔ Logging Configuration

📌 Future Enhancements

Web-based UI

Microservices architecture

REST APIs

Role-based dashboards

👨‍💻 Developed By

Prasanna Kumar S
RevWorkForce HRM System
