package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ExamQuestion;
import models.Question;

public class StudentExecComputerizedTest implements Initializable{

    @FXML
    private TableView<Question> tvQuestions;

    @FXML
    private TableColumn<Question, Integer> tcQuestionNumber;

    @FXML
    private TableColumn<ExamQuestion, Integer> tcQuestionPoints;

    @FXML
    private TableColumn<Question, String> tcQuestionContent;

    @FXML
    private TableColumn<Question, String> tcFilled;

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
    private TextField tfNote;

    @FXML
    private Label lblNote;
    
    
    public void initialize(URL location, ResourceBundle resources) {
		//setStudentIDTextField();
    	// Get questions
    	String examID = Client.getExamID();
		ModelWrapper<String> modelWrapper = new ModelWrapper<String>("121212", GET_QUESTION_LIST_BY_EXAM_ID);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
    	



    	
		ObservableList<Question> questions = FXCollections.observableArrayList();
		questions.addAll((ArrayList<Question>) Client.getQuestions());
		tvQuestions.setItems((ObservableList<Question>) questions);
    	
    	//tcQuestionNumber.setCellValueFactory(new PropertyValueFactory<Number, String>(tvQuestions.getItems().indexOf(tcQuestionNumber.getValue()));
		tcQuestionPoints.setCellValueFactory(new PropertyValueFactory<ExamQuestion, Integer>("points"));
    	tcQuestionContent.setCellValueFactory(new PropertyValueFactory<Question, String>("details"));
    	tcFilled.setCellValueFactory(new PropertyValueFactory<Question, String>(""));
    	

    	
    	/*
    	tcQuestionNumber.setCellFactory(col -> new TableCell<Task, String>() {
    	    @Override
    	    protected void updateIndex(int index) {
    	        super.updateIndex(index);
    	        if (isEmpty() || index < 0) {
    	            setText(null);
    	        } else {
    	            setText(Integer.toString(index));
    	        }
    	    }
    	});
		

		//String userID = Client.getUser().getUserID();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(userID, EXAM_EXECUTE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<ExecutedExam> exams = FXCollections.observableArrayList();
		exams.addAll(Client.getExecExams());
		tvExExams.setItems(exams);

		setExamGetCopyButtons(Client.getExecExams());
    	 */
		}
    
}
    
