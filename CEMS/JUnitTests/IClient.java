import models.User;

public interface IClient {

	void handleMessageFromClientUI(Object msg);

	User getUser();

	void setUser(User user);

}
