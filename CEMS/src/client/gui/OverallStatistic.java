package client.gui;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.sun.corba.se.spi.orb.Operation;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;


public class OverallStatistic implements Initializable{

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
	private BarChart<String, Number> graph;
	
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
	public enum Operation {COURSE,STUDENT,TEACHER};
	private Operation op = Operation.COURSE;
	public enum StatisticBy {StatisticByCourse,StatisticByTeacher,StatisticByStudent};
	private Series<String, Number> set = new XYChart.Series<String, Number>();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		courseSelect.getItems().addAll(Client.getSubjectCollection().getCourses());
	  	teacherFiltter.setVisible(false);
    	studentFiltter.setVisible(false);
		courseSelect.setVisible(false);
	}

	@SuppressWarnings("unchecked")
	@FXML
	void diplayGrapgh(ActionEvent event){
				if(!set.getData().isEmpty())
					set.getData().clear();
				
//				fix the Bug in the Chart Bar!!
				x_Exam.setAnimated(false);
				graph.getData().clear();
				graph.getData().addAll(set);
				x_Exam.setAnimated(false);
				graph.getData().clear();

		    	switch (op) {
					case COURSE:
						
						set.getData().add(new XYChart.Data<String, Number>("Algebra", 80));
						set.getData().add(new XYChart.Data<String, Number>("Phyton", 90));
						set.getData().add(new XYChart.Data<String, Number>("java", 100));
						set.getData().add(new XYChart.Data<String, Number>("Math", 50));
						graph.getData().addAll(set);
						
						break;
					case STUDENT:
						set.getData().add(new XYChart.Data<String, Number>("JACOB", 85));
						set.getData().add(new XYChart.Data<String, Number>("ARIK", 90));
						set.getData().add(new XYChart.Data<String, Number>("DVIR", 100));
						set.getData().add(new XYChart.Data<String, Number>("SHENHAV", 70));
						set.getData().add(new XYChart.Data<String, Number>("AVIEL", 80));
						graph.getData().addAll(set);
						
						break;
					case TEACHER:						
						set.getData().add(new XYChart.Data<String, Number>("Avi", 80));
						set.getData().add(new XYChart.Data<String, Number>("Katarina", 60));
						set.getData().add(new XYChart.Data<String, Number>("Anat", 70));
						graph.getData().addAll(set);
						
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
		 	statisticNavigation.clear();
		 	statisticNavigation.appendText("Selection of statistics by course, shows the grades of all students in the course.");
	    	StatisticNoteNavigation(event);
	    	
	    	courseSelect.setVisible(true);
	    	teacherFiltter.setVisible(false);
	    	studentFiltter.setVisible(false);
		 	op=Operation.COURSE;
		 	
//			fix the Bug in the Chart Bar!!
			x_Exam.setAnimated(false);
			graph.getData().clear();
		 		
	    }

	    @FXML
	    void statisticByStudent(ActionEvent event) {
	    	statisticNavigation.clear();
	    	statisticNavigation.appendText("Selection of statistics by student, shows all the grades that the student received");
	    	StatisticNoteNavigation(event);
	    	
	    	studentFiltter.setVisible(true);
	    	courseSelect.setVisible(false);
	    	teacherFiltter.setVisible(false);
	    	
	    	op=Operation.STUDENT;
	    	
//			fix the Bug in the Chart Bar!!
			x_Exam.setAnimated(false);
			graph.getData().clear();
			 		
	    }

	    @FXML
	    void statisticByTeacher(ActionEvent event) {
	    	statisticNavigation.clear();
	    	statisticNavigation.appendText("Selection of statistics by teacher, shows the scores of all exams belonging to the same teacher");
	    	StatisticNoteNavigation(event);
	    	
	    	studentFiltter.setVisible(false);
	    	courseSelect.setVisible(false);
	    	teacherFiltter.setVisible(true);
	    	
	    	op=Operation.TEACHER;
	    	
//			fix the Bug in the Chart Bar!!
			x_Exam.setAnimated(false);
			graph.getData().clear();
			 		
	    }
}