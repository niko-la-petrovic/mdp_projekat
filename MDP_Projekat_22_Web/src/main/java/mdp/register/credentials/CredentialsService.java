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

	private static String getUsernameKey(String username) {
		return credentialsPrefix + username;
	}

	protected Jedis jedis;

	public CredentialsService() throws Exception {
		jedis = JedisConnectionPool.getConnection();
	}

	public void addCredentials(PostCredentialsDto dto) throws UsernameExistsException {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = jedis.get(getUsernameKey(username));
		if (existingPasswordHash != null)
			throw new UsernameExistsException();

		var hash = PasswordUtil.hashPassword(dto.getPassword());
		jedis.set(getUsernameKey(username), hash);
	}

	@Override
	public boolean checkCredentials(PostCredentialsDto dto) {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = jedis.get(getUsernameKey(username));
		if (existingPasswordHash == null)
			return false;

		return PasswordUtil.checkPassword(dto.getPassword(), existingPasswordHash);
	}

	@Override
	public void deleteCredentials(String username) throws UsernameNotFoundException {
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = jedis.get(getUsernameKey(username));
		if (existingPasswordHash == null)
			throw new UsernameNotFoundException();

		jedis.del(getUsernameKey(username));
	}

	@Override
	public void updateCredentials(PutCredentialsDto dto) throws UsernameNotFoundException {
		String username = dto.getUsername();
		if (username == null)
			throw new IllegalArgumentException();

		var existingPasswordHash = jedis.get(getUsernameKey(username));
		if (existingPasswordHash == null)
			throw new UsernameNotFoundException();

		var hash = PasswordUtil.hashPassword(dto.getPassword());

		jedis.set(getUsernameKey(username), hash);
	}
}
