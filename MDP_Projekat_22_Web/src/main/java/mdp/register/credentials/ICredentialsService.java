package mdp.register.credentials;

import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.exceptions.UsernameExistsException;
import mdp.exceptions.UsernameNotFoundException;

public interface ICredentialsService {

	void addCredentials(PostCredentialsDto dto) throws UsernameExistsException;

	void updateCredentials(PutCredentialsDto dto) throws UsernameNotFoundException;

	boolean checkCredentials(PostCredentialsDto dto);

}
