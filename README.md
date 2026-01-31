
# 📘 RevWorkForce – Console Based HRM System

RevWorkForce is a **Java console-based Human Resource Management (HRM) system** built to manage employees, leave workflows, performance reviews, notifications, and system configurations in an enterprise-like environment.

The project is designed using a **layered architecture**, ensuring clean separation of concerns, maintainability, and scalability for future web or microservices-based expansion.

---

## 📌 Application Overview

RevWorkForce provides **role-based access control** for the following roles:

* 👤 **Employee**
* 👔 **Manager**
* 🛡️ **Admin**

Each role has clearly defined permissions and business responsibilities.

---

## 🧱 Architecture Design

The application follows a **Layered Architecture** model:

* **DAO Layer** – Handles all database operations and SQL queries
* **Service Layer** – Implements business logic and validations
* **Menu Layer** – Manages console-based user interaction
* **Model Layer** – Contains entity and POJO classes
* **Utility Layer** – Provides database connection and input handling

---

## 🛠️ Technologies Used

| Technology     | Purpose                      |
| -------------- | ---------------------------- |
| Java 7         | Core application development |
| JDBC           | Database connectivity        |
| Oracle 10g     | Relational database          |
| JUnit 4        | Unit testing                 |
| Log4j 2        | Logging framework            |
| Eclipse Indigo | Development IDE              |

---

## 📂 Complete Project Structure (Single View)

```
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
│   └── app.log
│
├── ERD.png
├── README.md
└── JUnit 4
```

---

## 👨‍💼 Employee Features

### 🔐 Authentication & Profile Management

* Login using **Employee ID or Email with Password**
* Edit profile details (phone, address, emergency contact)
* View reporting manager information
* Change password
* Forgot password using security question and answer

### 📅 Leave Management

* View leave balances (CL, SL, PL, PRL)
* Apply leave with reason and date range
* View applied leave status
* Cancel pending leave requests
* View company holiday calendar
* Receive leave approval or rejection notifications

### 📈 Performance Management

* Submit performance reviews
* Create yearly goals
* Update goal progress
* View manager feedback and ratings

### 🔔 Notification System

* In-app notifications stored in the database
* Unread notification count displayed at login
* Notification categories:

  * Leave status updates
  * Performance feedback
  * Company announcements

---

## 👔 Manager Features

*(Includes all Employee features plus)*

* View direct reportees
* Approve or reject employee leave requests
* View team leave calendar
* Review performance submissions
* Provide feedback and ratings
* Track team goals
* Generate team-level reports

---

## 🛡️ Admin Features

### 👥 Employee Management

* Add new employees
* Update employee details
* Activate or deactivate employee accounts
* Change reporting managers
* Search employees

### 📋 Leave Management

* Configure leave policies
* Assign leave quotas
* Adjust leave balances
* Revoke leaves
* Generate leave reports

### ⚙️ System Configuration

* Manage departments and designations
* Define performance cycles
* Configure HR policies
* Maintain audit logs

---

## 🔐 Security & Session Management

* Secure login validation
* Password change functionality
* Forgot password via security Q&A
* Status-based access control (ACTIVE / INACTIVE users)

---

## 📊 Logging

Logging is implemented using **Log4j 2**.

* Log file location: `logs/app.log`
* Logs capture:

  * User login activities
  * User actions
  * Errors and exceptions
  * System-level events

---

## 🧪 Testing

The project includes **JUnit 4 test cases**:

* EmployeeDAOTest
* LeaveDAOTest
* EmployeeServiceTest

✔ Core DAO and service methods tested
✔ Functional and validation logic covered

---

## 📈 Database Design

The application uses **Oracle 10g** with the following tables:

* EMPLOYEE
* LEAVE_BALANCE
* LEAVE_REQUEST
* NOTIFICATION
* PERFORMANCE_REVIEW
* GOALS
* HOLIDAY
* LEAVE_POLICY
* AUDIT_LOG

Entity relationships are documented in **ERD.png**.

---

## ▶ How to Run the Application

1. Open the project in **Eclipse**
2. Ensure the following:

   * Oracle database is running
   * JDBC driver JAR is added
   * Log4j 2 JAR files are added
3. Run the application:

   ```
   RevWorkForceApp.java
   ```

---

## 📎 Deliverables

* Complete source code
* ERD diagram
* README documentation
* Unit test cases
* Logging configuration

---

## 🚀 Future Enhancements

* Web-based user interface
* Microservices architecture
* RESTful APIs
* Role-based dashboards

---

## 👨‍💻 Developed By

**Prasanna Kumar S**
RevWorkForce – HRM System

---

