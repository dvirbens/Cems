package models;

import java.io.Serializable;
import java.util.Date;

public class ExamProcess implements Serializable {

	private String examID;
	private String date;
	private String teacherID;
	private String timeExtension;
	private String code;
	private ExamType type;

	public enum ExamType {
		COMPUTERIZED, MANUAL
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExamProcess(String examID, String date, String teacherID, String code, ExamType type) {
		super();
		this.examID = examID;
		this.date = date;
		this.teacherID = teacherID;
		this.code = code;
		this.type = type;
	}

	public ExamProcess(String date, String teacherID, String code, ExamType type) {
		super();
		this.date = date;
		this.teacherID = teacherID;
		this.code = code;
		this.type = type;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ExamProcess [examID=" + examID + ", date=" + date + ", teacherID=" + teacherID + ", timeExtension="
				+ timeExtension + ", code=" + code + "]";
	}

}
