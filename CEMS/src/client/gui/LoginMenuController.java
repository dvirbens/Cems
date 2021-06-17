package client.gui;

import static common.ModelWrapper.Operation.*;

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
	 * 
	 * @param event
	 */
	@FXML
	public void onClickLogin(ActionEvent event) {
		String userID = tfUserName.getText();
		String password = tfPassword.getText();
		User loggedInUser = loginUser(userID, password);
		switchScreenByUserType(loggedInUser);
	}

	public void switchScreenByUserType(User user) {
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
			lWrongInput.setText("User not found or already connected");
			lWrongInput.setVisible(true);
		}
	}

	public User loginUser(String userID, String password) {
		if (!ClientUI.isServerStatus()) {
			if (!Client.isStub()) {
				lWrongInput.setText("Cant connect to server");
				lWrongInput.setVisible(true);
			}
		} else {
			if (userID.isEmpty() || password.isEmpty()) {
				if (!Client.isStub()) {
					lWrongInput.setText("Empty fields");
					lWrongInput.setVisible(true);
				}
			} else {
				if (!Client.isStub()) {
					lWrongInput.setVisible(false);
					List<String> userInfo = new ArrayList<>();
					userInfo.add(userID);
					userInfo.add(password);
					// Sending request to the server via ClientUI
					ModelWrapper<String> modelWrapper = new ModelWrapper<>(userInfo, LOG_IN);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);
				}
				// Getting user from Client
				User user = Client.getUser();
				return user;
			}
		}
		return null;
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
