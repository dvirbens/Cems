package models;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userID;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private UserType type;
	private ErrorType error;

	public enum ErrorType {
		WRONG_ID, WRONG_PASSWORD
	}

	public enum UserType {
		Student, Teacher, Principal
	}

	public User() {

	}

	public User(ErrorType error) {
		this.error = error;
	}

	public User(String userID, String password, String firstName, String lastName, String email, UserType type) {
		super();
		this.userID = userID;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.type = type;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public ErrorType getError() {
		return error;
	}

	public void setError(ErrorType error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", type=" + type + ", error=" + error + "]";
	}

}
