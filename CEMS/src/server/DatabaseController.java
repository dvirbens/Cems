package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.SubjectCourseCollection;
import models.Database;
import models.Question;
import models.Exam;
import models.User;
import models.User.ErrorType;
import models.User.UserType;

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
	public boolean saveTest(Exam test) {
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
			System.err.print("Error occurred, test has not been saved ");
		}
		return false;
	}

	/**
	 * Saving new question on database using appropriate query by prepared
	 * statement.
	 * 
	 * @param question that's needed to be save on database
	 * @return boolean value{true = question saved successfully,false = can't save
	 *         test}
	 */
	public boolean saveQuestion(Question question) {
		int questionID;
		String courseID = Server.getSubjectCollection().getSubjectMap().get(question.getSubject());
		String questionLastID = getQuestionLastId(question.getSubject());
		if (questionLastID == null) {
			questionID = 0;
		} else {
			questionID = Integer.valueOf(questionLastID);
		}
		String finalID = courseID + String.format("%03d", ++questionID);
		PreparedStatement prepareStatement;
		try {
			prepareStatement = conn.prepareStatement("INSERT INTO Question VALUES (?,?,?,?,?,?,?,?,?,?);");
			prepareStatement.setString(1, finalID);
			prepareStatement.setString(2, question.getSubject());
			prepareStatement.setString(3, question.getCourse());
			prepareStatement.setString(4, question.getDetails());
			prepareStatement.setString(5, question.getAnswer1());
			prepareStatement.setString(6, question.getAnswer2());
			prepareStatement.setString(7, question.getAnswer3());
			prepareStatement.setString(8, question.getAnswer4());
			prepareStatement.setInt(9, question.getCorrectAnswer());
			prepareStatement.setString(10, question.getTeacherName());
			int resultSet = prepareStatement.executeUpdate();
			if (resultSet == 1) {
				System.out.print("Question Saved Succuessfully");
				return true;
			}

		} catch (SQLException e) {
			System.err.print("Error occurred, Question has not been saved ");
		}
		return false;
	}

	private String getQuestionLastId(String subject) {
		try {
			Statement statement = conn.createStatement();
			String sql = ("SELECT SUBSTRING(questionID, 3, 5) questionID FROM question WHERE Subject=\"" + subject
					+ "\" ORDER BY questionID DESC LIMIT 1;");
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				String id = resultSet.getString("questionID");
				return id;
			}
		} catch (SQLException e) {
			return null;
		}

		return null;
	}

	/**
	 * Get all of test list from database test table, using appropriate query and
	 * SQL statement variable.
	 * 
	 * @return list of all test on database
	 */
	public List<Exam> getTestList() {
		List<Exam> tests = new ArrayList<>();
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
				Exam test = new Exam(id, subject, course, duration, pointPerQuestion);
				tests.add(test);
			}
		} catch (SQLException e) {
			System.err.println("Test list not found");
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
	public Exam getTest(String givenId) {
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
				Exam test = new Exam(id, subject, course, duration, pointPerQuestion);
				return test;
			}
		} catch (SQLException e) {
			System.err.println("Test not found");
		}

		return null;
	}

	/**
	 * Get specific user by given user name and password from database test table,
	 * using appropriate query and SQL statement.
	 * 
	 * @param userName specific test id that's store on database
	 * @return password requested
	 */
	public User getUser(String userID, String password) {
		User user = null;
		try {
			Statement statement = conn.createStatement();
			String sql = ("SELECT * FROM User WHERE userID=" + userID + ";");
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				String passwordDB = resultSet.getString("password");
				UserType type = UserType.valueOf(resultSet.getString("type"));

				if (password.equals(passwordDB))
					user = new User(userID, password, firstName, lastName, email, type);
				else
					user = new User(ErrorType.WRONG_PASSWORD);

			} else
				user = new User(ErrorType.WRONG_ID);

		} catch (SQLException e) {
			System.err.println("User not found");
			return user;
		}

		return user;
	}

	/**
	 * Updating test on test database table, using SQL statement with appropriate
	 * query, and given test to edit.
	 * 
	 * @param testToEdit new test details to replace on database
	 * @return boolean value{true = test replaced successfully,false = can't edit
	 *         test}
	 */
	public boolean updateTest(Exam testToEdit) {
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
			System.err.print("Error occurred, test has not been updated ");
			return false;
		}
	}

	public SubjectCourseCollection updateSubjectCollection(SubjectCourseCollection subjectCollection) {
		subjectCollection = new SubjectCourseCollection();
		Map<String, String> subjectCodeMap = new HashMap<>();
		Map<String, List<String>> courseListMap = new HashMap<>();
		List<String> subjectList = new ArrayList<>();
		List<String> courseList = new ArrayList<>();
		subjectList.add("All Subject");
		courseList.add("All Courses");
		try {
			Statement statement = conn.createStatement();
			String subjectSQL = "SELECT * FROM Subject";
			ResultSet resultSetSubject = statement.executeQuery(subjectSQL);

			while (resultSetSubject.next()) {
				String id = resultSetSubject.getString("subjectID");
				String subject = resultSetSubject.getString("subjectName");
				subjectCodeMap.put(subject, id);
				subjectList.add(subject);
				String courseSQL = "SELECT (courseName) FROM Course WHERE subjectID=" + id + ";";
				Statement statement2 = conn.createStatement();
				ResultSet resultSetCourse = statement2.executeQuery(courseSQL);
				List<String> subjectCourseList = new ArrayList<>();

				while (resultSetCourse.next()) {
					String course = resultSetCourse.getString("courseName");
					subjectCourseList.add(course);
					courseList.add(course);
				}
				courseListMap.put(subject, subjectCourseList);
			}
			courseListMap.put("All Subject", courseList);
			subjectCollection.setSubjects(subjectList);
			subjectCollection.setCourses(courseList);
			subjectCollection.setCourseListMap(courseListMap);
			subjectCollection.setSubjectMap(subjectCodeMap);

		} catch (SQLException e) {
			System.err.println("Can't collect list");
			return subjectCollection;
		}

		return subjectCollection;
	}

	public List<Question> getQuestionList(String course) {
		List<Question> questionList = new ArrayList<>();
		
		try {
			Statement statement = conn.createStatement();
			String courseQuery= "SELECT * FROM Question WHERE Course=\""+course+"\";";
			ResultSet rsQuestionOfCourse=statement.executeQuery(courseQuery);
			while(rsQuestionOfCourse.next())
			{
				String questionID=rsQuestionOfCourse.getString("questionID");
				String teacherName=rsQuestionOfCourse.getString("TeacherName");
				String subjectRet=rsQuestionOfCourse.getString("Subject");
				String courseRet=rsQuestionOfCourse.getString("Course");
				String details=rsQuestionOfCourse.getString("Details");
				String answer1=rsQuestionOfCourse.getString("Answer1");
				String answer2=rsQuestionOfCourse.getString("Answer2");
				String answer3=rsQuestionOfCourse.getString("Answer3");
				String answer4=rsQuestionOfCourse.getString("Answer4");
				int correctAnswer=rsQuestionOfCourse.getInt("CorrectAnswer");
				Question q=new Question(questionID, teacherName, subjectRet, courseRet, details, answer1, answer2, answer3, answer4, correctAnswer);
				questionList.add(q);
				System.out.println(questionList);
			}
			
		} catch (SQLException e) {
			System.err.println("ERROR #22132 - ERROR LOADING QUSETION FROM DATABASE");
		}

		return questionList;
	}

}
