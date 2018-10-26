package com.cn.yc.component;

import com.cn.yc.service.QqRoobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by DT167 on 2018/2/28.
 */
@Component
public class InitCompoent implements ApplicationRunner {
    @Autowired
    private QqRoobotService qqRoobotService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //默认不启动
        //qqRoobotService.initStartQqRoot();
    }
}
