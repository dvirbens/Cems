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
 * communicate with server side, and send queries to SQL database.
 * 
 * @author Arikz ,Dvir ben simon
 *
 */
public class DatabaseController {

	/**
	 * Create connection instance to SQL Database
	 */
	private Connection conn;

	/**
	 * Database details instance, in order to store all SQL connection details
	 */
	private Database database;

	/**
	 * Server event listener in order to handle events that occurred by database,
	 * send it back to server log.
	 */
	private ServerEventListener logListener;

	/**
	 * @param database    store database details instance{ip,port,etc..} on class
	 * @param logListener store event listener on class
	 */
	public DatabaseController(Database database, ServerEventListener logListener) {
		super();
		this.database = database;
		this.logListener = logListener;
	}

	/**
	 * Creating new connection to SQL database driver by using database details
	 * instance. In case of connection problem terminate the connection.
	 * 
	 * @throws SQLException in case of connectivity problem.
	 */
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			logListener.printToLog("Driver definition succeed");
		} catch (Exception ex) {
			logListener.printToLog("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + database.getIp() + ":" + database.getPort() + "/"
					+ database.getScheme() + "?serverTimezone=IST", database.getUserName(), database.getPassword());
			logListener.printToLog("SQL connection succeed");
		} catch (SQLException ex) {
			logListener.printToLog("SQLException: " + ex.getMessage());
			logListener.printToLog("SQLState: " + ex.getSQLState());
			logListener.printToLog("VendorError: " + ex.getErrorCode());
			throw new SQLException();
		}

	}

	/**
	 * Saving new test on database using appropriate query by prepared statement.
	 * 
	 * @param test that's needed to be save on database
	 * @return boolean value{true = test saved successfully,false = can't save test}
	 */
	public boolean saveTest(Test test) {
		PreparedStatement prepareStatement;

		try {
			prepareStatement = conn.prepareStatement(
					"INSERT INTO Test (ID,Subject,Course,DurationTime,PointPerQuestion) VALUES (?,?,?,?,?);");
			prepareStatement.setString(1, test.getId());
			prepareStatement.setString(2, test.getSubject());
			prepareStatement.setString(3, test.getCourse());
			prepareStatement.setString(4, test.getDuration());
			prepareStatement.setString(5, test.getPointsPerQuestion());
			int resultSet = prepareStatement.executeUpdate();
			if (resultSet == 1) {
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
	 * Get all of test list from database test table, using appropriate query and
	 * SQL statement variable.
	 * 
	 * @return list of all test on database
	 */
	public List<Test> getTestList() {
		List<Test> tests = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = ("SELECT * FROM Test;");
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String id = resultSet.getString("Id");
				String subject = resultSet.getString("Subject");
				String course = resultSet.getString("Course");
				String duration = resultSet.getString("Duration");
				String pointPerQuestion = resultSet.getString("PointPerQuestion");
				Test test = new Test(id, subject, course, duration, pointPerQuestion);
				tests.add(test);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tests;
	}

	/**
	 * Get specific test by given id key from database test table, using appropriate
	 * query and SQL statement.
	 * 
	 * @param givenId specific test id that's store on database
	 * @return test requested
	 */
	public Test getTest(String givenId) {
		try {
			Statement statement = conn.createStatement();
			String sql = ("SELECT * FROM Test WHERE id=" + givenId + ";");
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				String id = resultSet.getString("Id");
				String subject = resultSet.getString("Subject");
				String course = resultSet.getString("Course");
				String duration = resultSet.getString("Duration");
				String pointPerQuestion = resultSet.getString("PointPerQuestion");
				Test test = new Test(id, subject, course, duration, pointPerQuestion);
				return test;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Updating test on test database table, using SQL statement with appropriate
	 * query, and given test to edit.
	 * 
	 * @param testToEdit new test details to replace on database
	 * @return boolean value{true = test replaced successfully,false = can't edit
	 *         test}
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
