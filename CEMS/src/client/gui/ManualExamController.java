package client.gui;

import static common.ModelWrapper.Operation.START_EXAM;
import static common.ModelWrapper.Operation.UPLOAD_FILE;
import static common.ModelWrapper.Operation.UPLOAD_FILE_TEACHER;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import models.Exam;
import models.ExamProcess;
import models.WordFile;

public class ManualExamController implements Initializable {

	@FXML
	private JFXComboBox<String> cbSubject;

	@FXML
	private JFXComboBox<String> cbCourse;

	@FXML
	private JFXTextField tfDuration;

	@FXML
	private Label fileNameLabel;

	@FXML
	private JFXButton btnUpload;

	@FXML
	private JFXTextField tfCode;

	@FXML
	private JFXButton btnStartExam;

	@FXML
	private Label masgeLabel;

	private String filePath;

	private File file;

	@FXML
	void onClickSubject(ActionEvent event) {
		String subjectSelected = cbSubject.getSelectionModel().getSelectedItem();
		cbCourse.getItems().clear();
		cbCourse.getItems().addAll(Client.getSubjectCollection().getCourseListBySubject(subjectSelected));
	}

	@FXML
	void onClickStartExam(ActionEvent event) {
		String code = tfCode.getText();
		String fileName = fileNameLabel.getText();
		boolean validInput = true;

		if (code.length() != 4) {
			masgeLabel.setText("Exam code should have 4 digits");
			validInput = false;
		}

		if (fileName.isEmpty()) {
			masgeLabel.setText("You must choose a file");
			validInput = false;
		}

		if (validInput) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Date date = new Date();
			String currentDate = formatter.format(date).toString();
			String teacherID = Client.getUser().getUserID();
			String duration = tfDuration.getText();
			String subject = cbSubject.getSelectionModel().getSelectedItem();
			String course = cbCourse.getSelectionModel().getSelectedItem();
			WordFile wordFile = getWordFile();

			ExamProcess examProcess = new ExamProcess(subject, course, duration, currentDate, teacherID, code,
					wordFile);

			ModelWrapper<ExamProcess> modelWrapper = new ModelWrapper<>(examProcess, START_EXAM);
			ClientUI.getClientController().sendClientUIRequest(modelWrapper);

			ExamManagementWindow examManagementWindow = new ExamManagementWindow(code, Integer.valueOf(duration));
			examManagementWindow.open();

		}

	}

	@FXML
	void onClickUpload(ActionEvent event) {
		FileChooser fc = new FileChooser();
		file = fc.showOpenDialog(null);
		if (file != null) {
			fileNameLabel.setText(file.getName());
		} else {
			System.out.println("File is not valid");
		}
	}

	private WordFile getWordFile() {
		FileInputStream fileIn;
		WordFile wordFile = new WordFile();
		if (file == null)
			return null;

		try {
			byte[] mybytearray = new byte[(int) file.length()];
			fileIn = new FileInputStream(file);
			BufferedInputStream bufferIn = new BufferedInputStream(fileIn);
			// DataInputStream dataIn = new DataInputStream(bufferIn);
			wordFile.initArray(mybytearray.length);
			wordFile.setSize(mybytearray.length);
			bufferIn.read(wordFile.getMybytearray(), 0, mybytearray.length);
			bufferIn.close();
			return wordFile;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbSubject.getItems().addAll(Client.getSubjectCollection().getSubjects());

	}

}
