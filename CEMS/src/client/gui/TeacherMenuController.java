package client.gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class TeacherMenuController {

	@FXML
	private JFXButton btnCreateExam;

	@FXML
	private JFXButton btnCreateQuestion;

	@FXML
	private JFXButton btnStartExam;

	@FXML
	private JFXButton btnStatistic;

	enum Buttons {
		CREATE_EXAM, CREATE_QUESTION, EXAM_STATISTIC, START_EXAM
	}

	@FXML
	void onClickCreateExam(ActionEvent event) {
		paintSelectedButton(Buttons.CREATE_EXAM);

		MainGuiController.getMenuHandler().setCreateExamScreen();

	}

	@FXML
	void onClickCreateQuestion(ActionEvent event) {
		paintSelectedButton(Buttons.CREATE_QUESTION);
		MainGuiController.getMenuHandler().setCreateQuestionScreen();
	}

	@FXML
	void onClickStartExam(ActionEvent event) {
		paintSelectedButton(Buttons.START_EXAM);
		MainGuiController.getMenuHandler().setStartExamScreen();
	}

	@FXML
	void onClickExamStatistic(ActionEvent event) {
		paintSelectedButton(Buttons.EXAM_STATISTIC);
		MainGuiController.getMenuHandler().setExamStatisticScreen();
	}

	private void paintSelectedButton(Buttons button) {

		switch (button) {
		case CREATE_EXAM:
			btnCreateExam.setStyle("-fx-background-color:#48a832");
			btnCreateQuestion.setStyle("-fx-background-color:#333333");
			btnStartExam.setStyle("-fx-background-color:#333333");
			btnStatistic.setStyle("-fx-background-color:#333333");
			break;

		case CREATE_QUESTION:
			btnCreateExam.setStyle("-fx-background-color:#333333");
			btnCreateQuestion.setStyle("-fx-background-color:#48a832");
			btnStartExam.setStyle("-fx-background-color:#333333");
			btnStatistic.setStyle("-fx-background-color:#333333");
			break;

		case EXAM_STATISTIC:
			btnCreateExam.setStyle("-fx-background-color:#333333");
			btnCreateQuestion.setStyle("-fx-background-color:#333333");
			btnStartExam.setStyle("-fx-background-color:#333333");
			btnStatistic.setStyle("-fx-background-color:#48a832");
			break;

		case START_EXAM:
			btnCreateExam.setStyle("-fx-background-color:#333333");
			btnCreateQuestion.setStyle("-fx-background-color:#333333");
			btnStartExam.setStyle("-fx-background-color:#48a832");
			btnStatistic.setStyle("-fx-background-color:#333333");
			break;
		}

	}

}
