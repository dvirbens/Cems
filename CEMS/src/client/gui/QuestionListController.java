package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.ExamQuestion;

public class QuestionListController implements Initializable {

	@FXML
	private TableView<ExamQuestion> tvQuestions;

	@FXML
	private TableColumn<ExamQuestion, String> tcID;

	@FXML
	private TableColumn<ExamQuestion, String> tcSubject;

	@FXML
	private TableColumn<ExamQuestion, String> tcTeacher;

	@FXML
	private TableColumn<ExamQuestion, Integer> tcPoints;

	@FXML
	private TableColumn<ExamQuestion, JFXButton> tcDetails;

	@FXML
	private TableColumn<ExamQuestion, JFXButton> tcNote;

	@FXML
	private JFXButton btnBack;

	private static List<ExamQuestion> questions;

	private static String backClassName;

	public QuestionListController() {

	}

	public QuestionListController(List<ExamQuestion> examQuestions, String backClassName) {
		questions = examQuestions;
		QuestionListController.backClassName = backClassName;
	}

	public void start() {
		try {
			Pane questionListPane = (Pane) FXMLLoader.load(getClass().getResource("QuestionList.fxml"));
			MainGuiController.getMenuHandler().getMainFrame().setCenter(questionListPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickBack(ActionEvent event) {
		switch (backClassName) {
		case "ExamStatisticController":
			MainGuiController.getMenuHandler().setExamStatisticScreen();
			break;

		case "ComputerizedExamController":
			MainGuiController.getMenuHandler().setComputerizedScreen();
			break;
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcID.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("questionID"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("subject"));
		tcTeacher.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("teacherName"));
		tcPoints.setCellValueFactory(new PropertyValueFactory<ExamQuestion, Integer>("points"));
		tcNote.setCellValueFactory(new PropertyValueFactory<ExamQuestion, JFXButton>("noteDetails"));
		tcDetails.setCellValueFactory(new PropertyValueFactory<ExamQuestion, JFXButton>("detailsButton"));

		setQuestionDetailButtons(questions);
		setQuestionNoteButtons(questions);

		ObservableList<ExamQuestion> examQuestions = FXCollections.observableArrayList();
		examQuestions.addAll(questions);
		tvQuestions.setItems(examQuestions);

	}

	public void setQuestionDetailButtons(List<ExamQuestion> questions) {
		for (ExamQuestion question : questions) {
			JFXButton detailsButton = new JFXButton();
			detailsButton.setPrefSize(90, 15);
			detailsButton
					.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			detailsButton.setText("Details");
			detailsButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (!QuestionDetailsController.isWindowOpend()) {
						QuestionDetailsController questionDetailsController = new QuestionDetailsController();
						QuestionDetailsController.setQuestion(question);
						questionDetailsController.start();
					}
				}

			});
			question.setDetailsButton(detailsButton);
		}
	}

	private void setQuestionNoteButtons(List<ExamQuestion> questions) {
		/*
		for (ExamQuestion question : questions) {
			JFXButton noteButton = new JFXButton();
			noteButton.setPrefSize(90, 15);
			noteButton.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			noteButton.setText("Note");
			noteButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println(question.getNote());
				}

			});
			question.setNoteDetails(noteButton);
		}
		*/
	}
}
