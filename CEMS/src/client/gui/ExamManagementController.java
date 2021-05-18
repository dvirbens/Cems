package client.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExamManagementController implements Initializable {

	@FXML
	private Label lTime;

	@FXML
	private Text tTime;

	public void start() {
		try {
			Pane examManagment = (Pane) FXMLLoader.load(getClass().getResource("ExamManagement.fxml"));
			Scene scene = new Scene(examManagment, 500, 400);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Exam Management");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class Stopwatch {
		private int min;
		private int sec;
		private Timer timer;

		public Stopwatch(int min) {
			this.min = min;
		}

		public void startTime() {

			int delay = 1000;
			int period = 1000;
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {
					System.out.println(String.format("%02d:%02d\n", min, sec));
					if (min == 0 && sec == 0) {
						timer.cancel();
					} else if (sec == 0) {
						min--;
						sec = 59;
					} else {
						sec--;
					}
				}
			}, delay, period);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
