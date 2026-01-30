package com.revworkforce.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revworkforce.util.DBConnection;

public class LeaveDAO {

	private static final Logger logger = LogManager.getLogger(LeaveDAO.class);

	public boolean applyLeave(int empId, String leaveType,
			java.sql.Date fromDate, java.sql.Date toDate, String reason) {

		boolean applied = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("INSERT INTO LEAVE_REQUEST "
							+ "(LEAVE_ID, EMP_ID, LEAVE_TYPE, FROM_DATE, TO_DATE, STATUS, REASON, MANAGER_COMMENT) "
							+ "VALUES (LEAVE_SEQ.NEXTVAL, ?, ?, ?, ?, 'PENDING', ?, NULL)");

			ps.setInt(1, empId);
			ps.setString(2, leaveType);
			ps.setDate(3, fromDate);
			ps.setDate(4, toDate);
			ps.setString(5, reason);

			int count = ps.executeUpdate();

			if (count > 0) {

				applied = true;
				logger.info("Leave applied by: " + empId);

				// Fetch manager ID of employee
				PreparedStatement ps2 = con
						.prepareStatement("SELECT MANAGER_ID FROM EMPLOYEE WHERE EMP_ID=?");

				ps2.setInt(1, empId);

				ResultSet rs = ps2.executeQuery();

				if (rs.next()) {

					int managerId = rs.getInt("MANAGER_ID");

					// Send notification to manager
					addNotification(managerId, "Employee " + empId
							+ " applied for a leave request.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return applied;
	}

	public ResultSet getPendingLeaves(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT L.LEAVE_ID, L.EMP_ID, L.LEAVE_TYPE, L.FROM_DATE, L.TO_DATE, L.REASON "
							+ "FROM LEAVE_REQUEST L JOIN EMPLOYEE E ON L.EMP_ID = E.EMP_ID "
							+ "WHERE E.MANAGER_ID = ? AND L.STATUS = 'PENDING'");

			ps.setInt(1, managerId);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean updateLeaveStatus(int leaveId, String status, String comment) {

		boolean updated = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("UPDATE LEAVE_REQUEST SET STATUS=?, MANAGER_COMMENT=? WHERE LEAVE_ID=?");

			ps.setString(1, status);
			ps.setString(2, comment);
			ps.setInt(3, leaveId);

			int count = ps.executeUpdate();

			if (count > 0) {
				updated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return updated;
	}

	public ResultSet getEmployeeLeaves(int empId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT LEAVE_ID, LEAVE_TYPE, FROM_DATE, TO_DATE, STATUS, REASON "
							+ "FROM LEAVE_REQUEST WHERE EMP_ID=?");

			ps.setInt(1, empId);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getHolidays() {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT HOLIDAY_DATE, HOLIDAY_NAME FROM HOLIDAYS ORDER BY HOLIDAY_DATE");

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean cancelLeave(int leaveId, int empId) {

		boolean cancelled = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("DELETE FROM LEAVE_REQUEST WHERE LEAVE_ID=? AND EMP_ID=? AND STATUS='PENDING'");

			ps.setInt(1, leaveId);
			ps.setInt(2, empId);

			int count = ps.executeUpdate();

			if (count > 0) {
				cancelled = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cancelled;
	}

	public void addNotification(int empId, String message) {

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("INSERT INTO NOTIFICATIONS VALUES (NOTIF_SEQ.NEXTVAL, ?, ?, 'UNREAD', SYSDATE)");

			ps.setInt(1, empId);
			ps.setString(2, message);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getNotifications(int empId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT NOTIF_ID, MESSAGE, STATUS FROM NOTIFICATIONS WHERE EMP_ID=? ORDER BY CREATED_DATE DESC");

			ps.setInt(1, empId);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean submitReview(int empId, String deliverables,
			String accomplishments, String improvements, int rating) {

		boolean submitted = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("INSERT INTO PERFORMANCE_REVIEW VALUES "
							+ "(REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, ?, NULL, NULL, 'SUBMITTED')");

			ps.setInt(1, empId);
			ps.setString(2, deliverables);
			ps.setString(3, accomplishments);
			ps.setString(4, improvements);
			ps.setInt(5, rating);

			int count = ps.executeUpdate();

			if (count > 0) {
				submitted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return submitted;
	}

	public ResultSet getMyReview(int empId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT DELIVERABLES, ACCOMPLISHMENTS, IMPROVEMENTS, SELF_RATING, STATUS, MANAGER_FEEDBACK, MANAGER_RATING "
							+ "FROM PERFORMANCE_REVIEW WHERE EMP_ID=?");

			ps.setInt(1, empId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean addGoal(int empId, String desc, java.sql.Date deadline,
			String priority, String metrics) {

		boolean added = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("INSERT INTO GOALS VALUES (GOAL_SEQ.NEXTVAL, ?, ?, ?, ?, ?, 0)");

			ps.setInt(1, empId);
			ps.setString(2, desc);
			ps.setDate(3, deadline);
			ps.setString(4, priority);
			ps.setString(5, metrics);

			int count = ps.executeUpdate();

			if (count > 0) {
				added = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return added;
	}

	public ResultSet getGoals(int empId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT GOAL_ID, GOAL_DESC, DEADLINE, PRIORITY, SUCCESS_METRICS, PROGRESS "
							+ "FROM GOALS WHERE EMP_ID=?");

			ps.setInt(1, empId);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean updateGoalProgress(int goalId, int progress) {

		boolean updated = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("UPDATE GOALS SET PROGRESS=? WHERE GOAL_ID=?");

			ps.setInt(1, progress);
			ps.setInt(2, goalId);

			int count = ps.executeUpdate();

			if (count > 0) {
				updated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return updated;
	}

	public ResultSet getSubmittedReviews(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT R.REVIEW_ID, R.EMP_ID, R.DELIVERABLES, R.ACCOMPLISHMENTS, "
							+ "R.IMPROVEMENTS, R.SELF_RATING "
							+ "FROM PERFORMANCE_REVIEW R JOIN EMPLOYEE E "
							+ "ON R.EMP_ID = E.EMP_ID WHERE E.MANAGER_ID=? AND R.STATUS='SUBMITTED'");

			ps.setInt(1, managerId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean addManagerFeedback(int reviewId, String feedback, int rating) {

		boolean updated = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("UPDATE PERFORMANCE_REVIEW SET MANAGER_FEEDBACK=?, "
							+ "MANAGER_RATING=?, STATUS='REVIEWED' WHERE REVIEW_ID=?");

			ps.setString(1, feedback);
			ps.setInt(2, rating);
			ps.setInt(3, reviewId);

			int count = ps.executeUpdate();

			if (count > 0)
				updated = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return updated;
	}

	public ResultSet getTeamMembers(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT EMP_ID, NAME, EMAIL, ROLE, PHONE, STATUS "
							+ "FROM EMPLOYEE WHERE MANAGER_ID=?");

			ps.setInt(1, managerId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getTeamLeaveBalances(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT E.EMP_ID, E.NAME, L.CL, L.SL, L.PL "
							+ "FROM EMPLOYEE E JOIN LEAVE_BALANCE L "
							+ "ON E.EMP_ID = L.EMP_ID "
							+ "WHERE E.MANAGER_ID=?");

			ps.setInt(1, managerId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getTeamGoals(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT G.GOAL_ID, G.EMP_ID, G.GOAL_DESC, G.DEADLINE, "
							+ "G.PRIORITY, G.PROGRESS "
							+ "FROM GOALS G JOIN EMPLOYEE E "
							+ "ON G.EMP_ID = E.EMP_ID "
							+ "WHERE E.MANAGER_ID=?");

			ps.setInt(1, managerId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getTeamPerformanceSummary(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT E.EMP_ID, E.NAME, R.SELF_RATING, "
							+ "R.MANAGER_RATING, R.STATUS "
							+ "FROM EMPLOYEE E JOIN PERFORMANCE_REVIEW R "
							+ "ON E.EMP_ID = R.EMP_ID "
							+ "WHERE E.MANAGER_ID=?");

			ps.setInt(1, managerId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getTeamLeaveCalendar(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT E.EMP_ID, E.NAME, L.FROM_DATE, L.TO_DATE, "
							+ "L.LEAVE_TYPE, L.STATUS "
							+ "FROM LEAVE_REQUEST L JOIN EMPLOYEE E "
							+ "ON L.EMP_ID = E.EMP_ID "
							+ "WHERE E.MANAGER_ID=? AND L.STATUS='APPROVED' "
							+ "ORDER BY L.FROM_DATE");

			ps.setInt(1, managerId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getTeamHierarchy(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT EMP_ID, NAME FROM EMPLOYEE WHERE MANAGER_ID=?");

			ps.setInt(1, managerId);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getTeamAttendanceSummary(int managerId) {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT E.EMP_ID, E.NAME, "
							+ "SUM(CASE WHEN A.STATUS='PRESENT' THEN 1 ELSE 0 END) AS PRESENT_DAYS, "
							+ "SUM(CASE WHEN A.STATUS='ABSENT' THEN 1 ELSE 0 END) AS ABSENT_DAYS "
							+ "FROM EMPLOYEE E JOIN ATTENDANCE A "
							+ "ON E.EMP_ID = A.EMP_ID "
							+ "WHERE E.MANAGER_ID=? "
							+ "GROUP BY E.EMP_ID, E.NAME");

			ps.setInt(1, managerId);

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean revokeLeave(int leaveId) {

		boolean revoked = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("DELETE FROM LEAVE_REQUEST WHERE LEAVE_ID=? AND STATUS='APPROVED'");

			ps.setInt(1, leaveId);

			if (ps.executeUpdate() > 0)
				revoked = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return revoked;
	}

	public ResultSet getAllLeaves() {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT LEAVE_ID, EMP_ID, LEAVE_TYPE, FROM_DATE, TO_DATE, STATUS FROM LEAVE_REQUEST");

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getLeaveReportByDepartment() {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT E.DEPARTMENT, COUNT(L.LEAVE_ID) AS TOTAL_LEAVES "
							+ "FROM EMPLOYEE E JOIN LEAVE_REQUEST L ON E.EMP_ID = L.EMP_ID "
							+ "GROUP BY E.DEPARTMENT");

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getLeaveReportByEmployee() {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT EMP_ID, COUNT(LEAVE_ID) AS TOTAL_LEAVES "
							+ "FROM LEAVE_REQUEST GROUP BY EMP_ID");

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean addLeavePolicy(String type, int maxDays, String paid,
			String carry) {

		boolean added = false;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("INSERT INTO LEAVE_POLICY VALUES (POLICY_SEQ.NEXTVAL, ?, ?, ?, ?)");

			ps.setString(1, type);
			ps.setInt(2, maxDays);
			ps.setString(3, paid);
			ps.setString(4, carry);

			if (ps.executeUpdate() > 0)
				added = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return added;
	}

	public ResultSet getLeavePolicies() {

		ResultSet rs = null;

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con
					.prepareStatement("SELECT LEAVE_TYPE, MAX_DAYS, IS_PAID, CARRY_FORWARD FROM LEAVE_POLICY");

			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

}
