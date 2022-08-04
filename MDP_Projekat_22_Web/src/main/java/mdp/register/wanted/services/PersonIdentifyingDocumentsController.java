package mdp.register.wanted.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mdp.exceptions.NoFilesFoundException;

@Path("/documents")
public class PersonIdentifyingDocumentsController {
private static final Logger logger  = Logger.getLogger(PersonIdentifyingDocumentsController.class.getName());

	private IPersonIdentifyingDocumentsService documentsService;

	public PersonIdentifyingDocumentsController() throws FileNotFoundException, IOException {
		documentsService = new PersonIdentifyingDocumentsService();
	}

	@GET
	@Path("/person/{personId}")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
	public Response getDetectedWantedPersons(@PathParam("personId") BigInteger personId) throws IOException {
		if (personId == null)
		{
logger.log(Level.WARNING, "Person id is null");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	logger.log(Level.INFO, String.format("Obtaining documents of person with id '%s'", personId));
		File file;
		try {
			file = documentsService.getArchivedPersonDocuments(personId);
		} catch (NoFilesFoundException | IOException e) {
			logger.log(Level.WARNING, String.format("Error with obtaining person documents for person '%s': '%s'", personId, e.getMessage()));
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		logger.log(Level.INFO, String.format("Sending documents of person with id '%s'", personId));
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
	}
}
