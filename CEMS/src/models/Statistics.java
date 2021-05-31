package models;

import java.io.Serializable;

public class Statistics implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userID = null;
	private String FirstName = null;
	private String LastName = null;
	private String Grade = null;
	
	public Statistics(String userID, String FirstName, String LastName, String Grade) {
		super();
		this.userID = userID;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Grade = Grade;
	}

//	constructor statistics by courses:
	public Statistics(String userID, String Grade) {
		super();
		this.userID = userID;
		this.Grade = Grade;
	}
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return FirstName;
	}
	
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getGrade() {
		return Grade;
	}

	public void setGrade(String grade) {
		Grade = grade;
	}
	
}
