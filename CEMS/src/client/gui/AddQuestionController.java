package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.gui.CreateExamController.AddButtonEvent;

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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
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
		System.out.println(tvQuestionPool.getItems());
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

			System.out.println(getTvQuestionPool().getItems());
			getTvQuestionPool().getItems().remove(getQuestion());
//
			ExamQuestion newQuestion = new ExamQuestion(getQuestion(), note, Integer.valueOf(points), type);
			String selecteddQuestionID = newQuestion.getQuestionID();
			JFXButton removeButton = new JFXButton();
			String noteTypeSelected = type.toString();
			removeButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					ExamQuestion todel = null;
					for (ExamQuestion qustion : CreateExamController.getExamQuestionList()) {
						if (qustion.getQuestionID().equals(selecteddQuestionID)) {
							todel = qustion;
						}

					}

					Question addQustion = new Question(todel.getQuestionID(), todel.getTeacherName(),
							todel.getSubject(), todel.getDetails(), todel.getAnswer1(), todel.getAnswer2(),
							todel.getAnswer3(), todel.getAnswer4(), todel.getCorrectAnswer(), todel.getDetailsButton());

					JFXButton addButton = new JFXButton();
					addButton.setPrefSize(70, 15);
					addButton.setStyle(
							"-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
					addButton.setText("Add");

					CreateExamController ic = new CreateExamController();
					addButton.setOnAction(ic.new AddButtonEvent(addQustion, tvQuestionPool, tvSelectedQuestion));
					addQustion.setAddButton(addButton);

					getTvQuestionPool().getItems().add(addQustion);
					CreateExamController.getExamQuestionList().remove(todel);
					getTvSelectedQuestion().getItems().remove(todel);

				}

			});

			removeButton.setPrefSize(90, 15);
			removeButton.setStyle("-fx-font-size:15;" + "-fx-background-color:#FF6366;" + "-fx-text-fill:white;"
					+ "-fx-font-weight: bold;" + "-fx-border-color:red;");
			removeButton.setShape(new Circle(2));
			removeButton.setMaxSize(2, 2);
			removeButton.setText("-");

			newQuestion.setRemoveButton(removeButton);
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