package client.gui;

import static common.ModelWrapper.Operation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import client.Client;
import client.ClientUI;
import common.ModelWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ExamExtension;
import models.ExamExtensionUI;

public class TimeExtentionController implements Initializable {

	@FXML
	private TableView<ExamExtensionUI> tvExtension;

	@FXML
	private TableColumn<ExamExtensionUI, String> tcExamID;

	@FXML
	private TableColumn<ExamExtensionUI, String> tcTeacherName;

	@FXML
	private TableColumn<ExamExtensionUI, String> tcDuration;

	@FXML
	private TableColumn<ExamExtensionUI, String> tcExtension;

	@FXML
	private TableColumn<ExamExtensionUI, String> tcCause;

	@FXML
	private TableColumn<ExamExtensionUI, JFXButton> tcConfirm;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("meow");
		tcExamID.setCellValueFactory(new PropertyValueFactory<ExamExtensionUI, String>("examID"));
		tcTeacherName.setCellValueFactory(new PropertyValueFactory<ExamExtensionUI, String>("teacherName"));
		tcDuration.setCellValueFactory(new PropertyValueFactory<ExamExtensionUI, String>("examDuration"));
		tcExtension.setCellValueFactory(new PropertyValueFactory<ExamExtensionUI, String>("timeExtension"));
		tcCause.setCellValueFactory(new PropertyValueFactory<ExamExtensionUI, String>("casue"));
		tcConfirm.setCellValueFactory(new PropertyValueFactory<ExamExtensionUI, JFXButton>("confirmButton"));

		ModelWrapper<String> modelWrapper = new ModelWrapper<>(GET_EXTENSION_REQUESTS);
		ClientUI.getClientController().sendClientUIRequest(modelWrapper);

		List<ExamExtension> examExtensionList = Client.getExamExtensions();
		List<ExamExtensionUI> examExtensionUIList = convertToUI(examExtensionList);
		ObservableList<ExamExtensionUI> examExtension = FXCollections.observableArrayList();
		examExtension.addAll(examExtensionUIList);
		tvExtension.setItems(examExtension);

	}

	private List<ExamExtensionUI> convertToUI(List<ExamExtension> examExtensionList) {
		List<ExamExtensionUI> extensionUIList = new ArrayList<>();
		for (ExamExtension extension : examExtensionList) {
			ExamExtensionUI extensionUI = new ExamExtensionUI(extension);
			JFXButton confirmButton = new JFXButton();
			confirmButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ModelWrapper<ExamExtension> modelWrapper = new ModelWrapper<>(extension, EXTENSION_CONFIRM);
					ClientUI.getClientController().sendClientUIRequest(modelWrapper);
				}
			});
			extensionUI.setConfirmButton(confirmButton);
			extensionUIList.add(extensionUI);
		}
		return extensionUIList;
	}

}
