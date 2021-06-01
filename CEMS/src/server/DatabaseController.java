package server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXButton;

import common.ModelWrapper.Operation;
import common.SubjectCourseCollection;
import models.Database;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import models.Exam;
import models.ExamProcess;
import models.ExamProcess.ExamType;
import models.*;
import models.ExamQuestion;
import models.ExamQuestion.NoteType;
import models.ExecutedExam;
import models.Question;
import models.StudentExecutedExam;
import models.User;
import models.User.ErrorType;
import models.User.UserType;
import models.WordFile;

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
			String sql = "SELECT SUBSTRING(examID, 5, 6) examID FROM exam WHERE Subject=" + "\"" + subject + "\""
					+ " AND Course=" + "\"" + course + "\"" + " ORDER BY examID DESC LIMIT 1;";
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
		String courseQuery;
		if (subject == null) {
			courseQuery = "SELECT * FROM Question;";
		} else {
			courseQuery = "SELECT * FROM Question WHERE Subject=\"" + subject + "\";";
		}

		try {
			Statement statement = conn.createStatement();

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

	// Belong to overall statisti - Statistic by course:
	// (i should change to ll 3 statuse by enum operation)
	public List<Statistics> getGradesForStatisticByCourse() {
		List<Statistics> set = new ArrayList<Statistics>();
		try {
			Statement statement = conn.createStatement();
			String courseStatistic = "select user.userID, user.FirstName, user.LastName, executedexambystudent.Grade\r\n"
					+ "from executedexambystudent, user\r\n"
					+ "where executedexambystudent.studentID=user.userID && Course=\"Algebra\" && ExecDate=\"12/05/22\";";
			ResultSet rsGtadeStatisticByCourse = statement.executeQuery(courseStatistic);
			while (rsGtadeStatisticByCourse.next()) {
				String userID = rsGtadeStatisticByCourse.getString("userID");
				String FirstName = rsGtadeStatisticByCourse.getString("FirstName");
				String LastName = rsGtadeStatisticByCourse.getString("LastName");
				String Grade = rsGtadeStatisticByCourse.getString("Grade");
				Statistics statisticByCourse = new Statistics(userID, FirstName, LastName, Grade);

//                            if(Statistics==Operation.STATISTIC_BY_COURSE_X) {
//                                    
//                            }
//                            else if(Statistics==Operation.STATISTIC_BY_COURSE_Y) {
//                                    String Grade = rsGtadeStatisticByCourse.getString("Grade");	
//                                    Statistics statisticByCourse = new Statistics(Grades);	
//                            }

				set.add(statisticByCourse);
			}
		} catch (SQLException e) {
			System.err.println("ERROR #23142 - ERROR LOADING EXAM FROM DATABASE");
		}
		return set;
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

	public List<ExamQuestion> getExamQuestionsList(String examID) {

		List<ExamQuestion> examQuestionsList = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String examQuestionQuery = "SELECT * FROM examquestion WHERE examID=\"" + examID + "\";";
			ResultSet rsexamQuestion = statement.executeQuery(examQuestionQuery);
			while (rsexamQuestion.next()) {
				String questionID = rsexamQuestion.getString("questionID");
				String note = rsexamQuestion.getString("note");
				int points = rsexamQuestion.getInt("point");
				NoteType type = NoteType.valueOf(rsexamQuestion.getString("type"));

				Statement statement2 = conn.createStatement();
				String questionQuery = "SELECT * FROM question WHERE questionID=\"" + questionID + "\";";
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

	public List<StudentExecutedExam> getExecutedExamListByStudentID(String studentID) {
		List<StudentExecutedExam> examList = new ArrayList<>();
		InputStream copyFile = null;
		WordFile checked_file = new WordFile();
		try {
			Statement statement = conn.createStatement();
			String courseQuery = "SELECT * FROM executedexambystudent WHERE studentID=\"" + studentID + "\";";
			ResultSet rsQuestionOfCourse = statement.executeQuery(courseQuery);
			while (rsQuestionOfCourse.next()) {
				String examID = rsQuestionOfCourse.getString("ExamID");
				String teacherID = rsQuestionOfCourse.getString("teacherID");
				String subject = rsQuestionOfCourse.getString("Subject");
				String course = rsQuestionOfCourse.getString("Course");
				String execDate = rsQuestionOfCourse.getString("ExecDate");
				String testType = rsQuestionOfCourse.getString("TestType");
				String grade = rsQuestionOfCourse.getString("Grade");
				boolean approval = rsQuestionOfCourse.getBoolean("Approved");
				Blob copy = rsQuestionOfCourse.getBlob("Copy");
				String alert = rsQuestionOfCourse.getString("Alert");

				if (testType != "Manual" || copy.length() == 0) {
					checked_file.setSize(0);
				} else {
					copyFile = copy.getBinaryStream();

					ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					int nRead;
					byte[] data = new byte[16384];

					try {
						while ((nRead = copyFile.read(data, 0, data.length)) != -1) {
							buffer.write(data, 0, nRead);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					byte[] arr = buffer.toByteArray();

					checked_file.initArray(arr.length);
					checked_file.setSize(arr.length);
					checked_file.setMybytearray(arr);
				}

				StudentExecutedExam exam = new StudentExecutedExam(examID, studentID, teacherID, subject, course,
						execDate, testType, grade, checked_file, approval, alert);

				examList.add(exam);
			}

		} catch (SQLException e) {
			System.err.println("ERROR #223688 - ERROR LOADING EXECUTED EXAM FROM DATABASE");
		}

		return examList;
	}

	/*
	 * public void startExam(ExamProcess examProcess) { PreparedStatement
	 * prepareStatement; try { switch (examProcess.getType()) { case Computerized:
	 * prepareStatement = conn.prepareStatement(
	 * "INSERT INTO ExamProcess(examID,code,teacherID,startDate,type) VALUES (?,?,?,?,?);"
	 * ); prepareStatement.setString(1, examProcess.getComputerizedExamID());
	 * prepareStatement.setString(2, examProcess.getCode());
	 * prepareStatement.setString(3, examProcess.getTeacherID());
	 * prepareStatement.setString(4, examProcess.getDate());
	 * prepareStatement.setString(5, examProcess.getType().toString()); int
	 * resultSetComputerized = prepareStatement.executeUpdate(); if
	 * (resultSetComputerized == 1) {
	 * System.out.println("Computerized Exam started Succuessfully"); } break;
	 * 
	 * case Manual: WordFile wordFile = examProcess.getManualFile(); InputStream
	 * targetStream = new ByteArrayInputStream(wordFile.getMybytearray());
	 * prepareStatement = conn.prepareStatement(
	 * "INSERT INTO ExamProcess(code,type,startDate,manualSubject,manualCourse,manualDuration,teacherID,manualExam) VALUES (?,?,?,?,?,?,?,?);"
	 * ); prepareStatement.setString(1, examProcess.getCode());
	 * prepareStatement.setString(2, examProcess.getType().toString());
	 * prepareStatement.setString(3, examProcess.getDate());
	 * prepareStatement.setString(4, examProcess.getManualSubject());
	 * prepareStatement.setString(5, examProcess.getManulCourse());
	 * prepareStatement.setString(6, examProcess.getManualDuration());
	 * prepareStatement.setString(7, examProcess.getTeacherID());
	 * prepareStatement.setBlob(8, targetStream); int resultSetManual =
	 * prepareStatement.executeUpdate(); if (resultSetManual == 1) {
	 * System.out.println("Manual Exam started Succuessfully"); }
	 * 
	 * break;
	 * 
	 * }
	 * 
	 * } catch (SQLException e) { e.printStackTrace();
	 * System.err.println("Error occurred, exam has not been started "); } }
	 * 
	 */

	public void UploadFile(StudentExecutedExam studentExam, WordFile file) {
		String sql = "INSERT INTO StudentUploadManualTest VALUES (?,?,?,?,?,?)";

		try {
			InputStream targetStream = new ByteArrayInputStream(file.getMybytearray());
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, studentExam.getStudentID());
			stmt.setString(2, studentExam.getExamID());
			stmt.setString(3, studentExam.getSubject());
			stmt.setString(4, studentExam.getCourse());
			stmt.setString(5, studentExam.getExecDate());
			stmt.setBlob(6, targetStream);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean insertToExecutedExamByStudent(String studentID, ExamProcess exam) {
		String sql = "INSERT INTO executedexambystudent VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, studentID);
			stmt.setString(2, exam.getExamId());
			stmt.setString(3, exam.getTeacherID());
			stmt.setString(4, exam.getSubject());
			stmt.setString(5, exam.getCourse());
			stmt.setString(6, exam.getDate());
			stmt.setString(7, exam.getType().toString());
			stmt.setString(8, "0");
			stmt.setString(9, null);
			stmt.setBoolean(10, false);
			stmt.setString(11, null);
			int resultSet = stmt.executeUpdate();
			if (resultSet == 1) {
				System.out.println("Student entered exam");
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR #223980 - ERROR INSERTING STUDENT TO EXAM IN DATABASE");
		}
		return false;
	}

	public String GetExamID(String userCode) {
		String sql = "SELECT examID FROM examprocess WHERE code = " + userCode;
		String examID = "";
		try {
			Statement statement = conn.createStatement();
			ResultSet examID_RS = statement.executeQuery(sql);

			if (examID_RS.next()) {
				examID = examID_RS.getString("ExamID");
			}
			return examID;

		} catch (SQLException e) {
			System.err.println("ERROR #223982 - EXAM ID NOT EXIST FOR CODE " + userCode);
		}
		return null;
	}

	public Exam GetExamByExamID(String examID) {
		Exam exam;

		try {
			Statement statement = conn.createStatement();
			String Query = "SELECT * FROM exam WHERE examID=\"" + examID + "\";";
			ResultSet rs = statement.executeQuery(Query);
			if (rs.next()) {
				String teacherID = rs.getString("teacherID");
				String subject = rs.getString("Subject");
				String course = rs.getString("Course");
				String duration = rs.getString("Duration");
				String teacherName = rs.getString("teacherName");
				List<ExamQuestion> questionsList = getExamQuestionsList(examID);
				System.out.println(questionsList);
				exam = new Exam(examID, teacherID, subject, course, duration, questionsList);
				exam.setTeacherName(teacherName);
				return exam;
			}

		} catch (SQLException e) {
			System.err.println("ERROR #223688 - ERROR LOADING EXECUTED EXAM FROM DATABASE");
		}

		return null;
	}

	public void insertStudentGrade(String studentID, String examID, String grade) {
		String sql = "UPDATE ExecutedExamByStudent SET Grade = ? WHERE studentID = ? AND examID = ?;";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, grade);
			stmt.setString(2, studentID);
			stmt.setString(3, examID);

			stmt.executeUpdate();
			System.out.println("Student ID: " + studentID + " in examID: " + examID + " got grade " + grade);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean saveExecutedExam(ExamProcess exam) {
		PreparedStatement prepareStatement;
		String creatorTeacherID = getCreatorTeacherID(exam.getExamId());
		creatorTeacherID = creatorTeacherID != null ? creatorTeacherID : "";
		try {
			prepareStatement = conn.prepareStatement("INSERT INTO ExecutedExam VALUES (?,?,?,?,?,?,?,?,?,?,?);");
			prepareStatement.setString(1, exam.getExamId());
			prepareStatement.setString(2, exam.getTeacherID());
			prepareStatement.setString(3, creatorTeacherID);
			prepareStatement.setString(4, exam.getSubject());
			prepareStatement.setString(5, exam.getCourse());
			prepareStatement.setString(6, exam.getDuration());
			prepareStatement.setString(7, exam.getDate());
			prepareStatement.setString(8, exam.getTime());
			prepareStatement.setDouble(9, 0);
			prepareStatement.setDouble(10, 0);
			prepareStatement.setString(11, exam.getType().toString());
			int resultSet = prepareStatement.executeUpdate();
			if (resultSet == 1) {
				System.out.print("Exam Saved Succuessfully");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error occurred, Exam has not been saved ");
			return false;
		}

		return true;

	}

	public List<ExecutedExam> getExecutedExamListByExecutorTeacherID(String loggedInTeacherId) {
		List<ExecutedExam> examList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String executedExamQuery = "SELECT * FROM executedExam WHERE executeTeacherID=\"" + loggedInTeacherId
					+ "\";";
			ResultSet rsExecutedExam = statement.executeQuery(executedExamQuery);
			while (rsExecutedExam.next()) {
				String examID = rsExecutedExam.getString("ExamID");
				String subject = rsExecutedExam.getString("subject");
				String course = rsExecutedExam.getString("course");
				String executeDate = rsExecutedExam.getString("executeDate");
				String type = rsExecutedExam.getString("type");
				String executeTeacherID = rsExecutedExam.getString("executeTeacherID");
				String teacherName = getUserName(executeTeacherID);
				ExecutedExam executedExam = new ExecutedExam(examID, teacherName, executeTeacherID, subject, course,
						executeDate, type);
				examList.add(executedExam);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR #223688 - ERROR LOADING EXECUTED EXAM FROM DATABASE");
		}

		return examList;
	}

	public List<ExecutedExam> getExecutedExamListByCreatorTeacherID(String loggedInTeacherId) {
		List<ExecutedExam> examList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			String executedExamQuery = "SELECT * FROM executedExam WHERE creatorTeacherID=\"" + loggedInTeacherId
					+ "\";";
			ResultSet rsExecutedExam = statement.executeQuery(executedExamQuery);
			while (rsExecutedExam.next()) {
				String examID = rsExecutedExam.getString("ExamID");
				String subject = rsExecutedExam.getString("subject");
				String course = rsExecutedExam.getString("course");
				String executeDate = rsExecutedExam.getString("executeDate");
				String type = rsExecutedExam.getString("type");
				String executeTeacherID = rsExecutedExam.getString("executeTeacherID");
				String teacherName = getUserName(executeTeacherID);
				ExecutedExam executedExam = new ExecutedExam(examID, teacherName, executeTeacherID, subject, course,
						executeDate, type);
				examList.add(executedExam);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR #223688 - ERROR LOADING EXECUTED EXAM FROM DATABASE");
		}

		return examList;
	}

	private String getCreatorTeacherID(String examID) {
		try {
			Statement statement = conn.createStatement();
			String examQuery = "SELECT * FROM Exam WHERE examID=\"" + examID + "\";";
			ResultSet rsExam = statement.executeQuery(examQuery);
			if (rsExam.next()) {
				String teacherID = rsExam.getString("teacherID");
				return teacherID;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR #223688 - ERROR LOADING EXAM FROM DATABASE");
		}
		return null;
	}

	private String getUserName(String userID) {
		try {
			Statement statement = conn.createStatement();
			String examQuery = "SELECT * FROM User WHERE userID=\"" + userID + "\";";
			ResultSet rsExam = statement.executeQuery(examQuery);
			if (rsExam.next()) {
				String firstName = rsExam.getString("FirstName");
				String lastName = rsExam.getString("LastName");
				return firstName + " " + lastName;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR #2245688 - ERROR LOADING USER INFO FROM DATABASE");
		}
		return null;
	}

	public List<StudentExecutedExam> getExecutedExamStudentList(String examID, String date, String executerTeacher) {
		List<StudentExecutedExam> studentList = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();

			String studentListQuery = "SELECT * FROM ExecutedExamByStudent WHERE examID=\"" + examID
					+ "\" AND ExecDate=\"" + date + "\" AND teacherID=\"" + executerTeacher + "\";";
			ResultSet rs = statement.executeQuery(studentListQuery);
			while (rs.next()) {
				String studentID = rs.getString("studentID");
				String teacherID = rs.getString("teacherID");
				String subject = rs.getString("Subject");
				String course = rs.getString("Course");
				String execDate = rs.getString("ExecDate");
				String testType = rs.getString("TestType");
				String grade = rs.getString("Grade");
				Blob blob = rs.getBlob("Copy");
				// InputStream inputStream = blob.getBinaryStream();
				WordFile copy = new WordFile();
				boolean approved = rs.getBoolean("Approved");
				String alert = rs.getString("Alert");
				String studentName = getUserName(studentID);

				StudentExecutedExam executedStudent = new StudentExecutedExam(examID, studentName, teacherID, subject,
						course, execDate, testType, grade, copy, approved, alert);

				studentList.add(executedStudent);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR #2245688 - ERROR LOADING USER INFO FROM DATABASE");
		}

		return studentList;
	}

	public void updateAlertValue(String studentID, String examID, String AlertPercent) {
		String sql = "UPDATE ExecutedExamByStudent SET Alert = ? WHERE studentID = ? AND examID = ?;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, AlertPercent);
			stmt.setString(2, studentID);
			stmt.setString(3, examID);

			stmt.executeUpdate();
			//System.out.println("Student ID: " + studentID + " in examID: " + examID + " got Alert " + AlertPercent);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
