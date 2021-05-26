package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import static common.ModelWrapper.Operation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private TableColumn<ExecutedExam, String> tcDuration;

	@FXML
	private TableColumn<ExecutedExam, JFXButton> tcQuestionList;

	@FXML
	private TableColumn<ExecutedExam, JFXButton> tcStudentsList;

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
		ModelWrapper<String> modelWrapper = new ModelWrapper<>(teacherID, GET_EXECUTED_EXAM_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		
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
