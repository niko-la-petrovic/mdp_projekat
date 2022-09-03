package mdp.register.credentials;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.exceptions.UsernameExistsException;
import mdp.exceptions.UsernameNotFoundException;

@Path("/credentials")
public class CredentialsController {
	private static final Logger logger = Logger.getLogger(CredentialsController.class.getName());

	private ICredentialsService credentialsService;
	private ValidatorFactory factory;
	private Validator validator;

	public CredentialsController() throws Exception {
		credentialsService = new CredentialsService();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ping")
	public String getPing() {
		return "pong";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCredentials() {
		logger.log(Level.INFO, "Getting credentials");
		var creds = credentialsService.getCredentials();

		return Response.ok(creds).build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchCredentials(@QueryParam("username") String username) {
		logger.log(Level.INFO, String.format("Searching for username '%s...'", username));
		var creds = credentialsService.getCredentialsStartingWith(username);

		return Response.ok(creds).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Response loginWithCredentials(PostCredentialsDto dto) {
		if (dto == null) {
			logger.log(Level.WARNING, "Dto is null");
			return Response.status(Status.BAD_REQUEST).build();
		}

		var violations = validator.validate(dto);
		if (!violations.isEmpty()) {
			logger.log(Level.WARNING, "Dto validation failed");
			return Response.status(Status.BAD_REQUEST).build();
		}

		if (credentialsService.checkCredentials(dto)) {
			logger.log(Level.INFO, "Login successful");
			return Response.status(Status.OK).build();
		} else {
			logger.log(Level.INFO, "Login failed");
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCredentials(PostCredentialsDto dto) {
		if (dto == null)
			return Response.status(Status.BAD_REQUEST).build();

		var violations = validator.validate(dto);
		if (!violations.isEmpty())
			return Response.status(Status.BAD_REQUEST).build();

		try {
			credentialsService.addCredentials(dto);
			return Response.ok(dto).build();
		} catch (UsernameExistsException e) {
			logger.log(Level.INFO,
					String.format("Attempted to create user with existing username '%s'", dto.getUsername()));
			return Response.status(Status.CONFLICT).build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCredentials(PutCredentialsDto dto) {
		if (dto == null) {
			logger.log(Level.WARNING, "Dto is null");
			return Response.status(Status.BAD_REQUEST).build();
		}

		try {
			logger.log(Level.INFO, String.format("Updating user with username '%s'", dto.getUsername()));
			credentialsService.updateCredentials(dto);
			logger.log(Level.INFO, String.format("Updated user with username '%s'", dto.getUsername()));
			return Response.ok().build();
		} catch (UsernameNotFoundException e) {
			logger.log(Level.INFO, String.format("Username '%s' not found", dto.getUsername()));
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	public Response deleteCredentials(@QueryParam("username") String username) {
		if (username == null) {
			logger.log(Level.WARNING, "Username is null");
			return Response.status(Status.BAD_REQUEST).build();
		}

		try {
			logger.log(Level.INFO, String.format("Deleting user with username '%s'", username));
			credentialsService.deleteCredentials(username);
			logger.log(Level.INFO, String.format("Deleted user with username '%s'", username));
			return Response.ok().build();
		} catch (UsernameNotFoundException e) {
			logger.log(Level.INFO, String.format("Username '%s' not found", username));
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
