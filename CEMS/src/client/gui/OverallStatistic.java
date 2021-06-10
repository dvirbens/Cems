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
	private double[] getAvarageAndMedian(List<StudentExecutedExam> list) {
		List<Integer> studentsGrade = new ArrayList<>();
		int sum = 0;

		for (StudentExecutedExam student : list) {
			int studentGrade = Integer.valueOf(student.getGrade());
			studentsGrade.add(studentGrade);
			sum += studentGrade;
		}

		studentsGrade.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});

		double avg = sum / studentsGrade.size();
		double median;

		int n = studentsGrade.size();
		if (n % 2 == 0) {
			int firstStudentGrade = studentsGrade.get((n / 2) - 1);
			int secondStudentGrade = studentsGrade.get((n / 2));
			median = (firstStudentGrade + secondStudentGrade) / 2;
		} else {
			median = studentsGrade.get(((n + 1) / 2) - 1);
		}
		double[] avgAndMedian = { avg, median };
		return avgAndMedian;
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
					int sum_avg = 0;
					List<ExecutedExam> statisticList = Client.getExecExams();
					XYChart.Series<String, Double> series = new XYChart.Series<>();
					for (ExecutedExam exams : statisticList) {
						String newData = exams.getExecutorTeacherName() + "\n" + exams.getExecDate() + "\n"
								+ exams.getExecTime();
						series.getData().add(new XYChart.Data<String, Double>(newData, Double.valueOf(sum_avg += exams.getAvg())));
					}
					int half = (int)statisticList.size();
					sum_avg = half/2;
					if(half%2 != 0) 
//						Collections.sort(statisticList);
//				
						median.setText(Double.toString(statisticList.get(half/2+1).getAvg()));
					else 
						median.setText(Double.toString((statisticList.get(half/2+1).getAvg() + statisticList.get(half/2+1).getAvg()) / 2 ));		
					avg.setText(Integer.toString(sum_avg));
					graph.getData().addAll(series);
					
					
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
					ModelWrapper<String> modelWrapper = new ModelWrapper<>(student_select,GET_EXECUTED_EXAM_LIST_BY_STUDENT);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);
					List<StudentExecutedExam> statisticList = Client.getExecutedExamStudentList();
					System.out.println(statisticList);
					XYChart.Series<String, Double> series = new XYChart.Series<>();
					for (StudentExecutedExam student_exams : statisticList) {
						series.getData().add(new XYChart.Data<String, Double>(student_exams.getCourse() , Double.parseDouble(student_exams.getGrade())));
					}
					
					
//					int half = (int)statisticList.size();
//					int sum_avg = half/2;
//					Collections.sort(statisticList);
//					System.out.println(statisticList);
//					if(half%2 != 0) 	
//						median.setText(statisticList.get(half/2+1).getGrade());
//					else 
//						median.setText(Double.toString((statisticList.get(half/2+1).getAvg() + statisticList.get(half/2+1).getAvg()) / 2 ));		
//					avg.setText(Integer.toString(sum_avg));
					graph.getData().addAll(series);
				}
			});
			break;
		case TEACHER:
			/*
			 * set.getData().add(new XYChart.Data<String, Double>("Avi", 80.0));
			 * set.getData().add(new XYChart.Data<String, Double>("Katarina", 60.0));
			 * set.getData().add(new XYChart.Data<String, Double>("Anat", 70.0));
			 * graph.getData().addAll(set);
			 */
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
	
	class StatisticByCourseComparator implements Comparator<ExecutedExam> {

	    @Override
	    public int compare(ExecutedExam o1, ExecutedExam o2) {
//	        return o1.getAvg().compareTo(o2.getAvg());
	    	return 0;
	    }
	}
}