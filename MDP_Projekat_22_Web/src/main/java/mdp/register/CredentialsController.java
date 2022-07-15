package mdp.register;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/credentials")
public class CredentialsController {
	private ICredentialsService credentialsService;

	public CredentialsController() {
		credentialsService = new CredentialsService();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPing() {
		return "pong";
	}
}
