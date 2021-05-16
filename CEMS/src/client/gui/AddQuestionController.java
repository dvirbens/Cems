package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.ExamQuestion;
import models.ExamQuestion.NoteType;
import models.Question;

public class AddQuestionController implements EventHandler<WindowEvent>, Initializable {

	@FXML
	private JFXButton btnAddQuestion;

	@FXML
	private JFXTextField tfQuestionPoints;

	@FXML
	private JFXComboBox<String> cbDisplayedFor;

	@FXML
	private TextArea taNotes;

	private static Question question;

	private static TableView<Question> tvQuestionPull;

	private static TableView<ExamQuestion> tvSelectedQuestion;

	private static boolean isWindowOpend;

	public void start() {
		Stage stage = new Stage();
		BorderPane mainPane;
		try {
			mainPane = (BorderPane) FXMLLoader.load(getClass().getResource("AddQuestion.fxml"));
			Scene scene = new Scene(mainPane, 370, 310);
			stage.setScene(scene);
			stage.setTitle("Add Question");
			stage.show();
			stage.setOnHidden(this);
			setWindowOpend(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void onClickAddQuestion(ActionEvent event) {
		String note = taNotes.getText();
		String points = tfQuestionPoints.getText();
		NoteType type = NoteType.None;

		if (cbDisplayedFor.getSelectionModel().getSelectedItem().equals("Students"))
			type = NoteType.Students;
		else
			type = NoteType.Teachers;

		getTvQuestionPull().getItems().remove(getQuestion());
		ExamQuestion newQuestion = new ExamQuestion(getQuestion(), note, Integer.valueOf(points), type);
		JFXButton noteButton = new JFXButton();
		noteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println(newQuestion.getNote());
			}

		});
		noteButton.setPrefSize(90, 15);
		noteButton.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
		noteButton.setText("Note");
		newQuestion.setNoteDetails(noteButton);
		getTvSelectedQuestion().getItems().add(newQuestion);
		CreateExamController.getExamQuestionList().add(newQuestion);
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		setWindowOpend(false);
	}

	public static Question getQuestion() {
		return question;
	}

	public static void setQuestion(Question question) {
		AddQuestionController.question = question;
	}

	public static TableView<Question> getTvQuestionPull() {
		return tvQuestionPull;
	}

	public static void setTvQuestionPull(TableView<Question> tvQuestionPull) {
		AddQuestionController.tvQuestionPull = tvQuestionPull;
	}

	public static TableView<ExamQuestion> getTvSelectedQuestion() {
		return tvSelectedQuestion;
	}

	public static void setTvSelectedQuestion(TableView<ExamQuestion> tvSelectedQuestion) {
		AddQuestionController.tvSelectedQuestion = tvSelectedQuestion;
	}

	public static boolean isWindowOpend() {
		return isWindowOpend;
	}

	public static void setWindowOpend(boolean isWindowOpend) {
		AddQuestionController.isWindowOpend = isWindowOpend;
	}

	@Override
	public void handle(WindowEvent arg0) {
		setWindowOpend(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbDisplayedFor.getItems().add("Students");
		cbDisplayedFor.getItems().add("Teachers");
	}

}