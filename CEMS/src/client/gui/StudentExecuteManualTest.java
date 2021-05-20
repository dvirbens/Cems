package client.gui;

import static common.ModelWrapper.Operation.UPLOAD_FILE;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import common.ModelWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

    @FXML
    void onUploadClick(ActionEvent event) 
    {
		String path = "C:\\ExampleTest.docx";
		FileInputStream fileIn;
		WordFile file = new WordFile();
		File newFile = new File(path);
		
			
			try {
				byte[] mybytearray = new byte[(int) newFile.length()];
				fileIn = new FileInputStream(newFile);
				BufferedInputStream bufferIn = new BufferedInputStream(fileIn);
				DataInputStream dataIn = new DataInputStream(bufferIn);
				file.initArray(mybytearray.length);
				file.setSize(mybytearray.length);
				bufferIn.read(file.getMybytearray(), 0, mybytearray.length);
				ModelWrapper<WordFile> modelWrapper = new ModelWrapper<>(file, UPLOAD_FILE);
				ClientUI.getClientController().sendClientUIRequest(modelWrapper);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			

    	}
}
