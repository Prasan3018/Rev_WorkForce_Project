package com.revworkforce.service;

import java.sql.ResultSet;
import com.revworkforce.dao.LeaveDAO;

public class ManagerService {

    private LeaveDAO leaveDAO = new LeaveDAO();

    public ResultSet viewPendingLeaves(int managerId) {
        return leaveDAO.getPendingLeaves(managerId);
    }

    public boolean approveOrRejectLeave(int leaveId, String status) {
        return leaveDAO.updateLeaveStatus(leaveId, status);
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


}
