package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.gui.LoginMenuController;
import models.Database;
import models.User;
import models.User.ErrorType;
import models.User.UserType;
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

	
	
	///////////////////////////////////////////////// SERVER SIDE ////////////////////////////////////////////////////////////////////////

	/**
	 * method under testing: GetUser 
	 * Connect to database, testing if the user pull from database 
	 * input: userID = 204459093 , password = 1234 
	 * Expected: user = {userID = 204459093 , firstName = Arik , lastName = Zagdon , Email = Arikz15@gmail.com}
	 */
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

	/**
	 * method under testing: GetUser 
	 * Connect to database, testing if user ins't exist. 
	 * input: userID = 1234 , password = 0000 // user ID isn't exist in database 
	 * Expected: ErrorType.WRONG_ID // indicating that entered id isn't exist
	 */
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

	/**
	 * method under testing: GetUser 
	 * Connect to database, testing if user enter wrong password 
	 * input: userID = 204459093 , password = 0000 // userID and password isn't match 
	 * Expected: ErrorType.WRONG_PASSWORD // indicating that entered id and password doesn't match.
	 */
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

	/**
	 * method under testing: GetUser 
	 * Connect to database, testing if user enter null on the id and password 
	 * input: userID = null , password = null 
	 * Expected: throw exception
	 */
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
	
	

	////////////////////////////////////////////////////////// CLIENT SIDE //////////////////////////////////////////////////////////////

	/**
	 * method under testing: loginUser 
	 * Test for login process, separate server side
	 * operations by handling method "handleMessageFromClientUI" 
	 * input: userID = 204459093, password = 1234 
	 * expected: user {userID = 204459093, password = 1234, firstName = Arik, lastName = Zagdon, email = arikz15@gmail.com}
	 */
	@Test
	void loginTestSuccess() {
		ClientController clientController = mock(ClientController.class);
		Client client = mock(Client.class);
		ClientUI clientUI = mock(ClientUI.class);

		ClientUI.setServerStatus(true);

		clientController.setClient(client);
		ClientController.setClientUI(clientUI);
		ClientUI.setClientController(clientController);

		doNothing().when(client).handleMessageFromClientUI(new Object());

		String userID = "204459093";
		String password = "1234";
		String firstName = "Arik";
		String lastName = "Zagdon";
		String email = "arikz15@gmail.com";
		UserType type = UserType.Teacher;
		User loggedInUser = new User(userID, password, firstName, lastName, email, type);

		when(clientUI.getUser()).thenReturn(loggedInUser);

		LoginMenuController loginMenuController = new LoginMenuController();
		User expectedUser = loginMenuController.loginUser(userID, password);
		assertEquals(expectedUser.getUserID(), loggedInUser.getUserID());
		assertEquals(expectedUser.getFirstName(), loggedInUser.getFirstName());
		assertEquals(expectedUser.getLastName(), loggedInUser.getLastName());
		assertEquals(expectedUser.getEmail(), loggedInUser.getEmail());

	}

	/**
	 * method under testing: loginUser 
	 * Test for login process, when the server is
	 * offline, 
	 * input: userID = 308315035, password = 1234 
	 * expected: null (the method print to the UI server is offline)
	 */
	@Test
	void loginTestServerIsNotConnected() {
		ClientController clientController = mock(ClientController.class);
		Client client = mock(Client.class);
		ClientUI clientUI = mock(ClientUI.class);

		ClientUI.setServerStatus(false);

		clientController.setClient(client);
		ClientController.setClientUI(clientUI);
		ClientUI.setClientController(clientController);

		LoginMenuController loginMenuController = mock(LoginMenuController.class);
		doNothing().when(loginMenuController).showErrorMessage("", false);
		User user = loginMenuController.loginUser("308315035", "1234");
		assertNull(user);
	}

	/**
	 * method under testing: loginUser 
	 * Test for login process, when the entered user is empty 
	 * input: userID = "" (empty), password = 1234 
	 * expected: null (the method print to the UI wrong user id)
	 */
	@Test
	void loginTestUserIDisEmpty() {
		ClientController clientController = mock(ClientController.class);
		Client client = mock(Client.class);
		ClientUI clientUI = mock(ClientUI.class);

		ClientUI.setServerStatus(true);

		clientController.setClient(client);
		ClientController.setClientUI(clientUI);
		ClientUI.setClientController(clientController);

		LoginMenuController loginMenuController = mock(LoginMenuController.class);
		doNothing().when(loginMenuController).showErrorMessage("", false);

		User user = loginMenuController.loginUser("", "1234");
		assertNull(user);
	}

	/**
	 * method under testing: loginUser 
	 * Test for login process , when the entered password is empty 
	 * input: userID = 204459093 , password = "" (empty) 
	 * expected: null (the method print to the UI wrong password)
	 */
	@Test
	void loginTestPasswordisEmpty() {
		ClientController clientController = mock(ClientController.class);
		Client client = mock(Client.class);
		ClientUI clientUI = mock(ClientUI.class);

		ClientUI.setServerStatus(true);

		clientController.setClient(client);
		ClientController.setClientUI(clientUI);
		ClientUI.setClientController(clientController);

		LoginMenuController loginMenuController = new LoginMenuController();
		loginMenuController.setlWrongInput(null);
		User user = loginMenuController.loginUser("", "1234");
		assertNull(user);
	}

	/**
	 * method under testing: onClickLogin
	 * Test for click button "login" , when the entered password is wrong 
	 * input: userID = 204459093 , password = "5555" (worng password) 
	 * expected: user error type = wrong password
	 */
	@Test
	void loginTestWrongPassword() {
		ClientController clientController = mock(ClientController.class);
		Client client = mock(Client.class);
		ClientUI clientUI = mock(ClientUI.class);

		ClientUI.setServerStatus(true);

		clientController.setClient(client);
		ClientController.setClientUI(clientUI);
		ClientUI.setClientController(clientController);

		User wrongPasswordUser = new User(ErrorType.WRONG_PASSWORD);

		when(clientUI.getUser()).thenReturn(wrongPasswordUser);
		LoginMenuController loginMenuController = new LoginMenuController();

		User loggedInUser = loginMenuController.loginUser("204459093", "5555");
		assertNull(loggedInUser.getUserID());
		assertNull(loggedInUser.getEmail());
		assertNull(loggedInUser.getFirstName());
		assertNull(loggedInUser.getLastName());
		assertNull(loggedInUser.getType());
		assertNull(loggedInUser.getPassword());
		assertEquals(wrongPasswordUser.getError(), loggedInUser.getError());
	}
	
	/**
	 * method under testing: onClickLogin
	 * Test for click button "login" , when the entered id is wrong 
	 * input: userID = 313131435(worng id) , password = "1234"
	 * expected: user error type = wrong ID
	 */
	@Test
	void loginTestId() {
		ClientController clientController = mock(ClientController.class);
		Client client = mock(Client.class);
		ClientUI clientUI = mock(ClientUI.class);

		ClientUI.setServerStatus(true);

		clientController.setClient(client);
		ClientController.setClientUI(clientUI);
		ClientUI.setClientController(clientController);

		User wrongPasswordUser = new User(ErrorType.WRONG_ID);

		when(clientUI.getUser()).thenReturn(wrongPasswordUser);
		LoginMenuController loginMenuController = new LoginMenuController();

		User loggedInUser = loginMenuController.loginUser("313131435", "1234");
		assertNull(loggedInUser.getUserID());
		assertNull(loggedInUser.getEmail());
		assertNull(loggedInUser.getFirstName());
		assertNull(loggedInUser.getLastName());
		assertNull(loggedInUser.getType());
		assertNull(loggedInUser.getPassword());
		assertEquals(wrongPasswordUser.getError(), loggedInUser.getError());
	}

}
