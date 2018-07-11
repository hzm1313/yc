package com.cn.yc.service.impl;

import com.cn.yc.service.RedisService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl
        implements RedisService
{
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;

    @Override
    public void addLink(String key, String listValue)
    {
        this.listOps.leftPush(key, listValue);
    }

    @Override
    public List<String> getLink(String key, Long start, Long end)
    {
        Long size = this.listOps.size(key);
        if (size.longValue() > 20L) {
            size = Long.valueOf(20L);
        }
        return this.listOps.range(key, 0L, size.longValue());
    }

    @Override
    public List<String> getAllLink(String key)
    {
        Long size = this.listOps.size(key);
        return this.listOps.range(key, 0L, size.longValue());
    }

    @Override
    public void removeKey(String key) {}

    @Override
    public void leftPush(String key, String value)
    {
        this.redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public String get(String key) {
       return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public void set(String key, String value, Long time) {
        redisTemplate.boundValueOps(key).set(value, time, TimeUnit.SECONDS);
    }

    @Override
    public void setLinkInfo(String key,String value) {
        redisTemplate.boundValueOps(key).set(value);
    }
}
