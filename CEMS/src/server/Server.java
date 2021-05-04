package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import common.ModelWrapper;
import models.Database;
import models.Test;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * Server class that's handle server-client communication
 * 
 * @author Arikz ,Dvir ben simon
 *
 */
public class Server extends AbstractServer {

	/**
	 * Server event listener in order to handle events that occurred by server, send
	 * to server user interface log.
	 */
	private ServerEventListener serverListener;

	/**
	 * Store database controller instance created by the constructor, in order to
	 * make transaction with database.
	 */
	private DatabaseController databseController;

	/**
	 * Indicate if the server is connected
	 */
	private static boolean isConnected = false;

	/**
	 * Creating new server connection with port given by constructor
	 * 
	 * @param port number
	 */
	public Server(int port) {
		super(port);
	}

	/**
	 * @param logListener server event listener, in order to handle event and update
	 *                    the log
	 * @param database    details instance to create database instance
	 * @param serverPort
	 */
	public Server(ServerEventListener logListener, Database database, String serverPort) {
		super(Integer.parseInt(serverPort));
		this.serverListener = logListener;
		databseController = new DatabaseController(database, logListener);
	}

	/**
	 * Function calls when client send message, the client send messages throw this
	 * function.
	 * 
	 * @param msg: get the returning message from client
	 */
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

	/**
	 * Method the being called when server has been started, when the server started
	 * there is link between SQL database, indicate the server is connected and
	 * print to log.
	 */
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

	/**
	 * Method the being called when server has been stopped, when the server stopped
	 * indicate the server is connected and print to log.
	 */
	protected void serverStopped() {
		isConnected = false;
		serverListener.printToLog("Server has stopped listening for connections");
		serverListener.changeButtonStatus(isConnected);
	}

	/**
	 * Print to log when new client is entered the server.
	 */
	protected void clientConnected(ConnectionToClient client) {
		serverListener.printToLog("New client connection, ip address: " + client.getInetAddress());
	}

	public static boolean isConnected() {
		return isConnected;
	}
}
