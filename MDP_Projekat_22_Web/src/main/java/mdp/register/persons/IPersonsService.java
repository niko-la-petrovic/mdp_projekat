package mdp.register.persons;

import java.io.File;
import java.io.IOException;

import mdp.dtos.PostPersonDto;

public interface IPersonsService {

	File getPersonsLogsFile();

	void addPersonToLogs(PostPersonDto dto) throws IOException;

}
