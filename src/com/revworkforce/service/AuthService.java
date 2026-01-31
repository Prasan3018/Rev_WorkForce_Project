package com.revworkforce.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revworkforce.util.DBConnection;
import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;


import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;

public class AuthService {

	private EmployeeDAO employeeDAO = new EmployeeDAO();

	// Login validation
	public Employee login(int empId, String password) {

		Employee emp = employeeDAO.validateLogin(empId, password);

		if (emp != null && emp.getStatus().equalsIgnoreCase("ACTIVE")) {
			return emp;
		}

		return null;
	}
	
	public Employee loginByIdOrEmail(String input, String password) {

	    try {

	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps = con.prepareStatement(
	            "SELECT * FROM EMPLOYEE WHERE (EMP_ID = ? OR EMAIL = ?) AND PASSWORD = ? AND STATUS='ACTIVE'"
	        );

	        // Try parse ID
	        int empId = -1;
	        try {
	            empId = Integer.parseInt(input);
	        } catch (Exception e) {
	            // input is email
	        }

	        ps.setInt(1, empId);
	        ps.setString(2, input);
	        ps.setString(3, password);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return mapEmployee(rs); // same mapping you already use
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public Employee loginById(int empId, String password) {
	    return employeeDAO.validateLogin(empId, password);
	}
	
	public Employee loginByEmail(String email, String password) {

	    Employee emp = null;

	    try {
	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps = con.prepareStatement(
	            "SELECT * FROM EMPLOYEE WHERE EMAIL=? AND PASSWORD=? AND STATUS='ACTIVE'"
	        );

	        ps.setString(1, email);
	        ps.setString(2, password);

	        ResultSet rs = ps.executeQuery();

	        if(rs.next()) {
	            emp = mapEmployee(rs);   // same mapping logic
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return emp;
	}

	private Employee mapEmployee(ResultSet rs) throws Exception {

	    Employee emp = new Employee();

	    emp.setEmpId(rs.getInt("EMP_ID"));
	    emp.setName(rs.getString("NAME"));
	    emp.setEmail(rs.getString("EMAIL"));
	    emp.setRole(rs.getString("ROLE"));
	    emp.setPhone(rs.getString("PHONE"));
	    emp.setAddress(rs.getString("ADDRESS"));
	    emp.setEmergencyContact(rs.getString("EMERGENCY_CONTACT"));
	    emp.setManagerId(rs.getInt("MANAGER_ID"));
	    emp.setStatus(rs.getString("STATUS"));

	    return emp;
	}

	// ================= FORGOT PASSWORD =================

	public boolean forgotPassword(int empId, String answer, String newPassword) {

	    boolean updated = false;

	    try {
	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps = con.prepareStatement(
	            "UPDATE EMPLOYEE SET PASSWORD=? WHERE EMP_ID=? AND SECURITY_ANSWER=?"
	        );

	        ps.setString(1, newPassword);
	        ps.setInt(2, empId);
	        ps.setString(3, answer);

	        int count = ps.executeUpdate();

	        if (count > 0) {
	            updated = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return updated;
	}

}
