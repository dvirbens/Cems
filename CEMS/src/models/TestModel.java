package models;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;

public class TestModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String secondName;
	private JFXButton button;
	
/****************************** Constructors ************************************/
	public TestModel(String firstName, String secondName) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
	}
	
/***************************** Setters and getters **********************************/
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public JFXButton getButton() {
		return button;
	}

	public void setButton(JFXButton button) {
		this.button = button;
	}
	
	
}
