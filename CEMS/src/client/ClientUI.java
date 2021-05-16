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

	public static ClientController clientController;

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
			System.out.println("Connected to server");
		} catch (IOException e1) {
			System.out.println("Cant connect to server");
			e1.printStackTrace();
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
