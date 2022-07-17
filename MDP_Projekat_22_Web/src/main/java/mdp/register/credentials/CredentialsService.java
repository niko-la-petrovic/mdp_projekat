package mdp.register.credentials;

import mdp.db.redis.JedisConnectionPool;
import mdp.dtos.PostCredentialsDto;
import mdp.dtos.PutCredentialsDto;
import mdp.exceptions.UsernameExistsException;
import mdp.exceptions.UsernameNotFoundException;
import mdp.util.PasswordUtil;
import redis.clients.jedis.Jedis;

public class CredentialsService implements ICredentialsService {
	private static final String credentialsPrefix = "credentials:";

	protected Jedis jedis;

	public CredentialsService() throws Exception {
		jedis = JedisConnectionPool.getConnection();
	}

	public void addCredentials(PostCredentialsDto dto) throws UsernameExistsException {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingUsername = jedis.get(getUsernameKey(username));
		if (existingUsername != null)
			throw new UsernameExistsException();

		var hash = PasswordUtil.hashPassword(dto.getPassword());
		jedis.set(getUsernameKey(username), hash);
	}

	@Override
	public void updateCredentials(PutCredentialsDto dto) throws UsernameNotFoundException {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingUsername = jedis.get(getUsernameKey(username));
		if (existingUsername == null)
			throw new UsernameNotFoundException();

		var hash = PasswordUtil.hashPassword(dto.getPassword());

		jedis.set(getUsernameKey(username), hash);
	}

	private static String getUsernameKey(String username) {
		return credentialsPrefix + username;
	}
}
