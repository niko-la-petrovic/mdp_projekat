package mdp.register;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
