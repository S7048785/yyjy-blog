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

	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 尝试获取锁
	 * @param key
	 * @return
	 */
	public boolean tryLock(String key) {
		Boolean flag = redisTemplate
				.opsForValue()
				.setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
		// 防止flag为null 引起空指针异常
		return Boolean.TRUE.equals(flag);
	}

	/**
	 * 解锁
	 * @param key
	 */
	public void unlock(String key) {
		redisTemplate.delete(key);
	}

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
		redisTemplate.expire(key, 1, TimeUnit.DAYS);
	}

	public void increment(String key, String hashKey, long increment) {
		redisTemplate.opsForHash().increment(key, hashKey, increment);
	}

	public String getHash(String key, String hashKey) {
		return (String) redisTemplate.opsForHash().get(key, hashKey);
	}

	public void setSortedSet(String key, String value, double score) {
		redisTemplate.opsForZSet().add(key, value, score);
	}

	/**
	 * 获取有序集合
	 * @param key
	 * @param start
	 * @param count
	 * @return
	 */
	public Collection<String> getSortedSetByScore(String key, long start, long offset, long count) {
		return redisTemplate.opsForZSet().reverseRangeByScore(key, 0, start, offset, count);
	}

	public Long getSortedSetCount (String key) {
		return redisTemplate.opsForZSet().size(key);
	}

	/**
	 * 设置有序集合
	 * @param key
	 * @param value
	 * @param score
	 */
	public void setSortedSetByScore(String key, String value, double score) {
		redisTemplate.opsForZSet().add(key, value, score);
		redisTemplate.expire(key, 1, TimeUnit.HOURS);
	}

	public void setSortedSetByScore(String key, Set<ZSetOperations.TypedTuple<String>> value) {
		redisTemplate.opsForZSet().add(key, value);
		redisTemplate.expire(key, 1, TimeUnit.HOURS);
	}


}
