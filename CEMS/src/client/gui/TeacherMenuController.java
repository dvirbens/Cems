package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TeacherMenuController implements Initializable{

	@FXML
	private JFXButton btnCreateExam;

	@FXML
	private JFXButton btnCreateQuestion;

	@FXML
	private JFXButton btnStartExam;

	@FXML
	private JFXButton btnStatistic;

	@FXML
	private JFXButton btnGradeApproval;

	@FXML
	private JFXButton btnLogout;
	
	
    @FXML
    private Label labelWelcome;
    
    

	enum Buttons {
		CREATE_EXAM, CREATE_QUESTION, EXAM_STATISTIC, START_EXAM, GRADE_APPROVAL
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

	@FXML
	void onClickGradeApproval(ActionEvent event) {
		paintSelectedButton(Buttons.GRADE_APPROVAL);
		MainGuiController.getMenuHandler().setGradeApprovalScreen();
	}

	@FXML
	void onClickLogout(ActionEvent event) {
		MainGuiController.getMenuHandler().setLoginMenu();
	}

	private void paintSelectedButton(Buttons button) {

		switch (button) {
		case CREATE_EXAM:
			btnCreateExam.setStyle("-fx-background-color:#48a832");
			btnCreateQuestion.setStyle("-fx-background-color:#333333");
			btnStartExam.setStyle("-fx-background-color:#333333");
			btnStatistic.setStyle("-fx-background-color:#333333");
			btnGradeApproval.setStyle("-fx-background-color:#333333");
			break;

		case CREATE_QUESTION:
			btnCreateExam.setStyle("-fx-background-color:#333333");
			btnCreateQuestion.setStyle("-fx-background-color:#48a832");
			btnStartExam.setStyle("-fx-background-color:#333333");
			btnStatistic.setStyle("-fx-background-color:#333333");
			btnGradeApproval.setStyle("-fx-background-color:#333333");
			break;

		case EXAM_STATISTIC:
			btnCreateExam.setStyle("-fx-background-color:#333333");
			btnCreateQuestion.setStyle("-fx-background-color:#333333");
			btnStartExam.setStyle("-fx-background-color:#333333");
			btnStatistic.setStyle("-fx-background-color:#48a832");
			btnGradeApproval.setStyle("-fx-background-color:#333333");
			break;

		case START_EXAM:
			btnCreateExam.setStyle("-fx-background-color:#333333");
			btnCreateQuestion.setStyle("-fx-background-color:#333333");
			btnStartExam.setStyle("-fx-background-color:#48a832");
			btnStatistic.setStyle("-fx-background-color:#333333");
			btnGradeApproval.setStyle("-fx-background-color:#333333");
			break;

		case GRADE_APPROVAL:
			btnCreateExam.setStyle("-fx-background-color:#333333");
			btnCreateQuestion.setStyle("-fx-background-color:#333333");
			btnStartExam.setStyle("-fx-background-color:#333333");
			btnStatistic.setStyle("-fx-background-color:#333333");
			btnGradeApproval.setStyle("-fx-background-color:#48a832");
			break;
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String name = Client.getUser().getFirstName()+" "+Client.getUser().getLastName();
		String LabelPrint="Welcome, "+name;
		labelWelcome.setText(LabelPrint);

	
	}

}
