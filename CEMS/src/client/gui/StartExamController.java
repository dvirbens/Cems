package client.gui;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartExamController {

	@FXML
	private JFXButton btnComputerized;

	@FXML
	private JFXButton btnManual;

	@FXML
	void onClickComputerized(ActionEvent event) {
		MainGuiController.getMenuHandler().setComputerizedScreen();

	}

	@FXML
	void onClickManual(ActionEvent event) {
		MainGuiController.getMenuHandler().setManualScreen();
	}

}
