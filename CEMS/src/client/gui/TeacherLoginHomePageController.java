package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TeacherLoginHomePageController  implements Initializable{
	

    @FXML
    private Label labelTeacherName;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		String name = Client.getUser().getFirstName()+" "+Client.getUser().getLastName();
		labelTeacherName.setText("Hey, "+name);
		
	}
	
	

}
