package models;

import java.io.Serializable;

public class ExamQuestion implements Serializable {

	private static final long serialVersionUID = 1L;
	private String questionID;
	private String note;
	private int points;
	private NoteType noteType;

	public enum NoteType {
		Students, Teachers, None
	}

	public ExamQuestion(String questionID, String note, int points, NoteType noteType) {
		this.questionID = questionID;
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

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public NoteType getNoteType() {
		return noteType;
	}

	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}

}
