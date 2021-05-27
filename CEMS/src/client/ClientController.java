package client;

import java.io.IOException;

/**
 * Class that's creating new client-server communication and handle all client
 * user interface requests.
 * 
  * @author Arikz ,Dvir ben simon
 *
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

	public Client getClient() {
		return client;
	}
	

}
