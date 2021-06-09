package client.gui;

import static common.ModelWrapper.Operation.GET_EXAM_IN_PROCESS;
import static common.ModelWrapper.Operation.INSERT_STUDENT_TO_EXAM;
import static common.ModelWrapper.Operation.UPLOAD_FILE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import client.Client;
import client.ClientUI;
import client.gui.ExecuteComputerizedExamController.StudentStopwatch;
import common.ModelWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import models.Exam;
import models.ExamProcess;
import models.StudentExecutedExam;
import models.WordFile;

public class ExecuteManualExamController implements Initializable {

	@FXML
	private Button btDownload;

	@FXML
	private Button btUpload;

	@FXML
	private Button btChooseFile;

	@FXML
	private Label lbTitle;

	@FXML
	private TextArea taInstructions;

	@FXML
	private TextField tfFileName;

	@FXML
	private TextField tfRemainingTime;

	@FXML
	private Label lblRemainingTime;
	
	@FXML
	private Label timeLabel;

	private StudentStopwatch sw;

	private File newFile;

	private long startTime;

	private long duration;

	private ExamProcess examProcess;

	private static String code;
	
	private volatile boolean shutdown = false;

	public ExecuteManualExamController() {

	}

	public ExecuteManualExamController(String code) {
		ExecuteManualExamController.code = code;
	}

	public void start() {
		try {
			Pane manualTestPane = (Pane) FXMLLoader.load(getClass().getResource("ManualTest.fxml"));
			MainGuiController.getMenuHandler().getMainFrame().setCenter(manualTestPane);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		shutdown = false;
		StudentMenuController.setLocked(true);
		btChooseFile.setVisible(false);
		tfFileName.setVisible(false);
		btUpload.setVisible(false);

		ModelWrapper<String> modelWrapper = new ModelWrapper<String>(code, GET_EXAM_IN_PROCESS);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		examProcess = Client.getExamProcess();

		StudentExecutedExam newStudent = new StudentExecutedExam(examProcess.getExamId(), Client.getUser().getUserID(), code, examProcess.getTeacherID());
		ModelWrapper<StudentExecutedExam> modelWrapperInsertStudent = new ModelWrapper<>(newStudent,
				INSERT_STUDENT_TO_EXAM);
		ClientUI.getClientController().sendClientUIRequest(modelWrapperInsertStudent);
		
		String teacherTime = Client.getExamProcess().getTime();
		Date date = new Date();
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		String currentTime = timeformat.format(date).toString();

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date1 = format.parse(currentTime);
			Date date2 = format.parse(teacherTime);
			long difference = date1.getTime() - date2.getTime();
			long examDuration = TimeUnit.MINUTES.toSeconds(Long.parseLong(examProcess.getDuration()));
			long durationInSecond = examDuration - TimeUnit.MILLISECONDS.toSeconds(difference);
			int minutes = (int) durationInSecond / 60;
			int second = (int) durationInSecond % 60;
			sw = new StudentStopwatch(minutes, second, timeLabel);
			sw.startTime();
			set2MinutesLeft();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void onUploadClick(ActionEvent event) {
		FileInputStream fileIn;
		WordFile file = new WordFile();
		if (newFile == null)
			return;

		try {
			byte[] mybytearray = new byte[(int) newFile.length()];
			fileIn = new FileInputStream(newFile);
			BufferedInputStream bufferIn = new BufferedInputStream(fileIn);
			// DataInputStream dataIn = new DataInputStream(bufferIn);
			file.initArray(mybytearray.length);
			file.setSize(mybytearray.length);
			bufferIn.read(file.getMybytearray(), 0, mybytearray.length);
			
			StudentExecutedExam student = new StudentExecutedExam(examProcess.getExamId(), Client.getUser().getUserID(), code, examProcess.getDate());
			ModelWrapper<StudentExecutedExam> modelWrapper = new ModelWrapper<>(file,student, UPLOAD_FILE);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);
			bufferIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		shutdown = true;
		MainGuiController.getMenuHandler().setStudentlMenu();
		StudentMenuController.setLocked(false);
	}

	@FXML
	void onChooseFileClick(ActionEvent event) {
		FileChooser fc = new FileChooser();
		newFile = fc.showOpenDialog(null);
		if (newFile != null) {
			btUpload.setVisible(true);
			tfFileName.setText(newFile.getName());
		} else {
			btUpload.setVisible(false);
			System.out.println("File is not valid");
		}
	}

	@FXML
	void onDownloadClick(ActionEvent event) {
		btChooseFile.setVisible(true);
		tfFileName.setVisible(true);
		String path = System.getProperty("user.home") + "/Desktop";

		File outputFile = new File(path + "/fileTest.docx");
		try {
			FileOutputStream fos = new FileOutputStream(outputFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(examProcess.getManualFile().getMybytearray(), 0, examProcess.getManualFile().getSize());
			bos.flush();
			fos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void set2MinutesLeft()
	{
		Thread timerThread = new Thread(() -> {
			while (!shutdown) {
				if (sw.getMin() == 2 && sw.getSec() == 0)
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
						      //Creating a dialog
						      Dialog<String> dialog = new Dialog<String>();
						      //Setting the title
						      dialog.setTitle("Warning");
						      ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
						      //Setting the content of the dialog
						      dialog.setContentText("Warning: You have 2 minutes left!");
						      //Adding buttons to the dialog pane
						      dialog.getDialogPane().getButtonTypes().add(type);
						      
						      dialog.showAndWait();
						}
					});

			      break;
				}
			}
			});
		timerThread.start();
	}
	
	public void setFreezePopup()
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
			  MainGuiController.getMenuHandler().setMainScreen();
		      //Creating a dialog
		      Dialog<String> dialog = new Dialog<String>();
		      //Setting the title
		      dialog.setTitle("Exam closed");
		      ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		      //Setting the content of the dialog
		      dialog.setContentText("The exam has been closed by your teacher");
		      //Adding buttons to the dialog pane
		      dialog.getDialogPane().getButtonTypes().add(type);
		      
		      dialog.showAndWait();
			}
		});

	}
	
	public class StudentStopwatch {
		private int min;
		private int sec;
		private Timer timer;
		private Label label;

		public StudentStopwatch(int min, int sec, Label label) {
			this.min = min;
			this.sec = sec;
			this.label = label;
		}

		public void startTime() {
			int delay = 1000;
			int period = 1000;
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {

					long timeExtension = Client.getTimeExtension();
					if (StudentMenuController.isClosed()) {
						timer.cancel();
						shutdown = true;
						StudentMenuController.setClosed(false);
						return;
					}

					if (timeExtension == -1) {
						setFreezePopup();
						shutdown = true;
						timer.cancel();
						return;
					} else if (timeExtension != 0) {
						min += (int) timeExtension;
						Client.setTimeExtension(0);
					}
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							label.setText(String.format("%02d:%02d\n", min, sec));
						}
					});

					if (min == 0 && sec == 0) {
						timer.cancel();
						shutdown = true;
						MainGuiController.getMenuHandler().setMainScreen();

					} else if (sec == 0) {
						min--;
						sec = 59;
					} else {
						sec--;
					}

				}
			}, delay, period);
		}
		
		

		public int getMin() {
			return min;
		}

		public int getSec() {
			return sec;
		}


		public void stopTime() {
			timer.cancel();
		}
		
	}

}
