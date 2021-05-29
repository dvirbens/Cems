package models;

import java.io.Serializable;
import java.util.Random;

public class ExamProcess implements Serializable {

	private String computerizedExamID;
	private String examId;
	private String date;
	private String time;
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

	public ExamProcess(String examId, String date,String time, String teacherID, String code) {
		super();
		this.examId = examId;
		this.date = date;
		this.time=time;
		this.teacherID = teacherID;
		this.code = code;
		type = ExamType.Computerized;
	}

	public ExamProcess(String manualSubject, String manulCourse, String manualDuration, String date,String time, String teacherID,
			String code, WordFile manualFile) {
		super();
		Random r=new Random();
		this.examId=String.valueOf(r.nextInt(99999)); 
		this.manualSubject = manualSubject;
		this.manulCourse = manulCourse;
		this.manualDuration = manualDuration;
		this.date = date;
		this.time=time;
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

	public String getexamId() {
		return examId;
	}

	public void setexamId(String examId) {
		this.examId = examId;
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
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ExamProcess [computerizedExamID=" + computerizedExamID + ", date=" + date + ", teacherID=" + teacherID
				+ ", code=" + code + ", manualSubject=" + manualSubject + ", manulCourse=" + manulCourse
				+ ", manualDuration=" + manualDuration + ", manualFile=" + manualFile + ", type=" + type + "]";
	}

}
