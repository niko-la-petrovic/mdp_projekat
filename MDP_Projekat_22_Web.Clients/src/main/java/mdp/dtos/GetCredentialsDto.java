package mdp.dtos;

import java.io.Serializable;

public class GetCredentialsDto implements Serializable {
	private static final long serialVersionUID = -4218787478702114327L;

	private String username;

	public GetCredentialsDto(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
