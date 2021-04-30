package client;
//
import gui.ClientLoginGuiController;
import gui.ClientMainGuiController;
import gui.ServerGuiController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientLoginGuiController clientLoginGuiController = new ClientLoginGuiController();
		clientLoginGuiController.start(primaryStage);
	}

}
