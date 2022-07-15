package mdp.models;

import java.io.Serializable;

public class PostCredentialsDto implements Serializable {
	private static final long serialVersionUID = -6999246901344056292L;

	private String username;
	private String password;

	public PostCredentialsDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
