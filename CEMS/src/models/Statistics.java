package models;

import java.io.Serializable;

/**
 * Class that  represent all the statistics of an specific exam / specific teacher.
 * 
 * @author Shenhav , Aviel
 *
 */

public class Statistics implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String examID = null;
	private String executeTeacherID = null;
	private String avg = null;
	private String median = null;
	
/****************************** Constructors ************************************/
	public Statistics(String userID, String FirstName, String LastName, String Grade) {
		super();
		this.examID = userID;
		this.executeTeacherID = FirstName;
		this.avg = LastName;
		this.median = Grade;
	}

/***************************** Setters and getters **********************************/
	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

	public String getExecuteTeacherID() {
		return executeTeacherID;
	}

	public void setExecuteTeacherID(String executeTeacherID) {
		this.executeTeacherID = executeTeacherID;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getMedian() {
		return median;
	}

	public void setMedian(String median) {
		this.median = median;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}