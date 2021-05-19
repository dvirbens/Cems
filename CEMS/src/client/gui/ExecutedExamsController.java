package client.gui;

import static common.ModelWrapper.Operation.EXAM_EXECUTE;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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
import models.ExecutedExam;

public class ExecutedExamsController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<ExecutedExam> tvExExams;

	@FXML
	private TableColumn<ExecutedExam, String> tcStudentID;

	@FXML
	private TableColumn<ExecutedExam, String> tcExamID;

	@FXML
	private TableColumn<ExecutedExam, String> tcSubject;

	@FXML
	private TableColumn<ExecutedExam, String> tcCourse;

	@FXML
	private TableColumn<ExecutedExam, String> tcExecDate;

	@FXML
	private TableColumn<ExecutedExam, String> tcTestType;

	@FXML
	private TableColumn<ExecutedExam, Integer> tcGrade;

	@FXML
	private TableColumn<ExecutedExam, JFXButton> tcGetTest;

	public void initialize(URL location, ResourceBundle resources) {
		tcExamID.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("id"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("course"));
		tcExecDate.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("execDate"));
		tcTestType.setCellValueFactory(new PropertyValueFactory<ExecutedExam, String>("testType"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<ExecutedExam, Integer>("grade"));
		tcGetTest.setCellValueFactory(new PropertyValueFactory<ExecutedExam, JFXButton>("getCopy"));

		String userID = Client.getUser().getUserID();

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(userID, EXAM_EXECUTE);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		ObservableList<ExecutedExam> exams = FXCollections.observableArrayList();
		exams.addAll(Client.getExecExams());
		tvExExams.setItems(exams);

		setExamGetCopyButtons(Client.getExecExams());
	}
	
	private void setExamGetCopyButtons(List<ExecutedExam> exams) {

		for (ExecutedExam exam : exams) {
			JFXButton getCoptyButton = new JFXButton();
			getCoptyButton.setPrefSize(90, 15);
			getCoptyButton
					.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
			getCoptyButton.setText("Get Copy");
			
			getCoptyButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					//ModelWrapper<String> modelWrapper = new ModelWrapper<>(EXAM_EXECUTE);
					//ClientUI.getClientController().sendClientUIRequest(modelWrapper);
					
					System.out.println("HELLO");
				}

			});

			exam.setGetCopy(getCoptyButton);
		}
	}

}
