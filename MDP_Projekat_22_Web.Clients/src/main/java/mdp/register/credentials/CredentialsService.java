package mdp.register.credentials;

import java.util.Map;

import mdp.db.redis.JedisConnectionPool;
import mdp.dtos.GetCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.exceptions.UsernameNotFoundException;
import mdp.util.PasswordUtil;
import redis.clients.jedis.Jedis;

public class CredentialsService {
	private static final String credentialsHash = "credentials";

	protected Jedis jedis;

	public CredentialsService() throws Exception {
		jedis = JedisConnectionPool.getConnection();
	}

	public void updateCredentials(PutCredentialsDto dto) throws UsernameNotFoundException {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		String existingPasswordHash = getUsernameHash(username);
		if (existingPasswordHash == null)
			throw new UsernameNotFoundException();

		String hash = PasswordUtil.hashPassword(dto.getPassword());

		setPassword(username, hash);
	}

	private void deleteUsernameEntry(String username) {
		jedis.hdel(credentialsHash, username);
	}

	private Map<String, String> getAllCredentials() {
		Map<String, String> credsMap = jedis.hgetAll(credentialsHash);
		return credsMap;
	}

	private String getUsernameHash(String username) {
		return jedis.hget(credentialsHash, username);
	}

	private void setPassword(String username, String hash) {
		jedis.hset(credentialsHash, username, hash);
	}

	public GetCredentialsDto[] getCredentialsStartingWith(String username) {
		Map<String, String> credsMap = getAllCredentials();
		GetCredentialsDto[] credentials = credsMap.keySet().stream().map(s -> new GetCredentialsDto(s))
				.filter(c -> c.getUsername().startsWith(username)).toArray(GetCredentialsDto[]::new);

		return credentials;
	}
}
