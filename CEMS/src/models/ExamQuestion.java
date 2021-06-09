package models;

import com.jfoenix.controls.JFXButton;

import javafx.scene.image.ImageView;

public class ExamQuestion extends Question {

	private static final long serialVersionUID = 1L;
	private String note;
	private int points;
	private NoteType noteType;
	private JFXButton noteDetails;
	private JFXButton removeButton;
	private JFXButton addButton;
	private ImageView checkImage;

	public enum NoteType {
		Students, Teachers, None
	}

	public ExamQuestion(Question question, String note, int points, NoteType noteType) {
		super(question.getQuestionID(), question.getTeacherName(), question.getSubject(), question.getDetails(),
				question.getAnswer1(), question.getAnswer2(), question.getAnswer3(), question.getAnswer4(),
				question.getCorrectAnswer(), question.getDetailsButton());
		this.note = note;
		this.points = points;
		this.noteType = noteType;
		
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public NoteType getNoteType() {
		return noteType;
	}

	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
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
	
	public void setVisibleImage()
	{
		this.checkImage.setVisible(true);
	}

	public JFXButton getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(JFXButton removeButton) {
		this.removeButton = removeButton;
	}
	
	

}
