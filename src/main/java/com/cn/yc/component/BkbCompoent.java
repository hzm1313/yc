package com.cn.yc.component;

import com.cn.yc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by hasee on 2017/12/22.
 */
@Component
public class BkbCompoent {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static BkbCompoent bkbCompoent;
    @PostConstruct
    public void init(){
        bkbCompoent = this;
        bkbCompoent.redisTemplate = this.redisTemplate;
    }

    public static String getBkbString(){
        return bkbCompoent.redisTemplate.boundValueOps(Constants.LINK_INFO).get();
    }

}
