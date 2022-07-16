package mdp.db.redis;

import java.io.FileNotFoundException;
import java.io.IOException;

import mdp.util.SettingsLoader;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JedisConnectionPool {
	private static final String JEDIS_CONNECTION_SETTINGS_NAME = "jedisConnection";
	private static JedisPool pool;
	private static JedisConnectionPoolSettings settings;

	public static Jedis getConnection() throws FileNotFoundException, IOException {
		if (pool == null) {
			SettingsLoader.loadSettings(JEDIS_CONNECTION_SETTINGS_NAME, props -> {
				var host = props.getProperty("host");
				var port = Integer.valueOf(props.getProperty("port"));
				var username = props.getProperty("username");
				var password = props.getProperty("password");

				settings = new JedisConnectionPoolSettings(host, port, username, password);
			});
			// TODO use username and password
			pool = new JedisPool(new JedisPoolConfig(), settings.getHost(), settings.getPort(),
					Protocol.DEFAULT_TIMEOUT);

		}

		return pool.getResource();
	}
}
