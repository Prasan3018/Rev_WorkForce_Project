package com.revworkforce.menu;

import java.util.Scanner;

import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;
import java.sql.ResultSet;

public class AdminMenu {

    private EmployeeService employeeService = new EmployeeService();

    public void showMenu() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Assign Leave Balance");
            System.out.println("3. View All Employees");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

            case 1:
                sc.nextLine();

                Employee emp = new Employee();

                System.out.print("Enter Employee ID: ");
                emp.setEmpId(sc.nextInt());
                sc.nextLine();

                System.out.print("Enter Name: ");
                emp.setName(sc.nextLine());

                System.out.print("Enter Email: ");
                emp.setEmail(sc.nextLine());

                System.out.print("Enter Password: ");
                emp.setPassword(sc.nextLine());

                System.out.print("Enter Role (EMPLOYEE/MANAGER): ");
                emp.setRole(sc.nextLine());

                System.out.print("Enter Phone: ");
                emp.setPhone(sc.nextLine());

                System.out.print("Enter Address: ");
                emp.setAddress(sc.nextLine());

                System.out.print("Enter Manager ID: ");
                emp.setManagerId(sc.nextInt());

                boolean result = employeeService.addEmployee(emp);

                if (result) {
                    System.out.println("Employee added successfully.");
                } else {
                    System.out.println("Failed to add employee.");
                }

                break;
                
            case 2:
                System.out.print("Enter Employee ID: ");
                int empId = sc.nextInt();

                System.out.print("Enter Casual Leave: ");
                int cl = sc.nextInt();

                System.out.print("Enter Sick Leave: ");
                int sl = sc.nextInt();

                System.out.print("Enter Paid Leave: ");
                int pl = sc.nextInt();

                boolean assigned = employeeService.assignLeaveBalance(empId, cl, sl, pl);

                if (assigned) {
                    System.out.println("Leave balance assigned successfully.");
                } else {
                    System.out.println("Failed to assign leave balance.");
                }
                break;
                
            case 3:
                try {
                    ResultSet rs = employeeService.viewAllEmployees();

                    System.out.println("\nEMP_ID | NAME | EMAIL | ROLE | PHONE | MANAGER_ID | STATUS");

                    while (rs.next()) {
                        System.out.println(
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("NAME") + " | " +
                            rs.getString("EMAIL") + " | " +
                            rs.getString("ROLE") + " | " +
                            rs.getString("PHONE") + " | " +
                            rs.getInt("MANAGER_ID") + " | " +
                            rs.getString("STATUS")
                        );
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;



            case 0:
                System.out.println("Logged out.");
                return;

            default:
                System.out.println("Invalid choice.");
            }
        }
    }
}
