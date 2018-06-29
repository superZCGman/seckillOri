package com.limai.user.util;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisUtil {

    @Resource
    protected RedisTemplate<String,Object> redisTemplate;

    private static final String PRE = "ms_";

    private String prefix2Key(final String key){
        if(StringUtils.isEmpty(key)){
            try{
                throw new Exception("Redis key 不能为空");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return PRE+key;
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public void set(final String key, final Object value) {
        remove(key);
        try {
            redisTemplate.opsForValue().set(prefix2Key(key), value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, final Object value, Long expireTime ,TimeUnit timeUnit) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(prefix2Key(key), value);
            redisTemplate.expire(prefix2Key(key), expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        redisTemplate.delete(prefix2Key(key));
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(prefix2Key(key));
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        result = redisTemplate.opsForValue().get(prefix2Key(key));
        return result;
    }

    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        return redisTemplate.opsForHash().get(prefix2Key(key),hashKey);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        redisTemplate.opsForList().rightPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        ;
        return redisTemplate.opsForList().range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        redisTemplate.opsForSet().add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        redisTemplate.opsForZSet().add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        return redisTemplate.opsForZSet().rangeByScore(key, scoure, scoure1);
    }

}
