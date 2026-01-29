package com.revworkforce.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revworkforce.model.Employee;
import com.revworkforce.util.DBConnection;

public class EmployeeDAO {

    // Validate login using EMP_ID and PASSWORD
    public Employee validateLogin(int empId, String password) {

        Employee emp = null;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM EMPLOYEE WHERE EMP_ID=? AND PASSWORD=?"
            );

            ps.setInt(1, empId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emp = new Employee();
                emp.setEmpId(rs.getInt("EMP_ID"));
                emp.setName(rs.getString("NAME"));
                emp.setEmail(rs.getString("EMAIL"));
                emp.setRole(rs.getString("ROLE"));
                emp.setPhone(rs.getString("PHONE"));
                emp.setAddress(rs.getString("ADDRESS"));
                emp.setEmergencyContact(rs.getString("EMERGENCY_CONTACT"));
                emp.setManagerId(rs.getInt("MANAGER_ID"));
                emp.setStatus(rs.getString("STATUS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp;
    }
    
    public Employee getEmployeeById(int empId) {

        Employee emp = null;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM EMPLOYEE WHERE EMP_ID=?"
            );

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emp = new Employee();
                emp.setEmpId(rs.getInt("EMP_ID"));
                emp.setName(rs.getString("NAME"));
                emp.setEmail(rs.getString("EMAIL"));
                emp.setRole(rs.getString("ROLE"));
                emp.setPhone(rs.getString("PHONE"));
                emp.setAddress(rs.getString("ADDRESS"));
                emp.setEmergencyContact(rs.getString("EMERGENCY_CONTACT"));
                emp.setManagerId(rs.getInt("MANAGER_ID"));
                emp.setStatus(rs.getString("STATUS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp;
    }

    public boolean updateProfile(Employee emp) {

        boolean updated = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE EMPLOYEE SET PHONE=?, ADDRESS=?, EMERGENCY_CONTACT=? WHERE EMP_ID=?"
            );

            ps.setString(1, emp.getPhone());
            ps.setString(2, emp.getAddress());
            ps.setString(3, emp.getEmergencyContact());
            ps.setInt(4, emp.getEmpId());

            int count = ps.executeUpdate();

            if (count > 0) {
                updated = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }

    
    public Employee getManagerDetails(int managerId) {

        Employee manager = null;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT EMP_ID, NAME, EMAIL, PHONE FROM EMPLOYEE WHERE EMP_ID=?"
            );

            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                manager = new Employee();
                manager.setEmpId(rs.getInt("EMP_ID"));
                manager.setName(rs.getString("NAME"));
                manager.setEmail(rs.getString("EMAIL"));
                manager.setPhone(rs.getString("PHONE"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return manager;
    }

 // Get leave balance for employee
    public int[] getLeaveBalance(int empId) {

        int[] balance = new int[3]; // CL, SL, PL

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT CL, SL, PL FROM LEAVE_BALANCE WHERE EMP_ID=?"
            );

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                balance[0] = rs.getInt("CL");
                balance[1] = rs.getInt("SL");
                balance[2] = rs.getInt("PL");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }
    
    public boolean addEmployee(Employee emp) {

        boolean added = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
            		 "INSERT INTO EMPLOYEE " +
            		 "(EMP_ID, NAME, EMAIL, PASSWORD, ROLE, PHONE, ADDRESS, JOINING_DATE, MANAGER_ID, STATUS, DOB, DEPARTMENT, DESIGNATION, SALARY) " +
            		 "VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, 'ACTIVE', ?, ?, ?, ?)"
            		);


            ps.setInt(1, emp.getEmpId());
            ps.setString(2, emp.getName());
            ps.setString(3, emp.getEmail());
            ps.setString(4, emp.getPassword());
            ps.setString(5, emp.getRole());
            ps.setString(6, emp.getPhone());
            ps.setString(7, emp.getAddress());
            ps.setInt(8, emp.getManagerId());
            ps.setDate(9, emp.getDob());
            ps.setString(10, emp.getDepartment());
            ps.setString(11, emp.getDesignation());
            ps.setDouble(12, emp.getSalary());

            int count = ps.executeUpdate();

            if (count > 0) {
                added = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return added;
    }
    
    
    public boolean assignLeaveBalance(int empId, int cl, int sl, int pl) {

        boolean assigned = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO LEAVE_BALANCE VALUES (?, ?, ?, ?)"
            );

            ps.setInt(1, empId);
            ps.setInt(2, cl);
            ps.setInt(3, sl);
            ps.setInt(4, pl);

            int count = ps.executeUpdate();

            if (count > 0) {
                assigned = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return assigned;
    }

    public ResultSet getAllEmployees() {

        ResultSet rs = null;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT EMP_ID, NAME, EMAIL, ROLE, PHONE, MANAGER_ID, STATUS FROM EMPLOYEE"
            );

            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public boolean updateEmployee(Employee emp) {

        boolean updated = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE EMPLOYEE SET NAME=?, EMAIL=?, PHONE=?, ADDRESS=?, " +
                "DEPARTMENT=?, DESIGNATION=?, SALARY=?, MANAGER_ID=? " +
                "WHERE EMP_ID=?"
            );

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setString(4, emp.getAddress());
            ps.setString(5, emp.getDepartment());
            ps.setString(6, emp.getDesignation());
            ps.setDouble(7, emp.getSalary());
            ps.setInt(8, emp.getManagerId());
            ps.setInt(9, emp.getEmpId());

            int count = ps.executeUpdate();

            if (count > 0) updated = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }

    public boolean updateEmployeeStatus(int empId, String status) {

        boolean updated = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE EMPLOYEE SET STATUS=? WHERE EMP_ID=?"
            );

            ps.setString(1, status);
            ps.setInt(2, empId);

            int count = ps.executeUpdate();

            if (count > 0) updated = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }
    
    public boolean changeManager(int empId, int managerId) {

        boolean changed = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE EMPLOYEE SET MANAGER_ID=? WHERE EMP_ID=?"
            );

            ps.setInt(1, managerId);
            ps.setInt(2, empId);

            int count = ps.executeUpdate();

            if (count > 0) changed = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return changed;
    }

    public ResultSet searchEmployees(String keyword) {

        ResultSet rs = null;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT EMP_ID, NAME, DEPARTMENT, DESIGNATION " +
                "FROM EMPLOYEE " +
                "WHERE NAME LIKE ? OR EMP_ID LIKE ? OR DEPARTMENT LIKE ? OR DESIGNATION LIKE ?"
            );

            String key = "%" + keyword + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);

            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public boolean adjustLeaveBalance(int empId, int cl, int sl, int pl) {

        boolean updated = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE LEAVE_BALANCE SET CL=?, SL=?, PL=? WHERE EMP_ID=?"
            );

            ps.setInt(1, cl);
            ps.setInt(2, sl);
            ps.setInt(3, pl);
            ps.setInt(4, empId);

            if (ps.executeUpdate() > 0)
                updated = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }

}



