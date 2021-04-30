package client;

import java.io.IOException;
import java.util.List;

import gui.ClientMainGuiController;
import models.Test;

public class ClientController {

	private ClientMainGuiController clientMainGuiController;
	private Client client;

	public ClientController(String ip, int port, ClientMainGuiController clientMainGuiController) {
		this.clientMainGuiController = clientMainGuiController;
		try {
			client = new Client(ip, port, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendClientUIRequest(Object msg) {
		client.handleMessageFromClientUI(msg);
	}

	public Client getClient() {
		return client;
	}

}
