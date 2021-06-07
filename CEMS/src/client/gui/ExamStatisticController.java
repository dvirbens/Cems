package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import models.ExecutedExamUI;
import models.StudentExecutedExam;

public class ExamStatisticController implements Initializable {

	@FXML
	private JFXComboBox<String> cbExamCourse;

	@FXML
	private JFXComboBox<String> cbExamSubject;

	@FXML
	private TableView<ExecutedExamUI> tvExecutedExams;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcID;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcSubject;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcCourse;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcDate;

	@FXML
	private TableColumn<ExecutedExamUI, JFXButton> tcDetails;

	@FXML
	private TableColumn<ExecutedExamUI, String> tcTeacher;

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
		String loggendInTeacherID = Client.getUser().getUserID();
		ModelWrapper<String> modelWrapperExecutedExams = new ModelWrapper<>(loggendInTeacherID,
				GET_EXECUTED_EXAM_LIST_BY_CREATOR);
		ClientUI.getClientController().sendClientUIRequest(modelWrapperExecutedExams);

		tcID.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("id"));
		tcTeacher.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("executorTeacherName"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("course"));
		tcDate.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, String>("execDate"));
		tcDetails.setCellValueFactory(new PropertyValueFactory<ExecutedExamUI, JFXButton>("questionList"));

		ObservableList<ExecutedExamUI> executedExam = FXCollections.observableArrayList();
		List<ExecutedExamUI> executedExamList = setExecutedExamsListUI(Client.getExecExams());
		executedExam.addAll(executedExamList);
		tvExecutedExams.setItems(executedExam);

		tvExecutedExams.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						bcExamStatistic.setAnimated(false);
						bcExamStatistic.getData().clear();

						String examID = newSelection.getId();
						String date = newSelection.getExecDate();
						String teacherID = newSelection.getTeacherID();
						List<String> parameters = Arrays.asList(examID, date, teacherID);

						ModelWrapper<String> modelWrapper = new ModelWrapper<>(parameters,
								GET_EXECUTED_EXAM_STUDENT_LIST);
						ClientUI.getClientController().sendClientUIRequest(modelWrapper);
						List<StudentExecutedExam> studentList = Client.getExecutedExamStudentList();

						XYChart.Series<String, Integer> newStats = new XYChart.Series<>();
						newStats.setName(newSelection.getSubject() + " " + newSelection.getCourse());

						for (StudentExecutedExam sutdent : studentList) {
							newStats.getData().add(
									new XYChart.Data<>(sutdent.getStudentName(), Integer.parseInt(sutdent.getGrade())));
						}

						bcExamStatistic.getData().add(newStats);
					}
				});
			}
		});
	}

	private List<ExecutedExamUI> setExecutedExamsListUI(List<ExecutedExam> executedExamsList) {
		List<ExecutedExamUI> executedExamsUIList = new ArrayList<>();
		for (ExecutedExam executedExam : executedExamsList) {
			ExecutedExamUI executedExamUI = new ExecutedExamUI(executedExam);
			JFXButton questionListButton = new JFXButton();
			questionListButton.setPrefSize(90, 15);
			questionListButton
					.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			questionListButton.setText("List");
			questionListButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ModelWrapper<String> modelWrapper = new ModelWrapper<>(executedExam.getId(),
							GET_QUESTION_LIST_BY_EXAM_ID);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);
					List<ExamQuestion> questionList = Client.getExamQuestions();
					MainGuiController.getMenuHandler().setQuestionListScreen(questionList, "ExamStatisticController");

				}
			});

			executedExamUI.setQuestionList(questionListButton);
			executedExamsUIList.add(executedExamUI);
		}

		return executedExamsUIList;
	}

}
