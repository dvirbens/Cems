package client.gui;

import static common.ModelWrapper.Operation.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.ExecutedExam;
import models.StudentExecutedExam;
import models.StudentExecutedExamUI;

public class GradeApproveStudentListController implements Initializable {

	@FXML
	private TableView<StudentExecutedExamUI> tvSutdents;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcExamId;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcStudent;

	@FXML
	private TableColumn<StudentExecutedExamUI, TextField> tcGrade;

	@FXML
	private TableColumn<StudentExecutedExamUI, String> tcCopyPercentage;

	@FXML
	private TableColumn<StudentExecutedExamUI, CheckBox> tcApproval;

	@FXML
	private JFXButton btnBack;

	@FXML
	private JFXButton btnSave;

	@FXML
	private Label messageLabel;

	private static ExecutedExam executedExam;

	private static List<StudentExecutedExamUI> executedExamStudentList;

	public GradeApproveStudentListController() {
	}

	public GradeApproveStudentListController(ExecutedExam executedExam) {
		GradeApproveStudentListController.executedExam = executedExam;
	}

	public void start() {
		try {
			Pane studentListPane = (Pane) FXMLLoader.load(getClass().getResource("StudentList.fxml"));
			MainGuiController.getMenuHandler().getMainFrame().setCenter(studentListPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickBack(ActionEvent event) {
		MainGuiController.getMenuHandler().setGradeApprovalScreen();
	}

	@FXML
	void onClickSave(ActionEvent event) {
		List<StudentExecutedExam> approvedStudents = new ArrayList<>();

		for (StudentExecutedExamUI studentUI : executedExamStudentList) {
			if (studentUI.isApproved()) {
				StudentExecutedExam approvedStudent = new StudentExecutedExam(studentUI,
						studentUI.getTfGrade().getText());
				approvedStudents.add(approvedStudent);
			}
		}

		double[] avgAndMedian = getAvarageAndMedian();

		String examID = approvedStudents.get(0).getExamID();
		String subject = approvedStudents.get(0).getSubject();
		String course = approvedStudents.get(0).getCourse();
		String teacherID = approvedStudents.get(0).getTeacherId();
		String execDate = approvedStudents.get(0).getExecDate();
		String testType = approvedStudents.get(0).getTestType();

		double avg = avgAndMedian[0];
		double median = avgAndMedian[1];

		ExecutedExam executedExam = new ExecutedExam(examID, subject, course, teacherID, execDate, testType, avg,
				median);

		ModelWrapper<ExecutedExam> modelWrapper1 = new ModelWrapper<>(executedExam, UPDATE_EXAM_STATISTIC);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper1);

		ModelWrapper<StudentExecutedExam> modelWrapper2 = new ModelWrapper<>(approvedStudents, SAVE_APPROVED_STUDENTS);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper2);

		String serverMessage = Client.getServerMessages();
		messageLabel.setStyle("-fx-text-fill: GREEN;");
		messageLabel.setText(serverMessage);
	}

	private double[] getAvarageAndMedian() {
		List<Integer> studentsGrade = new ArrayList<>();
		int sum = 0;

		for (StudentExecutedExam student : executedExamStudentList) {
			if (student.isApproved()) {
				int studentGrade = Integer.valueOf(student.getGrade());
				studentsGrade.add(studentGrade);
				sum += studentGrade;
			}
		}

		studentsGrade.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});

		double avg = sum / studentsGrade.size();
		double median;

		int n = studentsGrade.size();
		if (n % 2 == 0) {
			int firstStudentGrade = studentsGrade.get((n / 2) - 1);
			int secondStudentGrade = studentsGrade.get((n / 2));
			median = (firstStudentGrade + secondStudentGrade) / 2;
		} else {
			median = studentsGrade.get(((n + 1) / 2) - 1);
		}
		double[] avgAndMedian = { avg, median };
		return avgAndMedian;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String examID = executedExam.getId();
		String date = executedExam.getExecDate();
		String teacherID = executedExam.getTeacherID();
		List<String> parameters = Arrays.asList(examID, date, teacherID);

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(parameters, GET_EXECUTED_EXAM_STUDENT_LIST);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		tcExamId.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("examID"));
		tcStudent.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("studentName"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, TextField>("tfGrade"));
		tcCopyPercentage.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, String>("Alert"));
		tcApproval.setCellValueFactory(new PropertyValueFactory<StudentExecutedExamUI, CheckBox>("gradeApproval"));

		ObservableList<StudentExecutedExamUI> executedExam = FXCollections.observableArrayList();
		executedExamStudentList = changeToUIStudent(Client.getExecutedExamStudentList());
		executedExam.addAll(executedExamStudentList);
		tvSutdents.setItems(executedExam);

	}

	private List<StudentExecutedExamUI> changeToUIStudent(List<StudentExecutedExam> executedExamStudentList) {
		List<StudentExecutedExamUI> studentsUI = new ArrayList<>();
		for (StudentExecutedExam studentExecutedExam : executedExamStudentList) {
			StudentExecutedExamUI studentUI = new StudentExecutedExamUI(studentExecutedExam);

			TextField tfGrade = new TextField(studentExecutedExam.getGrade());
			studentUI.setTfGrade(tfGrade);

			CheckBox approveGrade = new CheckBox();
			approveGrade.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						studentUI.setApproved(true);
					} else {
						studentUI.setApproved(false);
					}
				}
			});

			studentUI.setGradeApproval(approveGrade);

			if (studentUI.isApproved())
				studentUI.getGradeApproval().selectedProperty().set(true);
			studentsUI.add(studentUI);
		}

		return studentsUI;
	}

}
