package client.gui;

import static common.ModelWrapper.Operation.GET_EXECUTED_EXAM_LIST_BY_EXECUTOR;

import java.net.URL;
import java.util.ArrayList;
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
import models.ExecutedExamUI;

public class GradeApprovalController implements Initializable {

	@FXML
	private TableView<ExecutedExamUI> tvExecutedExams;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcExamID;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcSubject;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcCourse;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcDate;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcType;

	@FXML
	private TableColumn<ExecutedExamUI, JFXButton> tcStudentList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tcExamID.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("id"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("course"));
		tcDate.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("execDate"));
		tcType.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("testType"));
		tcStudentList.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, JFXButton>("gradeApproval"));

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(Client.getUser().getUserID(),
				GET_EXECUTED_EXAM_LIST_BY_EXECUTOR);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<ExecutedExamUI> executedExams = FXCollections.observableArrayList();
		List<ExecutedExamUI> executedExamsList = setExecutedExamsListUI(Client.getExecExams());
		executedExams.addAll(executedExamsList);
		tvExecutedExams.setItems(executedExams);

	}

	private List<ExecutedExamUI> setExecutedExamsListUI(List<ExecutedExam> executedExamsList) {
		List<ExecutedExamUI> executedExamsUIList = new ArrayList<>();
		
		for (ExecutedExam executedExam : executedExamsList) {
			ExecutedExamUI executedExamUI = new ExecutedExamUI(executedExam);
			JFXButton gradeApprovalButton = new JFXButton();
			gradeApprovalButton.setPrefSize(90, 15);
			gradeApprovalButton
					.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			gradeApprovalButton.setText("Students");
			gradeApprovalButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					MainGuiController.getMenuHandler().setStudentListsScreen(executedExam);
				}
			});

			executedExamUI.setGradeApproval(gradeApprovalButton);
			executedExamsUIList.add(executedExamUI);
		}

		return executedExamsUIList;
	}

}
