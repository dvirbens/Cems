package models;

import java.io.Serializable;
import java.util.Arrays;

import ocsf.server.ConnectionToClient;

/**
 * Class that  represent a specific Student in a specific exam.
 * 
 * @author Shenhav , Aviel
 *
 */

public class StudentInExam implements Serializable {

	private static final long serialVersionUID = 1L;
	private String studentID;
	private String code;
	private String grade;
	private String execDuration;
	private ConnectionToClient client;
	private String[] solution;
	private boolean finished;

/****************************** Constructors ************************************/
	public StudentInExam(String studentID, String code, String grade, String execDuration, String[] solution) {
		super();
		this.studentID = studentID;
		this.code = code;
		this.grade = grade;
		this.execDuration = execDuration;
		this.solution = solution;
	}

	public StudentInExam(String studentID, ConnectionToClient client) {
		super();
		this.studentID = studentID;
		this.client = client;
	}

/***************************** Setters and getters **********************************/
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public ConnectionToClient getClient() {
		return client;
	}

	public void setClient(ConnectionToClient client) {
		this.client = client;
	}

	public String[] getSolution() {
		return solution;
	}

	public void setSolution(String[] solution) {
		this.solution = solution;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getExecDuration() {
		return execDuration;
	}

	public void setExecDuration(String execDuration) {
		this.execDuration = execDuration;
	}

	@Override
	public String toString() {
		return "StudentInExam [studentID=" + studentID + ", code=" + code + ", grade=" + grade + ", client=" + client
				+ ", solution=" + Arrays.toString(solution) + ", finished=" + finished + "]";
	}

}
