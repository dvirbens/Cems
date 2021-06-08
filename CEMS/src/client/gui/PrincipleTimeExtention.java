package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ExamExtension;
import models.ExamQuestion;

public class PrincipleTimeExtention implements Initializable {

	@FXML
	private TableView<ExamExtension> tvExtension;

	@FXML
	private TableColumn<ExamExtension, String> tcExamID;

	@FXML
	private TableColumn<ExamExtension, String> tcTeacherName;

	@FXML
	private TableColumn<ExamExtension, String> tcDuration;

	@FXML
	private TableColumn<ExamExtension, String> tcExtension;

	@FXML
	private TableColumn<ExamExtension, String> tcCause;

	@FXML
	private TableColumn<ExamExtension, JFXButton> tcConfirm;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tcExamID.setCellValueFactory(new PropertyValueFactory<ExamExtension, String>("examID"));
		tcTeacherName.setCellValueFactory(new PropertyValueFactory<ExamExtension, String>("teacherName"));
		tcDuration.setCellValueFactory(new PropertyValueFactory<ExamExtension, String>("examDuration"));
		tcExtension.setCellValueFactory(new PropertyValueFactory<ExamExtension, String>("timeExtension"));
		tcCause.setCellValueFactory(new PropertyValueFactory<ExamExtension, String>("casue"));

	}

}
