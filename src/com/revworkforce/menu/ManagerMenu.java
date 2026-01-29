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
            System.out.println("2. Review Employee Performance");
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
                    int empId = 0;

                    while (rs.next()) {
                        found = true;
                        empId = rs.getInt("EMP_ID");
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

                        // Send notification to employee
                        managerService.sendNotification(
                                empId,
                                "Your leave request (ID " + leaveId + ") has been " + status
                        );

                        System.out.println("Leave has been " + status + " successfully.");
                    }
                    else {
                        System.out.println("Failed to update leave.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
                
            case 2: {

                try {
                    ResultSet rs =
                        managerService.viewSubmittedReviews(manager.getEmpId());

                    System.out.println("\n----- Submitted Reviews -----");
                    System.out.println("REVIEW_ID | EMP_ID | SELF_RATING");

                    boolean found = false;

                    int empId = 0;

                    while (rs.next()) {

                        found = true;

                        empId = rs.getInt("EMP_ID");   // capture employee id

                        System.out.println(
                            rs.getInt("REVIEW_ID") + " | " +
                            empId + " | " +
                            rs.getInt("SELF_RATING")
                        );
                    }


                    if (!found) {
                        System.out.println("No reviews to process.");
                        break;
                    }

                    System.out.print("\nEnter REVIEW_ID to give feedback: ");
                    int reviewId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter feedback: ");
                    String feedback = sc.nextLine();

                    System.out.print("Enter rating (1-5): ");
                    int rating = sc.nextInt();

                    boolean updated =
                        managerService.giveFeedback(reviewId, feedback, rating);

                    if (updated) {

                        // Send notification to employee
                        managerService.sendNotification(
                            empId,
                            "Manager has reviewed your performance and provided feedback."
                        );

                        System.out.println("Feedback submitted successfully.");

                    } else {
                        System.out.println("Failed to submit feedback.");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }



            case 0:
                System.out.println("Logged out.");
                return;

            default:
                System.out.println("Invalid choice.");
            }
        }
    }
}
