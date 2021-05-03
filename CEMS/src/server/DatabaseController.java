package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Database;
import models.Test;

/**
 * Class that handles all of operation sent by client, database controller
 * communicate with server side, and send queries to database.
 * 
 * @author Arikz
 *
 */
public class DatabaseController {
	
	/**
	 * 
	 */
	private Connection conn;
	
	/**
	 * 
	 */
	private Database database;
	
	/**
	 * 
	 */
	private ServerEventListener logListener;

	/**
	 * @param database
	 * @param logListener
	 */
	public DatabaseController(Database database, ServerEventListener logListener) {
		super();
		this.database = database;
		this.logListener = logListener;
	}

	/**
	 * @throws SQLException
	 */
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			logListener.printToLog("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			logListener.printToLog("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + database.getIp() + ":" + database.getPort() + "/"
					+ database.getScheme() + "?serverTimezone=IST", database.getUserName(), database.getPassword());
			logListener.printToLog("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			logListener.printToLog("SQLException: " + ex.getMessage());
			logListener.printToLog("SQLState: " + ex.getSQLState());
			logListener.printToLog("VendorError: " + ex.getErrorCode());
			throw new SQLException();
		}

	}

	/**
	 * @param test
	 * @return
	 */
	public boolean saveTest(Test test) {
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO Test (ID,Subject,Course,DurationTime,PointPerQuestion) VALUES (?,?,?,?,?);");
			pstmt.setString(1, test.getId());
			pstmt.setString(2, test.getSubject());
			pstmt.setString(3, test.getCourse());
			pstmt.setString(4, test.getDuration());
			pstmt.setString(5, test.getPointsPerQuestion());
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				System.out.print("Test Saved Succuessfully");
				return true;
			}

		} catch (SQLException e) {
			System.out.print("Error occurred, test has not been saved ");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @return
	 */
	public List<Test> getTestList() {
		List<Test> tests = new ArrayList<>();
		try {
			Statement st = conn.createStatement();
			String sql = ("SELECT * FROM Test;");
			ResultSet rs;
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("Id");
				String subject = rs.getString("Subject");
				String course = rs.getString("Course");
				String duration = rs.getString("Duration");
				String pointPerQuestion = rs.getString("PointPerQuestion");
				Test test = new Test(id, subject, course, duration, pointPerQuestion);
				tests.add(test);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tests;
	}

	/**
	 * @param givenId
	 * @return
	 */
	public Test getTest(String givenId) {
		try {
			Statement st = conn.createStatement();
			String sql = ("SELECT * FROM Test WHERE id=" + givenId + ";");
			ResultSet rs;
			rs = st.executeQuery(sql);
			if (rs.next()) {
				String id = rs.getString("Id");
				String subject = rs.getString("Subject");
				String course = rs.getString("Course");
				String duration = rs.getString("Duration");
				String pointPerQuestion = rs.getString("PointPerQuestion");
				Test test = new Test(id, subject, course, duration, pointPerQuestion);
				return test;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param testToEdit
	 * @return
	 */
	public boolean updateTest(Test testToEdit) {
		PreparedStatement pstmt;
		try {
			String id = testToEdit.getId();
			String subject = testToEdit.getSubject();
			String course = testToEdit.getCourse();
			String duration = testToEdit.getDuration();
			String ppq = testToEdit.getPointsPerQuestion();
			String query = "UPDATE test SET Subject=?,Course=?,Duration=?,PointPerQuestion=? WHERE id=?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, subject);
			pstmt.setString(2, course);
			pstmt.setString(3, duration);
			pstmt.setString(4, ppq);
			pstmt.setString(5, id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
