package client.gui;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static common.ModelWrapper.Operation.*;
import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML controller class for Enter an exam by the student in javaFX graphic user interface.
 * 
 * @author Shenhav, Aviel
 *
 */

public class StudentEnterExamController {

	@FXML
	private JFXTextField tfCode;

	@FXML
	private JFXButton btComputerizedExam;

	@FXML
	private JFXButton btManualExam;

	@FXML
	private Label lbl_Status;

	/*Set a manual exam screen if the code is correct.*/
	@FXML
	void OnClickEnterManualExam(ActionEvent event) {
		String code = tfCode.getText();
		int ret = EnterExam();
		if (ret != -1)
			MainGuiController.getMenuHandler().setManualTestScreen(code);
	}

	/*Set a computerize exam screen if the code is correct.*/
	@FXML
	void OnClickEnterComputerizedExam(ActionEvent event) {
		String code = tfCode.getText();
		int ret = EnterExam();
		if (ret != -1)
			MainGuiController.getMenuHandler().setComputerizedTestScreen(code);
	}

	/*Check if the code is already used by the same student nor it exist.*/
	int EnterExam() {

		if (!tfCode.getText().equals("")) {
			String userID = Client.getUser().getUserID();
			ArrayList<String> elements = new ArrayList<>();
			elements.add(tfCode.getText());
			elements.add(userID);
			ModelWrapper<String> modelWrapper = new ModelWrapper<String>(elements, CHECK_CODE_BEFORE_INSERTION);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);

			if (Client.getServerMessages().equals("Check passed successfully.")) {
				Client.setExamCode(tfCode.getText());
				return 1;
			} else if (Client.getServerMessages().equals("Invalid code.")) {
				lbl_Status.setText("Invalid code. Please try again.");
				return -1;
			} else if (Client.getServerMessages().equals("Student already did this exam.")) {
				lbl_Status.setText("You already did this exam.");
				return -1;
			}

		}
		lbl_Status.setText("Please enter code.");
		return -1;

	}

}
