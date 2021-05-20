package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class OverallStatistic implements Initializable {

	@FXML
	private Pane PaneOverallStatistic;

	@FXML
	private HBox hBoxSelection;

	@FXML
	private JFXComboBox<String> courseSelect;

	@FXML
	private CategoryAxis x_Exam;

	@FXML
	private NumberAxis y_Grades;

	@FXML
	private BarChart<String, Number> graph;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		courseSelect.getItems().addAll(Client.getSubjectCollection().getCourses());
		
	}

	@SuppressWarnings("unchecked")
	@FXML
	void diplayGrapgh(ActionEvent event) {
//			display data on the graph:
		Series<String, Number> set1 = new XYChart.Series<String, Number>();

		set1.getData().add(new XYChart.Data<String, Number>("Algebra", 80));
		set1.getData().add(new XYChart.Data<String, Number>("Phyton", 90));
		set1.getData().add(new XYChart.Data<String, Number>("java", 100));
		set1.getData().add(new XYChart.Data<String, Number>("Alge", 50));
		set1.getData().add(new XYChart.Data<String, Number>("Phyt", 20));
		set1.getData().add(new XYChart.Data<String, Number>("jav", 10));

		graph.getData().addAll(set1);
		
//			set1.getData().add(new Data<String, Number>("alge", 92633.68));  
//			set1.getData().add(new Data<String, Number>("ufva",52633.68));

//			graph.getData().addAll(set1);
//			graph.setBarGap(10.0);
//			graph.getXAxis().setTickLabelGap(20);

//			getData().addAll(set1);

	}

	@FXML
	void handleCourseBtn(ActionEvent event) {

	}

	void diplayOnGrapgh(ActionEvent event) {

	}

//	    @FXML
//	    void diplayOnGrapgh(ActionEvent event) {
//	         final BarChart<String,Number> bc = 
//	             new BarChart<String,Number>(x_Exam,y_Grades);
//	         bc.setTitle("Country Summary");
//	         x_Exam.setLabel("Country");       
//	         y_Grades.setLabel("Value");
//	  
//	         XYChart.Series series1 = new XYChart.Series();
//	         series1.setName("2003");       
//	         series1.getData().add(new XYChart.Data("austria", 25601.34));
//	         series1.getData().add(new XYChart.Data("brazil", 20148.82));
//	         series1.getData().add(new XYChart.Data("france", 10000));
//	         series1.getData().add(new XYChart.Data("italy", 35407.15));
//	         series1.getData().add(new XYChart.Data("usa", 12000));      
//	         
//	         XYChart.Series series2 = new XYChart.Series();
//	         series2.setName("2004");
//	         series2.getData().add(new XYChart.Data("austria", 57401.85));
//	         series2.getData().add(new XYChart.Data("brazil", 41941.19));
//	         series2.getData().add(new XYChart.Data("france", 45263.37));
//	         series2.getData().add(new XYChart.Data("italy", 117320.16));
//	         series2.getData().add(new XYChart.Data("usa", 14845.27));  
//	         
//	         XYChart.Series series3 = new XYChart.Series();
//	         series3.setName("2005");
//	         series3.getData().add(new XYChart.Data("austria", 45000.65));
//	         series3.getData().add(new XYChart.Data("brazil", 44835.76));
//	         series3.getData().add(new XYChart.Data("france", 18722.18));
//	         series3.getData().add(new XYChart.Data("italy", 17557.31));
//	         series3.getData().add(new XYChart.Data("usa", 92633.68));  
//	         
//	         bc.getData().addAll(series1, series2, series3);
//	         
//	    }

}