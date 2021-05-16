package client.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class StartExamController {

	@FXML
	private JFXComboBox<?> cbExamCourse;

	@FXML
	private JFXComboBox<?> cbExamSubject;

	@FXML
	private TableView<?> tvExamPool;

	@FXML
	private JFXButton btnStartExam;

	@FXML
	private JFXTextField tfCode;

	@FXML
	void onClickExamSubject(ActionEvent event) {

	}

	@FXML
	void onClickStartExam(ActionEvent event) {

	}

}
