package com.revworkforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.revworkforce.util.DBConnection;

public class AuditDAO {

    public void logAction(int empId, String action) {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO AUDIT_LOG (LOG_ID, EMP_ID, ACTION, LOG_TIME) VALUES (AUDIT_SEQ.NEXTVAL, ?, ?, SYSDATE)"
            );

            ps.setInt(1, empId);
            ps.setString(2, action);


            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
