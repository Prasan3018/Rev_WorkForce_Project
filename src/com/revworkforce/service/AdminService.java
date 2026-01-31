package com.revworkforce.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.util.DBConnection;
import com.revworkforce.model.Employee;

public class AdminService {

	private EmployeeDAO employeeDAO = new EmployeeDAO();

	public boolean addEmployee(Employee emp) {
		return employeeDAO.addEmployee(emp);
	}

	public boolean updateEmployee(Employee emp) {
		return employeeDAO.updateEmployee(emp);
	}

	public boolean updateEmployeeStatus(int empId, String status) {
		return employeeDAO.updateEmployeeStatus(empId, status);
	}

	// AUDIT LOG VIEW (NOW CORRECTLY INSIDE CLASS)
	public ResultSet viewAuditLogs() {

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM AUDIT_LOG ORDER BY LOG_TIME DESC");

			return ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
