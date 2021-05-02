package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.ModelWrapper;
import models.Test;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	public static boolean awaitResponse = false;
	private static List<Test> tests;
	private static Test editTest;
	private static String errorMessage;

	public Client(String host, int port) throws IOException {
		super(host, port);
		tests = new ArrayList<>();
	}

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
