package client.gui;

import static common.ModelWrapper.Operation.CREATE_QUESTION;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import models.Question;
import models.User;

public class CreateQuestionController implements Initializable {

	@FXML
	private JFXComboBox<String> cbSubject;

	@FXML
	private JFXComboBox<String> cbCourse;

	@FXML
	private JFXComboBox<Integer> cbCorrectAnswer;

	@FXML
	private TextArea taQuestion;

	@FXML
	private JFXButton btnCreateQuestion;

	@FXML
	private TextArea taAnswer1;

	@FXML
	private TextArea taAnswer2;

	@FXML
	private TextArea taAnswer3;

	@FXML
	private TextArea taAnswer4;

	@FXML
	void cbCorrect(ActionEvent event) {

	}

	@FXML
	void onClickCreateQuestion(ActionEvent event) {
		User teacherUser = Client.getUser();
		String details = taQuestion.getText();
		String answer1 = taAnswer1.getText();
		String answer2 = taAnswer2.getText();
		String answer3 = taAnswer3.getText();
		String answer4 = taAnswer4.getText();
		String teacherName = teacherUser.getFirstName() + " " + teacherUser.getLastName();
		String subject = cbSubject.getSelectionModel().getSelectedItem();
		String course = cbCourse.getSelectionModel().getSelectedItem();
		int correctAnswer = cbCorrectAnswer.getSelectionModel().getSelectedItem();
		Question question = new Question(teacherName, subject, course, details, answer1, answer2, answer3, answer4,
				correctAnswer);

		ModelWrapper<Question> modelWrapper = new ModelWrapper<>(question, CREATE_QUESTION);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());
		cbCourse.getItems().addAll(Client.getSubjectCollection().getCourses());
		cbCorrectAnswer.getItems().add(1);
		cbCorrectAnswer.getItems().add(2);
		cbCorrectAnswer.getItems().add(3);
		cbCorrectAnswer.getItems().add(4);
	}

	@FXML
	void onSubjectSelected(ActionEvent event) {
		String subjectSelected = cbSubject.getSelectionModel().getSelectedItem();
		List<String> courseList = Client.getSubjectCollection().getCourseListBySubject(subjectSelected);
		cbCourse.getItems().clear();
		cbCourse.getItems().addAll(courseList);
	}

}
