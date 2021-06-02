package client.gui;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static common.ModelWrapper.Operation.STATISTIC_BY_COURSE_X;

import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.sun.corba.se.spi.orb.Operation;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Font;
import models.Exam;
import models.Statistics;
import server.DatabaseController;
import sun.util.resources.cldr.uz.CalendarData_uz_Arab_AF;

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
	private ArrayList<String> allCourse = new ArrayList<String>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		allCourse.addAll(Client.getSubjectCollection().getCourses());
		courseSelect.getItems().addAll(allCourse);
		
	  	teacherFiltter.setVisible(false);
    	studentFiltter.setVisible(false);
		courseSelect.setVisible(false);
	}
	
	public static void Qåueries(Connection con) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("select user.userID, user.FirstName, user.LastName, executedexambystudent.Grade\r\n"
					+ "from executedexambystudent, user\r\n"
					+ "where executedexambystudent.studentID=user.userID;");
			while(rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + rs.getString(3) + " " + rs.getString(4));
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
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
						Statistics st;
						String Together = new String();
						Number avg;
						Number median;
						String course_select = (String) courseSelect.getValue();
						ModelWrapper<String> modelWrapper = new ModelWrapper<>((String)course_select,STATISTIC_BY_COURSE_X);
						ClientUI.getClientController().sendClientUIRequest(modelWrapper);
						List <Statistics> statisticList = Client.getSet();
						for(int i=0;i<statisticList.size();i++) {
							st = statisticList.get(i);
							avg = Integer.parseInt(st.getAvg());
							Together = "ID:" + st.getExamID() +"\n"+ st.getExecuteTeacherID();
							set.getData().add(new XYChart.Data<String,Number>(Together.toString(), avg));	
						}
						if(!statisticList.isEmpty())statisticList.clear();
						x_Exam.setTickLabelRotation(45);
						// Set Font
						x_Exam.setTickLabelFont( new Font("Arial", 12));
						x_Exam.setAnimated(false);
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
		 	courseSelect.setVisible(true);
		 	teacherFiltter.setVisible(false);
	    	studentFiltter.setVisible(false);
		 	statisticNavigation.setText("Selection of statistics by course, shows the grades of all students in the course.");
	    	StatisticNoteNavigation(event);
	    	
	    	
		 	op=Operation.COURSE;
		 	
//			fix the Bug in the Chart Bar!!
			x_Exam.setAnimated(false);
			graph.getData().clear();
			set.getData().clear();
	    }

	    @FXML
	    void statisticByStudent(ActionEvent event) {
	    	studentFiltter.setVisible(true);
	    	courseSelect.setVisible(false);
	    	teacherFiltter.setVisible(false);
	    	
	    	statisticNavigation.setText("Selection of statistics by student, shows all the grades that the student received");
	    	StatisticNoteNavigation(event);
	    	
	    	
	    	courseSelect.setVisible(false);
	    	teacherFiltter.setVisible(false);
	    	
	    	op=Operation.STUDENT;
	    	
//			fix the Bug in the Chart Bar!!
			x_Exam.setAnimated(false);
			graph.getData().clear();
			set.getData().clear();	
	    }

	    @FXML
	    void statisticByTeacher(ActionEvent event) {
	    	teacherFiltter.setVisible(true);
	    	studentFiltter.setVisible(false);
	    	courseSelect.setVisible(false);
	    
	    	statisticNavigation.setText("Selection of statistics by teacher, shows the scores of all exams belonging to the same teacher");
	    	StatisticNoteNavigation(event);
	    	
	    	
	    	
	    	op=Operation.TEACHER;
	    	
//			fix the Bug in the Chart Bar!!
			x_Exam.setAnimated(false);
			graph.getData().clear();
			set.getData().clear();		
	    }
}