package gui;

//
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import client.ClientController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Test;

public class ClientLoginGuiController implements Initializable {

	private static ClientController clientController;

	@FXML
	private TextField tfPort;

	@FXML
	private Button btnConnect;
	

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
		clientController = new ClientController("localhost", port,clientMainGuiController);
		
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		
		clientMainGuiController.start(stage, clientController);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfPort.setText("5555");
		
	}
}
