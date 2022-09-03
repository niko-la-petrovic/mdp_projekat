package mdp.dtos;

import java.io.Serializable;

//TODO validation annotations
public class PutCredentialsDto implements Serializable {
	private static final long serialVersionUID = 1301581812055399692L;

	private String username;
	private String password;

	public PutCredentialsDto() {
	}

	public PutCredentialsDto(String username, String password) {
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
