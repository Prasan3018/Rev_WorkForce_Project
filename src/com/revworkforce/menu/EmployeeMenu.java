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
            System.out.println("6. View My Leave Applications");
            System.out.println("7. View Holiday Calendar");
            System.out.println("8. Cancel Pending Leave");
            System.out.println("9. View Notifications");
            System.out.println("10. Submit Performance Review");
            System.out.println("11. View Performance Review Status");
            System.out.println("12. Add Goal");
            System.out.println("13. View Goals");
            System.out.println("14. Update Goal Progress");

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

                System.out.print("Enter emergency contact: ");
                String emergency = sc.nextLine();

                Employee updatedEmp = new Employee();
                updatedEmp.setEmpId(emp.getEmpId());
                updatedEmp.setPhone(phone);
                updatedEmp.setAddress(address);
                updatedEmp.setEmergencyContact(emergency);

                boolean result = employeeService.updateProfile(updatedEmp);

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
                System.out.println("Privilege Leave : " + balance[3]);
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
                
            case 6: {

                try {
                    java.sql.ResultSet rs =
                            employeeService.viewMyLeaves(emp.getEmpId());

                    System.out.println("\n----- My Leave Applications -----");
                    System.out.println("LEAVE_ID | TYPE | FROM | TO | STATUS | REASON");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("LEAVE_ID") + " | " +
                            rs.getString("LEAVE_TYPE") + " | " +
                            rs.getDate("FROM_DATE") + " | " +
                            rs.getDate("TO_DATE") + " | " +
                            rs.getString("STATUS") + " | " +
                            rs.getString("REASON")
                        );
                    }

                    if (!found) {
                        System.out.println("No leave applications found.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            }
            
            case 7: {

                try {
                    java.sql.ResultSet rs =
                            employeeService.viewHolidays();

                    System.out.println("\n===== Holiday Calendar =====");
                    System.out.println("DATE | HOLIDAY NAME");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getDate("HOLIDAY_DATE") + " | " +
                            rs.getString("HOLIDAY_NAME")
                        );
                    }

                    if (!found) {
                        System.out.println("No holidays found.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            }
            
            case 8: {

                try {
                    java.sql.ResultSet rs =
                            employeeService.viewMyLeaves(emp.getEmpId());

                    System.out.println("\n--- Pending Leaves ---");
                    System.out.println("LEAVE_ID | TYPE | FROM | TO | STATUS");

                    boolean found = false;

                    while (rs.next()) {

                        if (rs.getString("STATUS").equalsIgnoreCase("PENDING")) {

                            found = true;

                            System.out.println(
                                rs.getInt("LEAVE_ID") + " | " +
                                rs.getString("LEAVE_TYPE") + " | " +
                                rs.getDate("FROM_DATE") + " | " +
                                rs.getDate("TO_DATE") + " | " +
                                rs.getString("STATUS")
                            );
                        }
                    }

                    if (!found) {
                        System.out.println("No pending leaves to cancel.");
                        break;
                    }

                    System.out.print("\nEnter Leave ID to cancel: ");
                    int leaveId = sc.nextInt();

                    boolean cancelled =
                            employeeService.cancelLeave(leaveId, emp.getEmpId());

                    if (cancelled) {
                        System.out.println("Leave cancelled successfully.");
                    } else {
                        System.out.println("Unable to cancel leave (maybe already approved).");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            }
            
            case 9: {

                try {
                    java.sql.ResultSet rs =
                            employeeService.viewNotifications(emp.getEmpId());

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

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            }
            
            case 10: {

                sc.nextLine();

                System.out.print("Enter key deliverables: ");
                String deliverables = sc.nextLine();

                System.out.print("Enter major accomplishments: ");
                String accomplishments = sc.nextLine();

                System.out.print("Enter areas of improvement: ");
                String improvements = sc.nextLine();

                System.out.print("Enter self rating (1-5): ");
                int rating = sc.nextInt();

                boolean submitted =
                    employeeService.submitPerformanceReview(
                        emp.getEmpId(),
                        deliverables,
                        accomplishments,
                        improvements,
                        rating
                    );

                if (submitted) {
                    System.out.println("Performance review submitted successfully.");
                } else {
                    System.out.println("Failed to submit review.");
                }

                break;
            }

            case 11: {

                try {
                    java.sql.ResultSet rs =
                            employeeService.viewMyPerformanceReview(emp.getEmpId());

                    System.out.println("\n----- My Performance Review -----");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("Deliverables     : " + rs.getString("DELIVERABLES"));
                        System.out.println("Accomplishments : " + rs.getString("ACCOMPLISHMENTS"));
                        System.out.println("Improvements    : " + rs.getString("IMPROVEMENTS"));
                        System.out.println("Self Rating     : " + rs.getInt("SELF_RATING"));
                        System.out.println("Status          : " + rs.getString("STATUS"));
                        System.out.println("Manager Feedback: " + rs.getString("MANAGER_FEEDBACK"));
                        System.out.println("Manager Rating  : " + rs.getInt("MANAGER_RATING"));
                    }

                    if (!found) {
                        System.out.println("No performance review found.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            }


            case 12: {

                sc.nextLine();

                System.out.print("Enter goal description: ");
                String desc = sc.nextLine();

                System.out.print("Enter deadline (yyyy-mm-dd): ");
                String dateStr = sc.nextLine();

                System.out.print("Enter priority (High/Medium/Low): ");
                String priority = sc.nextLine();

                System.out.print("Enter success metrics: ");
                String metrics = sc.nextLine();

                java.sql.Date deadline = java.sql.Date.valueOf(dateStr);

                boolean added = employeeService.addGoal(
                    emp.getEmpId(), desc, deadline, priority, metrics);

                if (added) {
                    System.out.println("Goal added successfully.");
                } else {
                    System.out.println("Failed to add goal.");
                }

                break;
            }

            case 13: {

                try {
                    java.sql.ResultSet rs =
                            employeeService.viewGoals(emp.getEmpId());

                    System.out.println("\n----- My Goals -----");
                    System.out.println("ID | DESCRIPTION | DEADLINE | PRIORITY | PROGRESS | METRICS");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            rs.getInt("GOAL_ID") + " | " +
                            rs.getString("GOAL_DESC") + " | " +
                            rs.getDate("DEADLINE") + " | " +
                            rs.getString("PRIORITY") + " | " +
                            rs.getInt("PROGRESS") + "% | " +
                            rs.getString("SUCCESS_METRICS")
                        );
                    }

                    if (!found) {
                        System.out.println("No goals found.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            }
            
            case 14: {

                System.out.print("Enter Goal ID: ");
                int goalId = sc.nextInt();

                System.out.print("Enter progress percentage (0-100): ");
                int progress = sc.nextInt();

                boolean updated =
                    employeeService.updateGoalProgress(goalId, progress);

                if (updated) {
                    System.out.println("Goal progress updated successfully.");
                } else {
                    System.out.println("Failed to update progress.");
                }

                break;
            }



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
        System.out.println("Emergency    : " + e.getEmergencyContact());
        System.out.println("Manager ID  : " + e.getManagerId());
        System.out.println("Status      : " + e.getStatus());
    }
}
