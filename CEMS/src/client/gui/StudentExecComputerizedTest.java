package client.gui;

import static common.ModelWrapper.Operation.GET_EXAM_BY_EXAM_ID;
import static common.ModelWrapper.Operation.GET_QUESTION_LIST_BY_EXAM_ID;

import java.net.URL;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
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
    
    private int[] answersArr;
    
    private int selectedRadio;
    
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
		
		answersArr = new int[exam.getExamQuestions().size()];
		
		duration = Long.parseLong(exam.getDuration());
		System.out.println("DURATION: " + duration);
    	
		ObservableList<ExamQuestion> questions = FXCollections.observableArrayList();
		questions.addAll(exam.getExamQuestions());
		tvQuestions.setItems((ObservableList<ExamQuestion>) questions);
    	
		//tcQuestionNumber.setCellValueFactory(new Callback<ExamQuestion, Integer>);
		
		tcQuestionNumber.setCellValueFactory(new Callback<CellDataFeatures<ExamQuestion, Integer>, ObservableValue<Integer>>() {
			  @Override public ObservableValue<Integer> call(CellDataFeatures<ExamQuestion, Integer> p) {
			    return new ReadOnlyObjectWrapper(tvQuestions.getItems().indexOf(p.getValue())+1 + "");
			  }
			});   
		
		
		tcQuestionPoints.setCellValueFactory(new PropertyValueFactory<ExamQuestion, Integer>("points"));
    	tcQuestionContent.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>("details"));
    	tcFilled.setCellValueFactory(new PropertyValueFactory<ExamQuestion, String>(""));
    	
    	tcQuestionNumber.setSortable(false);
    	tcQuestionPoints.setSortable(false);
    	tcQuestionContent.setSortable(false);
    	tcFilled.setSortable(false);
    	
    	AnswersGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle)
            {
            	selectedRadio = Character.getNumericValue((((JFXRadioButton)newToggle).getId().charAt(5)));
            	System.out.println(selectedRadio);
            }
        });
    	
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
	            long timeInSeconds = calcTime();
	            long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds);
	            long seconds = timeInSeconds % 60;
	            
	            final String time = Long.toString(minutes) + ":" + Long.toString(seconds);
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
    	
    	return elapsedSeconds;
    	
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
	
	@FXML
	public void onSaveClick(ActionEvent event)
	{
		  if (AnswersGroup.getSelectedToggle() != null) {
			  int selectedQuestion = tvQuestions.getSelectionModel().getSelectedIndex();
			  System.out.println("Question: " + selectedQuestion + " radio: " + selectedRadio);
			  answersArr[selectedQuestion] = selectedRadio;
		  }
	}
    
}
    
