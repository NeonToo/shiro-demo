package com.sap.csc.app.shiro.cache;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCache<K, V> implements Cache<K, V> {
	
	private final String name;
	
	private final RedisTemplate<K, V> redisTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RedisCache(String name, RedisTemplate redisTemplate) {
		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Cache name cannot be null.");
		}
		
		if(redisTemplate == null) {
			throw new IllegalArgumentException("RedisTemplate cannot be null.");
		}
		
		this.name = name;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public V get(K key) throws CacheException {
		return redisTemplate.boundValueOps(key).get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		return redisTemplate.keys((K)"*");
	}

	@Override
	public V put(K key, V value) throws CacheException {
		redisTemplate.boundValueOps(key).set(value);
		return this.get(key);
	}

	@Override
	public V remove(K key) throws CacheException {
		V oldValue = this.get(key);
		
		redisTemplate.delete(key);
		return oldValue;
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.delete(this.keys());
	}

	@Override
	public int size() {
		return this.keys().size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> values() {
		Set<K> keySet = this.keys();
		
		if(CollectionUtils.isNotEmpty(keySet)) {
			return keySet.stream().map(key -> this.get(key)).collect(Collectors.toList());
		}
		
		return CollectionUtils.EMPTY_COLLECTION;
	}
	
	public Boolean expire(K key, long timeout, TimeUnit unit) {
		return redisTemplate.expire(key, timeout, unit);
	}
	
	public String toString() {
		return new StringBuilder("RedisCache '")
				.append(name).append("' (")
				.append(size()).append(") entries") 
				.toString();
	}

}
