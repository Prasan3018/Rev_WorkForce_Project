package com.revworkforce.menu;

import java.sql.ResultSet;
import java.util.Scanner;
import com.revworkforce.service.ManagerService;


import com.revworkforce.model.Employee;

public class ManagerMenu {

    private ManagerService managerService = new ManagerService();

    public void showMenu(Employee manager) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== Manager Menu =====");
            System.out.println("1. View Pending Leave Requests");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {

            case 1:
                try {
                    ResultSet rs =
                        managerService.viewPendingLeaves(manager.getEmpId());

                    System.out.println("\n----- Pending Leave Requests -----");
                    System.out.println("LEAVE_ID | EMP_ID | TYPE | FROM | TO | REASON");
                    System.out.println("---------------------------------");

                    boolean found = false;

                    while (rs.next()) {
                        found = true;
                        System.out.println(
                            rs.getInt("LEAVE_ID") + " | " +
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("LEAVE_TYPE") + " | " +
                            rs.getDate("FROM_DATE") + " | " +
                            rs.getDate("TO_DATE") + " | " +
                            rs.getString("REASON")
                        );
                    }

                    if (!found) {
                        System.out.println("No pending leave requests.");
                        break;
                    }

                    System.out.println("---------------------------------");
                    System.out.print("Enter the LEAVE_ID you want to process: ");

                    int leaveId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Type A to Approve or R to Reject: ");
                    String action = sc.nextLine();

                    String status =
                        action.equalsIgnoreCase("A") ? "APPROVED" : "REJECTED";

                    boolean result =
                        managerService.approveOrRejectLeave(leaveId, status);

                    if (result) {
                        System.out.println("Leave has been " + status + " successfully.");
                    } else {
                        System.out.println("Failed to update leave.");
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
