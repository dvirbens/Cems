package client;

import java.io.IOException;

import client.gui.MainGuiController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main user interface class that's starting the client application, and all of
 * the graphic user interface component.
 * 
 * @author Arikz ,Dvir ben simon
 *
 */
public class ClientUI extends Application {

	private static ClientController clientController;
	public static boolean serverStatus=false;

	/**
	 * @param args main arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Function that's starting when all of javaFX component is ready
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			setClientController(new ClientController("localhost", 5555));
			serverStatus=true;
			System.out.println("Connected to server");
		} catch (IOException e1) {
			System.out.println("Cant connect to server");
		}

		MainGuiController clientMainGuiController = new MainGuiController();
		clientMainGuiController.start(primaryStage);

	}

	public static ClientController getClientController() {
		return clientController;
	}

	public static void setClientController(ClientController clientController) {
		ClientUI.clientController = clientController;
	}

}
