package client.gui;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrincipalMenuController {

	@FXML
	private JFXButton btnOverallStatistics;

	@FXML
	private JFXButton btnTimeExtensionRequest;

	enum Buttons {
		OVERALL_STATISTICS, TIME_EXTENSION_REQUEST
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

	private void paintSelectedButton(Buttons button) {

		switch (button) {
		case OVERALL_STATISTICS:
			btnOverallStatistics.setStyle("-fx-background-color:#48a832");
			btnTimeExtensionRequest.setStyle("-fx-background-color:#333333");
			break;

		case TIME_EXTENSION_REQUEST:
			btnOverallStatistics.setStyle("-fx-background-color:#333333");
			btnTimeExtensionRequest.setStyle("-fx-background-color:#48a832");
			break;
		}

	}

}
