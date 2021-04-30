package client;

//
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Test;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	public static boolean awaitResponse = false;
	private static ClientController clientController;
	private static List<Test> tests;

	public Client(String host, int port, ClientController clientController) throws IOException {
		super(host, port);
		this.clientController = clientController;
		tests = new ArrayList<>();
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		tests = (List<Test>) msg;
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

}
