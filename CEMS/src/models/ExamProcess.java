package models;

import java.io.Serializable;
import java.util.Random;

import ocsf.server.ConnectionToClient;

public class ExamProcess implements Serializable {

	private String examId;
	private String date;
	private String time;
	private String teacherID;
	private String code;
	private String subject;
	private String course;
	private String duration;
	private String finishedStudentsCount;
	private ConnectionToClient teacherClient;
	private WordFile manualFile;
	private ExamType type;

	public enum ExamType {
		Computerized, Manual
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExamProcess(String examId, String date, String time, String teacherID, String code, String subject,
			String course, String duration) {
		super();
		this.examId = examId;
		this.date = date;
		this.time = time;
		this.teacherID = teacherID;
		this.code = code;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
		this.type = ExamType.Computerized;
	}

	public ExamProcess(String examId, String date, String time, String teacherID, String code, String subject,
			String course, String duration, WordFile manualFile) {
		super();
		this.examId = examId;
		this.date = date;
		this.time = time;
		this.teacherID = teacherID;
		this.code = code;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
		this.manualFile = manualFile;
		this.type = ExamType.Manual;
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

	public void setexamId(String examId) {
		this.examId = examId;
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

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public ConnectionToClient getTeacherClient() {
		return teacherClient;
	}

	public void setTeacherClient(ConnectionToClient teacherClient) {
		this.teacherClient = teacherClient;
	}

	public String getFinishedStudentsCount() {
		return finishedStudentsCount;
	}

	public void setFinishedStudentsCount(String finishedStudentsCount) {
		this.finishedStudentsCount = finishedStudentsCount;
	}

	@Override
	public String toString() {
		return "ExamProcess [examId=" + examId + ", date=" + date + ", time=" + time + ", teacherID=" + teacherID
				+ ", code=" + code + ", subject=" + subject + ", course=" + course + ", duration=" + duration
				+ ", manualFile=" + manualFile + ", type=" + type + "]";
	}

}
