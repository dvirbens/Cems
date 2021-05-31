package models;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

import com.jfoenix.controls.JFXButton;

public class StudentExecutedExam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examID;
	private String studentID;
	private String TeacherId;
	private String subject;
	private String course;
	private String execDate;
	private String testType;
	private String grade;
	private WordFile copy;
	private String approved;
	private String Alert;

	private JFXButton getCopy;

	public StudentExecutedExam(String examID, String studentID, String teacherId, String subject, String course,
			String execDate, String testType, String grade, WordFile copy, String approved, String alert) {
		super();
		this.examID = examID;
		this.studentID = studentID;
		TeacherId = teacherId;
		this.subject = subject;
		this.course = course;
		this.execDate = execDate;
		this.testType = testType;
		this.grade = grade;
		this.copy = copy;
		this.approved = approved;
		Alert = alert;
	}

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getTeacherId() {
		return TeacherId;
	}

	public void setTeacherId(String teacherId) {
		TeacherId = teacherId;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getAlert() {
		return Alert;
	}

	public void setAlert(String alert) {
		Alert = alert;
	}

	public JFXButton getGetCopy() {
		return getCopy;
	}

	public void setGetCopy(JFXButton getCopy) {
		this.getCopy = getCopy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public WordFile getCopy() {
		return copy;
	}

	public void setCopy(WordFile copy) {
		this.copy = copy;
	}



	

	
}
