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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

	@FXML
	private Label messageLabel;

	private static Question question;

	private static TableView<Question> tvQuestionPool;

	private static TableView<ExamQuestion> tvSelectedQuestion;

	private static boolean isWindowOpend;

	public void start() {
		Stage stage = new Stage();
		Pane mainPane;
		try {
			mainPane = (Pane) FXMLLoader.load(getClass().getResource("AddQuestion.fxml"));
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
		boolean typeNotPicked = true;
		NoteType type = NoteType.None;
		messageLabel.setStyle("-fx-text-fill: RED;");

		if (!note.isEmpty() && cbDisplayedFor.getSelectionModel().getSelectedItem() == null) {
			typeNotPicked = false;
			messageLabel.setText("You must choose which group to display");
		} else if (points.isEmpty() || !isNumeric(points)) {
			messageLabel.setText("Wrong point input, must enter number value");
		}

		if (cbDisplayedFor.getSelectionModel().getSelectedItem() == null) {
			type = NoteType.None;
		} else {
			if (cbDisplayedFor.getSelectionModel().getSelectedItem().equals("Students"))
				type = NoteType.Students;
			else
				type = NoteType.Teachers;
		}

		if (!points.isEmpty() && isNumeric(points) && typeNotPicked) {
			getTvQuestionPool().getItems().remove(getQuestion());
			ExamQuestion newQuestion = new ExamQuestion(getQuestion(), note, Integer.valueOf(points), type);
			JFXButton noteButton = new JFXButton();
			String noteTypeSelected = type.toString();
			noteButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Stage stage = new Stage();
					VBox vbox = new VBox();
					vbox.setSpacing(10);
					vbox.setAlignment(Pos.CENTER);
					Label title = new Label("Note description for " + noteTypeSelected);
					TextArea taNote = new TextArea();
					taNote.setText(newQuestion.getNote());
					taNote.setEditable(false);
					JFXButton close = new JFXButton();
					close.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Node n = (Node) event.getSource();
							Stage s = (Stage) n.getScene().getWindow();
							s.close();
						}
					});
					close.setPrefSize(90, 15);
					close.setStyle(
							"-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
					close.setText("Close");
					vbox.getChildren().add(title);
					vbox.getChildren().add(taNote);
					vbox.getChildren().add(close);
					Scene scene = new Scene(vbox, 360, 330);
					stage.setScene(scene);
					stage.setTitle("Note description");
					stage.show();
				}

			});
			noteButton.setPrefSize(90, 15);
			noteButton.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			noteButton.setText("Note");
			newQuestion.setNoteDetails(noteButton);
			getTvSelectedQuestion().getItems().add(newQuestion);
			CreateExamController.getExamQuestionList().add(newQuestion);
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			setWindowOpend(false);
		}

	}

	public static Question getQuestion() {
		return question;
	}

	public static void setQuestion(Question question) {
		AddQuestionController.question = question;
	}

	public static TableView<Question> getTvQuestionPool() {
		return tvQuestionPool;
	}

	public static void setTvQuestionPull(TableView<Question> tvQuestionPull) {
		AddQuestionController.tvQuestionPool = tvQuestionPull;
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

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}