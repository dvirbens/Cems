package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.ExamQuestion;
import models.Question;

/**
 * AddNoteController class handle add note screen
 *
 */
public class AddNoteController implements Initializable {

	@FXML
	private JFXButton btnAddNote;

	@FXML
	private JFXTextField tfQuestionPoints;

	@FXML
	private JFXComboBox<String> cbDisplayedFor;

	@FXML
	private TextArea taNotes;

	@FXML
	private Label messageLabel;

	private static Question question;

	private static TableView<Question> tvQuestionPool;

	private static TableView<ExamQuestion> tvSelectedQuestion;

	/**
	 * This method load the fxml and display to the screen
	 */
	public void start() {
		Stage stage = new Stage();
		Pane mainPane;
		try {
			mainPane = (Pane) FXMLLoader.load(getClass().getResource("AddNote.fxml"));
			Scene scene = new Scene(mainPane, 370, 280);
			stage.setScene(scene);
			stage.setTitle("Add Note");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adding note on click
	 * @param event
	 */
	@FXML
	void onClickAddNote(ActionEvent event) {
		String note = taNotes.getText();

		messageLabel.setStyle("-fx-text-fill: RED;");
		
		if (note.isEmpty()) {
			messageLabel.setText("Note is empty");
		} else if (cbDisplayedFor.getSelectionModel().getSelectedItem() == null) {
			messageLabel.setText("You need to select group type");
		} else {

			if (cbDisplayedFor.getSelectionModel().getSelectedItem().equals("Teachers")) {
				CreateExamController.setTeacherNote(note);
			} else {
				CreateExamController.setStudentNote(note);
			}

			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();

		}

	}

	/**
	 * @return question
	 */
	public static Question getQuestion() {
		return question;
	}

	/**
	 * set question
	 * @param question
	 */
	public static void setQuestion(Question question) {
		AddNoteController.question = question;
	}

	/**
	 * @return tableview of question pool
	 */
	public static TableView<Question> getTvQuestionPool() {
		return tvQuestionPool;
	}

	/**
	 * set tableview of question pool
	 * @param tvQuestionPull
	 */
	public static void setTvQuestionPull(TableView<Question> tvQuestionPull) {
		AddNoteController.tvQuestionPool = tvQuestionPull;
	}

	/**
	 * @return selected question
	 */
	public static TableView<ExamQuestion> getTvSelectedQuestion() {
		return tvSelectedQuestion;
	}

	/**
	 * set selected question
	 * @param tvSelectedQuestion
	 */
	public static void setTvSelectedQuestion(TableView<ExamQuestion> tvSelectedQuestion) {
		AddNoteController.tvSelectedQuestion = tvSelectedQuestion;
	}

	/**
	 * combobox adding 2 options
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbDisplayedFor.getItems().add("Students");
		cbDisplayedFor.getItems().add("Teachers");
	}

}