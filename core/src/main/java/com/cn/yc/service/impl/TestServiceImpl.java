package com.cn.yc.service.impl;

import com.cn.yc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by DT167 on 2018/8/17.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void operationFile() {
        System.out.println("操作文件");
    }


    @Override
    public void operaton() {
        System.out.println("操作");
        while (true) {

        }
    }
}
