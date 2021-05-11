package client.gui;

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

	public void start() {
		
		Pane loginMenuPane, loginLogoPane;
		try {
			loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
			loginLogoPane = (Pane) FXMLLoader.load(getClass().getResource("LoginLogo.fxml"));
			MainGuiController.getMainPane().setLeft(loginMenuPane);
			MainGuiController.getMainPane().setCenter(loginLogoPane);
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
		MainGuiController.getClientController().sendClientUIRequest(modelWrapper);

		User user = Client.getUser();

		switch (user.getType()) {
		case Student:
			System.out.println("student menu");
			break;

		case Teacher:
			try {
				Pane teacherMenu = (Pane) FXMLLoader.load(getClass().getResource("TeacherMenu.fxml"));
				MainGuiController.getMainPane().setLeft(teacherMenu);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case Principal:
			System.out.println("principal menu");
			break;

		}

	}

}
