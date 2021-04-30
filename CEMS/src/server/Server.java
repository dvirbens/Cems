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

	private LogEventListener logListener;
	private DatabaseController databseController;
	private boolean isConnected = false;

	public Server(int port) {
		super(port);
	}

	public Server(LogEventListener logListener, Database database, String serverPort) {
		super(Integer.parseInt(serverPort));
		this.logListener = logListener;
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
			modelWrapperToClient = new ModelWrapper<Test>(test, ModelWrapper.LOAD_TEST);
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
		}

	}

	protected void serverStarted() {
		try {
			databseController.connectToDatabase();
			logListener.printToLog("Server listening for connections on port " + getPort());
		} catch (SQLException ex) {
			try {
				this.close();
				logListener.printToLog("Disconnected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	protected void serverStopped() {
		isConnected = false;
		logListener.printToLog("Server has stopped listening for connections");
	}

	public boolean isConnected() {
		return isConnected;
	}

}
