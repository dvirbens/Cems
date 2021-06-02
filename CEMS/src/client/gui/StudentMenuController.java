package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class StudentMenuController implements Initializable {

	@FXML
	private JFXButton btnExecutedExams;

	@FXML
	private JFXButton btnEnterExam;

	@FXML
	private JFXButton btnLogout;
	

    @FXML
    private Label labelWelcome;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String name = Client.getUser().getFirstName()+" "+Client.getUser().getLastName();
		String LabelPrint="Welcome, "+name;
		labelWelcome.setText(LabelPrint);
	}
	

    @FXML
    void onLogoClicked(MouseEvent event) {
    	MainGuiController.getMenuHandler().setStudentlMenu();

    }

}
