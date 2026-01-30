package com.revworkforce.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import com.revworkforce.model.Employee;
import com.revworkforce.service.AuthService;
import com.revworkforce.menu.EmployeeMenu;
import com.revworkforce.menu.ManagerMenu;
import com.revworkforce.menu.AdminMenu;
import com.revworkforce.util.InputUtil;

public class RevWorkForceApp {

	private static final Logger logger = LogManager
			.getLogger(RevWorkForceApp.class);

	public static void main(String[] args) {
		logger.info("Application started");


		Scanner sc = new Scanner(System.in);
		AuthService authService = new AuthService();

		System.out.println("=================================");
		System.out.println("      Welcome to RevWorkForce     ");
		System.out.println("=================================");


		int empId = InputUtil.getInt(sc, "Enter Employee ID: ");


		String password = InputUtil.getString(sc, "Enter Password: ");

		Employee emp = authService.login(empId, password);

		if (emp == null) {
			logger.warn("Invalid login attempt");
			System.out.println("Invalid credentials or inactive account.");
		} else {
			logger.info("Login successful");
			System.out.println("Login successful!");
			System.out.println("Welcome " + emp.getName());

			logger.info("User logged in: " + emp.getEmpId() + " Role: "
					+ emp.getRole());

			String role = emp.getRole();

			if (role.equalsIgnoreCase("EMPLOYEE")) {
				new EmployeeMenu().showMenu(emp);

			} else if (role.equalsIgnoreCase("MANAGER")) {
				new ManagerMenu().showMenu(emp);

			} else if (role.equalsIgnoreCase("ADMIN")) {
				new AdminMenu().showMenu();
			}
		}

		sc.close();
	}
}
