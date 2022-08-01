package mdp.register.credentials;

import java.util.ArrayList;
import java.util.Map;

import mdp.db.redis.JedisConnectionPool;
import mdp.dtos.GetCredentialsDto;
import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.exceptions.UsernameExistsException;
import mdp.exceptions.UsernameNotFoundException;
import mdp.util.PasswordUtil;
import redis.clients.jedis.Jedis;

public class CredentialsService implements ICredentialsService {
	private static final String credentialsHash = "credentials";

	protected Jedis jedis;

	public CredentialsService() throws Exception {
		jedis = JedisConnectionPool.getConnection();
	}

	public void addCredentials(PostCredentialsDto dto) throws UsernameExistsException {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash != null)
			throw new UsernameExistsException();

		var hash = PasswordUtil.hashPassword(dto.getPassword());
		setPassword(username, hash);
	}

	@Override
	public boolean checkCredentials(PostCredentialsDto dto) {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash == null)
			return false;

		return PasswordUtil.checkPassword(dto.getPassword(), existingPasswordHash);
	}

	@Override
	public void deleteCredentials(String username) throws UsernameNotFoundException {
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash == null)
			throw new UsernameNotFoundException();

		deleteUsernameEntry(username);
	}

	public GetCredentialsDto[] getCredentials() {
		var credsMap = getAllCredentials();
		var credentials = credsMap.keySet().stream().map(s -> new GetCredentialsDto(s))
				.toArray(GetCredentialsDto[]::new);

		return credentials;
	}

	@Override
	public void updateCredentials(PutCredentialsDto dto) throws UsernameNotFoundException {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash == null)
			throw new UsernameNotFoundException();

		var hash = PasswordUtil.hashPassword(dto.getPassword());

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
}
