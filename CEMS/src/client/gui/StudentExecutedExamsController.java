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

public class StudentExecutedExamsController {
	
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


	void initialize(URL location, ResourceBundle resources) {
		
		tcStudentID.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("studentID"));
		tcExamID.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("ExamID"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("Subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("Course"));
		tcExecDate.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("ExecDate"));
		tcTestType.setCellValueFactory(new PropertyValueFactory<ExecExam, String>("TestType"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<ExecExam, Integer>("Grade"));
		tcGetTest.setCellValueFactory(new PropertyValueFactory<ExecExam, JFXButton>("Get Test"));

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(EXAM_EXECUTE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		ObservableList<ExecExam> exams = FXCollections.observableArrayList();
		System.out.println("TEST");
		exams.addAll(Client.getExecExams());
		tvExExams.setItems(exams);
		//setExamQuestioListButtons(Client.getExams());
	}
	

}
