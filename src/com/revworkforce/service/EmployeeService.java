package com.revworkforce.service;

import com.revworkforce.dao.AuditDAO;
import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;
import com.revworkforce.dao.LeaveDAO;
import java.sql.ResultSet;

public class EmployeeService {

	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private AuditDAO auditDAO = new AuditDAO();


	// View employee profile
	public Employee viewProfile(int empId) {
		return employeeDAO.getEmployeeById(empId);
	}

	public boolean updateProfile(Employee emp) {
		return employeeDAO.updateProfile(emp);
	}

	public Employee viewManager(int managerId) {
		return employeeDAO.getManagerDetails(managerId);
	}

	public int[] viewLeaveBalance(int empId) {
		return employeeDAO.getLeaveBalance(empId);
	}

	private LeaveDAO leaveDAO = new LeaveDAO();

	public boolean applyLeave(int empId, String leaveType,
			java.sql.Date fromDate, java.sql.Date toDate, String reason) {
		return leaveDAO.applyLeave(empId, leaveType, fromDate, toDate, reason);
	}

	public boolean addEmployee(Employee emp) {
		return employeeDAO.addEmployee(emp);
	}

	public boolean assignLeaveBalance(int empId, int cl, int sl, int pl, int prl) {
		return employeeDAO.assignLeaveBalance(empId, cl, sl, pl, prl);
	}

	public ResultSet viewAllEmployees() {
		return employeeDAO.getAllEmployees();
	}

	public java.sql.ResultSet viewMyLeaves(int empId) {
		return leaveDAO.getEmployeeLeaves(empId);
	}

	public ResultSet viewHolidays() {
		return leaveDAO.getHolidays();
	}

	public boolean cancelLeave(int leaveId, int empId) {
		return leaveDAO.cancelLeave(leaveId, empId);
	}

	public ResultSet viewNotifications(int empId) {
		return leaveDAO.getNotifications(empId);
	}

	public boolean submitPerformanceReview(int empId, String deliverables,
			String accomplishments, String improvements, int rating) {

		return leaveDAO.submitReview(empId, deliverables, accomplishments,
				improvements, rating);
	}

	public ResultSet viewMyPerformanceReview(int empId) {
		return leaveDAO.getMyReview(empId);
	}

	public boolean addGoal(int empId, String desc, java.sql.Date deadline,
			String priority, String metrics) {

		return leaveDAO.addGoal(empId, desc, deadline, priority, metrics);
	}

	public ResultSet viewGoals(int empId) {
		return leaveDAO.getGoals(empId);
	}

	public boolean updateGoalProgress(int goalId, int progress) {
		return leaveDAO.updateGoalProgress(goalId, progress);
	}

	public boolean changeManager(int empId, int managerId) {
		return employeeDAO.changeManager(empId, managerId);
	}

	public ResultSet searchEmployees(String key) {
		return employeeDAO.searchEmployees(key);
	}

	public boolean updateEmployee(Employee emp) {
		return employeeDAO.updateEmployee(emp);
	}

	public boolean updateEmployeeStatus(int empId, String status) {
		return employeeDAO.updateEmployeeStatus(empId, status);
	}

	public boolean adjustLeaveBalance(int empId, int cl, int sl, int pl, int prl) {
		return employeeDAO.adjustLeaveBalance(empId, cl, sl, pl, prl);
	}

	public boolean revokeLeave(int leaveId) {
		return leaveDAO.revokeLeave(leaveId);
	}

	public ResultSet viewAllLeaves() {
		return leaveDAO.getAllLeaves();
	}

	public ResultSet getLeaveReportByDepartment() {
		return leaveDAO.getLeaveReportByDepartment();
	}

	public ResultSet getLeaveReportByEmployee() {
		return leaveDAO.getLeaveReportByEmployee();
	}

	public boolean addLeavePolicy(String type, int days, String paid,
			String carry) {
		return leaveDAO.addLeavePolicy(type, days, paid, carry);
	}

	public ResultSet viewLeavePolicies() {
		return leaveDAO.getLeavePolicies();
	}
	
	public boolean changePassword(int empId, String oldPass, String newPass) {
	    return employeeDAO.changePassword(empId, oldPass, newPass);
	}

	public boolean forgotPassword(int empId, String answer, String newPassword) {
	    return employeeDAO.resetPassword(empId, answer, newPassword);
	}
	
	public int getUnreadNotificationCount(int empId) {
	    return leaveDAO.getUnreadNotificationCount(empId);
	}
	
	public void markNotificationsRead(int empId) {
	    leaveDAO.markNotificationsRead(empId);
	}
	
	public void logAction(int empId, String action) {
	    auditDAO.logAction(empId, action);
	}

	public boolean postAnnouncement(String message) {
	    return leaveDAO.addAnnouncement(message);
	}

	




}
