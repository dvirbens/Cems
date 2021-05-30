package client.gui;

import static common.ModelWrapper.Operation.GET_EXECUTED_EXAM_STUDENT_LIST;

import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.ExecutedExam;
import models.StudentExecutedExam;

public class StudentListController implements Initializable {

	@FXML
	private TableView<StudentExecutedExam> tvSutdents;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcExamId;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcStudent;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcGrade;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcCopyPercentage;

	@FXML
	private TableColumn<StudentExecutedExam, JFXButton> tcCopy;

	@FXML
	private TableColumn<StudentExecutedExam, JFXButton> tcApproval;

	@FXML
	private JFXButton btnBack;

	private ExecutedExam executedExam;

	public StudentListController() {
	}

	public StudentListController(ExecutedExam executedExam) {
		this.executedExam = executedExam;
	}

	public void start() {
		try {
			Pane studentListPane = (Pane) FXMLLoader.load(getClass().getResource("StudentList.fxml"));
			MainGuiController.getMenuHandler().getMainFrame().setCenter(studentListPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickBack(ActionEvent event) {
		MainGuiController.getMenuHandler().setGradeApprovalScreen();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ModelWrapper<ExecutedExam> modelWrapper = new ModelWrapper<>(executedExam, GET_EXECUTED_EXAM_STUDENT_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		tcExamId.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("examID"));
		tcStudent.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("studentName"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("grade"));
		tcCopyPercentage.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("CopyPercentage"));
		tcCopy.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, JFXButton>("copy"));
		tcApproval.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, JFXButton>("approvalButton"));

		ObservableList<StudentExecutedExam> executedExam = FXCollections.observableArrayList();
		List<StudentExecutedExam> executedExamStudentList = Client.getExecutedExamStudentList();
		executedExam.addAll(executedExamStudentList);
		tvSutdents.setItems(executedExam);

	}

}
