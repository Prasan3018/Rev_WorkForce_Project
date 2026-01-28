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
                emp.setManagerId(rs.getInt("MANAGER_ID"));
                emp.setStatus(rs.getString("STATUS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp;
    }

    public boolean updateProfile(int empId, String phone, String address) {

        boolean updated = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE EMPLOYEE SET PHONE=?, ADDRESS=? WHERE EMP_ID=?"
            );

            ps.setString(1, phone);
            ps.setString(2, address);
            ps.setInt(3, empId);

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
                "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, 'ACTIVE')"
            );

            ps.setInt(1, emp.getEmpId());
            ps.setString(2, emp.getName());
            ps.setString(3, emp.getEmail());
            ps.setString(4, emp.getPassword());
            ps.setString(5, emp.getRole());
            ps.setString(6, emp.getPhone());
            ps.setString(7, emp.getAddress());
            ps.setInt(8, emp.getManagerId());

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

    
   
}



