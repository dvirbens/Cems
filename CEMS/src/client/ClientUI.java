package client;

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

	/**
	 * @param args main arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 *	Function that's starting when all of javaFX component is ready
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainGuiController clientMainGuiController = new MainGuiController();
		clientMainGuiController.start(primaryStage);
	}

}
