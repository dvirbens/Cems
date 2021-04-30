package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

	public UpdateTestGuiController() {
	}

	public UpdateTestGuiController(Test testToEdit) {
		super();
		this.testToEdit = testToEdit;
		
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
		tfId.setText(testToEdit.getId());
		tfId.setEditable(false);
		tfSubject.setText(testToEdit.getSubject());
		tfCourse.setText(testToEdit.getCourse());
		tfDuration.setText(testToEdit.getDuration());
		tfPointsPerQuestion.setText(testToEdit.getPointsPerQuestion());
		
	}
	
	

}
