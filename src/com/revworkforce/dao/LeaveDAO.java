package com.revworkforce.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.revworkforce.util.DBConnection;

public class LeaveDAO {

    public boolean applyLeave(int empId, String leaveType,
                              java.sql.Date fromDate,
                              java.sql.Date toDate,
                              String reason) {

        boolean applied = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO LEAVE_REQUEST VALUES (LEAVE_SEQ.NEXTVAL, ?, ?, ?, ?, 'PENDING', ?)"
            );

            ps.setInt(1, empId);
            ps.setString(2, leaveType);
            ps.setDate(3, fromDate);
            ps.setDate(4, toDate);
            ps.setString(5, reason);

            int count = ps.executeUpdate();

            if (count > 0) {
                applied = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applied;
    }
    
    public ResultSet getPendingLeaves(int managerId) {

        ResultSet rs = null;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT L.LEAVE_ID, L.EMP_ID, L.LEAVE_TYPE, L.FROM_DATE, L.TO_DATE, L.REASON " +
                "FROM LEAVE_REQUEST L JOIN EMPLOYEE E ON L.EMP_ID = E.EMP_ID " +
                "WHERE E.MANAGER_ID = ? AND L.STATUS = 'PENDING'"
            );

            ps.setInt(1, managerId);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }
    
    public boolean updateLeaveStatus(int leaveId, String status) {

        boolean updated = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE LEAVE_REQUEST SET STATUS=? WHERE LEAVE_ID=?"
            );

            ps.setString(1, status);
            ps.setInt(2, leaveId);

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
