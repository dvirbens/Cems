package models;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;

public class ExecutedExam implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String subject;
	private String course;
	private String TeacherId;
	private String execDate;
	private String testType;
	private int grade;
	private JFXButton getCopy;
	private JFXButton details;
	private JFXButton studentList;


	public ExecutedExam(String id, String subject, String course, String execDate, String testType, int grade) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.execDate = execDate;
		this.testType = testType;
		this.grade = grade;
	}

	public ExecutedExam(String id, String subject, String course, String execDate, String testType) {
		super();
		this.id = id;
		this.subject = subject;
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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
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

	public JFXButton getDetails() {
		return details;
	}

	public void setDetails(JFXButton details) {
		this.details = details;
	}
	
	public JFXButton getStudentList() {
		return studentList;
	}

	public void setStudentList(JFXButton studentList) {
		this.studentList = studentList;
	}
	


	public String getTeacherId() {
		return TeacherId;
	}

	public void setTeacherId(String teacherId) {
		TeacherId = teacherId;
	}

	@Override
	public String toString() {
		return "ExecutedExam [id=" + id + ", subject=" + subject + ", course=" + course + ", TeacherId=" + TeacherId
				+ ", execDate=" + execDate + ", testType=" + testType + ", grade=" + grade + "]";
	}


}
