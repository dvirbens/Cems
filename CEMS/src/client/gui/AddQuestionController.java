package client.gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Question;

public class AddQuestionController implements EventHandler<WindowEvent> {

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

	private static TableView<Question> tvSelectedQuestion;

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
		getTvQuestionPull().getItems().remove(getQuestion());
		getTvSelectedQuestion().getItems().add(getQuestion());
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

	public static TableView<Question> getTvSelectedQuestion() {
		return tvSelectedQuestion;
	}

	public static void setTvSelectedQuestion(TableView<Question> tvSelectedQuestion) {
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

}