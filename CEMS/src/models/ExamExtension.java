package models;

import java.io.Serializable;

public class ExamExtension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examID;
	private String code;
	private String teacherID;
	private String teacherName;
	private String timeExtension;
	private String examDuration;
	private String casue;

	public ExamExtension(String examID, String code, String teacherID, String teacherName, String timeExtension,
			String examDuration, String casue) {
		super();
		this.examID = examID;
		this.code = code;
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.timeExtension = timeExtension;
		this.examDuration = examDuration;
		this.casue = casue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getExamDuration() {
		return examDuration;
	}

	public void setExamDuration(String examDuration) {
		this.examDuration = examDuration;
	}

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

	@Override
	public String toString() {
		return "ExamExtension [code=" + code + ", teacherID=" + teacherID + ", teacherName=" + teacherName
				+ ", timeExtension=" + timeExtension + ", casue=" + casue + "]";
	}

}
