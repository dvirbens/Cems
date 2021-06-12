package client.gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML controller class for Student pages in javaFX graphic user interface, 
 * handle and set the page by what the student clicked. 
 * 
 * @author Shenhav, Aviel
 *
 */

public class StudentMenuController implements Initializable {

	@FXML
	private JFXButton btnExecutedExams;

	@FXML
	private JFXButton btnEnterExam;

	@FXML
	private JFXButton btnLogout;

	@FXML
	private Label labelWelcome;

	private static boolean locked;
	
	private static boolean closed = false;

	enum Buttons {
		EXECUTED_EXAMS, ENTER_EXAM
	}

	/*Set Executed Exams window on click*/
	@FXML
	void onClickExecutedExams(ActionEvent event) {
		if (locked)
		{
			if (setConfirmationPopup())
			{
				paintSelectedButton(Buttons.EXECUTED_EXAMS);
				MainGuiController.getMenuHandler().setExecutedExamsScreen();
				locked = false;
				closed = true;
			}
		}
		
		if (!locked) {
			paintSelectedButton(Buttons.EXECUTED_EXAMS);
			MainGuiController.getMenuHandler().setExecutedExamsScreen();
		}

	}

	/*Set EnterExam window on click*/
	@FXML
	void onClickEnterExam(ActionEvent event) {
		if (locked)
		{
			if (setConfirmationPopup())
			{
				paintSelectedButton(Buttons.ENTER_EXAM);
				MainGuiController.getMenuHandler().setEnterExamScreen();
				locked = false;
				closed = true;
			}
		}
		
		
		if (!locked) {
			paintSelectedButton(Buttons.ENTER_EXAM);
			MainGuiController.getMenuHandler().setEnterExamScreen();
		}
	}

	/*Set logout window on click*/
	@FXML
	void onClickLogout(ActionEvent event) {
		if (locked)
		{
			if (setConfirmationPopup())
			{
				MainGuiController.getMenuHandler().setLoginMenu();
				locked = false;
				closed = true;
			}
		}
		
		if (!locked) {
			MainGuiController.getMenuHandler().setLoginMenu();
		}
	}

	/**/
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
	
	/*Set a alert dialog on click menu*/
	public boolean setConfirmationPopup()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You are trying to exit exam");
		alert.setContentText("Are you sure?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true;
		} else {
		   return false;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String name = Client.getUser().getFirstName() + " " + Client.getUser().getLastName();
		String LabelPrint = "Welcome, " + name;
		labelWelcome.setText(LabelPrint);
	}

	@FXML
	void onLogoClicked(MouseEvent event) {
		MainGuiController.getMenuHandler().setStudentlMenu();
	}

	public static boolean isLocked() {
		return locked;
	}

	public static void setLocked(boolean locked) {
		StudentMenuController.locked = locked;
	}

	public static boolean isClosed() {
		return closed;
	}

	public static void setClosed(boolean closed) {
		StudentMenuController.closed = closed;
	}
	
	

}
