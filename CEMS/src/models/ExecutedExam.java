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
	private String execTime;
	private String testType;
	private String finishedStudentsCount;
	private boolean approved;
	private double avg;
	private double median;
	private JFXButton questionList;
	private JFXButton gradeApproval;

	public ExecutedExam(String id, String subject, String course, String teacherID, String executorTeacherName,
			String execDate, String execTime, String finishedStudentsCount, String testType, double avg, double median,
			boolean approved) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.teacherID = teacherID;
		this.executorTeacherName = executorTeacherName;
		this.execDate = execDate;
		this.execTime = execTime;
		this.finishedStudentsCount = finishedStudentsCount;
		this.testType = testType;
		this.avg = avg;
		this.median = median;
		this.approved = approved;

	}

	public ExecutedExam(String id, String subject, String course, String execDate, String testType, int grade) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.execDate = execDate;
		this.testType = testType;
	}

	public ExecutedExam(String id, String executorTeacherName, String teacherID, String subject, String course,
			String execDate, String execTime, String testType, boolean approved, double avg) {
		super();
		this.executorTeacherName = executorTeacherName;
		this.id = id;
		this.subject = subject;
		this.teacherID = teacherID;
		this.course = course;
		this.execDate = execDate;
		this.execTime = execTime;
		this.testType = testType;
		this.approved = approved;
		this.avg = avg;
	}

	public ExecutedExam(String id, String teacherID, String executorTeacherName, String execDate, String execTime,
			double avg, double median) {
		this.id = id;
		this.teacherID = teacherID;
		this.avg = avg;
		this.execDate = execDate;
		this.execTime = execTime;
		this.executorTeacherName = executorTeacherName;
		this.median = median;
	}

	public ExecutedExam(String id, String subject, String course, String teacherID, String execDate, String testType,
			double avg, double median, boolean approved) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.teacherID = teacherID;
		this.execDate = execDate;
		this.testType = testType;
		this.avg = avg;
		this.median = median;
		this.approved = approved;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
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

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
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

	public String getFinishedStudentsCount() {
		return finishedStudentsCount;
	}

	public void setFinishedStudentsCount(String finishedStudentsCount) {
		this.finishedStudentsCount = finishedStudentsCount;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "ExecutedExam [id=" + id + ", subject=" + subject + ", course=" + course + ", teacherID=" + teacherID
				+ ", executorTeacherName=" + executorTeacherName + ", execDate=" + execDate + ", execTime=" + execTime
				+ ", testType=" + testType + ", finishedStudentsCount=" + finishedStudentsCount + ", avg=" + avg
				+ ", median=" + median + ", questionList=" + questionList + ", gradeApproval=" + gradeApproval + "]";
	}

}
