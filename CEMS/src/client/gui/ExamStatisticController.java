package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ExamStatisticController implements Initializable {

	@FXML
	private JFXComboBox<?> cbExamCourse;

	@FXML
	private JFXComboBox<?> cbExamSubject;

	@FXML
	private TableView<?> tvExecutedExams;

	@FXML
	private TableColumn<?, ?> tcID;

	@FXML
	private TableColumn<?, ?> tcSubject;

	@FXML
	private TableColumn<?, ?> tcCourse;

	@FXML
	private TableColumn<?, ?> tcDuration;

	@FXML
	private TableColumn<?, ?> tcQuestionList;

	@FXML
	private TableColumn<?, ?> tcStudentsList;

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
