package com.revworkforce.main;

import java.util.Scanner;

import com.revworkforce.model.Employee;
import com.revworkforce.service.AuthService;
import com.revworkforce.menu.EmployeeMenu;
import com.revworkforce.menu.ManagerMenu;
import com.revworkforce.menu.AdminMenu;

public class RevWorkForceApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();

        System.out.println("=================================");
        System.out.println("      Welcome to RevWorkForce     ");
        System.out.println("=================================");

        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();

        System.out.print("Enter Password: ");
        String password = sc.next();

        Employee emp = authService.login(empId, password);

        if (emp == null) {
            System.out.println("Invalid credentials or inactive account.");
        } else {
            System.out.println("Login successful!");
            System.out.println("Welcome " + emp.getName());

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
