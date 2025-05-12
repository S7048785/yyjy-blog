package com.yyjy.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void setStr(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public void setStr(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	public String getStr(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void setHash(String key, String hashKey, String value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	public String getHash(String key, String hashKey) {
		return (String) redisTemplate.opsForHash().get(key, hashKey);
	}

	public void setSortedSet(String key, String value, double score) {
		redisTemplate.opsForZSet().add(key, value, score);
	}

	/**
	 * 批量设置有序集合
	 * @param key
	 * @param value
	 */
	public void setSortedSet(String key, Set<ZSetOperations.TypedTuple<String> > value) {

		redisTemplate.opsForZSet().add(key, value);
	}

	public Collection<String> getSortedSet(String key, long start, long end) {
		return redisTemplate.opsForZSet().range(key, start, end);
	}
}
