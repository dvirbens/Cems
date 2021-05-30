package client.gui;

import static common.ModelWrapper.Operation.INSERT_STUDENT_TO_EXAM;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class StudentEnterExamController implements Initializable {

	@FXML
	private JFXTextField tfCode;

	@FXML
	private JFXButton btComputerizedExam;

	@FXML
	private JFXButton btManualExam;

	private ArrayList<String> elements;

	@FXML
	void OnClickEnterManualExam(ActionEvent event) {
		int ret = EnterExam("Manual");
		if (ret != -1)
			MainGuiController.getMenuHandler().setManualTestScreen();

	}

	@FXML
	void OnClickEnterComputerizedExam(ActionEvent event) {
		int ret = EnterExam("Computerized");
		if (ret != -1)
			MainGuiController.getMenuHandler().setComputerizedTestScreen();
	}

	int EnterExam(String type) {
		// String examID;
		if (tfCode.getText() != "") {
			String userID = Client.getUser().getUserID();
			elements.add(userID);
			elements.add(tfCode.getText());
			elements.add(type);
			ModelWrapper<String> modelWrapper = new ModelWrapper<String>(elements, INSERT_STUDENT_TO_EXAM);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);
			System.out.println(Client.getExamID());
			// examID = Client.getExamID();
			Client.setExamCode(tfCode.getText());
			return 1;
			// return Integer.parseInt(examID);
		}
		return -1;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		elements = new ArrayList<String>();
	}

}
