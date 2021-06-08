package client.gui;

import static common.ModelWrapper.Operation.GET_EXAM_BY_CODE;
import static common.ModelWrapper.Operation.GET_QUESTION_LIST_BY_CODE;
import static common.ModelWrapper.Operation.INSERT_FINISHED_STUDENT;
import static common.ModelWrapper.Operation.INSERT_STUDENT_TO_EXAM;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import models.Exam;
import models.ExamQuestion;
import models.ExamQuestion.NoteType;
import models.StudentExecutedExam;
import models.StudentInExam;

public class ExecuteComputerizedExamController implements Initializable {

	@FXML
	private TableView<ExamQuestion> tvQuestions;

	@FXML
	private TableColumn<ExamQuestion, Integer> tcQuestionNumber;

	@FXML
	private TableColumn<ExamQuestion, Integer> tcQuestionPoints;

	@FXML
	private TableColumn<ExamQuestion, String> tcQuestionContent;

	@FXML
	private TableColumn<ExamQuestion, ImageView> tcFilled;

	@FXML
	private Label lblQuestions;

	@FXML
	private Label lblSelectedQuestion;

	@FXML
	private JFXButton btnSaveAnswer;

	@FXML
	private TextArea taSelectedQuestion;

	@FXML
	private JFXButton btnSubmitTest;

	@FXML
	private Label lblPossibleAnswers;

	@FXML
	private Label lblRemainingTime;

	@FXML
	private TextField tfRemainingTime;

	@FXML
	private JFXRadioButton radio1;

	@FXML
	private JFXRadioButton radio2;

	@FXML
	private JFXRadioButton radio3;

	@FXML
	private JFXRadioButton radio4;

	@FXML
	private ToggleGroup AnswersGroup;

	@FXML
	private TextField tfNote;

	@FXML
	private Label lblNote;

	private long startTime;

	private Exam exam;

	private long duration;

	private String[] answersArr;

	private Integer selectedRadio;

	private String examID;

	private static String code;

	public ExecuteComputerizedExamController() {

	}

	public ExecuteComputerizedExamController(String code) {
		ExecuteComputerizedExamController.code = code;

	}

