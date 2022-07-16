package mdp.register.credentials;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;

import mdp.db.redis.JedisConnectionPool;
import redis.clients.jedis.Jedis;

public class CredentialsService implements ICredentialsService {
	protected Jedis jedis;

	public CredentialsService() throws Exception {
		jedis = JedisConnectionPool.getConnection();
	}
}
