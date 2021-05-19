package client.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExamManagementController implements Initializable {

	@FXML
	private static Label lTime;

	@FXML
	private Text tTime;

	private static Label label;

	public void start() {
		try {
			VBox examManagement = new VBox();
			setVBoxComponents(examManagement);
			Scene scene = new Scene(examManagement, 500, 400);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Exam Management");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setVBoxComponents(VBox examManagement) {
		label = new Label();
		label.setPrefSize(515, 128);
		label.setStyle("-fx-background-color:#333333;" + "-fx-text-fill:white;");
		label.setAlignment(Pos.CENTER);
		examManagement.setAlignment(Pos.CENTER);

		JFXButton freezeExam = new JFXButton();
		freezeExam.setPrefSize(200, 30);
		freezeExam.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
		freezeExam.setText("Freeze exam");
		freezeExam.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}

		});

		JFXButton askForExstension = new JFXButton();
		askForExstension.setPrefSize(200, 30);
		askForExstension
				.setStyle("-fx-background-color:#48a832;" + "-fx-background-radius:10;" + "-fx-text-fill:white;");
		askForExstension.setText("Ask for time extension");
		askForExstension.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}

		});

		examManagement.getChildren().add(label);
		examManagement.getChildren().add(freezeExam);
		examManagement.getChildren().add(askForExstension);
		Stopwatch sw = new Stopwatch(3);
		sw.startTime();
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
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							label.setText(String.format("%02d:%02d\n", min, sec));
							if (min == 0 && sec == 0) {
								timer.cancel();
							} else if (sec == 0) {
								min--;
								sec = 59;
							} else {
								sec--;
							}
						}
					});
				}
			}, delay, period);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
