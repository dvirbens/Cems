package client.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MenuHandler {

	private BorderPane mainFrame;

	public MenuHandler(BorderPane mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void setLoginMenu() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
			Pane loginLogoPane = (Pane) FXMLLoader.load(getClass().getResource("LoginLogo.fxml"));
			mainFrame.setLeft(loginMenuPane);
			mainFrame.setCenter(loginLogoPane);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setTeacherMenu() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("TeacherMenu.fxml"));
			mainFrame.setLeft(loginMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPrincipalMenu() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
			mainFrame.setLeft(loginMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCreateExamScreen() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("CreateExam.fxml"));
			mainFrame.setCenter(loginMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCreateQuestionScreen() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("CreateQuestion.fxml"));
			mainFrame.setCenter(loginMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStartExamScreen() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("LoginLogo.fxml"));
			mainFrame.setCenter(loginMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setExamStatisticScreen() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("ExamStatistic.fxml"));
			mainFrame.setCenter(loginMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
