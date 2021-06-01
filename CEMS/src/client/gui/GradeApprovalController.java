package client.gui;

import static common.ModelWrapper.Operation.GET_EXECUTED_EXAM_LIST_BY_EXECUTOR;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ExecutedExam;

public class GradeApprovalController implements Initializable {

	@FXML
	private TableView<ExecutedExam> tvExecutedExams;

	@FXML
	private TableColumn<ExecutedExam, String> tcExamID;

	@FXML
	private TableColumn<ExecutedExam, String> tcSubject;

	@FXML
	private TableColumn<ExecutedExam, String> tcCourse;

	@FXML
	private TableColumn<ExecutedExam, String> tcDate;

	@FXML
	private TableColumn<ExecutedExam, String> tcType;

	@FXML
	private TableColumn<ExecutedExam, JFXButton> tcStudentList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tcExamID.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("id"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("course"));
		tcDate.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("execDate"));
		tcType.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("testType"));
		tcStudentList.setCellValueFactory(new PropertyValueFactory<ExecutedExam, JFXButton>("gradeApproval"));

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(Client.getUser().getUserID(),
				GET_EXECUTED_EXAM_LIST_BY_EXECUTOR);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<ExecutedExam> executedExams = FXCollections.observableArrayList();
		List<ExecutedExam> executedExamsList = Client.getExecExams();
		executedExamsList = setExecutedExamsListGradeApprovalButtons(executedExamsList);
		executedExams.addAll(executedExamsList);
		tvExecutedExams.setItems(executedExams);

	}

	private List<ExecutedExam> setExecutedExamsListGradeApprovalButtons(List<ExecutedExam> executedExamsList) {

		for (ExecutedExam exam : executedExamsList) {
			JFXButton gradeApprovalButton = new JFXButton();
			gradeApprovalButton.setPrefSize(90, 15);
			gradeApprovalButton
					.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			gradeApprovalButton.setText("Students");
			gradeApprovalButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					MainGuiController.getMenuHandler().setStudentListsScreen(exam);
				}
			});

			exam.setGradeApproval(gradeApprovalButton);
		}

		return executedExamsList;
	}

}
