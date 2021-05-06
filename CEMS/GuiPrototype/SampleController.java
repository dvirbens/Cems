
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SampleController implements Initializable {

	@FXML
	private JFXListView<String> lvRequest;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lvRequest.getItems().add("30 min time exstension Math exam");

	}

}
