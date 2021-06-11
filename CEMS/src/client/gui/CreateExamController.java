package client.gui;

import static common.ModelWrapper.Operation.GET_QUESTION_LIST;
import static common.ModelWrapper.Operation.GET_QUESTION_LIST_BY_SUBJECT;

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
import javafx.scene.control.TextField;
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
	private TableColumn<ExamQuestion, TextField> tcPointsSelected;

	@FXML
	private TableColumn<ExamQuestion, String> tcSubjectSelected;

	@FXML
	private TableColumn<ExamQuestion, String> tcTeacherSelected;

	@FXML
	private TableColumn<ExamQuestion, JFXButton> tcRemove;

	@FXML
	private TableColumn<ExamQuestion, JFXButton> tcDetailsSelected;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXButton btnNote;

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

	private static String teacherNote;
	private static String studentNote;

	enum Operation {
		REMOVE, ADD
	}

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
			addButton.setOnAction(new AddRemoveEvenetHandler(Operation.ADD, question));
			question.setAddRemoveButton(addButton);
		}
	}

	@FXML
	void onClickAddNote(ActionEvent event) {
		AddNoteController addNoteWindow = new AddNoteController();
		addNoteWindow.start();
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
		boolean wrongInput = false;
		for (ExamQuestion question : tvSelectedQuestion.getItems()) {

			if (question.getTfPoints().getText().isEmpty())
				wrongInput = true;

			if (!isNumeric(question.getTfPoints().getText()))
				wrongInput = true;

		}

		duration = tfDuration.getText();
		List<ExamQuestion> examQuestions = createRegularList(tvSelectedQuestion.getItems());
		messageLabel.setStyle("-fx-text-fill: RED;");

		if (examQuestions.isEmpty()) {
			messageLabel.setText("Question list is empty, please insert question");
		} else if (subject.isEmpty()) {
			messageLabel.setText("Please select exam subject");
		} else if (course.isEmpty()) {
			messageLabel.setText("Please select exam course");
		} else if (duration.isEmpty()) {
			messageLabel.setText("Please insert exam duration");
		} else if (!isNumeric(duration)) {
			messageLabel.setText("Duration must to be number value");
		} else if (wrongInput) {
			messageLabel.setText("Please insert numric points for every question");
		}

		if (!subject.isEmpty() && !course.isEmpty() && !duration.isEmpty() && examQuestions != null
				&& isNumeric(duration) && !examQuestions.isEmpty() && !wrongInput) {
			examQuestions = addAllPoints(examQuestions);
			String teacherID = Client.getUser().getUserID();
			String teacherNote = getTeacherNote();
			String studentNote = getStudentNote();
			Exam newExam = new Exam(subject, teacherID, course, duration, teacherNote, studentNote, examQuestions);
			newExam.setTeacherName(Client.getUser().getFirstName() + " " + Client.getUser().getLastName());
			ConfirmExamController confirmPage = new ConfirmExamController(newExam);
			confirmPage.start();
		}

	}

	private List<ExamQuestion> createRegularList(ObservableList<ExamQuestion> observableList) {
		List<ExamQuestion> examQuestions = new ArrayList<>();
		examQuestions.addAll(observableList);
		return examQuestions;
	}

	private List<ExamQuestion> addAllPoints(List<ExamQuestion> examQuestions) {

		for (ExamQuestion examQuestion : examQuestions) {
			String finalPoint = examQuestion.getTfPoints().getText();
			examQuestion.setPoints(Integer.valueOf(finalPoint));
		}

		return examQuestions;
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
		ModelWrapper<Question> modelWrapper = new ModelWrapper<>(GET_QUESTION_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		addQuestionList();

		tvSelectedQuestion.setPlaceholder(new Label("No question were added to the list"));
		cbQuestionSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());
		cbExamSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());

		tcIdPool.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));
		tcSubjectPool.setCellValueFactory(new PropertyValueFactory<Question, String>("subject"));
		tcTeacherPool.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tcAddPool.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("addRemoveButton"));
		tcDetailsPool.setCellValueFactory(new PropertyValueFactory<Question, JFXButton>("detailsButton"));

		tcIdSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("questionID"));
		tcSubjectSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("subject"));
		tcTeacherSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("teacherName"));
		tcPointsSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, TextField>("tfPoints"));
		tcRemove.setCellValueFactory(new PropertyValueFactory<ExamQuestion, JFXButton>("addRemoveButton"));
		tcDetailsSelected.setCellValueFactory(new PropertyValueFactory<ExamQuestion, JFXButton>("detailsButton"));

		setStudentNote("");
		setTeacherNote("");
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

	public class AddRemoveEvenetHandler implements EventHandler<ActionEvent> {

		private Operation operation;
		private Question question;
		private ExamQuestion examQuestion;

		public AddRemoveEvenetHandler(Operation operation, Question question) {
			this.operation = operation;
			this.question = question;
		}

		public AddRemoveEvenetHandler(Operation operation, ExamQuestion examQuestion) {
			this.operation = operation;
			this.examQuestion = examQuestion;
		}

		@Override
		public void handle(ActionEvent event) {

			switch (operation) {

			case ADD:
				tvQuestionPool.getItems().remove(question);
				examQuestion = new ExamQuestion(question);
				JFXButton remove = new JFXButton();
				remove.setPrefSize(70, 15);
				remove.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
				remove.setText("Remove");
				remove.setOnAction(new AddRemoveEvenetHandler(Operation.REMOVE, examQuestion));
				examQuestion.setAddRemoveButton(remove);
				TextField tfPoints = new TextField();
				examQuestion.setTfPoints(tfPoints);
				tvSelectedQuestion.getItems().add(examQuestion);
				break;

			case REMOVE:
				tvSelectedQuestion.getItems().remove(examQuestion);
				question = new Question(examQuestion);
				JFXButton add = new JFXButton();
				add.setPrefSize(70, 15);
				add.setStyle("-fx-background-color:#616161;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
				add.setText("Add");
				add.setOnAction(new AddRemoveEvenetHandler(Operation.ADD, question));
				question.setAddRemoveButton(add);
				tvQuestionPool.getItems().add(question);
				break;
			}

		}

	}

	public static String getTeacherNote() {
		return teacherNote;
	}

	public static void setTeacherNote(String teacherNote) {
		CreateExamController.teacherNote = teacherNote;
	}

	public static String getStudentNote() {
		return studentNote;
	}

	public static void setStudentNote(String studentNote) {
		CreateExamController.studentNote = studentNote;
	}

}
