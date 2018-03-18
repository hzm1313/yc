package com.cn.yc.component;

import com.cn.yc.service.QqRoobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by hasee on 2018/1/17.
 */
@Component
public class StartCompent implements CommandLineRunner {

    @Autowired
    private QqRoobotService qqRoobotService;


    @Override
    public void run(String... strings) throws Exception {
        //QQ机器人启动，定时任务，去判断SESSION是否过期，不在这里启动了
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                qqRoobotService.initStartQqRoot();
            }
        }).start();*/
    }
}
