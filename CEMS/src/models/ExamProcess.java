package models;

import java.io.Serializable;
import java.util.Date;

public class ExamProcess implements Serializable {

	private String computerizedExamID;
	private String date;
	private String teacherID;
	private String code;
	private String manualSubject;
	private String manulCourse;
	private String manualDuration;
	private WordFile manualFile;
	private ExamType type;

	public enum ExamType {
		Computerized, Manual
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExamProcess(String computerizedExamID, String date, String teacherID, String code) {
		super();
		this.computerizedExamID = computerizedExamID;
		this.date = date;
		this.teacherID = teacherID;
		this.code = code;
		type = ExamType.Computerized;
	}

	public ExamProcess(String manualSubject, String manulCourse, String manualDuration, String date, String teacherID,
			String code, WordFile manualFile) {
		super();
		this.manualSubject = manualSubject;
		this.manulCourse = manulCourse;
		this.manualDuration = manualDuration;
		this.date = date;
		this.teacherID = teacherID;
		this.code = code;
		this.manualFile = manualFile;
		type = ExamType.Manual;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
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

	public String getComputerizedExamID() {
		return computerizedExamID;
	}

	public void setComputerizedExamID(String computerizedExamID) {
		this.computerizedExamID = computerizedExamID;
	}

	public String getManualSubject() {
		return manualSubject;
	}

	public void setManualSubject(String manualSubject) {
		this.manualSubject = manualSubject;
	}

	public String getManulCourse() {
		return manulCourse;
	}

	public void setManulCourse(String manulCourse) {
		this.manulCourse = manulCourse;
	}

	public String getManualDuration() {
		return manualDuration;
	}

	public void setManualDuration(String manualDuration) {
		this.manualDuration = manualDuration;
	}

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
		this.type = type;
	}

	public WordFile getManualFile() {
		return manualFile;
	}

	public void setManualFile(WordFile manualFile) {
		this.manualFile = manualFile;
	}

	@Override
	public String toString() {
		return "ExamProcess [computerizedExamID=" + computerizedExamID + ", date=" + date + ", teacherID=" + teacherID
				+ ", code=" + code + ", manualSubject=" + manualSubject + ", manulCourse=" + manulCourse
				+ ", manualDuration=" + manualDuration + ", manualFile=" + manualFile + ", type=" + type + "]";
	}

}
