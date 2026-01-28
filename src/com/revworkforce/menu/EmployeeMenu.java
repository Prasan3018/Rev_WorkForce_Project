package com.revworkforce.menu;

import java.util.Scanner;

import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;

public class EmployeeMenu {

    private EmployeeService employeeService = new EmployeeService();

    public void showMenu(Employee emp) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Employee Menu =====");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. View Manager Details");
            System.out.println("4. View Leave Balance");
            System.out.println("5. Apply Leave");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

            case 1:
                Employee e = employeeService.viewProfile(emp.getEmpId());
                displayProfile(e);
                break;
                
            case 2:
                sc.nextLine(); // clear buffer
                System.out.print("Enter new phone: ");
                String phone = sc.nextLine();

                System.out.print("Enter new address: ");
                String address = sc.nextLine();

                boolean result = employeeService.editProfile(emp.getEmpId(), phone, address);

                if (result) {
                    System.out.println("Profile updated successfully.");
                } else {
                    System.out.println("Profile update failed.");
                }
                break;
                
            case 3:
                Employee mgr = employeeService.viewManager(emp.getManagerId());

                if (mgr != null) {
                    System.out.println("\n--- Manager Details ---");
                    System.out.println("Manager ID   : " + mgr.getEmpId());
                    System.out.println("Name         : " + mgr.getName());
                    System.out.println("Email        : " + mgr.getEmail());
                    System.out.println("Phone        : " + mgr.getPhone());
                } else {
                    System.out.println("Manager details not found.");
                }
                break;
                
            case 4:
                int[] balance = employeeService.viewLeaveBalance(emp.getEmpId());

                System.out.println("\n--- Leave Balance ---");
                System.out.println("Casual Leave : " + balance[0]);
                System.out.println("Sick Leave   : " + balance[1]);
                System.out.println("Paid Leave   : " + balance[2]);
                break;
                
            case 5:
                sc.nextLine(); // clear buffer

                System.out.print("Enter Leave Type (CL/SL/PL): ");
                String type = sc.nextLine();

                System.out.print("Enter From Date (yyyy-mm-dd): ");
                String from = sc.nextLine();

                System.out.print("Enter To Date (yyyy-mm-dd): ");
                String to = sc.nextLine();

                System.out.print("Enter Reason: ");
                String reason = sc.nextLine();

                java.sql.Date fromDate = java.sql.Date.valueOf(from);
                java.sql.Date toDate = java.sql.Date.valueOf(to);

                boolean applied = employeeService.applyLeave(
                    emp.getEmpId(), type, fromDate, toDate, reason
                );

                if (applied) {
                    System.out.println("Leave applied successfully. Status: PENDING");
                } else {
                    System.out.println("Leave application failed.");
                }
                break;





            case 0:
                System.out.println("Logged out successfully.");
                return;

            default:
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void displayProfile(Employee e) {
        System.out.println("\n----- My Profile -----");
        System.out.println("Employee ID : " + e.getEmpId());
        System.out.println("Name        : " + e.getName());
        System.out.println("Email       : " + e.getEmail());
        System.out.println("Role        : " + e.getRole());
        System.out.println("Phone       : " + e.getPhone());
        System.out.println("Address     : " + e.getAddress());
        System.out.println("Manager ID  : " + e.getManagerId());
        System.out.println("Status      : " + e.getStatus());
    }
}
