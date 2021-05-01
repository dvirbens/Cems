package gui;

import java.io.IOException;
//
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Database;
import server.ServerEventListener;
import server.Server;

public class ServerGuiController implements Initializable, ServerEventListener {

	@FXML
	private TextField tfIp;

	@FXML
	private TextField tfPort;

	@FXML
	private TextField tfScheme;

	@FXML
	private TextField tfUserName;

	@FXML
	private PasswordField tfPassword;

	@FXML
	private TextField tfPortServer;

	@FXML
	private Button btnStart;

	@FXML
	private TextArea taLogs;

	private Server server;

	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("ServerGui.fxml"));
			Scene scene = new Scene(root, 650, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("CEMS-Server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickStartServer(ActionEvent event) {

		if (Server.isConnected() == false) {
			btnStart.setText("Starting Server..");
			createServer();
			try {
				server.listen();
			} catch (Exception ex) {
				printToLog("ERROR - Could not listen for clients!");
			}
		} else {
			btnStart.setText("Disconnecting..");
			disconnectFromServer();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillDefaultDetails();
		createServer();
	}

	private void createServer() {
		String ip = tfIp.getText();
		String port = tfPort.getText();
		String scheme = tfScheme.getText();
		String username = tfUserName.getText();
		String password = tfPassword.getText();
		String serverPort = tfPortServer.getText();
		Database database = new Database(ip, port, scheme, username, password);
		server = new Server(this, database, serverPort);
	}

	public void disconnectFromServer() {
		try {
			server.close();
			printToLog("Disconnected from server");
		} catch (IOException e) {
			printToLog("Critical error disconnecting");
			e.printStackTrace();
		}
	}

	private void fillDefaultDetails() {
		tfIp.setText("127.0.0.1");
		tfPort.setText("3306");
		tfScheme.setText("cems");
		tfUserName.setText("root");
		tfPassword.setText("");
		tfPortServer.setText("5555");
	}

	@Override
	public void printToLog(String logs) {
		taLogs.appendText(logs + "\n");
	}

	@Override
	public void changeButtonStatus(boolean status) {
		Thread btnChangeToDisconnect = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (Exception ex) {
					System.out.println("Problem Occured");
				}
				Platform.runLater(() -> {
					if (status)
						btnStart.setText("Dissconnect");
					else
						btnStart.setText("Connect");
				});
			}
		});

		btnChangeToDisconnect.setDaemon(true);
		btnChangeToDisconnect.start();
	}

}