	public void start() {
		try {
			Pane computerizedTestPane = (Pane) FXMLLoader.load(getClass().getResource("ComputerizedTest.fxml"));
			MainGuiController.getMenuHandler().getMainFrame().setCenter(computerizedTestPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialize(URL location, ResourceBundle resources) {

		// Start time counter
		startTime = System.currentTimeMillis();
		setRemainingTime();

		StudentMenuController.setLocked(true);

		// Get questions
		ModelWrapper<String> modelWrapperQuestionList = new ModelWrapper<>(code, GET_QUESTION_LIST_BY_CODE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapperQuestionList);

		// Get exam
		ModelWrapper<String> modelWrapperEaxmDetails = new ModelWrapper<>(code, GET_EXAM_BY_CODE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapperEaxmDetails);

		exam = Client.getExam();

		String examID = exam.getId();
		String userID = Client.getUser().getUserID();
		String teacherID = exam.getTeacherID();

		StudentExecutedExam newStudent = new StudentExecutedExam(examID, userID, code, teacherID);
		ModelWrapper<StudentExecutedExam> modelWrapperInsertStudent = new ModelWrapper<>(newStudent,
				INSERT_STUDENT_TO_EXAM);
		ClientUI.getClientController().sendClientUIRequest(modelWrapperInsertStudent);

		answersArr = new String[exam.getExamQuestions().size()];

		String teacherTime = Client.getExamProcess().getTime();
		Date date = new Date();
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		String currentTime = timeformat.format(date).toString();

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date1 = format.parse(currentTime);
			Date date2 = format.parse(teacherTime);
			long difference = date1.getTime() - date2.getTime();
			long examDuration = TimeUnit.MINUTES.toSeconds(Long.parseLong(Client.getExam().getDuration()));
			duration = examDuration - TimeUnit.MILLISECONDS.toSeconds(difference);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Setting the table
		ObservableList<ExamQuestion> questions = FXCollections.observableArrayList();

		for (ExamQuestion q : exam.getExamQuestions()) {
			final ImageView imageview = new ImageView(new Image(getClass().getResource("check.jpg").toExternalForm()));
			imageview.setFitHeight(30);
			imageview.setFitWidth(30);
			imageview.setVisible(false);
			q.setCheckImage(imageview);
		}

		questions.addAll(exam.getExamQuestions());
		tvQuestions.setItems((ObservableList<ExamQuestion>) questions);

		tcQuestionNumber
				.setCellValueFactory(new Callback<CellDataFeatures<ExamQuestion, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<ExamQuestion, Integer> p) {
						return new ReadOnlyObjectWrapper(tvQuestions.getItems().indexOf(p.getValue()) + 1 + "");
					}
				});

		tcQuestionPoints.setCellValueFactory(new PropertyValueFactory<ExamQuestion, Integer>("points"));
		tcQuestionContent.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("details"));
		tcFilled.setCellValueFactory(new PropertyValueFactory<ExamQuestion, ImageView>("checkImage"));

		// Disable sort columns
		tcQuestionNumber.setSortable(false);
		tcQuestionPoints.setSortable(false);
		tcQuestionContent.setSortable(false);
		tcFilled.setSortable(false);

		// Adding listener to toggle group
		AnswersGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
				selectedRadio = Character.getNumericValue((((JFXRadioButton) newToggle).getId().charAt(5)));
			}
		});

		// Setting start selection on first row
		tvQuestions.getSelectionModel().select(0);

		// Loading answers for first question
		onRowClick();

		// Loading answers on click
		tvQuestions.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() > 0) {
				onRowClick();
			}
		});

	}

	public void setRemainingTime() {
		Thread timerThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000); // 1 second
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long timeInSeconds = calcTime();
				long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds);
				long timeExtension = Client.getTimeExtension();
				if (timeExtension == -1) {
					Platform.runLater(() -> {
						MainGuiController.getMenuHandler().setMainScreen();
					});
				} else if (timeExtension != 0) {
					minutes += Client.getTimeExtension();
					Client.setTimeExtension(0);
				}
				long seconds = timeInSeconds % 60;

				final String time = Long.toString(minutes) + ":" + Long.toString(seconds);
				Platform.runLater(() -> {
					tfRemainingTime.setText(time);
				});
			}
		});
		timerThread.start();
	}

	public long calcTime() {
		long elapsedTime = duration * 1000 - (System.currentTimeMillis() - startTime);
		long elapsedSeconds = elapsedTime / 1000;
		long secondsDisplay = elapsedSeconds % 60;
		long elapsedMinutes = elapsedSeconds / 60;

		return elapsedSeconds;

	}

	public void onRowClick() {
		// check the table's selected item and get selected item
		if (tvQuestions.getSelectionModel().getSelectedItem() != null) {
			ExamQuestion selectedRow = tvQuestions.getSelectionModel().getSelectedItem();
			int selectedRowIndex = tvQuestions.getSelectionModel().getSelectedIndex();
			taSelectedQuestion.setText(selectedRow.getDetails());
			Integer currentSelectedAnswer = null;
			if (answersArr[selectedRowIndex] != null)
				currentSelectedAnswer = Integer.parseInt(answersArr[selectedRowIndex]);
			if (selectedRow.getNoteType() == NoteType.Students) {
				tfNote.setText(selectedRow.getNote());
			} else {
				tfNote.setText("");
			}
			if (currentSelectedAnswer != null) {
				switch (currentSelectedAnswer) {
				case 1:
					AnswersGroup.selectToggle(radio1);
					break;
				case 2:
					AnswersGroup.selectToggle(radio2);
					break;
				case 3:
					AnswersGroup.selectToggle(radio3);
					break;
				case 4:
					AnswersGroup.selectToggle(radio4);
					break;
				}
			}
			radio1.setText(selectedRow.getAnswer1());
			radio2.setText(selectedRow.getAnswer2());
			radio3.setText(selectedRow.getAnswer3());
			radio4.setText(selectedRow.getAnswer4());

		}
	}

	@FXML
	public void onSaveClick(ActionEvent event) {
		if (AnswersGroup.getSelectedToggle() != null) {
			ExamQuestion selectedRow = tvQuestions.getSelectionModel().getSelectedItem();
			int selectedQuestion = tvQuestions.getSelectionModel().getSelectedIndex();
			answersArr[selectedQuestion] = (String) selectedRadio.toString();
			int selectedRowIndex = tvQuestions.getSelectionModel().getSelectedIndex();
			tvQuestions.getSelectionModel().select(selectedRowIndex + 1);
			selectedRow.setVisibleImage();
			selectedRow = tvQuestions.getSelectionModel().getSelectedItem();
			radio1.setText(selectedRow.getAnswer1());
			radio2.setText(selectedRow.getAnswer2());
			radio3.setText(selectedRow.getAnswer3());
			radio4.setText(selectedRow.getAnswer4());
		}
	}

	@FXML
	void onClickSubmit(ActionEvent event) {
		StudentMenuController.setLocked(false);
		Integer grade = 0;
		for (int i = 0; i < exam.getExamQuestions().size(); i++) {
			if (answersArr[i] != null) {
				if (Integer.parseInt(answersArr[i]) == exam.getExamQuestions().get(i).getCorrectAnswer()) {
					grade += exam.getExamQuestions().get(i).getPoints();
				}
			}
		}

		String userID = Client.getUser().getUserID();
		String finalGrade = grade.toString();

		StudentInExam finishedStudent = new StudentInExam(userID, code, finalGrade, answersArr);
		ModelWrapper<StudentInExam> modelWrapper = new ModelWrapper<>(finishedStudent, INSERT_FINISHED_STUDENT);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

	}

}
