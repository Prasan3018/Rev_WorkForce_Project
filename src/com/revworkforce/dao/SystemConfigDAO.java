package com.revworkforce.dao;

import java.sql.*;
import com.revworkforce.util.DBConnection;

public class SystemConfigDAO {

    public boolean addDepartment(int id, String name) {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO DEPARTMENT VALUES (?, ?)"
            );

            ps.setInt(1, id);
            ps.setString(2, name);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet viewDepartments() {

        try {
            Connection con = DBConnection.getConnection();
            return con.prepareStatement(
                "SELECT * FROM DEPARTMENT"
            ).executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addDesignation(int id, String name, int deptId) {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO DESIGNATION VALUES (?, ?, ?)"
            );

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, deptId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet viewDesignations() {

        try {
            Connection con = DBConnection.getConnection();

            return con.prepareStatement(
                "SELECT * FROM DESIGNATION"
            ).executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addReviewCycle(int id, String name, Date start, Date end) {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO REVIEW_CYCLE VALUES (?, ?, ?, ?, 'ACTIVE')"
            );

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDate(3, start);
            ps.setDate(4, end);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ResultSet viewReviewCycles() {

        try {
            Connection con = DBConnection.getConnection();

            return con.prepareStatement(
                "SELECT * FROM REVIEW_CYCLE"
            ).executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    public boolean addPolicy(int id, String name, String value) {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO SYSTEM_POLICY VALUES (?, ?, ?)"
            );

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, value);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ResultSet viewPolicies() {

        try {
            Connection con = DBConnection.getConnection();

            return con.prepareStatement(
                "SELECT * FROM SYSTEM_POLICY"
            ).executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
