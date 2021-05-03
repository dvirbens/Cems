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

/**
 * FXML controller class for main screen in javaFX graphic user interface, let
 * the user interact with the main application graphic user interface.
 * 
 * @author Arikz
 *
 */
public class ClientMainGuiController {

	/**
	 * Value for storing the main layout of the application, in order to change the
	 * sub layout inside it.
	 */
	private static BorderPane mainPane;

	@FXML
	private Button btnSearch;

	@FXML
	private TextField tfId;

	@FXML
	private Label labelStatus;

	/**
	 * Getting the application current stage and set new main screen scene.
	 * 
	 * @param stage of javaFX application
	 */
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

	/**
	 * @param event
	 */
	@FXML
	void onSearchClick(ActionEvent event) {
		ModelWrapper<Test> modelWrapper = new ModelWrapper<>(null, ModelWrapper.LOAD_TEST_LIST);
		ClientLoginGuiController.getClientController().sendClientUIRequest(modelWrapper);
		List<Test> tests = new ArrayList<>();
		tests.addAll(Client.getTests());
		TableGuiController tableGuiController = new TableGuiController(tests);
		tableGuiController.DisplayTable(mainPane);

	}

	/**
	 * Handle "Edit test" button, by save test id value, sending user interface
	 * request from client to server and create new scene with all details about
	 * particular test given by test id.If the test id isn't exit display error to
	 * screen.
	 * 
	 * @param event
	 */
	@FXML
	void onClickEditTest(ActionEvent event) {
		String id = tfId.getText();
		ModelWrapper<String> modelWrapper = new ModelWrapper<>(id, ModelWrapper.LOAD_TEST);
		ClientLoginGuiController.getClientController().sendClientUIRequest(modelWrapper);
		Test editTest = Client.getEditTest();
		if (editTest != null) {
			labelStatus.setText("");
			UpdateTestGuiController updateTestGuiController = new UpdateTestGuiController(editTest);
			updateTestGuiController.DisplayTable(mainPane);
		} else {
			labelStatus.setText("test dont found ");
			labelStatus.setTextFill(Color.color(1, 0, 0));
		}
	}

}
