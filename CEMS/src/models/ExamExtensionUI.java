package models;

import com.jfoenix.controls.JFXButton;

public class ExamExtensionUI extends ExamExtension {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFXButton confirmButton;

	public ExamExtensionUI(ExamExtension examExtension) {
		super(examExtension.getExamID(), examExtension.getCode(), examExtension.getTeacherID(),
				examExtension.getTeacherName(), examExtension.getTimeExtension(), examExtension.getExamDuration(),
				examExtension.getCasue());
	}

	public ExamExtensionUI(String examID, String code, String teacherID, String teacherName, String timeExtension,
			String examDuration, String casue) {
		super(examID, code, teacherID, teacherName, timeExtension, examDuration, casue);
	}

	public JFXButton getConfirmButton() {
		return confirmButton;
	}

	public void setConfirmButton(JFXButton confirmButton) {
		this.confirmButton = confirmButton;
	}

	@Override
	public String toString() {
		return "ExamExtensionUI [confirmButton=" + confirmButton + ", getCode()=" + getCode() + ", getTeacherID()="
				+ getTeacherID() + ", getTimeExtension()=" + getTimeExtension() + ", getCasue()=" + getCasue()
				+ ", getTeacherName()=" + getTeacherName() + ", getExamDuration()=" + getExamDuration()
				+ ", getExamID()=" + getExamID() + ", toString()=" + super.toString() + "]";
	}

}
