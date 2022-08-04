package mdp.register.wanted.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import mdp.exceptions.NoFilesFoundException;
import mdp.util.SettingsLoader;
import mdp.util.Util;

public class PersonIdentifyingDocumentsService implements IPersonIdentifyingDocumentsService {
private static final Logger logger = Logger.getLogger(PersonIdentifyingDocumentsService.class.getName());
	private PersonIdentifyingDocumentsServiceSettings settings;

	public PersonIdentifyingDocumentsService() throws FileNotFoundException, IOException {
		SettingsLoader.loadSettings("personIdentifyingDocuments", props -> {
			var persistenceFolderPath = props.getProperty("persistenceFolderPath");

			settings = new PersonIdentifyingDocumentsServiceSettings(persistenceFolderPath);
		});
	}

	public void addPersonDocuments(BigInteger personId, HashMap<String, byte[]> fileMap) throws IOException {
		logger.log(Level.INFO, String.format("Adding documents for person with id '%s'", personId));

		var personFolderFile = getPersonFolderFile(personId);

		var uploadId = Util.getStringUuid();

		for (var pair : fileMap.entrySet()) {
			var sourceFileName = new File(pair.getKey()).getName();
			var fileBytes = pair.getValue();
			var fileName = String.format("%s_%s", uploadId, sourceFileName);

			var filePath = Paths.get(personFolderFile.toPath().toString(), fileName);
			var file = filePath.toFile();

			inflateToFile(fileBytes, file);
		}
	}

	public File getArchivedPersonDocuments(BigInteger personId)
			throws NoFilesFoundException, FileNotFoundException, IOException {
		logger.log(Level.INFO, String.format("Getting archived documents of person with id '%s'", personId));

		var personFolderFile = getPersonFolderFile(personId);

		final String zipFilePrefix = "_zipped_";
		var children = personFolderFile.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return !pathname.getName().startsWith(zipFilePrefix);
			}
		});
		if (children.length == 0)
			throw new NoFilesFoundException();

		var zipFilePath = Paths.get(personFolderFile.toPath().toString(),
				String.format("%s%s.zip", zipFilePrefix, Util.getStringUuid()));
		var zipFile = zipFilePath.toFile();
		try (var fileStream = new FileOutputStream(zipFile)) {
			try (var zipStream = new ZipOutputStream(fileStream)) {
				for (var file : children) {
					zipStream.putNextEntry(new ZipEntry(file.getName()));

					try (var inFileStream = new FileInputStream(file)) {
						int length;
						byte[] buffer = new byte[1024];

						while ((length = inFileStream.read(buffer)) > 0) {
							zipStream.write(buffer, 0, length);
						}
					}
				}
			}
		}

		return zipFile;
	}

	private File getPersonFolderFile(BigInteger personId) {
		var folderFile = initializePersistenceFolder();

		var personFolderPath = Paths.get(folderFile.toPath().toString(), personId.toString());
		var personFolderFile = personFolderPath.toFile();
		if (!personFolderFile.exists())
			personFolderFile.mkdir();

		return personFolderFile;
	}

	private void inflateToFile(byte[] fileBytes, File file) throws IOException, FileNotFoundException {
		try (var inByteStream = new ByteArrayInputStream(fileBytes);
				var inDeflateStream = new InflaterInputStream(inByteStream);
				var outFileStream = new FileOutputStream(file);) {
			inDeflateStream.transferTo(outFileStream);
		}
	}

	private File initializePersistenceFolder() {
		var folderPath = Paths.get(settings.getPersistenceFolderPath());
		var folderFile = folderPath.toFile();
		if (!folderFile.exists())
			folderFile.mkdir();

		return folderFile;
	}
}
