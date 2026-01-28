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
}
