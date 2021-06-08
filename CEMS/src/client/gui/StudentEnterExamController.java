package client.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentEnterExamController {

	@FXML
	private JFXTextField tfCode;

	@FXML
	private JFXButton btComputerizedExam;

	@FXML
	private JFXButton btManualExam;

	@FXML
	void OnClickEnterManualExam(ActionEvent event) {
		String code = tfCode.getText();
		MainGuiController.getMenuHandler().setManualTestScreen(code);
	}

	@FXML
	void OnClickEnterComputerizedExam(ActionEvent event) {
		String code = tfCode.getText();
		MainGuiController.getMenuHandler().setComputerizedTestScreen(code);
	}

}
