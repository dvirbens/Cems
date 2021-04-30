package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Test;

public class TableGuiController implements Initializable {

	@FXML
	private TableView<Test> tvTest;

	@FXML
	private TableColumn<Test, String> tcId;

	@FXML
	private TableColumn<Test, String> tcSubject;

	@FXML
	private TableColumn<Test, String> tcCourse;

	@FXML
	private TableColumn<Test, Integer> tcDuration;

	@FXML
	private TableColumn<Test, Integer> tcPointsPerQuestion;

	private static List<Test> testsList;

	public TableGuiController() {
	}

	public TableGuiController(List<Test> testsList) {
		super();
		this.testsList = testsList;
	}

	public void DisplayTable(BorderPane mainPane) {
		Pane talbePane;
		try {
			talbePane = (Pane) FXMLLoader.load(getClass().getResource("TableGui.fxml"));
			mainPane.setCenter(talbePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcId.setCellValueFactory(new PropertyValueFactory<Test, String>("Id"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<Test, String>("Subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<Test, String>("Course"));
		tcDuration.setCellValueFactory(new PropertyValueFactory<Test, Integer>("Duration"));
		tcPointsPerQuestion.setCellValueFactory(new PropertyValueFactory<Test, Integer>("pointsPerQuestion"));

		if (testsList != null) {
			ObservableList<Test> tests = FXCollections.observableArrayList();
			tests.addAll(testsList);
			tvTest.setItems(tests);
		}

	}

}
