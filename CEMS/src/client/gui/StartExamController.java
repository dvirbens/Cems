package client.gui;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML controller class for student enter a specific exam, manual nor computerize in javaFX graphic user interface.
 * 
 * @author Shenhav, Aviel
 *
 */
public class StartExamController {

	@FXML
	private JFXButton btnComputerized;

	@FXML
	private JFXButton btnManual;

	/*Set computerize exam screen*/
	@FXML
	void onClickComputerized(ActionEvent event) {
		MainGuiController.getMenuHandler().setComputerizedScreen();

	}
	/*Set click manual screen*/
	@FXML
	void onClickManual(ActionEvent event) {
		MainGuiController.getMenuHandler().setManualScreen();
	}

}
