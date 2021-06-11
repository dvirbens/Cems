package client.gui;

import static common.ModelWrapper.Operation.CREATE_EXAM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.Exam;
import models.ExamQuestion;

public class ConfirmExamController implements Initializable {

	@FXML
	private TableView<ExamQuestion> tvQuestions;

	@FXML
	private TableColumn<ExamQuestion, String> tcID;

	@FXML
	private TableColumn<ExamQuestion, String> tcSubject;

	@FXML
	private TableColumn<ExamQuestion, String> tcTeacher;

	@FXML
	private TableColumn<ExamQuestion, Integer> tcPoints;

	@FXML
	private TableColumn<ExamQuestion, JFXButton> tcDetails;

	@FXML
	private JFXButton btnBack;

	@FXML
	private JFXButton btnCreate;

	private static Exam exam;

	public ConfirmExamController() {
	}

	public ConfirmExamController(Exam exam) {
		ConfirmExamController.exam = exam;
	}

	public void start() {
		try {
			Pane questionListPane = (Pane) FXMLLoader.load(getClass().getResource("ConfirmNewExamPage.fxml"));
			MainGuiController.getMenuHandler().getMainFrame().setCenter(questionListPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickBack(ActionEvent event) {
		MainGuiController.getMenuHandler().setCreateExamScreen();

	}

	@FXML
	void onClickCreate(ActionEvent event) {
		deletExamButtons();
		ModelWrapper<Exam> modelWrapper = new ModelWrapper<>(exam, CREATE_EXAM);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		MainGuiController.getMenuHandler().setCreateExamSucceeded();
	}

	private void deletExamButtons() {
		exam.setQuestionListButton(null);
		for (ExamQuestion question : exam.getExamQuestions()) {
			question.setAddRemoveButton(null);
			question.setDetailsButton(null);
			question.setNoteDetails(null);
			question.setTfPoints(null);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tcID.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("questionID"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("subject"));
		tcTeacher.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("teacherName"));
		tcPoints.setCellValueFactory(new PropertyValueFactory<ExamQuestion, Integer>("points"));
		tcDetails.setCellValueFactory(new PropertyValueFactory<ExamQuestion, JFXButton>("detailsButton"));

		System.out.println("Need to display this notes: ");
		System.out.println("Student note: " + exam.getStudentNote());
		System.out.println("Teacher note: " + exam.getTeacherNote());

		ObservableList<ExamQuestion> examQuestions = FXCollections.observableArrayList();
		List<ExamQuestion> examQuestionsList = exam.getExamQuestions();
		examQuestions.addAll(examQuestionsList);
		tvQuestions.setItems(examQuestions);

	}

}
