package client.gui;

import static common.ModelWrapper.Operation.EXAM_EXECUTE;

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
import models.StudentExecutedExam;

public class ExecutedExamsController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<StudentExecutedExam> tvExExams;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcStudentID;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcExamID;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcSubject;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcCourse;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcExecDate;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcTestType;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcGrade;

	@FXML
	private TableColumn<StudentExecutedExam, JFXButton> tcGetTest;

	public void initialize(URL location, ResourceBundle resources) {
		tcExamID.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("examID"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("course"));
		tcExecDate.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("execDate"));
		tcTestType.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("testType"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("grade"));
		tcGetTest.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, JFXButton>("getCopy"));

		String userID = Client.getUser().getUserID();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(userID, EXAM_EXECUTE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<StudentExecutedExam> exams = FXCollections.observableArrayList();
		List<StudentExecutedExam> studentList = addCopyButtons(Client.getExecutedExamStudentList());
		exams.addAll(studentList);
		tvExExams.setItems(exams);
	}

	private List<StudentExecutedExam> addCopyButtons(List<StudentExecutedExam> executedExamStudentList) {

		for (StudentExecutedExam studentExam : executedExamStudentList) {

			if (studentExam.getTestType().equals("Manual")) {
				JFXButton getCopyButton = new JFXButton();
				getCopyButton.setPrefSize(90, 15);
				getCopyButton.setStyle(
						"-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
				getCopyButton.setText("Get Copy");

				getCopyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						MainGuiController.getMenuHandler().setStudentComputerizedTestReportScreen();
					}

				});
				studentExam.setGetCopy(getCopyButton);
			}

			if (!studentExam.isApproved())
				studentExam.setGrade("Not Checked");

		}
		return executedExamStudentList;
	}

}
