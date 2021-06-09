package client.gui;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class StudentComputerizedTestReportController {

	    @FXML
	    private TableView<?> tvQuestions;

	    @FXML
	    private TableColumn<?, ?> tcQuestionNumber;

	    @FXML
	    private TableColumn<?, ?> tcQuestionPoints;

	    @FXML
	    private TableColumn<?, ?> tcFilled;

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
	    private Label lblPossibleAnswers11;

	    @FXML
	    private Label lbl_grade;

	    @FXML
	    private JFXButton btnBack;

	    @FXML
	    void onClickBack(ActionEvent event) {
			MainGuiController.getMenuHandler().setExecutedExamsScreen();
	    }
}
