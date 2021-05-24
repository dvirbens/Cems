package client.gui;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.ExamQuestion;

public class MenuHandler {

	private BorderPane mainFrame;

	public MenuHandler(BorderPane mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void setLoginMenu() {
		try {
			Pane loginMenuPane = (Pane) FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
			Pane loginLogoPane = (Pane) FXMLLoader.load(getClass().getResource("LoginLogo.fxml"));
			mainFrame.setLeft(loginMenuPane);
			mainFrame.setCenter(loginLogoPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTeacherMenu() {
		try {
			Pane teacherMenuPane = (Pane) FXMLLoader.load(getClass().getResource("TeacherMenu.fxml"));
			mainFrame.setLeft(teacherMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPrincipalMenu() {
		try {
			Pane principalMenuPane = (Pane) FXMLLoader.load(getClass().getResource("PrincipalMenu.fxml"));
			mainFrame.setLeft(principalMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStudentlMenu() {
		try {
			Pane sutdentMenuPane = (Pane) FXMLLoader.load(getClass().getResource("StudentMenu.fxml"));
			mainFrame.setLeft(sutdentMenuPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCreateExamScreen() {
		try {
			Pane createExamPane = (Pane) FXMLLoader.load(getClass().getResource("CreateExam.fxml"));
			mainFrame.setCenter(createExamPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCreateQuestionScreen() {
		try {
			Pane createQuestionPane = (Pane) FXMLLoader.load(getClass().getResource("CreateQuestion.fxml"));
			mainFrame.setCenter(createQuestionPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStartExamScreen() {
		try {
			Pane startExamPane = (Pane) FXMLLoader.load(getClass().getResource("StartExam.fxml"));
			mainFrame.setCenter(startExamPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setExamStatisticScreen() {
		try {
			Pane examStatisticPane = (Pane) FXMLLoader.load(getClass().getResource("ExamStatistic.fxml"));
			mainFrame.setCenter(examStatisticPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setGradeApprovalScreen() {
		try {
			Pane gradeApprovalPane = (Pane) FXMLLoader.load(getClass().getResource("GradeApproval.fxml"));
			mainFrame.setCenter(gradeApprovalPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setExecutedExamsScreen() {
		try {
			Pane executedExamsPane = (Pane) FXMLLoader.load(getClass().getResource("ExecutedExams.fxml"));
			mainFrame.setCenter(executedExamsPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setManualTestScreen() {
		try {
			Pane manualTestPane = (Pane) FXMLLoader.load(getClass().getResource("ManualTest.fxml"));
			mainFrame.setCenter(manualTestPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setComputerizedTestScreen() {
		try {
			Pane computerizedTestPane = (Pane) FXMLLoader.load(getClass().getResource("ComputerizedTest.fxml"));
			mainFrame.setCenter(computerizedTestPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setEnterExamScreen() {
		try {
			Pane enterExamPane = (Pane) FXMLLoader.load(getClass().getResource("EnterExam.fxml"));
			mainFrame.setCenter(enterExamPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOverallStatisticsScreen() {
		try {
			Pane overallStatisticsPane = (Pane) FXMLLoader.load(getClass().getResource("OverallStatistics.fxml"));
			mainFrame.setCenter(overallStatisticsPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTimeExtensionRequestsScreen() {
		try {
			Pane timeExtensionRequestsPane = (Pane) FXMLLoader
					.load(getClass().getResource("TimeExtensionRequests.fxml"));
			mainFrame.setCenter(timeExtensionRequestsPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setQuestionListScreen(List<ExamQuestion> examQuestions) {
		QuestionListController questionListController = new QuestionListController(examQuestions);
		questionListController.start();
	}

	public BorderPane getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(BorderPane mainFrame) {
		this.mainFrame = mainFrame;
	}

}
