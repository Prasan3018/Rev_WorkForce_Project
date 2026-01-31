package com.revworkforce.main;

import com.revworkforce.util.SessionUtil;
import org.apache.logging.log4j.LogManager;
import com.revworkforce.service.EmployeeService;

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
		
		
		


		while (true) {

			System.out.println("=================================");
			System.out.println("      Welcome to RevWorkForce     ");
			System.out.println("=================================");
			System.out.println("1. Login");
			System.out.println("2. Forgot Password");
			System.out.println("0. Exit");

			int mainChoice = InputUtil.getInt(sc, "Enter choice: ");

			// EXIT
			if (mainChoice == 0) {
				logger.info("Application closed by user");
				System.out.println("Application closed.");
				break;
			}

			// FORGOT PASSWORD (NEW – ONLY ADDITION)
			if (mainChoice == 2) {

				int id = InputUtil.getInt(sc, "Enter Employee ID: ");
				String answer = InputUtil.getString(sc,
						"Enter security answer: ");
				String newPass = InputUtil
						.getString(sc, "Enter new password: ");

				boolean reset = authService.forgotPassword(id, answer, newPass);

				if (reset)
					System.out.println("Password reset successfully!");
				else
					System.out.println("Invalid answer or user not found.");

				continue;
			}

			// INVALID OPTION
			if (mainChoice != 1) {
				System.out.println("Invalid option. Try again.");
				continue;
			}

			// ===== EXISTING LOGIN FLOW (UNCHANGED) =====

			System.out.println("Login using:");
			System.out.println("1. Employee ID");
			System.out.println("2. Email");
			System.out.print("Choose option: ");

			int option = InputUtil.getInt(sc, "");

			Employee emp = null;
			int empId = 0;

			if (option == 1) {

				empId = InputUtil.getInt(sc, "Enter Employee ID: ");
				String password = InputUtil.getString(sc, "Enter Password: ");

				emp = authService.loginById(empId, password);

			} else if (option == 2) {

				String email = InputUtil.getString(sc, "Enter Email: ");
				String password = InputUtil.getString(sc, "Enter Password: ");

				emp = authService.loginByEmail(email, password);

			} else {
				System.out.println("Invalid option");
				continue;
			}

			if (emp == null) {

				logger.warn("Invalid login attempt for ID: " + empId);
				System.out.println("Invalid credentials or inactive account.");
				continue;

			} else {

				logger.info("Login successful for ID: " + empId);
				System.out.println("Login successful!");
				System.out.println("Welcome " + emp.getName());
				
				SessionUtil.updateActivity();
				
				new EmployeeService().logAction(emp.getEmpId(), "User logged in");

				
				EmployeeService es = new EmployeeService();
				int unread = es.getUnreadNotificationCount(emp.getEmpId());

				if (unread > 0) {
				    System.out.println("You have " + unread + " unread notifications.");
				}


				String role = emp.getRole();
				logger.info("Role detected: " + role);

				if (role.equalsIgnoreCase("EMPLOYEE")) {
					new EmployeeMenu().showMenu(emp);

				} else if (role.equalsIgnoreCase("MANAGER")) {
					new ManagerMenu().showMenu(emp);

				} else if (role.equalsIgnoreCase("ADMIN")) {
					new AdminMenu().showMenu();
				}
			}
		}

		sc.close();
	}
}
