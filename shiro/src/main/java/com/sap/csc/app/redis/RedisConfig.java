package com.sap.csc.app.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig implements AutoCloseable {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private JedisConnectionFactory jedisConnectionFactory;
	
	private RedisClusterConfiguration redisClusterConfiguration;
	
	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
		logger.info("----- Redis RedisCacheManager -----");
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		
//		cacheManager.setDefaultExpiration(1800);
		return cacheManager;
	}
	
	// Once configured, the template is thread-safe
	@Bean
	public RedisTemplate<?, ?> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		logger.info("----- Redis RedisTemplate -----");
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		final RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		final RedisSerializer<Object> objectSerializer = new JdkSerializationRedisSerializer();
		
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(objectSerializer);
		redisTemplate.setHashValueSerializer(objectSerializer);
		
		return redisTemplate;
	}
	
	@Bean
	public synchronized JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration) {
		logger.info("----- Redis JedisConnection -----");
		
		if(this.jedisConnectionFactory == null) {
			if(this.isClusterEnabled()) {
				this.jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration);
			} else {
				this.jedisConnectionFactory = new JedisConnectionFactory();
				this.jedisConnectionFactory.setHostName(redisProperties.getHost());
				this.jedisConnectionFactory.setPort(redisProperties.getPort());
			}
			
		}
		
		return this.jedisConnectionFactory;
	}

	@Bean
	public synchronized RedisClusterConfiguration redisClusterConfiguration() {
		logger.info("----- Redis Cluster Configuration -----");
		
		if(this.redisClusterConfiguration == null) {
			if(this.isClusterEnabled()) {
				this.redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
				this.redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
			}
		}
		
		return this.redisClusterConfiguration;
	}

	@Override
	public void close() throws Exception {
		if(this.jedisConnectionFactory != null) {
			jedisConnectionFactory.destroy();
		}
	}
	
	private boolean isClusterEnabled() {
		if(this.redisProperties.getCluster() != null) {
			return true;
		}
		
		return false;
	}

}
