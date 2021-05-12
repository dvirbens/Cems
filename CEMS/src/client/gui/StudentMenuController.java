package client.gui;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentMenuController {

	@FXML
	private JFXButton btnExecutedExams;

	@FXML
	private JFXButton btnEnterExam;

	@FXML
	private JFXButton btnLogout;

	enum Buttons {
		EXECUTED_EXAMS, ENTER_EXAM
	}

	@FXML
	void onClickExecutedExams(ActionEvent event) {
		paintSelectedButton(Buttons.EXECUTED_EXAMS);
		MainGuiController.getMenuHandler().setExecutedExamsScreen();
	}

	@FXML
	void onClickEnterExam(ActionEvent event) {
		paintSelectedButton(Buttons.ENTER_EXAM);
		MainGuiController.getMenuHandler().setEnterExamScreen();
	}

	@FXML
	void onClickLogout(ActionEvent event) {
		MainGuiController.getMenuHandler().setLoginMenu();
	}

	private void paintSelectedButton(Buttons button) {

		switch (button) {
		case EXECUTED_EXAMS:
			btnExecutedExams.setStyle("-fx-background-color:#48a832");
			btnEnterExam.setStyle("-fx-background-color:#333333");
			break;

		case ENTER_EXAM:
			btnExecutedExams.setStyle("-fx-background-color:#333333");
			btnEnterExam.setStyle("-fx-background-color:#48a832");
			break;
		}

	}

}
