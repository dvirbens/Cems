package client.gui;

import static common.ModelWrapper.Operation.EXAM_EXECUTE;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.StudentExecutedExam;
import models.WordFile;

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
		
		exams.addAll(Client.getExecutedExamStudentList());
		for (StudentExecutedExam exam : exams)
		{
			if (exam.getApproved().equals("0"))
			{
				exam.setGrade("Not checked");
			}
		}
		tvExExams.setItems(exams);

		setExamGetCopyButtons(Client.getExecutedExamStudentList());
	}
	
	private void setExamGetCopyButtons(List<StudentExecutedExam> exams) {

		for (StudentExecutedExam exam : exams) {
			if (exam.getTestType().equals("Manual") && (exam.getCopy().getSize() != 0))
			{
				JFXButton getCopyButton = new JFXButton();
				getCopyButton.setPrefSize(90, 15);
				getCopyButton
						.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
				getCopyButton.setText("Get Copy");
				
				getCopyButton.setOnAction(new EventHandler<ActionEvent>() {
	
					@Override
					public void handle(ActionEvent event) {

						String path = System.getProperty("user.home") + "/Desktop";
						
						File outputFile = new File(path + "/TestAfterCheck.docx");
						try {
							FileOutputStream fos = new FileOutputStream(outputFile);
							BufferedOutputStream bos = new BufferedOutputStream(fos);
							bos.write(exam.getCopy().getMybytearray(), 0, exam.getCopy().getSize());
							bos.flush();
							fos.flush();
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
	
				});
				exam.setGetCopy(getCopyButton);
			}
			
		}
	}
	

}
