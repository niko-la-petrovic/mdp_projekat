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
			logger.log(Level.INFO, "Invalid request");
			return Response.status(Status.BAD_REQUEST).build();
		}

		try {
			personsService.addPersonToLogs(dto);
			return Response.ok().build();
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("Failed to add persons log: %s", e.getMessage()));
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
	public Response getDetectedWantedPersons() throws IOException {
		var file = personsService.getPersonsLogsFile();

		if (!file.exists())
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
	}
}
