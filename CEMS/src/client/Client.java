package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import common.ModelWrapper;
import models.Test;
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

	/**
	 * Constructor creating new client connection.
	 * 
	 * @param host: Server host/ip info connection {default: 127.0.0.1}.
	 * @param port: Server port info connection.
	 * @throws IOException handle connection problem by enter wrong server details.
	 */
	public Client(String host, int port) throws IOException {
		super(host, port);
		tests = new ArrayList<>();
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
			case ModelWrapper.LOAD_TEST:
				editTest = (Test) modelWrapperToClient.getElement();
				break;

			case ModelWrapper.LOAD_QUESTION:
				break;

			case ModelWrapper.LOAD_TEST_LIST:
				tests = (List<Test>) modelWrapperToClient.getElements();
				break;

			case ModelWrapper.LOAD_QUESTION_LIST:
				break;

			case ModelWrapper.UPDATE_TEST:
				break;

			case ModelWrapper.ENTERED_WRONG_ID:
				setErrorMessage("Test dont found ");
				editTest = null;
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

}
