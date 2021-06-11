package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.ComputerizedTestReport;
import models.StudentExecutedExam;

public class ExecutedExamsController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<StudentExecutedExam> tvExExams;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcStudentID;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcExamID;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcSubject;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcCourse;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcExecDate;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcTestType;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcGrade;

	@FXML
	private TableColumn<StudentExecutedExam, JFXButton> tcGetTest;
	

	public void initialize(URL location, ResourceBundle resources) {
					
		tcExamID.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("examID"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("course"));
		tcExecDate.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("execDate"));
		tcTestType.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("testType"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("grade"));
		tcGetTest.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, JFXButton>("getCopy"));

		String userID = Client.getUser().getUserID();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(userID, EXAM_EXECUTE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		if (!Client.getExecutedExamStudentList().isEmpty())
		{
			ObservableList<StudentExecutedExam> exams = FXCollections.observableArrayList();
			List<StudentExecutedExam> studentList = addCopyButtons(Client.getExecutedExamStudentList());
			exams.addAll(studentList);
			tvExExams.setItems(exams);
		}
		
	}

	private List<StudentExecutedExam> addCopyButtons(List<StudentExecutedExam> executedExamStudentList) {
	
		for (StudentExecutedExam studentExam : executedExamStudentList) {
			JFXButton getCopyButton = new JFXButton();
			getCopyButton.setPrefSize(90, 15);
			getCopyButton
					.setStyle("-fx-background-color:#3399FF;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			getCopyButton.setText("Get Copy");

			ModelWrapper<String> modelWrapper = new ModelWrapper<>(studentExam.getExamID(), GET_QUESTION_LIST_BY_EXAM_ID);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);
			
			List<String> elements = new ArrayList<>();
			elements.add(studentExam.getStudentID());
			elements.add(studentExam.getExamID());
			elements.add(studentExam.getExecDate());
			modelWrapper = new ModelWrapper<>(elements, GET_SELECTED_ANSWERS);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);
			
			List<ComputerizedTestReport> questionsReport = new ArrayList<>();
			int numOfQuestions = Client.getExamQuestions().size();
			if (Client.getSelectedAnswers() != null)
			{
				for (int i=0; i < numOfQuestions; i++)
				{
					ComputerizedTestReport questionReport;
					System.out.println(Client.getSelectedAnswers().split("")[i]
					+ " " + Integer.toString(Client.getExamQuestions().get(i).getCorrectAnswer()));
					if (Client.getSelectedAnswers().split("")[i].equals(Integer.toString(Client.getExamQuestions().get(i).getCorrectAnswer())))
					{
								final ImageView imageview_correct = new ImageView(new Image(getClass().getResource("correct.png").toExternalForm()));
								imageview_correct.setFitHeight(30);
								imageview_correct.setFitWidth(30);
								questionReport = new ComputerizedTestReport(Client.getSelectedAnswers().split("")[i],
								Integer.toString(Client.getExamQuestions().get(i).getCorrectAnswer()),
								Integer.toString(Client.getExamQuestions().get(i).getPoints()), imageview_correct, Client.getExamQuestions().get(i));
					}
					else
					{
								final ImageView imageview_wrong = new ImageView(new Image(getClass().getResource("wrong.png").toExternalForm()));
								imageview_wrong.setFitHeight(30);
								imageview_wrong.setFitWidth(30);
								questionReport = new ComputerizedTestReport(Client.getSelectedAnswers().split("")[i],
								Integer.toString(Client.getExamQuestions().get(i).getCorrectAnswer()),
								Integer.toString(Client.getExamQuestions().get(i).getPoints()), imageview_wrong, Client.getExamQuestions().get(i));
					}
					questionsReport.add(questionReport);
				}
			}
			

			/*
			report.setSubject(studentExam.getSubject());
			report.setCourse(studentExam.getCourse());
			report.setSelectedAnswers(Client.getSelectedAnswers().split(""));
			report.setQuestions(Client.getExamQuestions());
			*/
			
			getCopyButton.setOnAction(new EventHandler<ActionEvent>() {			
				@Override
				public void handle(ActionEvent event) {
					MainGuiController.getMenuHandler().setStudentComputerizedTestReportScreen(questionsReport, studentExam.getSubject(),
							studentExam.getCourse());
				}

			});
			studentExam.setGetCopy(getCopyButton);

			if (!studentExam.isApproved())
				studentExam.setGrade("Not Checked");

		}
		return executedExamStudentList;
	}

}
