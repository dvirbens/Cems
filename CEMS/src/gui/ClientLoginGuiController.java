package gui;

//
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientLoginGuiController implements Initializable {

	private static ClientController clientController;

	@FXML
	private TextField tfPort;

	@FXML
	private Button btnConnect;

	@FXML
	private Label labelStatus;

	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("ClientLoginGui.fxml"));
			Scene scene = new Scene(root, 300, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("CEMS");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onConnect(ActionEvent event) {
		ClientMainGuiController clientMainGuiController = new ClientMainGuiController();

		int port = Integer.parseInt(tfPort.getText());
		try {
			labelStatus.setText("");
			clientController = new ClientController("localhost", port);
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			clientMainGuiController.start(stage, clientController);
		} catch (IOException e) {
			e.printStackTrace();
			labelStatus.setText("ERROR--Worng Port");
			labelStatus.setTextFill(Color.color(1, 0, 0));
			labelStatus.setAlignment(Pos.CENTER);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfPort.setText("5555");
	}
}
