package models;

import java.io.Serializable;
import java.util.List;

import javafx.scene.image.ImageView;

public class ComputerizedTestReport implements Serializable{

	private String subject;
	private String course;
	private int numOfQuestions;
	private String[] selectedAnswers;
	private List<String> correctAnswers;
	private ImageView correctImg;
	private List<ExamQuestion> questions;
	
	public ComputerizedTestReport(String subject, String course, String[] selectedAnswers, List<ExamQuestion> questions) {
		super();
		this.subject = subject;
		this.course = course;
		this.selectedAnswers = selectedAnswers;
		this.correctImg = correctImg;
		this.questions = questions;
		
		this.numOfQuestions = questions.size();
		for (int i=0; i < numOfQuestions; i++)
		{
			correctAnswers.add(String.valueOf(questions.get(i).getCorrectAnswer()));
		}
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

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public void setNumOfQuestions(int numOfQuestions) {
		this.numOfQuestions = numOfQuestions;
	}

	public String[] getSelectedAnswers() {
		return selectedAnswers;
	}

	public void setSelectedAnswers(String[] selectedAnswers) {
		this.selectedAnswers = selectedAnswers;
	}

	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(List<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public ImageView getCorrectImg() {
		return correctImg;
	}

	public void setCorrectImg(ImageView correctImg) {
		this.correctImg = correctImg;
	}

	public List<ExamQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<ExamQuestion> questions) {
		this.questions = questions;
	}
	
	
	
	
	
}
