package client.gui;

import static common.ModelWrapper.Operation.GET_USER;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.User;
import models.User.ErrorType;

/**
 * LoginMenuController class handle the login menu screen.
 *
 */
public class LoginMenuController implements Initializable {

	@FXML
	private JFXTextField tfUserName;

	@FXML
	private JFXPasswordField tfPassword;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private Label lWrongInput;

	@FXML
	private Label labelServerStatus;

	@FXML
	private Label labelStatus;

	/**
	 * On click try to login
	 * @param event
	 */
	@FXML
	void onClickLogin(ActionEvent event) {
		String userID = tfUserName.getText();
		String password = tfPassword.getText();

		if (!ClientUI.isServerStatus()) {
			lWrongInput.setText("Cant connect to server");
			lWrongInput.setVisible(true);

		} else {

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

	/**
	 * Setting default login input
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfUserName.setText("204459093");
		tfPassword.setText("1234");

		if (ClientUI.isServerStatus()) {
			labelStatus.setStyle("-fx-text-fill: GREEN;");
			labelStatus.setText("Online");
		} else {
			labelStatus.setStyle("-fx-text-fill: RED;");
			labelStatus.setText("Offline");
		}
	}

}
