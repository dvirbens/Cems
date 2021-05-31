package models;

import ocsf.server.ConnectionToClient;

public class StudentInExam {

	private String studentID;
	private ConnectionToClient client;
	private String[] solution;
	private boolean finished;

	public StudentInExam(String studentID, ConnectionToClient client) {
		super();
		this.studentID = studentID;
		this.client = client;
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



}