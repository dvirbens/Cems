package client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import common.ModelWrapper;
import models.Question;
import models.Test;
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
	private static List<Test> tests;
	/**
	 * Value that hold the test the will be shown on editTest user interface.
	 */
	private static Test editTest;
	/**
	 * Value that hold the error message causing by server side.
	 */
	private static String errorMessage;

	private static String[] subjectList = { "Mathematics", "Social Studies", "Language", "Science" };

	private static String[] courseList = { "Algebra", "Geometry", "Algebra II", "Trigonometry", "Geography",
			"History" };

	/**
	 * Value that hold the user, use to login and and open appropriate menu
	 */
	private static User user;

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
			ModelWrapper<?> modelWrapperToClient = (ModelWrapper<?>) msg;
			switch (modelWrapperToClient.getOperation()) {
			case UPDATE_TEST:
				editTest = (Test) modelWrapperToClient.getElement();
				break;

			case LOAD_TEST:
				editTest = (Test) modelWrapperToClient.getElement();
				break;

			case LOAD_QUESTION:
				break;

			case LOAD_TEST_LIST:
				tests = (List<Test>) modelWrapperToClient.getElements();
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
				break;

			case EXAM_EXTENSION_REQUEST:
				break;

			case OVERALL_STATISTICS:
				break;

			case GET_USER:
				User user = (User) modelWrapperToClient.getElement();
				setUser(user);
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

	public static List<Test> getTests() {
		return tests;
	}

	public static void setTests(List<Test> tests) {
		Client.tests = tests;
	}

	public static Test getEditTest() {
		return editTest;
	}

	public static void setEditTest(Test editTest) {
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

	public static String[] getSubjectList() {
		return subjectList;
	}

	public static void setSubjectList(String[] subjectList) {
		Client.subjectList = subjectList;
	}

	public static String[] getCourseList() {
		return courseList;
	}

	public static void setCourseList(String[] courseList) {
		Client.courseList = courseList;
	}
	
	

}
