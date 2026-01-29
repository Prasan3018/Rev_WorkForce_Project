package com.revworkforce.service;

import java.sql.ResultSet;
import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;

import com.revworkforce.dao.LeaveDAO;

public class ManagerService {

    private LeaveDAO leaveDAO = new LeaveDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();


    public ResultSet viewPendingLeaves(int managerId) {
        return leaveDAO.getPendingLeaves(managerId);
    }

    public boolean approveOrRejectLeave(int leaveId, String status, String comment) {
        return leaveDAO.updateLeaveStatus(leaveId, status, comment);
    }

    
    public void sendNotification(int empId, String message) {
        leaveDAO.addNotification(empId, message);
    }
    
    public ResultSet viewSubmittedReviews(int managerId) {
        return leaveDAO.getSubmittedReviews(managerId);
    }

    public boolean giveFeedback(int reviewId,
            String feedback, int rating) {

        return leaveDAO.addManagerFeedback(reviewId, feedback, rating);
    }
    
    public ResultSet viewNotifications(int empId) {
        return leaveDAO.getNotifications(empId);
    }

    public ResultSet viewTeamMembers(int managerId) {
        return leaveDAO.getTeamMembers(managerId);
    }
    
    public ResultSet viewTeamLeaveBalances(int managerId) {
        return leaveDAO.getTeamLeaveBalances(managerId);
    }

    public ResultSet viewTeamGoals(int managerId) {
        return leaveDAO.getTeamGoals(managerId);
    }

    public ResultSet viewTeamPerformanceSummary(int managerId) {
        return leaveDAO.getTeamPerformanceSummary(managerId);
    }
    
    public ResultSet viewTeamLeaveCalendar(int managerId) {
        return leaveDAO.getTeamLeaveCalendar(managerId);
    }
    
    public ResultSet viewTeamHierarchy(int managerId) {
        return leaveDAO.getTeamHierarchy(managerId);
    }

    public Employee viewTeamMemberProfile(int empId) {
        return employeeDAO.getEmployeeById(empId);
    }


    public ResultSet viewTeamAttendanceSummary(int managerId) {
        return leaveDAO.getTeamAttendanceSummary(managerId);
    }


}
