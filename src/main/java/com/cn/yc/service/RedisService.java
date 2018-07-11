package com.cn.yc.service;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public abstract interface RedisService
{
    public abstract void addLink(String paramString1, String paramString2);

    public abstract List<String> getLink(String paramString, Long paramLong1, Long paramLong2);

    public abstract List<String> getAllLink(String paramString);

    public abstract void removeKey(String paramString);

    public abstract void leftPush(String paramString1, String paramString2);

    public abstract String get(String key);

    public abstract void set(String key,String value,Long time);

    public abstract void setLinkInfo(String key,String value);
}
