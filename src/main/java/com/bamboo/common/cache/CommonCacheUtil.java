package com.bamboo.common.cache;

import java.util.Map;

/**
 * 公用缓存工具的接口
 *
 */
public interface CommonCacheUtil {

	/**
	 * 根据key，从缓存中取回字符串值
	 * @param key 缓存key
	 * @return
	 */
	public String get(String key);
	
	/**
	 * 根据一组key，从缓存中取回一组字符串值
	 * @param keys
	 * @return
	 */
	public String[] get(final String[] keys);
	
	/**
	 * 根据一组key，从缓存中取回一组字符串值，取回的字符串值以Map返回。
	 * @param keys
	 * @return
	 */
	public Map<String, String> getToMap(final String[] keys);
	
	
	/**
	 * 根据可以，从缓存中取出一个对象
	 * @param key 缓存key
	 * @return
	 */
	public Object getObject(String key);
	
	/**
	 * 根据一组key，从缓存中取回一组对象
	 * @param keys
	 * @return
	 */
	public Object[] getObject(final String[] keys);
	
	/**
	 * 根据一组key，从缓存中取回一组对象，取回的对象存在Map中返回。
	 * @param keys
	 * @return
	 */
	public Map<String, Object> getObjectToMap(final String[] keys);
	
	
	/**
	 * 根据key，将字符串值存入缓存，如果之前相同key已存在，则覆盖原有的值
	 * @param key 缓存key
	 * @param value 存入的值
	 * @return
	 */
	public boolean put(String key, String value);
	
	/**
	 * 根据key，将字符串值存入缓存，如果之前相同key已存在，则覆盖原有的值。
	 * 存入时，为该数据设置一个过期的时间（单位：秒），如果过期时间为负数值，则永久保留。
	 * @param key 缓存key
	 * @param value 存入的值
	 * @param expire 过期时间（秒）
	 * @return
	 */
	public boolean put(String key, String value, int expire);

	/**
	 * 根据key，将一个对象存入缓存，如果之前相同key已存在，则覆盖原有的值
	 * @param key 缓存key
	 * @param objValue 存入的对象
	 * @return
	 */
	public void put(String key, Object objValue);
	
	/**
	 * 根据key，将一个对象存入缓存，如果之前相同key已存在，则覆盖原有的值。
	 * 存入时，为该数据设置一个过期的时间（单位：秒），如果过期时间为负数值，则永久保留。
	 * @param key 缓存key
	 * @param objValue 存入的对象
	 * @param expire expire 过期时间（秒）
	 * @return
	 */
	public void putObject(String key, Object objValue, int expire);
	
	/**
	 * 根据可以，删除缓存中的值
	 * @param key 缓存key
	 * @return
	 */
	public void remove(String key);
	
	/**
	 * 根据一组key，删除缓存中的值
	 * @params keys 缓存key数组
	 * @return
	 */
	public void remove(String[] keys);
	
	/**
	 * 根据key，增加缓存中对应key的值（此key之前应该无值，或值为整数值），并返回增加后的值
	 * @param key
	 * @param incrValue
	 * @return
	 */
	public Long incrNumValue(String key, long incrValue);
	
	/**
	 * 根据key，减少缓存中对应key的值（此key之前应该无值，或值为整数值），并返回减少后的值
	 * @param key
	 * @param decrValue
	 * @return
	 */
	public Long decrNumValue(String key, long decrValue);
	
}
