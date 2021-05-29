package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PrincipalMenuController implements Initializable {

	@FXML
	private JFXButton btnExamPool;

	@FXML
	private JFXButton btnQuestionPool;

	@FXML
	private JFXButton btnOverallStatistics;

	@FXML
	private JFXButton btnTimeExtensionRequest;

	@FXML
	private JFXButton btnLogout;

	@FXML
	private Label labelWelcome;

	enum Buttons {
		OVERALL_STATISTICS, TIME_EXTENSION_REQUEST, EXAM_POOL, QUESTION_POOL
	}

	@FXML
	void onClickExamPool(ActionEvent event) {
		paintSelectedButton(Buttons.EXAM_POOL);
		MainGuiController.getMenuHandler().setExamPoolScreen();
	}

	@FXML
	void onClickQuestionPool(ActionEvent event) {
		paintSelectedButton(Buttons.QUESTION_POOL);
		MainGuiController.getMenuHandler().setQuestionPoolScreen();
	}

	@FXML
	void onClickOverallStatistics(ActionEvent event) {
		paintSelectedButton(Buttons.OVERALL_STATISTICS);
		MainGuiController.getMenuHandler().setOverallStatisticsScreen();
	}

	@FXML
	void onClickTimeExtensionRequest(ActionEvent event) {
		paintSelectedButton(Buttons.TIME_EXTENSION_REQUEST);
		MainGuiController.getMenuHandler().setTimeExtensionRequestsScreen();
	}

	@FXML
	void onClickLogout(ActionEvent event) {
		MainGuiController.getMenuHandler().setLoginMenu();
	}

	private void paintSelectedButton(Buttons button) {

		switch (button) {

		case EXAM_POOL:
			btnExamPool.setStyle("-fx-background-color:#48a832");
			btnQuestionPool.setStyle("-fx-background-color:#333333");
			btnOverallStatistics.setStyle("-fx-background-color:#333333");
			btnTimeExtensionRequest.setStyle("-fx-background-color:#333333");
			break;

		case QUESTION_POOL:
			btnExamPool.setStyle("-fx-background-color:#333333");
			btnQuestionPool.setStyle("-fx-background-color:#48a832");
			btnOverallStatistics.setStyle("-fx-background-color:#333333");
			btnTimeExtensionRequest.setStyle("-fx-background-color:#333333");
			break;

		case OVERALL_STATISTICS:
			btnExamPool.setStyle("-fx-background-color:#333333");
			btnQuestionPool.setStyle("-fx-background-color:#333333");
			btnOverallStatistics.setStyle("-fx-background-color:#48a832");
			btnTimeExtensionRequest.setStyle("-fx-background-color:#333333");
			break;

		case TIME_EXTENSION_REQUEST:
			btnExamPool.setStyle("-fx-background-color:#333333");
			btnQuestionPool.setStyle("-fx-background-color:#333333");
			btnOverallStatistics.setStyle("-fx-background-color:#333333");
			btnTimeExtensionRequest.setStyle("-fx-background-color:#48a832");
			break;

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String name = Client.getUser().getFirstName() + " " + Client.getUser().getLastName();
		String LabelPrint = "Welcome, " + name;
		labelWelcome.setText(LabelPrint);

	}

}
