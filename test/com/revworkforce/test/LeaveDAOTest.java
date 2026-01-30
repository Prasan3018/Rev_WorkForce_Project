package com.revworkforce.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.Date;

import com.revworkforce.dao.LeaveDAO;

public class LeaveDAOTest {

    LeaveDAO dao = new LeaveDAO();

    @Test
    public void testApplyLeave() {

        boolean result = dao.applyLeave(
                1001,
                "CL",
                Date.valueOf("2026-02-10"),
                Date.valueOf("2026-02-11"),
                "JUnit Test Leave"
        );

        assertTrue(result);
    }

    @Test
    public void testGetNotifications() {

        assertNotNull(dao.getNotifications(1001));
    }

}
