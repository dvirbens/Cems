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
	private double avg;
	private double median;

	public ExecutedExam(String id, String subject, String course, String teacherID, String executorTeacherName,
			String execDate, String execTime, String testType, double avg, double median) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.teacherID = teacherID;
		this.executorTeacherName = executorTeacherName;
		this.execDate = execDate;
		this.execTime = execTime;
		this.testType = testType;
		this.avg = avg;
		this.median = median;
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
			double avg, double median) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.teacherID = teacherID;
		this.execDate = execDate;
		this.testType = testType;
		this.avg = avg;
		this.median = median;
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

	@Override
	public String toString() {
		return "ExecutedExam [id=" + id + ", subject=" + subject + ", course=" + course + ", teacherID=" + teacherID
				+ ", executorTeacherName=" + executorTeacherName + ", execDate=" + execDate + ", execTime=" + execTime
				+ ", testType=" + testType + ", avg=" + avg + ", median=" + median + "]";
	}

}
