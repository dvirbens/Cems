package client.gui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import models.ComputerizedTestReport;
import models.ExamQuestion;
import models.StudentExecutedExam;

public class StudentComputerizedTestReportController implements Initializable{


    @FXML
    private TableView<ComputerizedTestReport> tvQuestions;

    @FXML
    private TableColumn<ComputerizedTestReport, Integer> tcQuestionNumber;

    @FXML
    private TableColumn<ComputerizedTestReport, String> tcSelectedAnswer;
    
    @FXML
    private TableColumn<ComputerizedTestReport, String> tcCorrectAnswer;
    
    @FXML
    private TableColumn<ComputerizedTestReport, String> tcQuestionPoints;

    @FXML
    private TableColumn<ComputerizedTestReport, ImageView> tcCorrect;

    @FXML
    private Label lblQuestions;

    @FXML
    private Label lblSelectedQuestion;

    @FXML
    private TextArea taSelectedQuestion;

    @FXML
    private Label lblPossibleAnswers1;

    @FXML
    private JFXRadioButton radio1;

    @FXML
    private ToggleGroup AnswersGroup;

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

    @FXML
    private TextField lbl_Course;

    @FXML
    private Label lblPossibleAnswers11;

    @FXML
    private Label lbl_grade;

    @FXML
    private TextField lbl_Subject;

    @FXML
    private JFXButton btnBack;
	    
    private ComputerizedTestReport exam;

	    
    
    
	    public StudentComputerizedTestReportController() {
	    }

		public StudentComputerizedTestReportController(ComputerizedTestReport exam) {
			this.exam = exam;
		}

		public void start() {
			try {
				Pane studentComputerizedTestReport = (Pane) FXMLLoader.load(getClass().getResource("StudentComputerizedTestReport.fxml"));
				MainGuiController.getMenuHandler().getMainFrame().setCenter(studentComputerizedTestReport);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			set_Subject_Course();
			
			tcQuestionNumber
			.setCellValueFactory(new Callback<CellDataFeatures<ComputerizedTestReport, Integer>, ObservableValue<Integer>>() {
				@Override
				public ObservableValue<Integer> call(CellDataFeatures<ComputerizedTestReport, Integer> p) {
					return new ReadOnlyObjectWrapper(tvQuestions.getItems().indexOf(p.getValue()) + 1 + "");
				}
			});
			
			
			tcSelectedAnswer
			.setCellValueFactory(new Callback<CellDataFeatures<ComputerizedTestReport, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ComputerizedTestReport, String> p) {
					return new ReadOnlyObjectWrapper((exam.getSelectedAnswers())[tvQuestions.getItems().indexOf(p.getValue())]);
				}
			});
			
			tcCorrectAnswer
			.setCellValueFactory(new Callback<CellDataFeatures<ComputerizedTestReport, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ComputerizedTestReport, String> p) {
					return new ReadOnlyObjectWrapper((exam.getCorrectAnswers()).get(tvQuestions.getItems().indexOf(p.getValue())));
				}
			});
			
			tcQuestionPoints.setCellValueFactory(new PropertyValueFactory<ComputerizedTestReport, String>("points"));
			tcCorrect.setCellValueFactory(new PropertyValueFactory<ComputerizedTestReport, ImageView>("correctImg"));

			for(int i=0; i < exam.getNumOfQuestions(); i++)
			{
				if (exam.getSelectedAnswers()[i] == exam.getCorrectAnswers().get(i))
				{
					final ImageView imageview = new ImageView(new Image(getClass().getResource("correct.png").toExternalForm()));
					imageview.setFitHeight(30);
					imageview.setFitWidth(30);
				}
				else
				{
					final ImageView imageview = new ImageView(new Image(getClass().getResource("wrong.png").toExternalForm()));
					imageview.setFitHeight(30);
					imageview.setFitWidth(30);
				}

			}
			
		}
		
		public void set_Subject_Course()
		{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					//lbl_Subject.setText(exam.getSubject());
					//lbl_Course.setText(exam.getCourse());	
					System.out.println("Course: " + exam.getCourse());
				}
			});
		}
		
		
		@FXML
	    void onClickBack(ActionEvent event) {
			MainGuiController.getMenuHandler().setExecutedExamsScreen();
	    }
}
