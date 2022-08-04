package mdp.register.persons;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mdp.dtos.PostPersonDto;

@Path("/persons")
public class PersonsController {
	private static final Logger logger = Logger.getLogger(PersonsController.class.getName());

	private IPersonsService personsService;

	public PersonsController() throws FileNotFoundException, IOException {
		personsService = new PersonsService();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postPersonToLogs(PostPersonDto dto) {
		if (dto == null) {
			logger.log(Level.WARNING, "Invalid request");
			return Response.status(Status.BAD_REQUEST).build();
		}

		try {
			logger.log(Level.INFO, String.format("Logging person with ID '%s'", dto.getPersonId()));
			personsService.addPersonToLogs(dto);
			logger.log(Level.INFO, String.format("Logged person with ID '%s'", dto.getPersonId()));
			return Response.ok().build();
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("Failed to add persons log: %s", e.getMessage()));
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
	public Response getPassedPersonsLogs() throws IOException {
		logger.log(Level.INFO, "Providing passed persons logs");
		var file = personsService.getPersonsLogsFile();

		if (!file.exists()) {
			logger.log(Level.INFO, "Passed persons logs empty");
			return Response.status(Status.NOT_FOUND).build();
		}

		logger.log(Level.INFO, "Sending passed persons logs to client");
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
	}
}
