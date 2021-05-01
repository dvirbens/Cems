package client;

import java.io.IOException;
import java.util.List;

import gui.ClientMainGuiController;
import models.Test;

public class ClientController {

	private ClientMainGuiController clientMainGuiController;
	private Client client;

	public ClientController(String ip, int port, ClientMainGuiController clientMainGuiController) throws IOException {
		this.clientMainGuiController = clientMainGuiController;
		client = new Client(ip, port);
		client.openConnection();
	}

	public void sendClientUIRequest(Object msg) {
		client.handleMessageFromClientUI(msg);
	}

	public Client getClient() {
		return client;
	}

}
