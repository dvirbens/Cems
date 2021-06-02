package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StudentHomePageController implements Initializable {


    @FXML
    private Label labelName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		String name = Client.getUser().getFirstName()+" "+Client.getUser().getLastName();
		labelName.setText("Hey, "+name);
		
	}
	
}
