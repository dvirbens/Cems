package client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Database;
import models.User;
import models.User.ErrorType;
import server.DatabaseController;
import server.ServerEventListener;

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

	@Test
	void onClickLoginTestClientUI() {

	}

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

}
