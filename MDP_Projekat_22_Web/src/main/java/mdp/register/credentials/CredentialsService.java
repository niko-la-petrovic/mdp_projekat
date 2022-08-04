package mdp.register.credentials;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import mdp.db.redis.JedisConnectionPool;
import mdp.dtos.GetCredentialsDto;
import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.exceptions.UsernameExistsException;
import mdp.exceptions.UsernameNotFoundException;
import mdp.util.PasswordUtil;
import redis.clients.jedis.Jedis;

public class CredentialsService implements ICredentialsService {
private static final Logger logger = Logger.getLogger(CredentialsService.class.getName());

	private static final String credentialsHash = "credentials";

	protected Jedis jedis;

	public CredentialsService() throws Exception {
		jedis = JedisConnectionPool.getConnection();
	}

	public void addCredentials(PostCredentialsDto dto) throws UsernameExistsException {
		String username = dto.getUsername();
		if (username == null){
logger.log(Level.WARNING, "Username is null");
			throw new IllegalArgumentException();
		}

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash != null){
logger.log(Level.WARNING, String.format("Username '%s' exists", username));
			throw new UsernameExistsException();
		}

		logger.log(Level.INFO, String.format("Adding user with username '%s'", dto.getUsername()));
		var hash = PasswordUtil.hashPassword(dto.getPassword());
		setPassword(username, hash);
		logger.log(Level.INFO, String.format("Added user with username '%s'", dto.getUsername()));
	}

	@Override
	public boolean checkCredentials(PostCredentialsDto dto) {
		String username = dto.getUsername();
		if (username == null)
		{
logger.log(Level.WARNING, "Username is null");
			throw new IllegalArgumentException();
		}

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash == null)
		{
			logger.log(Level.WARNING, String.format("User with username '%s' does not exist", username));
			return false;
		}

logger.log(Level.INFO, String.format("User with username '%s' attempted to log in", username));
		return PasswordUtil.checkPassword(dto.getPassword(), existingPasswordHash);
	}

	@Override
	public void deleteCredentials(String username) throws UsernameNotFoundException {
		if (username == null){
logger.log(Level.WARNING, "Username is null");
			throw new IllegalArgumentException();
		}

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash == null)
		{
logger.log(Level.WARNING, String.format("User with username '%s' does not exist", username));
			throw new UsernameNotFoundException();
		}

		logger.log(Level.INFO, String.format("Deleting user with username '%s'", username));
		deleteUsernameEntry(username);
	}

	public GetCredentialsDto[] getCredentials() {
		logger.log(Level.INFO, "Getting credentials");
		var credsMap = getAllCredentials();
		var credentials = credsMap.keySet().stream().map(s -> new GetCredentialsDto(s))
				.toArray(GetCredentialsDto[]::new);

		return credentials;
	}

	@Override
	public void updateCredentials(PutCredentialsDto dto) throws UsernameNotFoundException {
		String username = dto.getUsername();
		if (username == null){
logger.log(Level.WARNING, "Username is null");
			throw new IllegalArgumentException();
		}

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash == null)
		{
logger.log(Level.WARNING, String.format("User with username '%s' does not exist", username));
			throw new UsernameNotFoundException();
		}

		var hash = PasswordUtil.hashPassword(dto.getPassword());

		logger.log(Level.INFO, String.format("Setting new password for user with username '%s'", username));
		setPassword(username, hash);
	}

	private void deleteUsernameEntry(String username) {
		jedis.hdel(credentialsHash, username);
	}

	private Map<String, String> getAllCredentials() {
		var credsMap = jedis.hgetAll(credentialsHash);
		return credsMap;
	}

	private String getUsernameHash(String username) {
		return jedis.hget(credentialsHash, username);
	}

	private void setPassword(String username, String hash) {
		jedis.hset(credentialsHash, username, hash);
	}

	@Override
	public GetCredentialsDto[] getCredentialsStartingWith(String username) {
		logger.log(Level.INFO, String.format("Getting credentials where username starts with '%s'", username));
		var credsMap = getAllCredentials();
		var credentials = credsMap.keySet().stream().map(s -> new GetCredentialsDto(s))
				.filter(c -> c.getUsername().startsWith(username)).toArray(GetCredentialsDto[]::new);

		return credentials;
	}
}
