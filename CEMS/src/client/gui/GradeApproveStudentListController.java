package client.gui;

import static common.ModelWrapper.Operation.GET_EXECUTED_EXAM_STUDENT_LIST;
import static common.ModelWrapper.Operation.SAVE_APPROVED_STUDENTS;
import static common.ModelWrapper.Operation.UPDATE_EXAM_STATISTIC;

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

public class GradeApproveStudentListController implements Initializable {

	@FXML
	private TableView<StudentExecutedExam> tvSutdents;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcExamId;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcStudent;

	@FXML
	private TableColumn<StudentExecutedExam, TextField> tcGrade;

	@FXML
	private TableColumn<StudentExecutedExam, String> tcCopyPercentage;

	@FXML
	private TableColumn<StudentExecutedExam, CheckBox> tcApproval;

	@FXML
	private JFXButton btnBack;

	@FXML
	private JFXButton btnSave;

	@FXML
	private Label messageLabel;

	private static ExecutedExam executedExam;

	private static List<StudentExecutedExam> executedExamStudentList;

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

		for (StudentExecutedExam student : executedExamStudentList) {
			if (student.isApproved()) {
				StudentExecutedExam approvedStudent = new StudentExecutedExam(student, student.getTfGrade().getText());
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

		tcExamId.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("examID"));
		tcStudent.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("studentName"));
		tcGrade.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, TextField>("tfGrade"));
		tcCopyPercentage.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, String>("Alert"));
		tcApproval.setCellValueFactory(new PropertyValueFactory<StudentExecutedExam, CheckBox>("gradeApproval"));

		ObservableList<StudentExecutedExam> executedExam = FXCollections.observableArrayList();
		executedExamStudentList = addApproveButton(Client.getExecutedExamStudentList());
		executedExam.addAll(executedExamStudentList);
		tvSutdents.setItems(executedExam);

	}

	private List<StudentExecutedExam> addApproveButton(List<StudentExecutedExam> executedExamStudentList) {

		for (StudentExecutedExam executedStudentExam : executedExamStudentList) {
			TextField tfGrade = new TextField(executedStudentExam.getGrade());
			executedStudentExam.setTfGrade(tfGrade);

			CheckBox approveGrade = new CheckBox();
			approveGrade.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						executedStudentExam.setApproved(true);
					} else {
						executedStudentExam.setApproved(false);
					}
				}
			});

			executedStudentExam.setGradeApproval(approveGrade);

			if (executedStudentExam.isApproved())
				executedStudentExam.getGradeApproval().selectedProperty().set(true);
		}

		return executedExamStudentList;
	}

}
