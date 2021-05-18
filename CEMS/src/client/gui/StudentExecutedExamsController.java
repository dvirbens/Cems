package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

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
import models.Exam;
import models.ExecExam;

public class StudentExecutedExamsController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<ExecExam> tvExExams;

	@FXML
	private TableColumn<ExecExam, String> tcStudentID;

	@FXML
	private TableColumn<ExecExam, String> tcExamID;

	@FXML
	private TableColumn<ExecExam, String> tcSubject;

	@FXML
	private TableColumn<ExecExam, String> tcCourse;

	@FXML
	private TableColumn<ExecExam, String> tcExecDate;

	@FXML
	private TableColumn<ExecExam, String> tcTestType;

	@FXML
	private TableColumn<ExecExam, Integer> tcGrade;

	@FXML
	private TableColumn<ExecExam, JFXButton> tcGetTest;

	public void initialize(URL location, ResourceBundle resources) {
		tcExamID.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("id"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("course"));
		tcExecDate.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("execDate"));
		tcTestType.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("testType"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<ExecExam, Integer>("grade"));
		tcGetTest.setCellValueFactory(new PropertyValueFactory<ExecExam, JFXButton>("getCopy"));

		String userID = Client.getUser().getUserID();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(userID, EXAM_EXECUTE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<ExecExam> exams = FXCollections.observableArrayList();
		exams.addAll(Client.getExecExams());
		tvExExams.setItems(exams);

		setExamGetCopyButtons(Client.getExecExams());
	}
	
	private void setExamGetCopyButtons(List<ExecExam> exams) {

		for (ExecExam exam : exams) {
			JFXButton getCoptyButton = new JFXButton();
			getCoptyButton.setPrefSize(90, 15);
			getCoptyButton
					.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			getCoptyButton.setText("Get Copy");
			
			getCoptyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					//ModelWrapper<String> modelWrapper = new ModelWrapper<>(EXAM_EXECUTE);
					//ClientUI.getClientController().sendClientUIRequest(modelWrapper);
					
					System.out.println("HELLO");
				}

			});

			exam.setGetCopy(getCoptyButton);
		}
	}

}
