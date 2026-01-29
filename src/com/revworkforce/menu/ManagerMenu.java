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
            System.out.println("3. View Notifications");
            System.out.println("4. View My Team Members");
            System.out.println("5. View Team Leave Balances");
            System.out.println("6. View Team Goals & Progress");
            System.out.println("7. View Team Performance Summary");
            System.out.println("8. View Team Leave Calendar");
            System.out.println("9. View Team Hierarchy");
            System.out.println("10. View Team Member Profile");
            System.out.println("11. View Team Attendance Summary");
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

                    System.out.print("Enter comment: ");
                    String comment = sc.nextLine();

                    boolean result =
                        managerService.approveOrRejectLeave(leaveId, status, comment);


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
            
            case 3: {

                try {
                    ResultSet rs =
                        managerService.viewNotifications(manager.getEmpId());

                    System.out.println("\n----- Notifications -----");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("NOTIF_ID") + ". " +
                            rs.getString("MESSAGE") +
                            " [" + rs.getString("STATUS") + "]"
                        );
                    }

                    if (!found) {
                        System.out.println("No notifications.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }

            case 4: {

                try {
                    ResultSet rs =
                        managerService.viewTeamMembers(manager.getEmpId());

                    System.out.println("\n----- My Team Members -----");
                    System.out.println("EMP_ID | NAME | EMAIL | ROLE | PHONE | STATUS");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("NAME") + " | " +
                            rs.getString("EMAIL") + " | " +
                            rs.getString("ROLE") + " | " +
                            rs.getString("PHONE") + " | " +
                            rs.getString("STATUS")
                        );
                    }

                    if (!found) {
                        System.out.println("No team members found.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            
            case 5: {

                try {
                    ResultSet rs =
                        managerService.viewTeamLeaveBalances(manager.getEmpId());

                    System.out.println("\n----- Team Leave Balances -----");
                    System.out.println("EMP_ID | NAME | CL | SL | PL");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("NAME") + " | " +
                            rs.getInt("CL") + " | " +
                            rs.getInt("SL") + " | " +
                            rs.getInt("PL")
                        );
                    }

                    if (!found) {
                        System.out.println("No leave balance records found.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            
            case 6: {

                try {
                    ResultSet rs =
                        managerService.viewTeamGoals(manager.getEmpId());

                    System.out.println("\n----- Team Goals & Progress -----");
                    System.out.println("GOAL_ID | EMP_ID | DESCRIPTION | DEADLINE | PRIORITY | PROGRESS");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("GOAL_ID") + " | " +
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("GOAL_DESC") + " | " +
                            rs.getDate("DEADLINE") + " | " +
                            rs.getString("PRIORITY") + " | " +
                            rs.getInt("PROGRESS") + "%"
                        );
                    }

                    if (!found) {
                        System.out.println("No goals found.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }

            case 7: {

                try {
                    ResultSet rs =
                        managerService.viewTeamPerformanceSummary(manager.getEmpId());

                    System.out.println("\n----- Team Performance Summary -----");
                    System.out.println("EMP_ID | NAME | SELF_RATING | MANAGER_RATING | STATUS");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("NAME") + " | " +
                            rs.getInt("SELF_RATING") + " | " +
                            rs.getInt("MANAGER_RATING") + " | " +
                            rs.getString("STATUS")
                        );
                    }

                    if (!found) {
                        System.out.println("No performance records found.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            
            case 8: {

                try {
                    ResultSet rs =
                        managerService.viewTeamLeaveCalendar(manager.getEmpId());

                    System.out.println("\n----- Team Leave Calendar -----");
                    System.out.println("EMP_ID | NAME | FROM_DATE | TO_DATE | TYPE | STATUS");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("NAME") + " | " +
                            rs.getDate("FROM_DATE") + " | " +
                            rs.getDate("TO_DATE") + " | " +
                            rs.getString("LEAVE_TYPE") + " | " +
                            rs.getString("STATUS")
                        );
                    }

                    if (!found) {
                        System.out.println("No approved leaves found.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }

            case 9: {

                try {

                    ResultSet rs =
                        managerService.viewTeamHierarchy(manager.getEmpId());

                    System.out.println("\nManager: " + manager.getName() +
                                       " (" + manager.getEmpId() + ")");

                    System.out.println("\nEmployees:");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("EMP_ID") + " - " +
                            rs.getString("NAME")
                        );
                    }

                    if (!found) {
                        System.out.println("No employees found.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }

            case 10: {

                System.out.print("Enter Employee ID to view profile: ");
                int empId = sc.nextInt();

                Employee empProfile =
                    managerService.viewTeamMemberProfile(empId);

                if (empProfile != null) {

                    System.out.println("\n----- Employee Profile -----");
                    System.out.println("Employee ID : " + empProfile.getEmpId());
                    System.out.println("Name        : " + empProfile.getName());
                    System.out.println("Email       : " + empProfile.getEmail());
                    System.out.println("Role        : " + empProfile.getRole());
                    System.out.println("Phone       : " + empProfile.getPhone());
                    System.out.println("Address     : " + empProfile.getAddress());
                    System.out.println("Emergency   : " + empProfile.getEmergencyContact());
                    System.out.println("Manager ID  : " + empProfile.getManagerId());
                    System.out.println("Status      : " + empProfile.getStatus());

                } else {
                    System.out.println("Employee not found.");
                }

                break;
            }

            case 11: {

                try {
                    ResultSet rs =
                        managerService.viewTeamAttendanceSummary(manager.getEmpId());

                    System.out.println("\n----- Team Attendance Summary -----");
                    System.out.println("EMP_ID | NAME | PRESENT_DAYS | ABSENT_DAYS");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("EMP_ID") + " | " +
                            rs.getString("NAME") + " | " +
                            rs.getInt("PRESENT_DAYS") + " | " +
                            rs.getInt("ABSENT_DAYS")
                        );
                    }

                    if (!found) {
                        System.out.println("No attendance data found.");
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
