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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Question;
import models.QuestionInExam;

public class CreateExamController implements Initializable {

	@FXML
	public TableView<Question> tvQuestionPull;

	@FXML
	private TableColumn<Question, String> tcIdPull;

	@FXML
	private TableColumn<Question, String> tcSubjectPull;

	@FXML
	private TableColumn<Question, String> tcTeacherPull;

	@FXML
	private TableColumn<Question, JFXButton> tcDetailsPull;

	@FXML
	private TableView<QuestionInExam> tvSelectedQuestion;

	@FXML
	private TableColumn<Question, JFXButton> tcAddPull;

	@FXML
	private TableColumn<QuestionInExam, String> tcIdSelected;

	@FXML
	private TableColumn<QuestionInExam, String> tcSubjectSelected;

	@FXML
	private TableColumn<QuestionInExam, String> tcTeacherSelected;

	@FXML
	private TableColumn<QuestionInExam, JFXButton> tcDetailsSelected;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXTextField cbDuration;

	@FXML
	private JFXButton btnContinue;

	@FXML
	private JFXComboBox<String> cbQuestionSubject;

	@FXML
	private JFXComboBox<String> cbExamCourse;

	@FXML
	private JFXComboBox<String> cbExamSubject;

	@FXML
	void onSubjectSelected(ActionEvent event) {
		String subjectSelected = cbQuestionSubject.getSelectionModel().getSelectedItem();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(subjectSelected, GET_QUESTION_LIST);
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
					if (!QuestionDetailsController.isWindowOpend()) {
						QuestionDetailsController questionDetailsController = new QuestionDetailsController();
						QuestionDetailsController.setQuestion(question);
						questionDetailsController.start();
					}
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

	@FXML
	void onClickExamSubject(ActionEvent event) {
		String subject = cbExamSubject.getSelectionModel().getSelectedItem();
		cbExamCourse.getItems().addAll(Client.getSubjectCollection().getCourseListBySubject(subject));
		
	}

	/**
	 * Setting all table column and creating new data set from client request that
	 * has been sent to database.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbQuestionSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());
		cbExamSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());

		tcIdPull.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));
		tcSubjectPull.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		tcTeacherPull.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tcAddPull.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("addButton"));
		tcDetailsPull.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("detailsButton"));

		tcIdSelected.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("questionID"));
		tcSubjectSelected.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("subject"));
		tcTeacherSelected.setCellValueFactory(new PropertyValueFactory<QuestionInExam, String>("teacherName"));
		tcDetailsSelected.setCellValueFactory(new PropertyValueFactory<QuestionInExam, JFXButton>("detailsButton"));

	}

}
