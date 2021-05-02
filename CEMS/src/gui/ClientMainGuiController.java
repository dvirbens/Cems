package gui;

import java.util.ArrayList;
import java.util.List;

import client.Client;
import client.ClientController;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Test;

public class ClientMainGuiController {

	private static BorderPane mainPane;
	private static ClientController clientController;

	@FXML
	private Button btnSearch;

	@FXML
	private TextField tfId;

	@FXML
	private Label labelStatus;

	public void start(Stage stage) {
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
		ModelWrapper<String> modelWrapper = new ModelWrapper<>(id, ModelWrapper.LOAD_TEST);
		clientController.sendClientUIRequest(modelWrapper);
		Test editTest = Client.getEditTest();
		if (editTest != null) {
			labelStatus.setText("");
			UpdateTestGuiController updateTestGuiController = new UpdateTestGuiController(editTest, clientController);
			updateTestGuiController.DisplayTable(mainPane);
		} else {

			labelStatus.setText("test dont found ");
			labelStatus.setTextFill(Color.color(1, 0, 0));
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
