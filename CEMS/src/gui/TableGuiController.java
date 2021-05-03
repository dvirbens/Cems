package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.Test;

/**
 * FXML controller class for table layout graphic user interface, display table
 * with test list collected from client class.
 * 
 * @author Arikz
 *
 */
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

	/**
	 * Method that's displaying new table layout scene inside center on borderPane
	 * from main layout.
	 * 
	 * @param mainPane primary main layout
	 */
	public void DisplayTable(BorderPane mainPane) {
		Pane talbePane;
		try {
			talbePane = (Pane) FXMLLoader.load(getClass().getResource("TableGui.fxml"));
			mainPane.setCenter(talbePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setting all table column and creating new data set from client request that has been
	 * sent to database.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcId.setCellValueFactory(new PropertyValueFactory<Test, String>("Id"));
		tcSubject.setCellValueFactory(new PropertyValueFactory<Test, String>("Subject"));
		tcCourse.setCellValueFactory(new PropertyValueFactory<Test, String>("Course"));
		tcDuration.setCellValueFactory(new PropertyValueFactory<Test, Integer>("Duration"));
		tcPointsPerQuestion.setCellValueFactory(new PropertyValueFactory<Test, Integer>("pointsPerQuestion"));

		ObservableList<Test> tests = FXCollections.observableArrayList();
		tests.addAll(Client.getTests());
		tvTest.setItems(tests);

	}

}
