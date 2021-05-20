package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class StudentEnterExamController implements Initializable {

    @FXML
    private JFXTextField tfCode;

    @FXML
    private JFXButton btComputerizedExam;

    @FXML
    private JFXButton btManualExam;
    
    @FXML
    void OnClickEnterExam(ActionEvent event) {
    	if( Integer.parseInt(tfCode.getText()) == 123)
    		MainGuiController.getMenuHandler().setManualTestScreen();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    
}
