package client.gui;

import static common.ModelWrapper.Operation.GET_USER;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.User;
import models.User.ErrorType;

public class LoginMenuController {

	@FXML
	private JFXTextField tfUserName;

	@FXML
    private JFXPasswordField tfPassword;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private Label lWrongInput;

	@FXML
	void onClickLogin(ActionEvent event) {
		String userID = tfUserName.getText();
		String password = tfPassword.getText();

		if (userID.isEmpty() || password.isEmpty()) {
			lWrongInput.setText("Empty fields");
			lWrongInput.setVisible(true);
		} else {
			lWrongInput.setVisible(false);
			List<String> userInfo = new ArrayList<>();
			userInfo.add(userID);
			userInfo.add(password);

			ModelWrapper<String> modelWrapper = new ModelWrapper<>(userInfo, GET_USER);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);

			User user = Client.getUser();
			if (user != null) {
				if (user.getUserID() != null) {
					switch (user.getType()) {
					case Student:
						MainGuiController.getMenuHandler().setStudentlMenu();
						break;

					case Teacher:
						MainGuiController.getMenuHandler().setTeacherMenu();
						break;

					case Principal:
						MainGuiController.getMenuHandler().setPrincipalMenu();
						break;
					}
				} else {
					if (user.getError() == ErrorType.WRONG_ID) {
						lWrongInput.setText("User not found");
						lWrongInput.setVisible(true);
					}

					if (user.getError() == ErrorType.WRONG_PASSWORD) {
						lWrongInput.setText("Wrong password");
						lWrongInput.setVisible(true);
					}
				}
			} else {
				lWrongInput.setText("User not found");
				lWrongInput.setVisible(true);
			}
		}
	}

}
