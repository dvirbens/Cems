package models;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;

public class StudentExecutedExam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examID;
	private String studentID;
	private String studentName;
	private String TeacherId;
	private String subject;
	private String course;
	private String execDate;
	private String testType;
	private String grade;
	private WordFile copy;
	private boolean approved;
	private String Alert;

	private JFXButton getCopy;

	public StudentExecutedExam(String examID, String studentName, String teacherId, String subject, String course,
			String execDate, String testType, String grade, WordFile copy, boolean approved, String alert) {
		super();
		this.examID = examID;
		this.studentName = studentName;
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Override
	public String toString() {
		return "StudentExecutedExam [examID=" + examID + ", studentID=" + studentID + ", studentName=" + studentName
				+ ", TeacherId=" + TeacherId + ", subject=" + subject + ", course=" + course + ", execDate=" + execDate
				+ ", testType=" + testType + ", grade=" + grade + ", copy=" + copy + ", approved=" + approved
				+ ", Alert=" + Alert + ", getCopy=" + getCopy + "]";
	}
	
}
