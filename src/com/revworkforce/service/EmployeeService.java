package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;
import com.revworkforce.dao.LeaveDAO;
import java.sql.ResultSet;


public class EmployeeService {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    // View employee profile
    public Employee viewProfile(int empId) {
        return employeeDAO.getEmployeeById(empId);
    }
    
    public boolean editProfile(int empId, String phone, String address) {
        return employeeDAO.updateProfile(empId, phone, address);
    }

    public Employee viewManager(int managerId) {
        return employeeDAO.getManagerDetails(managerId);
    }
    
    public int[] viewLeaveBalance(int empId) {
        return employeeDAO.getLeaveBalance(empId);
    }
    
    private LeaveDAO leaveDAO = new LeaveDAO();

    public boolean applyLeave(int empId, String leaveType,
                              java.sql.Date fromDate,
                              java.sql.Date toDate,
                              String reason) {
        return leaveDAO.applyLeave(empId, leaveType, fromDate, toDate, reason);
    }

    public boolean addEmployee(Employee emp) {
        return employeeDAO.addEmployee(emp);
    }
    
    public boolean assignLeaveBalance(int empId, int cl, int sl, int pl) {
        return employeeDAO.assignLeaveBalance(empId, cl, sl, pl);
    }

    public ResultSet viewAllEmployees() {
        return employeeDAO.getAllEmployees();
    }


}
