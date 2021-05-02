package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientController;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.Test;

public class UpdateTestGuiController implements Initializable {

	@FXML
	private TextField tfId;

	@FXML
	private TextField tfSubject;

	@FXML
	private TextField tfCourse;

	@FXML
	private TextField tfDuration;

	@FXML
	private TextField tfPointsPerQuestion;

	@FXML
	private Button btnUpdate;

	private static Test testToEdit;

	private static ClientController clientController;

	public UpdateTestGuiController() {
	}

	public UpdateTestGuiController(Test testToEdit, ClientController clientController) {
		super();
		this.testToEdit = testToEdit;
		this.clientController = clientController;
	}

	public void DisplayTable(BorderPane mainPane) {
		Pane editTestPane;
		try {
			editTestPane = (Pane) FXMLLoader.load(getClass().getResource("UpdateTestGui.fxml"));
			mainPane.setCenter(editTestPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (testToEdit != null) {
			tfId.setText(testToEdit.getId());
			tfId.setEditable(false);
			tfSubject.setText(testToEdit.getSubject());
			tfCourse.setText(testToEdit.getCourse());
			tfDuration.setText(testToEdit.getDuration());
			tfPointsPerQuestion.setText(testToEdit.getPointsPerQuestion());
		}
	}

	@FXML
	public void onUpdateClicked(ActionEvent event) {
		String id = tfId.getText();
		String subject = tfSubject.getText();
		String course = tfCourse.getText();
		String duration = tfDuration.getText();
		String ppq = tfPointsPerQuestion.getText();

		Test test = new Test(id, subject, course, duration, ppq);
		ModelWrapper<Test> modelWrapper = new ModelWrapper<>(test, ModelWrapper.UPDATE_TEST);
		ClientLoginGuiController.getClientController().sendClientUIRequest(modelWrapper);
	}

}
