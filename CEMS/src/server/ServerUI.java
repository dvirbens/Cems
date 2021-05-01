package server;

import java.io.IOException;

//
import gui.ServerGuiController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				System.exit(0);
			}
		});
		ServerGuiController serverGui = new ServerGuiController();
		serverGui.start(primaryStage);
	}

}
