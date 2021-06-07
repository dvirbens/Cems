package client.gui;

import static common.ModelWrapper.Operation.EXAM_EXECUTE;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import models.StudentExecutedExam;
import models.StudentExecutedExamUI;

public class ExecutedExamsController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<StudentExecutedExamUI> tvExExams;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcStudentID;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcExamID;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcSubject;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcCourse;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcExecDate;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcTestType;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcGrade;

	@FXML
	private TableColumn<StudentExecutedExamUI, JFXButton> tcGetTest;

	public void initialize(URL location, ResourceBundle resources) {
		tcExamID.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("examID"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("course"));
		tcExecDate.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("execDate"));
		tcTestType.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("testType"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("grade"));
		tcGetTest.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, JFXButton>("getCopy"));

		String userID = Client.getUser().getUserID();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(userID, EXAM_EXECUTE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<StudentExecutedExamUI> exams = FXCollections.observableArrayList();
		List<StudentExecutedExamUI> studentList = setStudentsUI(Client.getExecutedExamStudentList());
		System.out.println(studentList);
		exams.addAll(studentList);
		tvExExams.setItems(exams);
	}

	private List<StudentExecutedExamUI> setStudentsUI(List<StudentExecutedExam> executedExamStudentList) {
		List<StudentExecutedExamUI> studentList = new ArrayList<>();
		for (StudentExecutedExam studentExam : executedExamStudentList) {
			StudentExecutedExamUI studenExamUI = new StudentExecutedExamUI(studentExam);
			if (studentExam.getTestType().equals("Manual")) {
				JFXButton getCopyButton = new JFXButton();
				getCopyButton.setPrefSize(90, 15);
				getCopyButton.setStyle(
						"-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
				getCopyButton.setText("Get Copy");

				getCopyButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						String path = System.getProperty("user.home") + "/Desktop";

						File outputFile = new File(path + "/TestAfterCheck.docx");
						try {
							FileOutputStream fos = new FileOutputStream(outputFile);
							BufferedOutputStream bos = new BufferedOutputStream(fos);
							bos.write(studentExam.getCopy().getMybytearray(), 0, studentExam.getCopy().getSize());
							bos.flush();
							fos.flush();
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				});
				studenExamUI.setGetCopy(getCopyButton);
			}

			if (!studentExam.isApproved())
				studenExamUI.setGrade("Not Checked");

			studentList.add(studenExamUI);

		}
		return studentList;
	}

}
