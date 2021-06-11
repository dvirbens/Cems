package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


//This class represent one question of Computerized test report
public class ComputerizedTestReport implements Serializable{

	private String selectedAnswer;
	private String correctAnswer;
	private String points;
	private ImageView correctImg;
	private ExamQuestion question;
	private JFXButton detailsBtn;
	
	public ComputerizedTestReport(String selectedAnswer, String correctAnswer, String points, ImageView correctImg, ExamQuestion question) {
		super();
		if (selectedAnswer.equals("9"))
		{
			this.selectedAnswer = "NONE";
		}
		else
		{
			this.selectedAnswer = selectedAnswer;
		}
		this.correctAnswer = correctAnswer;
		this.points = points;
		this.correctImg = correctImg;
		this.question = question;
	}
	public String getSelectedAnswer() {
		return selectedAnswer;
	}
	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public ImageView getCorrectImg() {
		return correctImg;
	}
	public void setCorrectImg(ImageView correctImg) {
		this.correctImg = correctImg;
	}
	public ExamQuestion getQuestion() {
		return question;
	}
	public void setQuestion(ExamQuestion question) {
		this.question = question;
	}
	public JFXButton getDetailsBtn() {
		return detailsBtn;
	}
	public void setDetailsBtn(JFXButton detailsBtn) {
		this.detailsBtn = detailsBtn;
	}

	

	
}
