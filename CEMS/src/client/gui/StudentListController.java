package client.gui;

import static common.ModelWrapper.Operation.GET_EXECUTED_EXAM_STUDENT_LIST;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
	private TableColumn<StudentExecutedExam, CheckBox> tcApproval;

	@FXML
	private JFXButton btnBack;

	@FXML
	private JFXButton btnSave;

	@FXML
	private Label messageLabel;

	private static ExecutedExam executedExam;

	List<StudentExecutedExam> executedExamStudentList;

	public StudentListController() {
	}

	public StudentListController(ExecutedExam executedExam) {
		StudentListController.executedExam = executedExam;
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

	@FXML
	void onClickSave(ActionEvent event) {
		for (StudentExecutedExam student : executedExamStudentList) {
			if (student.isApproved()) {
				System.out.println(student.getStudentName());
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String examID = executedExam.getId();
		String date = executedExam.getExecDate();
		String teacherID = executedExam.getTeacherID();
		List<String> parameters = Arrays.asList(examID, date, teacherID);

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(parameters, GET_EXECUTED_EXAM_STUDENT_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		tcExamId.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("examID"));
		tcStudent.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("studentName"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("grade"));
		tcCopyPercentage.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("CopyPercentage"));
		tcCopy.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, JFXButton>("copy"));
		tcApproval.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, CheckBox>("gradeApproval"));

		ObservableList<StudentExecutedExam> executedExam = FXCollections.observableArrayList();
		executedExamStudentList = Client.getExecutedExamStudentList();
		setExecutedExamStudentListCheckBoxes();
		executedExam.addAll(executedExamStudentList);
		tvSutdents.setItems(executedExam);

	}

	private void setExecutedExamStudentListCheckBoxes() {

		for (StudentExecutedExam student : executedExamStudentList) {
			CheckBox approveGrade = new CheckBox();
			approveGrade.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						student.setApproved(true);
					} else {
						student.setApproved(false);
					}
				}
			});
			student.setGradeApproval(approveGrade);
		}
	}

}
