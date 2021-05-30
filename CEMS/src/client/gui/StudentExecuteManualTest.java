package client.gui;

import static common.ModelWrapper.Operation.GET_EXAM_BY_EXAM_ID;
import static common.ModelWrapper.Operation.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Exam;
import models.ExamProcess;
import models.WordFile;

public class StudentExecuteManualTest implements Initializable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	private File newFile;
	
	private long startTime;
	
	private long duration;
	
	private String examID;
	
	private ExamProcess examProcess;
	
	private Exam exam;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btChooseFile.setVisible(false);
		tfFileName.setVisible(false);
		btUpload.setVisible(false);
		
		// Start time counter
		startTime = System.currentTimeMillis();
		setRemainingTime();
		
		// Get ExamID
		examID = Client.getExamProcess().getexamId();
		exam = Client.getExam();
		
		ModelWrapper<String> modelWrapper = new ModelWrapper<String>(examID, GET_EXAM_BY_EXAM_ID);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		
		modelWrapper = new ModelWrapper<String>(Client.getExamCode(), GET_EXAM_IN_PROCESS);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);
		examProcess = Client.getExamProcess();
		
		String teacherTime = Client.getExamProcess().getTime();
		Date date = new Date();
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
		String currentTime = timeformat.format(date).toString();

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date1 = format.parse(currentTime);
			Date date2 = format.parse(teacherTime);
			long difference = date1.getTime() - date2.getTime();
			System.out.println(difference);
			long examDuration = TimeUnit.MINUTES.toSeconds(Long.parseLong(Client.getExam().getDuration()));
			duration = examDuration - TimeUnit.MILLISECONDS.toSeconds(difference);
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
			ModelWrapper<WordFile> modelWrapper = new ModelWrapper<>(file, UPLOAD_FILE);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);
			bufferIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onChooseFileClick(ActionEvent event) {
		btUpload.setVisible(true);
		FileChooser fc = new FileChooser();
		newFile = fc.showOpenDialog(null);
		if (newFile != null) {
			tfFileName.setText(newFile.getName());
		} else {
			System.out.println("File is not valid");
		}
	}
	
    @FXML
    void onDownloadClick(ActionEvent event) {
		btChooseFile.setVisible(true);
		tfFileName.setVisible(true);
		
		//File outputFile = new File(examProcess.getManualFile());
		//FileOutputStream fos = new FileOutputStream(examProcess.getManualFile())
    }
    
	public void setRemainingTime() {
		Thread timerThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000); // 1 second
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long timeInSeconds = calcTime();
				long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds);
				if (Client.getTimeExtension() != 0) {
					minutes += Client.getTimeExtension();
					Client.setTimeExtension(0);
				}
				long seconds = timeInSeconds % 60;

				final String time = Long.toString(minutes) + ":" + Long.toString(seconds);
				Platform.runLater(() -> {
					tfRemainingTime.setText(time);
				});
			}
		});
		timerThread.start();
	}

	public long calcTime() {
		long elapsedTime = duration  * 1000 - (System.currentTimeMillis() - startTime);
		long elapsedSeconds = elapsedTime / 1000;
		long secondsDisplay = elapsedSeconds % 60;
		long elapsedMinutes = elapsedSeconds / 60;

		return elapsedSeconds;

	}

}
