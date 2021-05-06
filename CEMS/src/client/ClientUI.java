package client;

//
import gui.ClientLoginGuiController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
		ClientLoginGuiController clientLoginGuiController = new ClientLoginGuiController();
		clientLoginGuiController.start(primaryStage);
	}

}
