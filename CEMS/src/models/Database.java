package models;

import java.io.Serializable;

/**
 * Class that demonstrate database, store on that object all database attribute.
 * 
 * @author Arikz ,Dvir ben simon
 *
 */
public class Database implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ip;
	private String port;
	private String scheme;
	private String userName;
	private String password;
	
	/****************************** Constructors ************************************/
	public Database(String ip, String port, String scheme, String userName, String password) {
		super();
		this.ip = ip;
		this.port = port;
		this.scheme = scheme;
		this.userName = userName;
		this.password = password;
	}
	
	/***************************** Setters and getters **********************************/
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
