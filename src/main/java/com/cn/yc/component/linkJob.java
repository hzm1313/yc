package com.cn.yc.component;

import com.cn.yc.service.LinkService;
import com.cn.yc.service.LinkSpiderService;
import com.cn.yc.service.QqRoobotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hasee on 2017/12/17.
 */
@Component
public class linkJob {
    private static final Logger log = LoggerFactory.getLogger(linkJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private LinkService linkService;

    @Autowired
    private LinkSpiderService linkSpiderService;

    @Autowired
    private QqRoobotService qqRoobotService;

    @Scheduled(fixedRate = 30000)
    @Async
    public void reportCurrentTime() {
        linkSpiderService.spiderTradeInfo();
      /*  String result  = linkService.updateHttpInfo();
        String html  = linkService.updateHtmlDate();*/
    }

    @Scheduled(fixedRate = 60000 * 60 * 3)
    public void updateNews() {
        linkSpiderService.spiderNews();
    }

    @Scheduled(fixedRate = 60000 * 60 * 3)
    public void qqRoobot() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                qqRoobotService.initStartQqRoot();
            }
        }).start();
    }
}
