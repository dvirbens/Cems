package client.gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Question;

public class AddQuestionController {

	@FXML
	private JFXButton btnAddQuestion;

	@FXML
	private JFXTextField tfQuestionPoints;

	@FXML
	private JFXComboBox<String> cbDisplayedFor;

	@FXML
	private TextArea taNotes;
	private Question question;

	public AddQuestionController() {

	}

	public AddQuestionController(Question question) {
		this.question = question;

	}

	public void start() {
		Stage stage = new Stage();
		BorderPane mainPane;
		try {
			mainPane = (BorderPane) FXMLLoader.load(getClass().getResource("AddQuestion.fxml"));
			Scene scene = new Scene(mainPane, 370, 310);
			stage.setScene(scene);
			stage.setTitle("Add Question");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void onClickAddQuestion(ActionEvent event) {

	}

}