package client.gui;

import static common.ModelWrapper.Operation.GET_EXECUTED_EXAM_LIST_BY_CREATOR;
import static common.ModelWrapper.Operation.GET_QUESTION_LIST_BY_EXAM_ID;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ExamQuestion;
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
	private BarChart<String, Integer> bcExamStatistic;

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
		tcDetails.setCellValueFactory(new PropertyValueFactory<ExecutedExam, JFXButton>("questionList"));

		ObservableList<ExecutedExam> executedExam = FXCollections.observableArrayList();
		List<ExecutedExam> executedExamList = Client.getExecExams();
		executedExamList = setExecutedExamsListQuestionListButtons(executedExamList);
		executedExam.addAll(executedExamList);
		tvExecutedExams.setItems(executedExam);

		tvExecutedExams.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				System.out.println(newSelection.getId());

				XYChart.Series<String, Integer> newStats = new XYChart.Series<>();
				newStats.setName("Algebra exam");
				newStats.getData().add(new XYChart.Data<>("Arik Zagdon", 20));
				newStats.getData().add(new XYChart.Data<>("Aviel Turgeman", 40));
				newStats.getData().add(new XYChart.Data<>("Dvir ben simon", 100));
				newStats.getData().add(new XYChart.Data<>("Shenhav Hezi", 90));
				newStats.getData().add(new XYChart.Data<>("Yakov Shitrit", 30));

				bcExamStatistic.getData().add(newStats);

			}
		});

		XYChart.Series<String, Integer> stats = new XYChart.Series<>();
		stats.setName("Math exam");
		stats.getData().add(new XYChart.Data<>("Arik Zagdon", 90));
		stats.getData().add(new XYChart.Data<>("Aviel Turgeman", 60));
		stats.getData().add(new XYChart.Data<>("Dvir ben simon", 100));
		stats.getData().add(new XYChart.Data<>("Shenhav Hezi", 70));
		stats.getData().add(new XYChart.Data<>("Yakov Shitrit", 50));

		bcExamStatistic.getData().add(stats);
	}

	private List<ExecutedExam> setExecutedExamsListQuestionListButtons(List<ExecutedExam> executedExamsList) {
		for (ExecutedExam exam : executedExamsList) {
			JFXButton questionListButton = new JFXButton();
			questionListButton.setPrefSize(90, 15);
			questionListButton
					.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			questionListButton.setText("List");
			questionListButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ModelWrapper<String> modelWrapper = new ModelWrapper<>(exam.getId(), GET_QUESTION_LIST_BY_EXAM_ID);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);
					List<ExamQuestion> questionList = Client.getExamQuestions();
					MainGuiController.getMenuHandler().setQuestionListScreen(questionList, "ExamStatisticController");

				}
			});

			exam.setQuestionList(questionListButton);
		}

		return executedExamsList;
	}

}
