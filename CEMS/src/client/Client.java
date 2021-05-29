package client;

import java.io.IOException;
import java.util.List;

import common.ModelWrapper;
import common.SubjectCourseCollection;
import models.Exam;
import models.ExecutedExam;
import models.Question;
import models.User;
import ocsf.client.AbstractClient;

/**
 * Class that's handle server-client communication.
 * 
 * @author Arikz ,Dvir ben simon
 */
public class Client extends AbstractClient {

	/**
	 * Value that's indicating if the server answer back to client, in order to stop
	 * listening on "HandleMessageFromClientUI method.
	 */
	public static boolean awaitResponse = false;
	/**
	 * Value the holds the list of test that will be shown on table user interface.
	 */
	private static List<Exam> exams;
	/*
	 * Value that hold the list of the executed tests of the user.
	 */
	private static List<ExecutedExam> execExams;

	/**
	 * value that hold exam id of specific test
	 */
	private static String examID;
	/**
	 * value that hold last exam code
	 */
	private static String examCode;
	/**
	 * value that hold current executing exam
	 */
	private static Exam exam;
	/**
	 * 
	 * 
	 * /** Value the holds the list of question that will be shown on table user
	 * interface.
	 */
	private static List<Question> questions;

	/**
	 * Value that hold the test the will be shown on editTest user interface.
	 */
	private static Exam editTest;
	/**
	 * Value that hold the messages server side sent.
	 */
	private static String serverMessages;

	/**
	 * Value that hold the user, use to login and and open appropriate menu
	 */
	private static User user;
	
	private static long timeExtension = 0;

	/**
	 * Value that hold the user, use to login and and open appropriate menu
	 */
	private static SubjectCourseCollection subjectCollection;

	/**
	 * Constructor creating new client connection.
	 * 
	 * @param host: Server host/ip info connection {default: 127.0.0.1}.
	 * @param port: Server port info connection.
	 * @throws IOException handle connection problem by enter wrong server details.
	 */
	public Client(String host, int port) throws IOException {
		super(host, port);
	}

	/**
	 * Function calls when server send message, the server send messages throw this
	 * function.
	 * 
	 * @param msg: get the returning message from server
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {

		if (msg != null) {
			ModelWrapper<?> modelWrapperFromServer = (ModelWrapper<?>) msg;
			switch (modelWrapperFromServer.getOperation()) {

			case GET_QUESTION_LIST:
				questions = (List<Question>) modelWrapperFromServer.getElements();
				break;

			case LOAD_QUESTION_LIST:
				break;

			case ENTERED_WRONG_ID:
				break;

			case TEST_STATISTICS:
				break;

			case GET_EXECUTED_EXAM_LIST:
				execExams = (List<ExecutedExam>) modelWrapperFromServer.getElements();
				break;

			case START_EXAM_SUCCESS:
				setServerMessages("Exam has been started");

				break;
			case START_EXAM_FAILD:
				setServerMessages("Can't start exam");
				break;

			case CLOSE_EXAM:
				setServerMessages("Exam has been stopped");
				break;

			case CREATE_QUESTION:
				setServerMessages("Question has been saved suucessfully");
				break;

			case EXAM_EXECUTE:
				execExams = (List<ExecutedExam>) modelWrapperFromServer.getElements();
				break;

			case EXTENSION_REQUEST:
				setServerMessages("Extension has been sent suucessfully");
				break;

			case OVERALL_STATISTICS:
				break;

			case GET_USER:
				User user = (User) modelWrapperFromServer.getElement();
				setUser(user);
				break;

			case GET_SUBJECT_COURSE_LIST:
				subjectCollection = (SubjectCourseCollection) modelWrapperFromServer.getElement();
				break;

			case GET_QUESTION_LIST_BY_SUBJECT:
				questions = (List<Question>) modelWrapperFromServer.getElements();
				break;

			case CREATE_EXAM:
				setServerMessages("Exam has been saved successfully");
				break;

			case GET_EXAMS_LIST:
				exams = (List<Exam>) modelWrapperFromServer.getElements();
				break;

			case GET_EXAMS_LIST_BY_SUBJECT:
				exams = (List<Exam>) modelWrapperFromServer.getElements();
				break;

			case GET_EXAMS_LIST_BY_COURSE:
				exams = (List<Exam>) modelWrapperFromServer.getElements();
				break;

			case UPLOAD_FILE:
				setServerMessages("FileUploaded.");
				break;

			case INSERT_STUDENT_TO_EXAM:
				setExamID((String) modelWrapperFromServer.getElement());
				setServerMessages("Student entered exam successfully");
				break;
				
			case ERROR_INSERT_STUDENT_TO_EXAM:
				setServerMessages("Invalid code.");
				break;

			case GET_EXAM_ID:
				setExamID((String) modelWrapperFromServer.getElement());
				break;

			case GET_QUESTION_LIST_BY_EXAM_ID:
				questions = (List<Question>) modelWrapperFromServer.getElements();
				break;

			case GET_EXAM_BY_EXAM_ID:
				Client.setExam((Exam) modelWrapperFromServer.getElement());
				break;
			
			case STUDENT_TIME_EXTENSION:
				Client.setTimeExtension(Long.parseLong((String) modelWrapperFromServer.getElement()));
				break;
				
			case INSERT_STUDENT_GRADE:
				break;

			case GET_EXECUTED_EXAM_LIST_OWNER:
				execExams = (List<ExecutedExam>) modelWrapperFromServer.getElements();
				break;
				
			default:
				break;

			}
		}

		awaitResponse = false;

	}

	/**
	 * Function that starting when the client user interface send message to the
	 * server in order to get services back.
	 * 
	 * @param msg: object that client send to server in order to get message back.
	 */
	public void handleMessageFromClientUI(Object msg) {
		try {
			openConnection();
			awaitResponse = true;
			sendToServer(msg);

			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			quit();
		}

	}

	/**
	 * Function that's close all server communications and terminate the client
	 * application.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}

	public static List<Exam> getExams() {
		return exams;
	}

	public static void setExams(List<Exam> exams) {
		Client.exams = exams;
	}

	public static Exam getEditTest() {
		return editTest;
	}

	public static void setEditTest(Exam editTest) {
		Client.editTest = editTest;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Client.user = user;
	}

	public static String getServerMessages() {
		return serverMessages;
	}

	public static void setServerMessages(String serverMessages) {
		Client.serverMessages = serverMessages;
	}

	public static SubjectCourseCollection getSubjectCollection() {
		return subjectCollection;
	}

	public static void setSubjectCollection(SubjectCourseCollection subjectCollection) {
		Client.subjectCollection = subjectCollection;
	}

	public static List<Question> getQuestions() {
		return questions;
	}

	public static void setQuestions(List<Question> questions) {
		Client.questions = questions;
	}

	public static List<ExecutedExam> getExecExams() {
		return execExams;
	}

	public static void setExecExams(List<ExecutedExam> execExams) {
		Client.execExams = execExams;
	}

	public static String getExamID() {
		return examID;
	}

	public static void setExamID(String examID) {
		Client.examID = examID;
	}

	public static String getExamCode() {
		return examCode;
	}

	public static void setExamCode(String examCode) {
		Client.examCode = examCode;
	}

	public static Exam getExam() {
		return exam;
	}

	public static void setExam(Exam exam) {
		Client.exam = exam;
	}

	public static long getTimeExtension() {
		return timeExtension;
	}

	public static void setTimeExtension(long timeExtension) {
		Client.timeExtension = timeExtension;
	}
	
	

}
