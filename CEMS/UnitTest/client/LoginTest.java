package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import client.gui.LoginMenuController;
import javafx.event.ActionEvent;
import models.Database;
import models.User;
import models.User.ErrorType;
import models.User.UserType;
import server.DatabaseController;
import server.ServerEventListener;
import static org.mockito.Mockito.*;

class LoginTest {

	private Database database;
	private DatabaseController dbController;

	@BeforeEach
	void setUp() throws Exception {
		database = new Database("127.0.0.1", "3306", "cems", "root", "1234");
		dbController = new DatabaseController(database, new ServerEventListener() {

			@Override
			public void printToLog(String logs) {
				System.out.println(logs);
			}

			@Override
			public void changeButtonStatus(boolean status) {
				System.out.println(status);
			}
		});
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	////////////////////// SERVER SIDE/////////////////////////////

	@Test
	void databaseControllerGetUserSuccessTest() {
		try {
			dbController.connectToDatabase();
			User user = dbController.getUser("204459093", "1234");
			assertEquals(user.getUserID(), "204459093");
			assertEquals(user.getFirstName(), "Arik");
			assertEquals(user.getLastName(), "Zagdon");
			assertEquals(user.getEmail(), "Arikz15@gmail.com");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void databaseControllerUserNotExist() {
		try {
			dbController.connectToDatabase();
			User user = dbController.getUser("1234", "0000");
			assertEquals(ErrorType.WRONG_ID, user.getError());
		} catch (SQLException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}

	@Test
	void databaseControllerWrongPassword() {
		try {
			dbController.connectToDatabase();
			User user = dbController.getUser("204459093", "0000");
			assertEquals(ErrorType.WRONG_PASSWORD, user.getError());
		} catch (SQLException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}

	@Test
	void databaseControllerNullUser() {
		try {
			dbController.connectToDatabase();
			User user = dbController.getUser(null, null);
			assertNull(user);
		} catch (Exception e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}

	////////////////////// CLIENT SIDE/////////////////////////////

	@Test
	void onClickLoginTestClientUI() {
		Client client = mock(Client.class);
		
		String userID = "204459093";
		String password = "1234";
		String firstName = "Arik";
		String lastName = "Zagdon";
		String email = "arikz15@gmail.com";
		UserType type = UserType.Teacher;
		User user = new User(userID, password, firstName, lastName, email, type);
		
		when(Client.getUser()).thenReturn(user);

		LoginMenuController loginMenuController = new LoginMenuController();
		loginMenuController.onClickLogin(new ActionEvent());

		User loggedInUser = Client.getUser();
		System.out.println(loggedInUser);
	}

}
