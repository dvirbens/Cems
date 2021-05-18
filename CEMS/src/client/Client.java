package client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import common.ModelWrapper;
import common.SubjectCourseCollection;
import models.Question;
import models.Exam;
import models.ExecutedExam;
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
	 * Value the holds the list of question that will be shown on table user
	 * interface.
	 */
	private static List<Question> questions;

	/**
	 * Value that hold the test the will be shown on editTest user interface.
	 */
	private static Exam editTest;
	/**
	 * Value that hold the error message causing by server side.
	 */
	private static String errorMessage;

	/**
	 * Value that hold the user, use to login and and open appropriate menu
	 */
	private static User user;

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
			case UPDATE_TEST:
				editTest = (Exam) modelWrapperFromServer.getElement();
				break;

			case LOAD_TEST:
				editTest = (Exam) modelWrapperFromServer.getElement();
				break;

			case LOAD_QUESTION:
				break;

			case LOAD_QUESTION_LIST:
				break;

			case ENTERED_WRONG_ID:
				break;

			case TEST_STATISTICS:
				break;

			case START_EXAM:
				break;

			case CREATE_QUESTION:
				System.out.println("Question has been saved suucessfully");
				break;

			case EXAM_EXECUTE:
				execExams = (List<ExecutedExam>) modelWrapperFromServer.getElements();
				break;

			case EXAM_EXTENSION_REQUEST:
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

			case GET_QUESTION_LIST:
				questions = (List<Question>) modelWrapperFromServer.getElements();
				break;

			case CREATE_EXAM:
				System.out.println("Exam has been saved successfully");
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

	public static String getErrorMessage() {
		return errorMessage;
	}

	public static void setErrorMessage(String errorMessage) {
		Client.errorMessage = errorMessage;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Client.user = user;
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

}
