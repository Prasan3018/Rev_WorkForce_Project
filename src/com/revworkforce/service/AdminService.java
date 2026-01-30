package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
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
}
