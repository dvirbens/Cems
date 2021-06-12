package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.ExecutedExam;
import models.StudentExecutedExam;

public class OverallStatistic implements Initializable {

	@FXML
	private Pane PaneOverallStatistic;

	@FXML
	private HBox hBoxSelection;

	@FXML
	private JFXComboBox<String> courseSelect;

	@FXML
	private JFXButton displayBtn;

	@FXML
	private ToggleGroup statisticBy;

	@FXML
	private JFXRadioButton slct_course;

	@FXML
	private JFXRadioButton slct_teacher;

	@FXML
	private JFXRadioButton slct_student;

	@FXML
	private CategoryAxis x_Exam;

	@FXML
	private NumberAxis y_Grades;

	@FXML
	private Label avg;

	@FXML
	private Label median;

	@FXML
	private BarChart<String, Double> graph;

	@FXML
	private JFXTextArea statisticNavigation;

	@FXML
	private HBox HboxFiltter;

	@FXML
	private JFXTextField teacherFiltter;

	@FXML
	private JFXTextField studentFiltter;

	@FXML
	void studentFiltteringBy(ActionEvent event) {

	}

	@FXML
	void teacherFiltteringBy(ActionEvent event) {

	}

	@FXML
	private VBox VboxGraph;

	public enum Operation {
		COURSE, STUDENT, TEACHER
	};

	private Operation op = Operation.COURSE;

	private Object executedExamStudentList;

	public enum StatisticBy {
		StatisticByCourse, StatisticByTeacher, StatisticByStudent
	};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		courseSelect.getItems().addAll(Client.getSubjectCollection().getCourses());

