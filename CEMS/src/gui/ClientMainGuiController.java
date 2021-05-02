package gui;

import java.io.IOException;
//
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import client.Client;
import client.ClientController;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Test;
import server.DatabaseController;

public class ClientMainGuiController {

	private Client client;
	private static BorderPane mainPane;
	private static ClientController clientController;

	@FXML
	private Button btnSearch;

	@FXML
	private TextField tfId;
	

    @FXML
    private Label labelStatus;

	private List<Test> testList;

	public void start(Stage stage, ClientController clientController) {
		this.clientController = clientController;
		try {
			mainPane = (BorderPane) FXMLLoader.load(getClass().getResource("ClientMainGui.fxml"));
			Scene scene = new Scene(mainPane, 850, 500);
			stage.setScene(scene);
			stage.setTitle("CEMS");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onSearchClick(ActionEvent event) {

		ModelWrapper<Test> modelWrapper = new ModelWrapper<>(null, ModelWrapper.LOAD_TEST_LIST);
		clientController.sendClientUIRequest(modelWrapper);
		List<Test> tests = new ArrayList<>();
		tests.addAll(Client.getTests());
		TableGuiController tableGuiController = new TableGuiController(tests);
		tableGuiController.DisplayTable(mainPane);

	}

	@FXML
	void onClickEditTest(ActionEvent event) {
		String id = tfId.getText();
		ModelWrapper<String> modelWrapper = new ModelWrapper(id, ModelWrapper.LOAD_TEST);
		clientController.sendClientUIRequest(modelWrapper);
		Test editTest = Client.getEditTest();
		if (editTest != null) {
			labelStatus.setText("");
			UpdateTestGuiController updateTestGuiController = new UpdateTestGuiController(editTest, clientController);
			updateTestGuiController.DisplayTable(mainPane);
		} else {
			
			labelStatus.setText("test dont found ");
			labelStatus.setTextFill(Color.color(1, 0, 0));
			
			/*
			String errorMessage = Client.getErrorMessage();
			openDialog("Test dont found",errorMessage);*/
		}
	}
	
	public static void openDialog(String title, String message) {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle(title);
		dialog.setContentText(message);
		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();
	}

}
