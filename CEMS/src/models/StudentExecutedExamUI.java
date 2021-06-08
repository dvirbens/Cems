package models;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class StudentExecutedExamUI extends StudentExecutedExam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examID;
	private String studentName;
	private WordFile copy;
	private CheckBox gradeApproval;
	private TextField tfGrade;
	private JFXButton getCopy;

	public StudentExecutedExamUI(StudentExecutedExam studentExecutedExam) {
		super(studentExecutedExam.getExamID(), studentExecutedExam.getStudentID(), studentExecutedExam.getStudentName(),
				studentExecutedExam.getTeacherId(), studentExecutedExam.getSubject(), studentExecutedExam.getCourse(),
				studentExecutedExam.getExecDate(), studentExecutedExam.getTestType(), studentExecutedExam.getGrade(),
				studentExecutedExam.getAlert(), studentExecutedExam.isApproved());
		this.examID = studentExecutedExam.getExamID();
		this.studentName = studentExecutedExam.getStudentName();
	}

	public StudentExecutedExamUI(String examID, String studentID, String studentName, String teacherId, String subject,
			String course, String execDate, String testType, String grade, String alert, boolean approved) {
		super(examID, studentID, studentName, teacherId, subject, course, execDate, testType, grade, alert, approved);
	}

	public WordFile getCopy() {
		return copy;
	}

	public void setCopy(WordFile copy) {
		this.copy = copy;
	}

	public CheckBox getGradeApproval() {
		return gradeApproval;
	}

	public void setGradeApproval(CheckBox gradeApproval) {
		this.gradeApproval = gradeApproval;
	}

	public TextField getTfGrade() {
		return tfGrade;
	}

	public void setTfGrade(TextField tfGrade) {
		this.tfGrade = tfGrade;
	}

	public JFXButton getGetCopy() {
		return getCopy;
	}

	public void setGetCopy(JFXButton getCopy) {
		this.getCopy = getCopy;
	}

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Override
	public String toString() {
		return "StudentExecutedExamUI [examID=" + examID + ", studentName=" + studentName + ", copy=" + copy
				+ ", gradeApproval=" + gradeApproval + ", tfGrade=" + tfGrade + ", getCopy=" + getCopy + "]";
	}

}
