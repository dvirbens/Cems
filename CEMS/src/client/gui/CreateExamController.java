package client.gui;

import static common.ModelWrapper.Operation.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Exam;
import models.ExamQuestion;
import models.Question;

public class CreateExamController implements Initializable {

	@FXML
	public TableView<Question> tvQuestionPool;

	@FXML
	private TableColumn<Question, String> tcIdPool;

	@FXML
	private TableColumn<Question, String> tcSubjectPool;

	@FXML
	private TableColumn<Question, String> tcTeacherPool;

	@FXML
	private TableColumn<Question, JFXButton> tcDetailsPool;

	@FXML
	private TableView<ExamQuestion> tvSelectedQuestion;

	@FXML
	private TableColumn<Question, JFXButton> tcAddPool;

	@FXML
	private TableColumn<ExamQuestion, String> tcIdSelected;

	@FXML
	private TableColumn<ExamQuestion, Integer> tcPointsSelected;

	@FXML
	private TableColumn<ExamQuestion, String> tcSubjectSelected;

	@FXML
	private TableColumn<ExamQuestion, String> tcTeacherSelected;

	@FXML
	private TableColumn<ExamQuestion, JFXButton> tcNoteSelected;

	@FXML
	private TableColumn<ExamQuestion, JFXButton> tcDetailsSelected;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXTextField tfDuration;

	@FXML
	private JFXButton btnContinue;

	@FXML
	private JFXComboBox<String> cbQuestionSubject;

	@FXML
	private JFXComboBox<String> cbExamCourse;

	@FXML
	private Label messageLabel;

	@FXML
	private JFXComboBox<String> cbExamSubject;

	private static List<ExamQuestion> examQuestionList;

	@FXML
	void onSubjectSelected(ActionEvent event) {
		String subjectSelected = cbQuestionSubject.getSelectionModel().getSelectedItem();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(subjectSelected, GET_QUESTION_LIST_BY_SUBJECT);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		addQuestionList();

	}

	private void addQuestionList() {
		ObservableList<Question> questions = FXCollections.observableArrayList();
		questions.addAll(Client.getQuestions());
		tvQuestionPool.setItems(questions);

		for (Question question : Client.getQuestions()) {
			JFXButton detailsButton = new JFXButton();
			detailsButton.setPrefSize(90, 15);
			detailsButton
					.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
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
			addButton.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			addButton.setText("Add");
			addButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (!AddQuestionController.isWindowOpend()) {
						AddQuestionController addQuestionController = new AddQuestionController();
						AddQuestionController.setQuestion(question);
						AddQuestionController.setTvQuestionPull(tvQuestionPool);
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
		String subject, course, duration;
		if (cbExamSubject.getSelectionModel().getSelectedItem() == null) {
			subject = "";
		} else {
			subject = cbExamSubject.getSelectionModel().getSelectedItem();
		}

		if (cbExamCourse.getSelectionModel().getSelectedItem() == null) {
			course = "";
		} else {
			course = cbExamCourse.getSelectionModel().getSelectedItem();
		}
		duration = tfDuration.getText();
		List<ExamQuestion> examQuestion = getExamQuestionList();
		messageLabel.setStyle("-fx-text-fill: RED;");

		if (examQuestion.isEmpty()) {
			messageLabel.setText("Question list is empty, please insert question");
		} else if (subject.isEmpty()) {
			messageLabel.setText("Please select exam subject");
		} else if (course.isEmpty()) {
			messageLabel.setText("Please select exam course");
		} else if (duration.isEmpty()) {
			messageLabel.setText("Please insert exam duration");
		} else if (!isNumeric(duration)) {
			messageLabel.setText("Duration must to be number value");
		}

		if (!subject.isEmpty() && !course.isEmpty() && !duration.isEmpty() && examQuestion != null
				&& isNumeric(duration)) {
			deletExamQuestionListButtons();
			Exam newExam = new Exam(subject, Client.getUser().getUserID(), course, duration, examQuestion);
			newExam.setTeacherName(Client.getUser().getFirstName() + " " + Client.getUser().getLastName());
			ModelWrapper<Exam> modelWrapper = new ModelWrapper<>(newExam, CREATE_EXAM);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);
			messageLabel.setStyle("-fx-text-fill: GREEN;");
			messageLabel.setText(Client.getServerMessages());
		}

	}

	private void deletExamQuestionListButtons() {
		List<ExamQuestion> examQuestions = getExamQuestionList();
		for (ExamQuestion question : examQuestions) {
			question.setAddButton(null);
			question.setDetailsButton(null);
			question.setNoteDetails(null);
		}
	}

	@FXML
	void onClickExamSubject(ActionEvent event) {
		String subject = cbExamSubject.getSelectionModel().getSelectedItem();
		cbExamCourse.getItems().clear();
		cbExamCourse.getItems().addAll(Client.getSubjectCollection().getCourseListBySubject(subject));

	}

	/**
	 * Setting all table column and creating new data set from client request that
	 * has been sent to database.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		examQuestionList = new ArrayList<>();

		ModelWrapper<Question> modelWrapper = new ModelWrapper<>(GET_QUESTION_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		addQuestionList();

		tvSelectedQuestion.setPlaceholder(new Label("No question were added to the list"));
		cbQuestionSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());
		cbExamSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());

		tcIdPool.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));
		tcSubjectPool.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		tcTeacherPool.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tcAddPool.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("addButton"));
		tcDetailsPool.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("detailsButton"));

		tcIdSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("questionID"));
		tcSubjectSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("subject"));
		tcTeacherSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("teacherName"));
		tcPointsSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, Integer>("points"));
		tcNoteSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, JFXButton>("noteDetails"));
		tcDetailsSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, JFXButton>("detailsButton"));

	}

	public static List<ExamQuestion> getExamQuestionList() {
		return examQuestionList;
	}

	public static void setExamQuestionList(List<ExamQuestion> examQuestionList) {
		CreateExamController.examQuestionList = examQuestionList;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
