package com.revworkforce.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revworkforce.service.EmployeeService;
import com.revworkforce.model.Employee;

public class EmployeeServiceTest {

	EmployeeService service = new EmployeeService();

	@Test
	public void testViewProfile() {

		Employee emp = service.viewProfile(1001);

		assertNotNull(emp);
		assertEquals(1001, emp.getEmpId());
	}

	@Test
	public void testAssignLeaveBalance() {

		boolean result = service.assignLeaveBalance(1001, 5, 5, 5, 0);

		assertTrue(result);
	}

}
