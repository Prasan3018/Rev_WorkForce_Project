---------------------------------------------------
-- 1. EMPLOYEE TABLE
---------------------------------------------------

CREATE TABLE EMPLOYEE (
    EMP_ID NUMBER PRIMARY KEY,
    NAME VARCHAR2(100),
    EMAIL VARCHAR2(100),
    PASSWORD VARCHAR2(50),
    ROLE VARCHAR2(20),
    PHONE VARCHAR2(20),
    ADDRESS VARCHAR2(200),
    EMERGENCY_CONTACT VARCHAR2(20),
    MANAGER_ID NUMBER,
    STATUS VARCHAR2(20),
    SECURITY_QUESTION VARCHAR2(200),
    SECURITY_ANSWER VARCHAR2(100)
);

---------------------------------------------------
-- 2. LEAVE_BALANCE TABLE
---------------------------------------------------

CREATE TABLE LEAVE_BALANCE (
    EMP_ID NUMBER PRIMARY KEY,
    CL NUMBER,
    SL NUMBER,
    PL NUMBER,
    PRL NUMBER
);

---------------------------------------------------
-- 3. LEAVE_REQUEST TABLE
---------------------------------------------------

CREATE TABLE LEAVE_REQUEST (
    LEAVE_ID NUMBER PRIMARY KEY,
    EMP_ID NUMBER,
    LEAVE_TYPE VARCHAR2(10),
    FROM_DATE DATE,
    TO_DATE DATE,
    REASON VARCHAR2(200),
    STATUS VARCHAR2(20),
    MANAGER_COMMENT VARCHAR2(200)
);

---------------------------------------------------
-- 4. HOLIDAYS TABLE
---------------------------------------------------

CREATE TABLE HOLIDAYS (
    HOLIDAY_ID NUMBER PRIMARY KEY,
    HOLIDAY_DATE DATE,
    HOLIDAY_NAME VARCHAR2(100)
);

---------------------------------------------------
-- 5. NOTIFICATIONS TABLE
---------------------------------------------------

CREATE TABLE NOTIFICATIONS (
    NOTIF_ID NUMBER PRIMARY KEY,
    EMP_ID NUMBER,
    MESSAGE VARCHAR2(300),
    STATUS VARCHAR2(20),
    CREATED_DATE DATE DEFAULT SYSDATE
);

---------------------------------------------------
-- 6. PERFORMANCE_REVIEW TABLE
---------------------------------------------------

CREATE TABLE PERFORMANCE_REVIEW (
    REVIEW_ID NUMBER PRIMARY KEY,
    EMP_ID NUMBER,
    DELIVERABLES VARCHAR2(300),
    ACCOMPLISHMENTS VARCHAR2(300),
    IMPROVEMENTS VARCHAR2(300),
    SELF_RATING NUMBER,
    STATUS VARCHAR2(20),
    MANAGER_FEEDBACK VARCHAR2(300),
    MANAGER_RATING NUMBER
);

---------------------------------------------------
-- 7. GOALS TABLE
---------------------------------------------------

CREATE TABLE GOALS (
    GOAL_ID NUMBER PRIMARY KEY,
    EMP_ID NUMBER,
    GOAL_DESC VARCHAR2(300),
    DEADLINE DATE,
    PRIORITY VARCHAR2(20),
    SUCCESS_METRICS VARCHAR2(200),
    PROGRESS NUMBER
);

---------------------------------------------------
-- 8. LEAVE_POLICY TABLE
---------------------------------------------------

CREATE TABLE LEAVE_POLICY (
    LEAVE_TYPE VARCHAR2(10) PRIMARY KEY,
    MAX_DAYS NUMBER,
    IS_PAID VARCHAR2(10),
    CARRY_FORWARD VARCHAR2(10)
);

---------------------------------------------------
-- 9. AUDIT_LOG TABLE
---------------------------------------------------

CREATE TABLE AUDIT_LOG (
    LOG_ID NUMBER PRIMARY KEY,
    ACTION VARCHAR2(200),
    USER_ID NUMBER,
    LOG_TIME DATE DEFAULT SYSDATE
);

---------------------------------------------------
-- SEQUENCES
---------------------------------------------------

CREATE SEQUENCE LEAVE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE NOTIF_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE REVIEW_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE GOAL_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE AUDIT_SEQ START WITH 1 INCREMENT BY 1;

---------------------------------------------------
-- INSERT SAMPLE DATA
---------------------------------------------------

INSERT INTO EMPLOYEE VALUES
(9001,'System Admin','admin@rev.com','admin123','ADMIN','9999999999','HQ',NULL,NULL,'ACTIVE',NULL,NULL);

INSERT INTO EMPLOYEE VALUES
(2001,'Manager','manager@rev.com','manager123','MANAGER','8888888888','Office',NULL,NULL,'ACTIVE',NULL,NULL);

