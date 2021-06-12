package models;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Class that extends Question class and represent additional information about
 * the questions.
 * 
 * @author Shenhav , Aviel
 *
 */
public class ExamQuestion extends Question implements Serializable {

	private static final long serialVersionUID = 1L;
	private String studentNote;
	private String teacherNotes;
	private int points;
	private JFXButton noteDetails;
	private JFXButton removeButton;
	private TextField tfPoints;
	private ImageView checkImage;

/****************************** Constructors ************************************/
	public ExamQuestion(Question question) {
		super(question.getQuestionID(), question.getTeacherName(), question.getSubject(), question.getDetails(),
				question.getAnswer1(), question.getAnswer2(), question.getAnswer3(), question.getAnswer4(),
				question.getCorrectAnswer(), question.getDetailsButton());
	}

	public ExamQuestion(Question question, int points) {
		super(question.getQuestionID(), question.getTeacherName(), question.getSubject(), question.getDetails(),
				question.getAnswer1(), question.getAnswer2(), question.getAnswer3(), question.getAnswer4(),
				question.getCorrectAnswer(), question.getDetailsButton());
		this.points = points;
	}

/***************************** Setters and getters **********************************/
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public JFXButton getNoteDetails() {
		return noteDetails;
	}

	public void setNoteDetails(JFXButton noteDetails) {
		this.noteDetails = noteDetails;
	}

	public ImageView getCheckImage() {
		return checkImage;
	}

	public void setCheckImage(ImageView checkImage) {
		this.checkImage = checkImage;
	}

	public void setVisibleImage() {
		this.checkImage.setVisible(true);
	}

	public JFXButton getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(JFXButton removeButton) {
		this.removeButton = removeButton;
	}

	public String getStudentNote() {
		return studentNote;
	}

	public void setStudentNote(String studentNote) {
		this.studentNote = studentNote;
	}

	public String getTeacherNotes() {
		return teacherNotes;
	}

	public void setTeacherNotes(String teacherNotes) {
		this.teacherNotes = teacherNotes;
	}

	public TextField getTfPoints() {
		return tfPoints;
	}

	public void setTfPoints(TextField tfPoints) {
		this.tfPoints = tfPoints;
	}
	
}
