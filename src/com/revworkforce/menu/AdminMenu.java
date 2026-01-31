package com.revworkforce.menu;

import com.revworkforce.util.SessionUtil;

import java.util.Scanner;
import com.revworkforce.service.AdminService;
import com.revworkforce.service.SystemConfigService;

import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;
import java.sql.ResultSet;

public class AdminMenu {

	private EmployeeService employeeService = new EmployeeService();
	private AdminService adminService = new AdminService();
	private SystemConfigService systemService = new SystemConfigService();

	public void showMenu() {

		Scanner sc = new Scanner(System.in);

		while (true) {
			
			if (SessionUtil.isSessionExpired()) {
                System.out.println("Session expired due to inactivity.");
                return;
            }
            SessionUtil.updateActivity();
			
			
			System.out.println("\n===== Admin Menu =====");
			System.out.println("1. Add Employee");
			System.out.println("2. Update Employee");
			System.out.println("3. Deactivate Employee");
			System.out.println("4. Reactivate Employee");
			System.out.println("5. Assign Leave Balance");
			System.out.println("6. View All Employees");
			System.out.println("7. Change Reporting Manager");
			System.out.println("8. Search Employee");
			System.out.println("9. Adjust Leave Balance");
			System.out.println("10. View All Leave Applications");
			System.out.println("11. Revoke Leave");
			System.out.println("12. Leave Report By Department");
			System.out.println("13. Leave Report By Employee");
			System.out.println("14. Add Leave Policy");
			System.out.println("15. View Leave Policies");
			System.out.println("16. View Audit Logs");
			System.out.println("17. Add Department");
			System.out.println("18. View Departments");
			System.out.println("19. Add Designation");
			System.out.println("20. View Designations");
			System.out.println("21. Add Performance Review Cycle");
			System.out.println("22. View Review Cycles");
			System.out.println("23. Add System Policy");
			System.out.println("24. View System Policies");
			System.out.println("25. Post System Announcement");


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

				System.out.print("Enter security question: ");
				emp.setSecurityQuestion(sc.nextLine());

				System.out.print("Enter security answer: ");
				emp.setSecurityAnswer(sc.nextLine());

				boolean result = employeeService.addEmployee(emp);

				if (result) {
					System.out.println("Employee added successfully.");
				} else {
					System.out.println("Failed to add employee.");
				}

				break;

			case 2:
				sc.nextLine();

				Employee upd = new Employee();

				System.out.print("Employee ID: ");
				upd.setEmpId(sc.nextInt());
				sc.nextLine();

				System.out.print("New Name: ");
				upd.setName(sc.nextLine());

				System.out.print("New Email: ");
				upd.setEmail(sc.nextLine());

				System.out.print("New Phone: ");
				upd.setPhone(sc.nextLine());

				System.out.print("New Address: ");
				upd.setAddress(sc.nextLine());

				System.out.print("New Manager ID: ");
				upd.setManagerId(sc.nextInt());

				boolean updated = employeeService.updateEmployee(upd);

				System.out.println(updated ? "Updated successfully"
						: "Update failed");
				break;

			case 3:
				System.out.print("Enter Employee ID: ");
				int deId = sc.nextInt();

				boolean deact = employeeService.updateEmployeeStatus(deId,
						"INACTIVE");

				System.out.println(deact ? "Employee deactivated" : "Failed");
				break;

			case 4:
				System.out.print("Enter Employee ID: ");
				int acId = sc.nextInt();

				boolean act = employeeService.updateEmployeeStatus(acId,
						"ACTIVE");

				System.out.println(act ? "Employee activated" : "Failed");
				break;

			case 5:
				System.out.print("Enter Employee ID: ");
				int empId = sc.nextInt();

				System.out.print("Enter Casual Leave: ");
				int cl = sc.nextInt();

				System.out.print("Enter Sick Leave: ");
				int sl = sc.nextInt();

				System.out.print("Enter Paid Leave: ");
				int pl = sc.nextInt();

				System.out.print("Enter Privilege Leave: ");
				int prl = sc.nextInt();

				boolean assigned = employeeService.assignLeaveBalance(empId,
						cl, sl, pl, prl);

				if (assigned) {
					System.out.println("Leave balance assigned successfully.");
				} else {
					System.out.println("Failed to assign leave balance.");
				}
				break;

			case 6:
				try {
					ResultSet rs = employeeService.viewAllEmployees();

					System.out
							.println("\nEMP_ID | NAME | EMAIL | ROLE | PHONE | MANAGER_ID | STATUS");

					while (rs.next()) {
						System.out.println(rs.getInt("EMP_ID") + " | "
								+ rs.getString("NAME") + " | "
								+ rs.getString("EMAIL") + " | "
								+ rs.getString("ROLE") + " | "
								+ rs.getString("PHONE") + " | "
								+ rs.getInt("MANAGER_ID") + " | "
								+ rs.getString("STATUS"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 7:
				System.out.print("Employee ID: ");
				int eid = sc.nextInt();

				System.out.print("New Manager ID: ");
				int mid = sc.nextInt();

				boolean changed = employeeService.changeManager(eid, mid);

				System.out.println(changed ? "Manager updated" : "Failed");
				break;

			case 8:
				sc.nextLine();

				System.out.print("Enter keyword: ");
				String key = sc.nextLine();

				try {
					ResultSet rs2 = employeeService.searchEmployees(key);

					System.out.println("EMP_ID | NAME | DEPT | DESIGNATION");

					while (rs2.next()) {
						System.out.println(rs2.getInt("EMP_ID") + " | "
								+ rs2.getString("NAME") + " | "
								+ rs2.getString("DEPARTMENT") + " | "
								+ rs2.getString("DESIGNATION"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 9:
				System.out.print("Employee ID: ");
				int aid = sc.nextInt();

				System.out.print("New CL: ");
				int ncl = sc.nextInt();

				System.out.print("New SL: ");
				int nsl = sc.nextInt();

				System.out.print("New PL: ");
				int npl = sc.nextInt();

				System.out.print("New PRL: ");
				int prl2 = sc.nextInt();

				boolean adj = employeeService.adjustLeaveBalance(aid, ncl, nsl,
						npl, prl2);

				System.out.println(adj ? "Balance updated" : "Failed");
				break;

			case 10:
				try {
					ResultSet lr = employeeService.viewAllLeaves();

					System.out
							.println("LEAVE_ID | EMP_ID | TYPE | FROM | TO | STATUS");

					while (lr.next()) {
						System.out.println(lr.getInt("LEAVE_ID") + " | "
								+ lr.getInt("EMP_ID") + " | "
								+ lr.getString("LEAVE_TYPE") + " | "
								+ lr.getDate("FROM_DATE") + " | "
								+ lr.getDate("TO_DATE") + " | "
								+ lr.getString("STATUS"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 11:
				System.out.print("Enter Leave ID: ");
				int lid = sc.nextInt();

				boolean rev = employeeService.revokeLeave(lid);

				System.out.println(rev ? "Leave revoked" : "Failed");
				break;

			case 12:
				try {
					ResultSet rd = employeeService.getLeaveReportByDepartment();

					System.out.println("DEPARTMENT | TOTAL_LEAVES");

					while (rd.next()) {
						System.out.println(rd.getString("DEPARTMENT") + " | "
								+ rd.getInt("TOTAL_LEAVES"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 13:
				try {
					ResultSet re = employeeService.getLeaveReportByEmployee();

					System.out.println("EMP_ID | TOTAL_LEAVES");

					while (re.next()) {
						System.out.println(re.getInt("EMP_ID") + " | "
								+ re.getInt("TOTAL_LEAVES"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 14:
				sc.nextLine();

				System.out.print("Leave Type: ");
				String type = sc.nextLine();

				System.out.print("Max Days: ");
				int days = sc.nextInt();
				sc.nextLine();

				System.out.print("Is Paid (YES/NO): ");
				String paid = sc.nextLine();

				System.out.print("Carry Forward (YES/NO): ");
				String carry = sc.nextLine();

				boolean added = employeeService.addLeavePolicy(type, days,
						paid, carry);

				System.out.println(added ? "Policy added" : "Failed");
				break;

			case 15:
				try {
					ResultSet rp = employeeService.viewLeavePolicies();

					System.out
							.println("TYPE | MAX_DAYS | PAID | CARRY_FORWARD");

					while (rp.next()) {
						System.out.println(rp.getString("LEAVE_TYPE") + " | "
								+ rp.getInt("MAX_DAYS") + " | "
								+ rp.getString("IS_PAID") + " | "
								+ rp.getString("CARRY_FORWARD"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 16:
				try {
					ResultSet rs = adminService.viewAuditLogs();

					System.out.println("\nLOG_ID | EMP_ID | ACTION | TIME");

					while (rs.next()) {
						System.out.println(rs.getInt("LOG_ID") + " | "
								+ rs.getInt("EMP_ID") + " | "
								+ rs.getString("ACTION") + " | "
								+ rs.getDate("LOG_TIME"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 17:
				System.out.print("Dept ID: ");
				int did = sc.nextInt();
				sc.nextLine();

				System.out.print("Dept Name: ");
				String dname = sc.nextLine();

				System.out
						.println(systemService.addDepartment(did, dname) ? "Department added"
								: "Failed");
				break;

			case 18:
				try {
					ResultSet dr = systemService.viewDepartments();

					System.out.println("ID | NAME");

					while (dr.next()) {
						System.out.println(dr.getInt("DEPT_ID") + " | "
								+ dr.getString("DEPT_NAME"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 19:
				System.out.print("Designation ID: ");
				int desid = sc.nextInt();
				sc.nextLine();

				System.out.print("Designation Name: ");
				String desname = sc.nextLine();

				System.out.print("Department ID: ");
				int dept = sc.nextInt();

				System.out.println(systemService.addDesignation(desid, desname,
						dept) ? "Designation added" : "Failed");
				break;

			case 20:
				try {
					ResultSet ds = systemService.viewDesignations();

					System.out.println("ID | NAME | DEPT");

					while (ds.next()) {
						System.out.println(ds.getInt("DESIG_ID") + " | "
								+ ds.getString("DESIG_NAME") + " | "
								+ ds.getInt("DEPT_ID"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 21:
				System.out.print("Cycle ID: ");
				int cid = sc.nextInt();
				sc.nextLine();

				System.out.print("Cycle Name: ");
				String cname = sc.nextLine();

				System.out.print("Start Date (yyyy-mm-dd): ");
				String sd = sc.nextLine();

				System.out.print("End Date (yyyy-mm-dd): ");
				String ed = sc.nextLine();

				java.sql.Date start = java.sql.Date.valueOf(sd);
				java.sql.Date end = java.sql.Date.valueOf(ed);

				boolean cr = systemService.addReviewCycle(cid, cname, start,
						end);

				System.out.println(cr ? "Cycle added" : "Failed");
				break;

			case 22:
				try {
					ResultSet rc = systemService.viewReviewCycles();

					System.out.println("ID | NAME | START | END | STATUS");

					while (rc.next()) {
						System.out.println(rc.getInt("CYCLE_ID") + " | "
								+ rc.getString("CYCLE_NAME") + " | "
								+ rc.getDate("START_DATE") + " | "
								+ rc.getDate("END_DATE") + " | "
								+ rc.getString("STATUS"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 23:
				System.out.print("Policy ID: ");
				int pid = sc.nextInt();
				sc.nextLine();

				System.out.print("Policy Name: ");
				String pname = sc.nextLine();

				System.out.print("Policy Value: ");
				String pvalue = sc.nextLine();

				boolean pol = systemService.addPolicy(pid, pname, pvalue);

				System.out.println(pol ? "Policy added" : "Failed");
				break;

			case 24:
				try {
					ResultSet pr = systemService.viewPolicies();

					System.out.println("ID | NAME | VALUE");

					while (pr.next()) {
						System.out.println(pr.getInt("POLICY_ID") + " | "
								+ pr.getString("POLICY_NAME") + " | "
								+ pr.getString("POLICY_VALUE"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case 25:
			    sc.nextLine();

			    System.out.print("Enter announcement message: ");
			    String msg = sc.nextLine();

			    boolean sent = employeeService.postAnnouncement(msg);

			    System.out.println(sent ? "Announcement sent to all users" : "Failed");
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
