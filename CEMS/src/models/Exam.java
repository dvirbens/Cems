package models;

import java.io.Serializable;
import java.util.List;

import com.jfoenix.controls.JFXButton;

/**
 * Class that describe test on project system, store all necessary attributes
 * inside this model.
 * 
 * @author Arikz ,Dvir ben simon
 *
 */
public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String teacherID;
	private String subject;
	private String course;
	private String duration;
	private String teacherName;
	private String studentNote;
	private String teacherNote;
	private List<ExamQuestion> examQuestions;
	private JFXButton questionListButton;
	private JFXButton editExamButton;

	/******************************
	 * Constructors
	 ************************************/
	public Exam(String id, String teacherID, String subject, String course, String duration,
			List<ExamQuestion> examQuestions, String studentNote, String teacherNote) {
		super();
		this.teacherID = teacherID;
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
		this.examQuestions = examQuestions;
		this.studentNote = studentNote;
		this.teacherNote = teacherNote;
	}

	public Exam(String id, String subject, String course, String duration, List<ExamQuestion> examQuestions,
			JFXButton questionListButton) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
		this.examQuestions = examQuestions;
		this.questionListButton = questionListButton;
	}

	public Exam(String subject, String teacherID, String course, String duration, String teacherNote,
			String studentNote, List<ExamQuestion> examQuestions) {
		super();
		this.teacherID = teacherID;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
		this.studentNote = studentNote;
		this.teacherNote = teacherNote;
		this.examQuestions = examQuestions;
	}

	public Exam(String examID, String subject, String teacherID, String course, String duration, String teacherNote,
			String studentNote, List<ExamQuestion> examQuestions) {
		super();
		this.id = examID;
		this.teacherID = teacherID;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
		this.studentNote = studentNote;
		this.teacherNote = teacherNote;
		this.examQuestions = examQuestions;
	}

	public Exam(String id, String subject, String course, String duration) {
		super();
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
	}

	/*****************************
	 * Setters and getters
	 **********************************/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<ExamQuestion> getExamQuestions() {
		return examQuestions;
	}

	public void setExamQuestions(List<ExamQuestion> examQuestions) {
		this.examQuestions = examQuestions;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public JFXButton getQuestionListButton() {
		return questionListButton;
	}

	public void setQuestionListButton(JFXButton questionListButton) {
		this.questionListButton = questionListButton;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getStudentNote() {
		return studentNote;
	}

	public void setStudentNote(String studentNote) {
		this.studentNote = studentNote;
	}

	public String getTeacherNote() {
		return teacherNote;
	}

	public void setTeacherNote(String teacherNote) {
		this.teacherNote = teacherNote;
	}

	public JFXButton getEditExamButton() {
		return editExamButton;
	}

	public void setEditExamButton(JFXButton editExamButton) {
		this.editExamButton = editExamButton;
	}

}
