package models;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;

public class ExecutedExam implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String subject;
	private String course;
	private String teacherID;
	private String executorTeacherName;
	private String execDate;
	private String testType;
	private JFXButton questionList;
	private JFXButton gradeApproval;

	public ExecutedExam(String id, String subject, String course, String execDate, String testType, int grade) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.execDate = execDate;
		this.testType = testType;
	}

	public ExecutedExam(String id, String executorTeacherName, String teacherID, String subject, String course,
			String execDate, String testType) {
		super();
		this.executorTeacherName = executorTeacherName;
		this.id = id;
		this.subject = subject;
		this.teacherID = teacherID;
		this.course = course;
		this.execDate = execDate;
		this.testType = testType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getExecDate() {
		return execDate;
	}

	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public String getExecutorTeacherName() {
		return executorTeacherName;
	}

	public void setExecutorTeacherName(String executorTeacherName) {
		this.executorTeacherName = executorTeacherName;
	}

	public void setQuestionList(JFXButtonSerializeable questionList) {
		this.questionList = questionList;
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
		return "ExecutedExam [id=" + id + ", subject=" + subject + ", course=" + course + ", teacherID=" + teacherID
				+ ", executorTeacherName=" + executorTeacherName + ", execDate=" + execDate + ", testType=" + testType
				+ ", questionList=" + questionList + ", gradeApproval=" + gradeApproval + "]";
	}

}
