package client;

import java.io.IOException;

/**
 * The Client controller handle user interface requests.
 */
public class ClientController {

	/**
	 * Store client that's handling server-client communication.
	 */
	private Client client;

	/**
	 * @param host: Server host/ip info connection {default: 127.0.0.1}.
	 * @param port: Server port info connection.
	 * @throws IOException handle connection problem by enter wrong server details.
	 */
	public ClientController(String ip, int port) throws IOException {

		client = new Client(ip, port);
		client.openConnection();

	}

	/**
	 * @param msg object that client controller send to client in order to ask for
	 *            server request.
	 */
	public void sendClientUIRequest(Object msg) {
		client.handleMessageFromClientUI(msg);
	}

	
	/**
	 * @return the current client
	 */
	public Client getClient() {
		return client;
	}
	

}
