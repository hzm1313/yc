package com.cn.yc.utils;

import com.cn.yc.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        String result  = linkService.updateHttpInfo();
        log.info("The time is now to request linkInfo {}", dateFormat.format(new Date())+" "+result);
    }
}
