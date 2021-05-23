package models;

import java.io.Serializable;

public class ExamExtension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examID;
	private String teacherID;
	private String teacherName;
	private String timeExtension;
	private String casue;

	public ExamExtension(String examID, String teacherID, String teacherName, String timeExtension, String casue) {
		super();
		this.examID = examID;
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.timeExtension = timeExtension;
		this.casue = casue;
	}

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public String getTimeExtension() {
		return timeExtension;
	}

	public void setTimeExtension(String timeExtension) {
		this.timeExtension = timeExtension;
	}

	public String getCasue() {
		return casue;
	}

	public void setCasue(String casue) {
		this.casue = casue;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Override
	public String toString() {
		return "ExamExtension [examID=" + examID + ", teacherID=" + teacherID + ", teacherName=" + teacherName
				+ ", timeExtension=" + timeExtension + ", casue=" + casue + "]";
	}

}
