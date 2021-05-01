package server;

//
public interface ServerEventListener {
	void printToLog(String logs);

	void changeButtonStatus(boolean status);
}
