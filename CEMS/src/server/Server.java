package server;

//
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ModelWrapper;
import models.Database;
import models.Test;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {

	private ServerEventListener serverListener;
	private DatabaseController databseController;
	private static boolean isConnected = false;

	public Server(int port) {
		super(port);
	}

	public Server(ServerEventListener logListener, Database database, String serverPort) {
		super(Integer.parseInt(serverPort));
		this.serverListener = logListener;
		databseController = new DatabaseController(database, logListener);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
	
		ModelWrapper<?> modelWrapperFromClient = (ModelWrapper<?>) msg;
		ModelWrapper<?> modelWrapperToClient;

		switch (modelWrapperFromClient.getOperation()) {

		case ModelWrapper.LOAD_TEST:
			String id = (String) modelWrapperFromClient.getElement();
			Test test = databseController.getTest(id);
			if (test != null)
				modelWrapperToClient = new ModelWrapper<Test>(test, ModelWrapper.LOAD_TEST);
			else
				modelWrapperToClient = new ModelWrapper<>(null, ModelWrapper.ENTERED_WRONG_ID);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case ModelWrapper.LOAD_QUESTION:
			break;

		case ModelWrapper.LOAD_TEST_LIST:
			List<Test> testArray = databseController.getTestList();
			modelWrapperToClient = new ModelWrapper<Test>(testArray, ModelWrapper.LOAD_TEST_LIST);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case ModelWrapper.LOAD_QUESTION_LIST:
			break;
		case ModelWrapper.UPDATE_TEST:
			Test testToEdit = (Test) modelWrapperFromClient.getElement();
			databseController.updateTest(testToEdit);
			try {
				client.sendToClient(modelWrapperFromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}

	}

	protected void serverStarted() {
		try {
			databseController.connectToDatabase();
			isConnected = true;
			serverListener.printToLog("Server listening for connections on port " + getPort());
			serverListener.changeButtonStatus(isConnected);
		} catch (SQLException ex) {
			try {
				this.close();
				serverListener.printToLog("Disconnected");
				serverListener.changeButtonStatus(!isConnected);
			} catch (IOException e) {
				System.out.println("MEOW");
				e.printStackTrace();
			}
		}
	}

	protected void serverStopped() {
		isConnected = false;
		serverListener.printToLog("Server has stopped listening for connections");
		serverListener.changeButtonStatus(isConnected);
	}

	public static boolean isConnected() {
		return isConnected;
	}

	protected void clientConnected(ConnectionToClient client) {
		serverListener.printToLog("New client connection, ip address: " + client.getInetAddress());
	}
}
