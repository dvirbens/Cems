package server;

//
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

public class DatabaseController {
	private Connection conn;
	private Database database;
	private LogEventListener logListener;

	public static final int LOAD_TEST = 0;
	public static final int LOAD_QUESTION = 1;
	public static final int LOAD_TEST_LIST = 2;
	public static final int LOAD_QUESTION_LIST = 3;

	public DatabaseController(Database database, LogEventListener logListener) {
		super();
		this.database = database;
		this.logListener = logListener;
	}

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

	public List<Test> getTests() {
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

}
