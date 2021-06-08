package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.corba.se.spi.activation.Server;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PrincipleTimeExtention  implements Initializable {

	    @FXML
	    private TableView<?> tableExt;

	    @FXML
	    private TableColumn<?, ?> teacherCol;

	    @FXML
	    private TableColumn<?, ?> durationCol;

	    @FXML
	    private TableColumn<?, ?> extentionCol;

	    @FXML
	    private TableColumn<?, ?> viewCol;

	    public enum RequsetToServer {
			ExamExtentionRequest
		};
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

		}

}