		teacherFiltter.setVisible(false);
		studentFiltter.setVisible(false);
		courseSelect.setVisible(false);
	}

	@SuppressWarnings("unchecked")
	@FXML
	void diplayGrapgh(ActionEvent event) {
		switch (op) {
		case COURSE:
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					graph.setAnimated(false);
					graph.getData().clear();

					String course_select = courseSelect.getSelectionModel().getSelectedItem();
					ModelWrapper<String> modelWrapper = new ModelWrapper<>(course_select,
							GET_EXECUTED_EXAM_LIST_BY_COURSE);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);

					List<ExecutedExam> executedExamList = Client.getExecExams();
					XYChart.Series<String, Double> series = new XYChart.Series<>();

					for (ExecutedExam exams : executedExamList) {
						String newData = exams.getExecutorTeacherName() + "\n" + exams.getExecDate() + "\n"
								+ exams.getExecTime();
						series.getData().add(new XYChart.Data<String, Double>(newData, Double.valueOf(exams.getAvg())));
					}
					graph.getData().addAll(series);

					double[] avgAndMedian = getAvarageAndMedian(executedExamList);
					if (avgAndMedian != null) {
						String avarageStr = String.valueOf(avgAndMedian[0]);
						String medianStr = String.valueOf(avgAndMedian[1]);
						avg.setText(avarageStr);
						median.setText(medianStr);
					}
				}
			});
			break;

		case STUDENT:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					graph.setAnimated(false);
					graph.getData().clear();
					String student_select = studentFiltter.getText();
					ModelWrapper<String> modelWrapper = new ModelWrapper<>(student_select,
							GET_EXECUTED_EXAM_LIST_BY_STUDENT);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);

					List<StudentExecutedExam> executedExamsList = Client.getExecutedExamStudentList();
					XYChart.Series<String, Double> series = new XYChart.Series<>();
					System.out.println(executedExamsList);
					for (StudentExecutedExam student_exams : executedExamsList) {
						String newData = student_exams.getCourse() + "\n" + student_exams.getExecDate() + "\n"
								+ student_exams.getGrade();
						series.getData().add(new XYChart.Data<String, Double>(newData,
								Double.parseDouble(student_exams.getGrade())));
					}

					graph.getData().addAll(series);

					double[] avgAndMedian = getAvarageAndMedian(executedExamsList);
					if (avgAndMedian != null) {
						String avarageStr = String.valueOf(avgAndMedian[0]);
						String medianStr = String.valueOf(avgAndMedian[1]);
						avg.setText(avarageStr);
						median.setText(medianStr);
					}
				}
			});
			break;
		case TEACHER:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					graph.setAnimated(false);
					graph.getData().clear();

					String teacherID = teacherFiltter.getText();
					ModelWrapper<String> modelWrapper = new ModelWrapper<>(teacherID,
							GET_EXECUTED_EXAM_LIST_BY_TEACHER);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);
					List<ExecutedExam> executedExamList = Client.getExecExams();
					System.out.println(executedExamList);
					XYChart.Series<String, Double> series = new XYChart.Series<>();

					for (ExecutedExam executedExam : executedExamList) {
						if (executedExam.isApproved()) {
							String newData = executedExam.getId() + "\n" + executedExam.getCourse() + "\n"
									+ executedExam.getExecDate() + "\n" + executedExam.getExecTime();
							series.getData().add(new XYChart.Data<String, Double>(newData, executedExam.getAvg()));
						}

					}
					graph.getData().addAll(series);

					double[] avgAndMedian = getAvarageAndMedian(executedExamList);
					if (avgAndMedian != null) {
						String avarageStr = String.valueOf(avgAndMedian[0]);
						String medianStr = String.valueOf(avgAndMedian[1]);
						avg.setText(avarageStr);
						median.setText(medianStr);
					}

				}
			});
			break;
		default:
			break;
		}
	}

	@FXML
	void additional_filterByCourse(ActionEvent event) {

	}

	@FXML
	void StatisticNoteNavigation(ActionEvent event) {

	}

	@FXML
	void statisticByCourse(ActionEvent event) {

		courseSelect.setVisible(true);
		teacherFiltter.setVisible(false);
		studentFiltter.setVisible(false);
		statisticNavigation
				.setText("Selection of statistics by course, shows the grades of all students in the course.");
		StatisticNoteNavigation(event);

		op = Operation.COURSE;
	}

	@FXML
	void statisticByStudent(ActionEvent event) {
		studentFiltter.setVisible(true);
		courseSelect.setVisible(false);
		teacherFiltter.setVisible(false);

		statisticNavigation
				.setText("Selection of statistics by student, shows all the grades that the student received");
		StatisticNoteNavigation(event);

		courseSelect.setVisible(false);
		teacherFiltter.setVisible(false);

		op = Operation.STUDENT;
	}

	@FXML
	void statisticByTeacher(ActionEvent event) {
		teacherFiltter.setVisible(true);
		studentFiltter.setVisible(false);
		courseSelect.setVisible(false);

		statisticNavigation.setText(
				"Selection of statistics by teacher, shows the scores of all exams belonging to the same teacher");
		StatisticNoteNavigation(event);

		op = Operation.TEACHER;
	}

	private double[] getAvarageAndMedian(List<?> examList) {
		if (!examList.isEmpty()) {

			List<Double> grades = new ArrayList<>();
			int sum = 0;

			for (Object exam : examList) {
				double examGrade = 0;

				if (exam instanceof ExecutedExam)
					examGrade = ((ExecutedExam) exam).getAvg();
				else if (exam instanceof StudentExecutedExam)
					examGrade = Integer.valueOf(((StudentExecutedExam) exam).getGrade());

				sum += examGrade;
				grades.add(examGrade);

			}

			grades.sort(new Comparator<Double>() {

				@Override
				public int compare(Double o1, Double o2) {
					return (int) (o1 - o2);
				}
			});

			double avg = sum / grades.size();
			double median;

			int n = grades.size();
			if (n % 2 == 0) {
				double firstGrade = grades.get((n / 2) - 1);
				double secondGrade = grades.get((n / 2));
				median = (firstGrade + secondGrade) / 2;
			} else {
				median = grades.get(((n + 1) / 2) - 1);
			}
			double[] avgAndMedian = { avg, median };

			return avgAndMedian;
		}
		return null;
	}

}