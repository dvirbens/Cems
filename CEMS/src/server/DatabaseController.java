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
import models.Exam;
import models.ExamProcess;
import models.ExamQuestion;
import models.ExamQuestion.NoteType;
import models.ExecutedExam;
import models.Question;
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
	public boolean saveExam(Exam exam) {
		PreparedStatement prepareStatement;
		int examID;
		String subjectID = Server.getSubjectCollection().getSubjectMap().get(exam.getSubject());
		String courseID = Server.getSubjectCollection().getCourseMap().get(exam.getCourse());
		System.out.println(subjectID);
		System.out.println(courseID);

		String examLastID = getExamLastId(exam.getSubject(), exam.getCourse());

		if (examLastID == null) {
			examID = 0;
		} else {
			examID = Integer.valueOf(examLastID);
		}
		String finalExamID = subjectID + courseID + String.format("%02d", ++examID);
		System.out.println(finalExamID);

		try {
			prepareStatement = conn.prepareStatement("INSERT INTO Exam VALUES (?,?,?,?,?,?);");
			prepareStatement.setString(1, finalExamID);
			prepareStatement.setString(2, exam.getTeacherID());
			prepareStatement.setString(3, exam.getTeacherName());
			prepareStatement.setString(4, exam.getSubject());
			prepareStatement.setString(5, exam.getCourse());
			prepareStatement.setString(6, exam.getDuration());

			int resultSet = prepareStatement.executeUpdate();
			if (resultSet == 1) {
				for (ExamQuestion question : exam.getExamQuestions()) {
					System.out.println(question);
					saveExamQuestion(question, finalExamID);
				}
				System.out.print("Exam Saved Succuessfully");
				return true;
			}

		} catch (SQLException e) {
			System.err.print("Error occurred, Exam has not been saved ");
			return false;
		}

		return true;
	}

	private void saveExamQuestion(ExamQuestion examQuestion, String examID) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = conn.prepareStatement("INSERT INTO ExamQuestion VALUES (?,?,?,?,?);");
			prepareStatement.setString(1, examQuestion.getQuestionID());
			prepareStatement.setString(2, examQuestion.getNote());
			prepareStatement.setInt(3, examQuestion.getPoints());
			prepareStatement.setString(4, examID);
			prepareStatement.setString(5, examQuestion.getNoteType().toString());
			int resultSet = prepareStatement.executeUpdate();
			if (resultSet == 1) {
				System.out.print("Question Saved Succuessfully");
			}

		} catch (SQLException e) {
			System.err.print("Error occurred, Question has not been saved ");
		}

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
		String subjectID = Server.getSubjectCollection().getSubjectMap().get(question.getSubject());
		String questionLastID = getQuestionLastId(question.getSubject());
		if (questionLastID == null) {
			questionID = 0;
		} else {
			questionID = Integer.valueOf(questionLastID);
		}
		String finalID = subjectID + String.format("%03d", ++questionID);
		PreparedStatement prepareStatement;
		try {
			prepareStatement = conn.prepareStatement("INSERT INTO Question VALUES (?,?,?,?,?,?,?,?,?);");
			prepareStatement.setString(1, finalID);
			prepareStatement.setString(2, question.getSubject());
			prepareStatement.setString(3, question.getDetails());
			prepareStatement.setString(4, question.getAnswer1());
			prepareStatement.setString(5, question.getAnswer2());
			prepareStatement.setString(6, question.getAnswer3());
			prepareStatement.setString(7, question.getAnswer4());
			prepareStatement.setInt(8, question.getCorrectAnswer());
			prepareStatement.setString(9, question.getTeacherName());
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

	private String getExamLastId(String subject, String course) {
		try {
			Statement statement = conn.createStatement();
			String sql = ("SELECT SUBSTRING(examID, 5, 6) examID FROM exam WHERE Subject=" + subject + " AND Course="
					+ course + " ORDER BY examID DESC LIMIT 1;");
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				String id = resultSet.getString("examID");
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
	 * 
	 *         public List<Exam> getTestList() { List<Exam> tests = new
	 *         ArrayList<>(); try { Statement statement = conn.createStatement();
	 *         String query = ("SELECT * FROM Test;"); ResultSet resultSet =
	 *         statement.executeQuery(query); while (resultSet.next()) { String id =
	 *         resultSet.getString("Id"); String subject =
	 *         resultSet.getString("Subject"); String course =
	 *         resultSet.getString("Course"); String duration =
	 *         resultSet.getString("Duration"); String pointPerQuestion =
	 *         resultSet.getString("PointPerQuestion"); Exam test = new Exam(id,
	 *         subject, course, duration, pointPerQuestion, null); tests.add(test);
	 *         } } catch (SQLException e) { System.err.println("Test list not
	 *         found"); }
	 * 
	 *         return tests; }
	 */

	/**
	 * Get specific test by given id key from database test table, using appropriate
	 * query and SQL statement.
	 * 
	 * @param givenId specific test id that's store on database
	 * @return test requested
	 * 
	 *         public Exam getTest(String givenId) { try { Statement statement =
	 *         conn.createStatement(); String sql = ("SELECT * FROM Test WHERE id="
	 *         + givenId + ";"); ResultSet resultSet = statement.executeQuery(sql);
	 *         if (resultSet.next()) { String id = resultSet.getString("Id"); String
	 *         subject = resultSet.getString("Subject"); String course =
	 *         resultSet.getString("Course"); String duration =
	 *         resultSet.getString("Duration"); String pointPerQuestion =
	 *         resultSet.getString("PointPerQuestion"); Exam test = new Exam(id,
	 *         subject, course, duration, pointPerQuestion, null); return test; } }
	 *         catch (SQLException e) { System.err.println("Test not found"); }
	 * 
	 *         return null; }
	 * 
	 */

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
	 * 
	 *         public boolean updateTest(Exam testToEdit) { PreparedStatement pstmt;
	 *         try { String id = testToEdit.getId(); String subject =
	 *         testToEdit.getSubject(); String course = testToEdit.getCourse();
	 *         String duration = testToEdit.getDuration(); String ppq =
	 *         testToEdit.getPointsPerQuestion(); String query = "UPDATE test SET
	 *         Subject=?,Course=?,Duration=?,PointPerQuestion=? WHERE id=?;"; pstmt
	 *         = conn.prepareStatement(query); pstmt.setString(1, subject);
	 *         pstmt.setString(2, course); pstmt.setString(3, duration);
	 *         pstmt.setString(4, ppq); pstmt.setString(5, id);
	 *         pstmt.executeUpdate(); return true; } catch (SQLException e) {
	 *         System.err.print("Error occurred, test has not been updated ");
	 *         return false; } }
	 */

	public SubjectCourseCollection updateSubjectCollection(SubjectCourseCollection subjectCollection) {
		subjectCollection = new SubjectCourseCollection();
		Map<String, String> subjectCodeMap = new HashMap<>();
		Map<String, String> courseCodeMap = new HashMap<>();
		Map<String, List<String>> courseListMap = new HashMap<>();
		List<String> subjectList = new ArrayList<>();
		List<String> courseList = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String subjectSQL = "SELECT * FROM Subject";
			ResultSet resultSetSubject = statement.executeQuery(subjectSQL);

			while (resultSetSubject.next()) {
				String subjectID = resultSetSubject.getString("subjectID");
				String subject = resultSetSubject.getString("subjectName");
				subjectCodeMap.put(subject, subjectID);
				subjectList.add(subject);
				String courseSQL = "SELECT * FROM Course WHERE subjectID=" + subjectID + ";";
				Statement statement2 = conn.createStatement();
				ResultSet resultSetCourse = statement2.executeQuery(courseSQL);
				List<String> subjectCourseList = new ArrayList<>();

				while (resultSetCourse.next()) {
					String courseID = resultSetCourse.getString("courseID");
					String course = resultSetCourse.getString("courseName");
					courseCodeMap.put(course, courseID);
					subjectCourseList.add(course);
					courseList.add(course);
				}
				courseListMap.put(subject, subjectCourseList);
			}
			subjectCollection.setSubjects(subjectList);
			subjectCollection.setCourses(courseList);
			subjectCollection.setCourseListMap(courseListMap);
			subjectCollection.setSubjectMap(subjectCodeMap);
			subjectCollection.setCourseMap(courseCodeMap);

		} catch (SQLException e) {
			System.err.println("Can't collect list");
			return subjectCollection;
		}

		return subjectCollection;
	}

	public List<Question> getQuestionList(String subject) {
		List<Question> questionList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String courseQuery = "SELECT * FROM Question WHERE Subject=\"" + subject + "\";";
			ResultSet rsQuestionOfCourse = statement.executeQuery(courseQuery);
			while (rsQuestionOfCourse.next()) {
				String questionID = rsQuestionOfCourse.getString("questionID");
				String teacherName = rsQuestionOfCourse.getString("TeacherName");
				String subjectRet = rsQuestionOfCourse.getString("Subject");
				String details = rsQuestionOfCourse.getString("Details");
				String answer1 = rsQuestionOfCourse.getString("Answer1");
				String answer2 = rsQuestionOfCourse.getString("Answer2");
				String answer3 = rsQuestionOfCourse.getString("Answer3");
				String answer4 = rsQuestionOfCourse.getString("Answer4");
				int correctAnswer = rsQuestionOfCourse.getInt("CorrectAnswer");
				Question q = new Question(questionID, teacherName, subjectRet, details, answer1, answer2, answer3,
						answer4, correctAnswer);
				questionList.add(q);
			}

		} catch (SQLException e) {
			System.err.println("ERROR #22132 - ERROR LOADING QUSETION FROM DATABASE");
		}

		return questionList;
	}

	public List<Exam> getExamListBySubject(String subject) {
		List<Exam> examList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String courseQuery = "SELECT * FROM Exam WHERE Subject=\"" + subject + "\";";
			ResultSet rsQuestionOfCourse = statement.executeQuery(courseQuery);
			while (rsQuestionOfCourse.next()) {
				String examID = rsQuestionOfCourse.getString("examID");
				String teacherID = rsQuestionOfCourse.getString("teacherID");
				String teacherName = rsQuestionOfCourse.getString("teacherName");
				String course = rsQuestionOfCourse.getString("Course");
				String duration = rsQuestionOfCourse.getString("Duration");
				List<ExamQuestion> questionsList = getExamQuestionsList(examID);
				Exam exam = new Exam(examID, teacherID, subject, course, duration, questionsList);
				exam.setTeacherName(teacherName);
				examList.add(exam);
			}

		} catch (SQLException e) {
			System.err.println("ERROR #22132 - ERROR LOADING EXAM FROM DATABASE");
		}

		return examList;
	}

	public List<Exam> getExamListByCourse(String course) {
		List<Exam> examList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String courseQuery = "SELECT * FROM Exam WHERE Course=\"" + course + "\";";
			ResultSet rsQuestionOfCourse = statement.executeQuery(courseQuery);
			while (rsQuestionOfCourse.next()) {
				String examID = rsQuestionOfCourse.getString("examID");
				String teacherID = rsQuestionOfCourse.getString("teacherID");
				String teacherName = rsQuestionOfCourse.getString("teacherName");
				String subject = rsQuestionOfCourse.getString("Subject");
				String duration = rsQuestionOfCourse.getString("Duration");
				List<ExamQuestion> questionsList = getExamQuestionsList(examID);
				Exam exam = new Exam(examID, teacherID, subject, course, duration, questionsList);
				exam.setTeacherName(teacherName);
				examList.add(exam);
			}

		} catch (SQLException e) {
			System.err.println("ERROR #223637 - ERROR LOADING EXAM FROM DATABASE");
		}

		return examList;
	}

	private List<ExamQuestion> getExamQuestionsList(String examID) {

		List<ExamQuestion> examQuestionsList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String examQuestionQuery = "SELECT * FROM ExamQuestion WHERE examID=\"" + examID + "\";";
			ResultSet rsexamQuestion = statement.executeQuery(examQuestionQuery);
			while (rsexamQuestion.next()) {
				String questionID = rsexamQuestion.getString("questionID");
				String note = rsexamQuestion.getString("note");
				int points = rsexamQuestion.getInt("point");
				NoteType type = NoteType.valueOf(rsexamQuestion.getString("type"));

				Statement statement2 = conn.createStatement();
				String questionQuery = "SELECT * FROM Question WHERE questionID=\"" + questionID + "\";";
				ResultSet rsQuestion = statement2.executeQuery(questionQuery);
				if (rsQuestion.next()) {
					String subject = rsQuestion.getString("Subject");
					String details = rsQuestion.getString("Details");
					String answer1 = rsQuestion.getString("Answer1");
					String answer2 = rsQuestion.getString("Answer2");
					String answer3 = rsQuestion.getString("Answer3");
					String answer4 = rsQuestion.getString("Answer4");
					int correctAnswer = rsQuestion.getInt("CorrectAnswer");
					String teacherName = rsQuestion.getString("TeacherName");

					Question question = new Question(questionID, teacherName, subject, details, answer1, answer2,
							answer3, answer4, correctAnswer);
					ExamQuestion examQuestion = new ExamQuestion(question, note, points, type);
					examQuestionsList.add(examQuestion);
				}

			}

		} catch (SQLException e) {
			System.err.println("ERROR #22642 - ERROR LOADING QUESTION FROM DATABASE");
		}

		return examQuestionsList;
	}

	public List<Exam> getExamList() {
		List<Exam> examList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String courseQuery = "SELECT * FROM Exam;";
			ResultSet rsQuestionOfCourse = statement.executeQuery(courseQuery);
			while (rsQuestionOfCourse.next()) {
				String examID = rsQuestionOfCourse.getString("examID");
				String teacherID = rsQuestionOfCourse.getString("teacherID");
				String teacherName = rsQuestionOfCourse.getString("teacherName");
				String subject = rsQuestionOfCourse.getString("Subject");
				String duration = rsQuestionOfCourse.getString("Duration");
				String course = rsQuestionOfCourse.getString("Course");
				List<ExamQuestion> questionsList = getExamQuestionsList(examID);
				Exam exam = new Exam(examID, teacherID, subject, course, duration, questionsList);
				exam.setTeacherName(teacherName);
				examList.add(exam);
			}

		} catch (SQLException e) {
			System.err.println("ERROR #223637 - ERROR LOADING EXAM FROM DATABASE");
		}

		return examList;
	}

	public List<ExecutedExam> getExecutedExamListByID(String studentID) {
		List<ExecutedExam> examList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String courseQuery = "SELECT * FROM ExecutedExam WHERE studentID=\"" + studentID + "\";";
			ResultSet rsQuestionOfCourse = statement.executeQuery(courseQuery);
			while (rsQuestionOfCourse.next()) {
				String examID = rsQuestionOfCourse.getString("ExamID");
				String subject = rsQuestionOfCourse.getString("Subject");
				String course = rsQuestionOfCourse.getString("Course");
				String execDate = rsQuestionOfCourse.getString("ExecDate");
				String testType = rsQuestionOfCourse.getString("TestType");
				Integer grade = rsQuestionOfCourse.getInt("Grade");

				ExecutedExam exam = new ExecutedExam(examID, subject, course, execDate, testType, grade);
				// exam.setGetCopy(blob);
				examList.add(exam);
			}

		} catch (SQLException e) {
			System.err.println("ERROR #223688 - ERROR LOADING EXECUTED EXAM FROM DATABASE");
		}

		return examList;
	}

	public void startExam(ExamProcess examProcess) {
		System.out.println(examProcess);
		PreparedStatement prepareStatement;
		try {
			prepareStatement = conn.prepareStatement("INSERT INTO ExamProcess VALUES (?,?,?,?,?);");
			prepareStatement.setString(1, examProcess.getExamID());
			prepareStatement.setString(2, examProcess.getCode());
			prepareStatement.setString(3, examProcess.getTeacherID());
			prepareStatement.setString(4, examProcess.getDate());
			prepareStatement.setString(5, examProcess.getTimeExtension());
			int resultSet = prepareStatement.executeUpdate();
			if (resultSet == 1) {
				System.out.print("Exam started Succuessfully");
			}

		} catch (SQLException e) {
			System.err.print("Error occurred, exam has not been started ");
		}
	}

	public void closeExam(String code) {
		try {
			Statement statement = conn.createStatement();
			String query = "DELETE FROM ExamProcess WHERE code=" + code + ";";
			statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
