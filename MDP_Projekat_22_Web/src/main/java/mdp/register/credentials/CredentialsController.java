package mdp.register.credentials;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.exceptions.UsernameExistsException;
import mdp.exceptions.UsernameNotFoundException;

@Path("/credentials")
public class CredentialsController {
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
	public String getPing() {
		return "pong";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCredentials(PostCredentialsDto dto) throws UsernameExistsException {
		if (dto == null)
			return Response.status(Status.BAD_REQUEST).build();

		var violations = validator.validate(dto);
		if (!violations.isEmpty())
			return Response.status(Status.BAD_REQUEST).build();

		try {
			credentialsService.addCredentials(dto);
			return Response.ok(dto).build();
		} catch (UsernameExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.CONFLICT).build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCredentials(PutCredentialsDto dto) {
		if (dto == null)
			return Response.status(Status.BAD_REQUEST).build();

		try {
			credentialsService.updateCredentials(dto);
			return Response.ok().build();
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}