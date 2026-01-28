package com.revworkforce.service;

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
}
