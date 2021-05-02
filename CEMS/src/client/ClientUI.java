package client;

//
import gui.ClientLoginGuiController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main user interface class that's starting the client application, and all of
 * the graphic user interface componnent.
 * 
 * @author Arikz
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
		ClientLoginGuiController clientLoginGuiController = new ClientLoginGuiController();
		clientLoginGuiController.start(primaryStage);
	}

}
