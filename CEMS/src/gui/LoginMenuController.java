package gui;

import static common.ModelWrapper.Operation.GET_USER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.User;

public class LoginMenuController {

	@FXML
	private JFXTextField tfUserName;

	@FXML
	private JFXTextField tfPassword;

	@FXML
	private JFXButton btnLogin;

	public void start(BorderPane mainPane) {
		Pane loginMenuPane, loginLogoPane;
		try {
			loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
			loginLogoPane = (Pane) FXMLLoader.load(getClass().getResource("LoginLogo.fxml"));
			mainPane.setLeft(loginMenuPane);
			mainPane.setCenter(loginLogoPane);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLogin(ActionEvent event) {
		String userID = tfUserName.getText();
		String password = tfPassword.getText();

		List<String> userInfo = new ArrayList<>();
		userInfo.add(userID);
		userInfo.add(password);

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(userInfo, GET_USER);
		ClientMainGuiController.getClientController().sendClientUIRequest(modelWrapper);

		User user = Client.getUser();

		switch (user.getType()) {
		case Student:
			System.out.println("student menu");
			break;

		case Teacher:
			System.out.println("teacher menu");
			break;

		case Principal:
			System.out.println("principal menu");
			break;

		}

	}

}
