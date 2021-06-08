package models;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;

public class ExecutedExamUI extends ExecutedExam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFXButton questionList;
	private JFXButton gradeApproval;

	public ExecutedExamUI(ExecutedExam executedExam) {
		super(executedExam.getId(), executedExam.getSubject(), executedExam.getCourse(), executedExam.getTeacherID(),
				executedExam.getExecutorTeacherName(), executedExam.getExecDate(), executedExam.getExecTime(),
				executedExam.getTestType(), executedExam.getAvg(), executedExam.getMedian());
	}

	public ExecutedExamUI(String id, String teacherID, String executorTeacherName, String execDate, String execTime,
			double avg, double median) {
		super(id, teacherID, executorTeacherName, execDate, execTime, avg, median);
	}

	public JFXButton getQuestionList() {
		return questionList;
	}

	public void setQuestionList(JFXButton questionList) {
		this.questionList = questionList;
	}

	public JFXButton getGradeApproval() {
		return gradeApproval;
	}

	public void setGradeApproval(JFXButton gradeApproval) {
		this.gradeApproval = gradeApproval;
	}

	@Override
	public String toString() {
		return "ExecutedExamUI [questionList=" + questionList + ", gradeApproval=" + gradeApproval + ", getAvg()="
				+ getAvg() + ", getMedian()=" + getMedian() + ", getId()=" + getId() + ", getSubject()=" + getSubject()
				+ ", getCourse()=" + getCourse() + ", getExecDate()=" + getExecDate() + ", getTestType()="
				+ getTestType() + ", getTeacherID()=" + getTeacherID() + ", getExecutorTeacherName()="
				+ getExecutorTeacherName() + ", getExecTime()=" + getExecTime() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
