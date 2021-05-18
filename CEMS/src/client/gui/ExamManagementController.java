package client.gui;

import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExamManagementController {

	@FXML
	private Label ltime;

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
		
		Stopwatch sw = new Stopwatch(10);
		sw.startTime();
	}

	public class Stopwatch {
		private int interval;
		private Timer timer;

		public Stopwatch(int interval) {
			this.interval = interval;
		}

		public void startTime() {
			int delay = 1000;
			int period = 1000;
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {
					System.out.println(setInterval());

				}
			}, delay, period);
		}

		private final int setInterval() {
			if (interval == 1)
				timer.cancel();
			return --interval;
		}
	}

}
