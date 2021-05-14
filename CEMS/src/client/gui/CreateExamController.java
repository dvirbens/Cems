package client.gui;

import static common.ModelWrapper.Operation.GET_QUESTION_LIST;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Question;

public class CreateExamController implements Initializable {

	@FXML
	public TableView<Question> tvQuestionPull;

	@FXML
	private TableColumn<Question, String> tcIdPull;

	@FXML
	private TableColumn<Question, String> tcSubjectPull;

	@FXML
	private TableColumn<Question, String> tcCoursePull;

	@FXML
	private TableColumn<Question, String> tcTeacherPull;

	@FXML
	private TableColumn<Question, JFXButton> tcDetailsPull;

	@FXML
	private TableView<Question> tvSelectedQuestion;

	@FXML
	private TableColumn<Question, JFXButton> tcAddPull;

	@FXML
	private TableColumn<Question, String> tcIdSelected;

	@FXML
	private TableColumn<Question, String> tcSubjectSelected;

	@FXML
	private TableColumn<Question, String> tcCourseSelected;

	@FXML
	private TableColumn<Question, String> tcTeacherSelected;

	@FXML
	private TableColumn<Question, JFXButton> tcDetailsSelected;

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

		for (Question question : Client.getQuestions()) {
			JFXButton detailsButton = new JFXButton();
			detailsButton.setPrefSize(90, 15);
			detailsButton
					.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			detailsButton.setText("Details");
			detailsButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println(question);
				}

			});
			question.setDetailsButton(detailsButton);
			JFXButton addButton = new JFXButton();
			addButton.setPrefSize(70, 15);
			addButton.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			addButton.setText("Add");
			addButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (!AddQuestionController.isWindowOpend()) {
						AddQuestionController addQuestionController = new AddQuestionController();
						AddQuestionController.setQuestion(question);
						AddQuestionController.setTvQuestionPull(tvQuestionPull);
						AddQuestionController.setTvSelectedQuestion(tvSelectedQuestion);
						addQuestionController.start();
					}

				}
			});

			question.setAddButton(addButton);
		}

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
		tcAddPull.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("addButton"));
		tcDetailsPull.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("detailsButton"));

		tcIdSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));
		tcSubjectSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		tcCourseSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("course"));
		tcTeacherSelected.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tcDetailsSelected.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("detailsButton"));

	}

}
