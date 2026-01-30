package com.revworkforce.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;

public class EmployeeDAOTest {

    EmployeeDAO dao = new EmployeeDAO();

    @Test
    public void testGetEmployeeById() {

        Employee emp = dao.getEmployeeById(1001); // existing employee

        assertNotNull(emp);
        assertEquals(1001, emp.getEmpId());
    }

    @Test
    public void testUpdateEmployeeStatus() {

        boolean result = dao.updateEmployeeStatus(1001, "ACTIVE");

        assertTrue(result);
    }

}
