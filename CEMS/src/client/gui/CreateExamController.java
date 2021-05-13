package client.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import static common.ModelWrapper.Operation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Question;

public class CreateExamController implements Initializable {

	@FXML
	private TableView<Question> tvQuestionPull;

	@FXML
	private TableColumn<Question, String> tcIdPull;

	@FXML
	private TableColumn<Question, String> tcSubjectPull;

	@FXML
	private TableColumn<Question, String> tcCoursePull;

	@FXML
	private TableColumn<Question, String> tcTeacherPull;

	@FXML
	private TableColumn<Question, Button> tcViewPull;

	@FXML
	private TableView<Question> tvSelectedQuestion;

	@FXML
	private TableColumn<Question, String> tcIdSelected;

	@FXML
	private TableColumn<Question, String> tcSubjectSelected;

	@FXML
	private TableColumn<Question, String> tcCourseSelected;

	@FXML
	private TableColumn<Question, String> tcTeacherSelected;

	@FXML
	private TableColumn<Question, Button> tcViewSelected;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXTextField cbDuration;

	@FXML
	private JFXButton btnContinue;

	@FXML
	private JFXComboBox<String> cbSubject;

	@FXML
	private JFXComboBox<String> cbCourse;

	@FXML
	void onSubjectSelected(ActionEvent event) {
		String subjectSelected = cbSubject.getSelectionModel().getSelectedItem();
		List<String> courseList = Client.getSubjectCollection().getCourseListBySubject(subjectSelected);
		cbCourse.getItems().clear();
		cbCourse.getItems().addAll(courseList);
	}

	@FXML
	void onClickSearch(ActionEvent event) {
		String subjectSelected = cbSubject.getSelectionModel().getSelectedItem();
		String courseSelected = cbCourse.getSelectionModel().getSelectedItem();
		List<String> sortByList = new ArrayList<>();
		sortByList.add(subjectSelected);
		sortByList.add(courseSelected);
		ModelWrapper<String> modelWrapper = new ModelWrapper<>(sortByList, GET_QUESTION_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<Question> questions = FXCollections.observableArrayList();
		questions.addAll(Client.getQuestions());
		tvQuestionPull.setItems(questions);
		
	}

	@FXML
	void onClickContinue(ActionEvent event) {

	}

	/**
	 * Setting all table column and creating new data set from client request that
	 * has been sent to database.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cbSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());
		cbCourse.getItems().addAll(Client.getSubjectCollection().getCourses());

		tcIdPull.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));
		tcSubjectPull.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		tcCoursePull.setCellValueFactory(new PropertyValueFactory<Question, String>("course"));
		tcTeacherPull.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tcViewPull.setCellValueFactory(new PropertyValueFactory<Question, Button>("view"));

		tcIdSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));
		tcSubjectSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		tcCourseSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("course"));
		tcTeacherSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tcViewSelected.setCellValueFactory(new PropertyValueFactory<Question, Button>("view"));

	}

}
