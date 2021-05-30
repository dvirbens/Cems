package models;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;

public class StudentExecutedExam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examID;
	private String studentName;
	private String grade;
	private String CopyPercentage;
	private JFXButton copy;
	private JFXButton approvalButton;

	public StudentExecutedExam(String examID, String studentName, String grade, String copyPercentage) {
		super();
		this.examID = examID;
		this.studentName = studentName;
		this.grade = grade;
		CopyPercentage = copyPercentage;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCopyPercentage() {
		return CopyPercentage;
	}

	public void setCopyPercentage(String copyPercentage) {
		CopyPercentage = copyPercentage;
	}

	public JFXButton getCopy() {
		return copy;
	}

	public void setCopy(JFXButton copy) {
		this.copy = copy;
	}

	public JFXButton getApprovalButton() {
		return approvalButton;
	}

	public void setApprovalButton(JFXButton approvalButton) {
		this.approvalButton = approvalButton;
	}

	@Override
	public String toString() {
		return "StudentExecutedExam [examID=" + examID + ", studentName=" + studentName + ", grade=" + grade
				+ ", CopyPercentage=" + CopyPercentage + ", copy=" + copy + ", approvalButton=" + approvalButton + "]";
	}
}
