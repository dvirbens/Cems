package server;
//
import gui.ServerGuiController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerUI extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ServerGuiController serverGui = new ServerGuiController();
		serverGui.start(primaryStage);
	}

}