INSERT INTO EMPLOYEE VALUES
(1001,'Prasanna','prasanna@gmail.com','emp123','EMPLOYEE','7777777777','City','9999999999',2001,'ACTIVE','Fav color?','blue');

---------------------------------------------------
-- LEAVE BALANCE INSERT
---------------------------------------------------

INSERT INTO LEAVE_BALANCE VALUES (1001,10,8,15,5);

---------------------------------------------------
-- LEAVE POLICY INSERT
---------------------------------------------------

INSERT INTO LEAVE_POLICY VALUES ('CL',12,'YES','NO');
INSERT INTO LEAVE_POLICY VALUES ('SL',10,'YES','NO');
INSERT INTO LEAVE_POLICY VALUES ('PL',20,'YES','YES');
INSERT INTO LEAVE_POLICY VALUES ('PRL',5,'YES','YES');

---------------------------------------------------
-- BASIC SELECT QUERIES
---------------------------------------------------

-- View all employees
SELECT * FROM EMPLOYEE;

-- View leave balances
SELECT * FROM LEAVE_BALANCE;

-- View leave requests
SELECT * FROM LEAVE_REQUEST;

-- View holidays
SELECT * FROM HOLIDAYS;

-- View notifications
SELECT * FROM NOTIFICATIONS;

-- View performance reviews
SELECT * FROM PERFORMANCE_REVIEW;

-- View goals
SELECT * FROM GOALS;

-- View leave policies
SELECT * FROM LEAVE_POLICY;

-- View audit logs
SELECT * FROM AUDIT_LOG;

---------------------------------------------------
-- APPLY LEAVE
---------------------------------------------------

INSERT INTO LEAVE_REQUEST VALUES
(LEAVE_SEQ.NEXTVAL,1001,'CL',DATE '2026-02-10',DATE '2026-02-11','Medical Leave','PENDING',NULL);

---------------------------------------------------
-- MANAGER APPROVE LEAVE
---------------------------------------------------

UPDATE LEAVE_REQUEST
SET STATUS='APPROVED',
    MANAGER_COMMENT='Approved'
WHERE LEAVE_ID=1;

---------------------------------------------------
-- ADD NOTIFICATION
---------------------------------------------------

INSERT INTO NOTIFICATIONS VALUES
(NOTIF_SEQ.NEXTVAL,1001,'Your leave request approved','UNREAD',SYSDATE);

---------------------------------------------------
-- MARK NOTIFICATIONS READ
---------------------------------------------------

UPDATE NOTIFICATIONS
SET STATUS='READ'
WHERE EMP_ID=1001;

---------------------------------------------------
-- SUBMIT PERFORMANCE REVIEW
---------------------------------------------------

INSERT INTO PERFORMANCE_REVIEW VALUES
(REVIEW_SEQ.NEXTVAL,1001,'Completed project','Good delivery','Need time mgmt',4,'SUBMITTED',NULL,NULL);

---------------------------------------------------
-- MANAGER FEEDBACK
---------------------------------------------------

UPDATE PERFORMANCE_REVIEW
SET MANAGER_FEEDBACK='Good work',
    MANAGER_RATING=4,
    STATUS='REVIEWED'
WHERE REVIEW_ID=1;

---------------------------------------------------
-- ADD GOAL
---------------------------------------------------

INSERT INTO GOALS VALUES
(GOAL_SEQ.NEXTVAL,1001,'Learn Spring Boot',DATE '2026-06-30','HIGH','Complete project',20);

---------------------------------------------------
-- UPDATE GOAL PROGRESS
---------------------------------------------------

UPDATE GOALS
SET PROGRESS=60
WHERE GOAL_ID=1;

---------------------------------------------------
-- ADMIN ADJUST LEAVE BALANCE
---------------------------------------------------

UPDATE LEAVE_BALANCE
SET CL=12, SL=9, PL=18, PRL=6
WHERE EMP_ID=1001;

---------------------------------------------------
-- LEAVE REPORT BY DEPARTMENT
---------------------------------------------------

SELECT E.DEPARTMENT, COUNT(L.LEAVE_ID) TOTAL_LEAVES
FROM EMPLOYEE E JOIN LEAVE_REQUEST L
ON E.EMP_ID=L.EMP_ID
GROUP BY E.DEPARTMENT;

---------------------------------------------------
-- LEAVE REPORT BY EMPLOYEE
---------------------------------------------------

SELECT EMP_ID, COUNT(LEAVE_ID) TOTAL_LEAVES
FROM LEAVE_REQUEST
GROUP BY EMP_ID;

---------------------------------------------------
-- AUDIT LOG INSERT
---------------------------------------------------

INSERT INTO AUDIT_LOG VALUES
(AUDIT_SEQ.NEXTVAL,'User logged in',1001,SYSDATE);

---------------------------------------------------
-- END OF FILE
---------------------------------------------------
