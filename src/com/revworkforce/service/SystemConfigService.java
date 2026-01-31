package com.revworkforce.service;

import java.sql.ResultSet;
import com.revworkforce.dao.SystemConfigDAO;

public class SystemConfigService {

    private SystemConfigDAO dao = new SystemConfigDAO();

    public boolean addDepartment(int id, String name) {
        return dao.addDepartment(id, name);
    }

    public ResultSet viewDepartments() {
        return dao.viewDepartments();
    }

    public boolean addDesignation(int id, String name, int deptId) {
        return dao.addDesignation(id, name, deptId);
    }

    public ResultSet viewDesignations() {
        return dao.viewDesignations();
    }
    
    public boolean addReviewCycle(int id, String name, 
            java.sql.Date start, java.sql.Date end) {

        return dao.addReviewCycle(id, name, start, end);
    }

    public ResultSet viewReviewCycles() {
        return dao.viewReviewCycles();
    }
    
    public boolean addPolicy(int id, String name, String value) {
        return dao.addPolicy(id, name, value);
    }

    public ResultSet viewPolicies() {
        return dao.viewPolicies();
    }


}
