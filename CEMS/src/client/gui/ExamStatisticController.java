package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ExecutedExam;

public class ExamStatisticController implements Initializable {

	@FXML
	private JFXComboBox<String> cbExamCourse;

	@FXML
	private JFXComboBox<String> cbExamSubject;

	@FXML
	private TableView<ExecutedExam> tvExecutedExams;

	@FXML
	private TableColumn<ExecutedExam, String> tcID;

	@FXML
	private TableColumn<ExecutedExam, String> tcSubject;

	@FXML
	private TableColumn<ExecutedExam, String> tcCourse;

	@FXML
	private TableColumn<ExecutedExam, String> tcDate;

	@FXML
	private TableColumn<ExecutedExam, JFXButton> tcDetails;
	
    @FXML
    private TableColumn<ExecutedExam, String> tcTeacher;

	@FXML
	private Label avgLabel;

	@FXML
	private Label medLabel;

	@FXML
	private LineChart<String, Integer> clExamStatistic;

	@FXML
	void onClickExamCourse(ActionEvent event) {

	}

	@FXML
	void onClickExamSubject(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String teacherID = Client.getUser().getUserID();
		ModelWrapper<String> modelWrapper = new ModelWrapper<>(teacherID, GET_EXECUTED_EXAM_LIST_BY_CREATOR);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		tcID.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("id"));
		tcTeacher.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("executorTeacherName"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("course"));
		tcDate.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("execDate"));
		tcDetails.setCellValueFactory(new PropertyValueFactory<ExecutedExam, JFXButton>("detailsButton"));

		ObservableList<ExecutedExam> executedExam = FXCollections.observableArrayList();
		List<ExecutedExam> executedExamList = Client.getExecExams();
		executedExam.addAll(executedExamList);
		tvExecutedExams.setItems(executedExam);
		
		tvExecutedExams.setRowFactory(null);
		
		XYChart.Series<String, Integer> stats = new XYChart.Series<>();
		stats.setName("Math exam");
		stats.getData().add(new XYChart.Data<>("Arik Zagdon", 90));
		stats.getData().add(new XYChart.Data<>("Aviel Turgeman", 60));
		stats.getData().add(new XYChart.Data<>("Dvir ben simon", 80));
		stats.getData().add(new XYChart.Data<>("Shenhav Hezi", 70));
		stats.getData().add(new XYChart.Data<>("Yakov Shitrit", 50));

		clExamStatistic.getData().add(stats);
	}

}
