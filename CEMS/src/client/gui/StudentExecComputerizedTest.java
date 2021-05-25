package client.gui;

import static common.ModelWrapper.Operation.GET_EXAM_BY_EXAM_ID;
import static common.ModelWrapper.Operation.GET_QUESTION_LIST_BY_EXAM_ID;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Exam;
import models.ExamQuestion;
import models.ExamQuestion.NoteType;

public class StudentExecComputerizedTest implements Initializable{

    @FXML
    private TableView<ExamQuestion> tvQuestions;

    @FXML
    private TableColumn<ExamQuestion, Integer> tcQuestionNumber;

    @FXML
    private TableColumn<ExamQuestion, Integer> tcQuestionPoints;

    @FXML
    private TableColumn<ExamQuestion, String> tcQuestionContent;

    @FXML
    private TableColumn<ExamQuestion, String> tcFilled;

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
    
    public void initialize(URL location, ResourceBundle resources) {
		//setStudentIDTextField();
    	
    	//Start time counter
    	startTime = System.currentTimeMillis();
    	
    	setRemainingTime();

    	//Get ExamID
    	String examID = Client.getExamID();
    	
    	// Get questions
    	ModelWrapper<String> modelWrapper = new ModelWrapper<String>(examID, GET_QUESTION_LIST_BY_EXAM_ID);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
    	
		//Get exam
		modelWrapper = new ModelWrapper<String>(examID, GET_EXAM_BY_EXAM_ID);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		exam = Client.getExam();
		
		duration = Long.parseLong(exam.getDuration());
		System.out.println("DURATION: " + duration);
    	
		ObservableList<ExamQuestion> questions = FXCollections.observableArrayList();
		questions.addAll(exam.getExamQuestions());
		tvQuestions.setItems((ObservableList<ExamQuestion>) questions);
    	
		
		tcQuestionPoints.setCellValueFactory(new PropertyValueFactory<ExamQuestion, Integer>("points"));
    	tcQuestionContent.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("details"));
    	tcFilled.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>(""));
    	
    	tvQuestions.getSelectionModel().select(0);
    	onRowClick();
    	tvQuestions.setOnMouseClicked((MouseEvent event) -> {
    	    if (event.getClickCount() > 0) {
    	    	onRowClick();
    	    }
    	});

		}
    
    public void setRemainingTime()
    {
	    Thread timerThread = new Thread(() -> {
	        while (true) {
	            try {
	                Thread.sleep(1000); //1 second
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            final String time = Long.toString(calcTime());
	            Platform.runLater(() -> {
	                tfRemainingTime.setText(time);
	            });
	        }
	    });   timerThread.start();
    }
    
    public long calcTime()
    {
    	long elapsedTime = duration*60*1000 - (System.currentTimeMillis() - startTime);
    	long elapsedSeconds = elapsedTime / 1000;
    	long secondsDisplay = elapsedSeconds % 60;
    	long elapsedMinutes = elapsedSeconds / 60;

    	return elapsedMinutes;
    	
    }
    
	public void onRowClick() {
	    // check the table's selected item and get selected item
	    if (tvQuestions.getSelectionModel().getSelectedItem() != null) {
	    	ExamQuestion selectedRow = tvQuestions.getSelectionModel().getSelectedItem();
	    	taSelectedQuestion.setText(selectedRow.getDetails());
	    	if (selectedRow.getNoteType() == NoteType.Students)
	    	{
	    		tfNote.setText(selectedRow.getNote());
	    	}
	    	else
	    	{
	    		tfNote.setText("");
	    	}
	    	
	    	radio1.setText(selectedRow.getAnswer1());
	    	radio2.setText(selectedRow.getAnswer2());
	    	radio3.setText(selectedRow.getAnswer3());
	    	radio4.setText(selectedRow.getAnswer4());
	    }
	}
    
}
    
