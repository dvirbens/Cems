package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Exam;

public class StartExamController implements Initializable {

	@FXML
	private JFXComboBox<String> cbExamCourse;

	@FXML
	private JFXComboBox<String> cbExamSubject;

	@FXML
	private TableView<Exam> tvExamPool;

	@FXML
	private TableColumn<Exam, String> tcID;

	@FXML
	private TableColumn<Exam, String> tcSubject;

	@FXML
	private TableColumn<Exam, String> tcCourse;

	@FXML
	private TableColumn<Exam, String> tcDuration;

	@FXML
	private TableColumn<Exam, JFXButton> tcQuestionList;

	@FXML
	private JFXButton btnStartExam;

	@FXML
	private JFXTextField tfCode;

	@FXML
	void onClickExamSubject(ActionEvent event) {
		String subjectSelected = cbExamSubject.getSelectionModel().getSelectedItem();
		cbExamCourse.getItems().clear();
		cbExamCourse.getItems().addAll(Client.getSubjectCollection().getCourseListBySubject(subjectSelected));

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(subjectSelected, GET_EXAMS_LIST_BY_SUBJECT);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<Exam> exams = FXCollections.observableArrayList();
		exams.addAll(Client.getExams());
		tvExamPool.setItems(exams);
		setExamQuestioListButtons(Client.getExams());

	}

	private void setExamQuestioListButtons(List<Exam> exams) {

		for (Exam exam : exams) {
			JFXButton questionListButton = new JFXButton();
			questionListButton.setPrefSize(90, 15);
			questionListButton
					.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			questionListButton.setText("List");
			questionListButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					MainGuiController.getMenuHandler().setQuestionListScreen(exam.getExamQuestions());
				}

			});

			exam.setQuestionListButton(questionListButton);
		}
	}

	@FXML
	void onClickExamCourse(ActionEvent event) {
		String courseSlected = cbExamCourse.getSelectionModel().getSelectedItem();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(courseSlected, GET_EXAMS_LIST_BY_COURSE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<Exam> exams = FXCollections.observableArrayList();
		exams.addAll(Client.getExams());
		tvExamPool.setItems(exams);
		setExamQuestioListButtons(Client.getExams());

	}

	@FXML
	void onClickStartExam(ActionEvent event) {
		String code = tfCode.getText();
		if (!code.isEmpty()) {
			String focusedExamID = tvExamPool.getFocusModel().getFocusedItem().getId();

			for (Exam exam : Client.getExams()) {
				if (exam.getId().equals(focusedExamID)) {
					ExamManagementWindow examManagementController = new ExamManagementWindow();
					examManagementController.start(code, Integer.valueOf(exam.getDuration()));
				}
			}

		}

	}

	/**
	 * Setting all table column and creating new data set from client request that
	 * has been sent to database.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbExamSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());

		tcID.setCellValueFactory(new PropertyValueFactory<Exam, String>("id"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<Exam, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<Exam, String>("course"));
		tcDuration.setCellValueFactory(new PropertyValueFactory<Exam, String>("duration"));
		tcQuestionList.setCellValueFactory(new PropertyValueFactory<Exam, JFXButton>("questionListButton"));

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(GET_EXAMS_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		ObservableList<Exam> exams = FXCollections.observableArrayList();
		exams.addAll(Client.getExams());
		tvExamPool.setItems(exams);
		setExamQuestioListButtons(Client.getExams());
	}

}
