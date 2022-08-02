package mdp.register.wanted.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/wanted")
public class WantedPersonsController {
	private static final Logger logger = Logger.getLogger(WantedPersonsController.class.getName());

	private IWantedPersonsService wantedPerosnsService;

	public WantedPersonsController() throws FileNotFoundException, IOException {
		wantedPerosnsService = new WantedPersonsService();
	}

	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
	public Response getDetectedWantedPersons() throws IOException {
		var file = wantedPerosnsService.getWantedPersonsLogsFile();

		if (!file.exists())
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
	}
}
