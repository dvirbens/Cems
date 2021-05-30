package models;

import ocsf.server.ConnectionToClient;

public class StudentInExam {

	String studentID;
	ConnectionToClient client;
	String[] soloution;
	boolean isFinished;

	public StudentInExam(String studentID, ConnectionToClient client) {
		super();
		this.studentID = studentID;
		this.client = client;
		this.soloution = soloution;
		this.isFinished = isFinished;
	}

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

	public String[] getSoloution() {
		return soloution;
	}

	public void setSoloution(String[] soloution) {
		this.soloution = soloution;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

}
