package com.cn.yc.web.controller.coomon;

import com.cn.yc.bean.ResponseBase;
import com.cn.yc.service.RedisService;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController
{
    @Autowired
    private RedisService redisService;

    @RequestMapping({"/record"})
    @ResponseBody
    public ResponseBase getPray()
    {
        SimpleDateFormat dfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String key = dfDate.format(now);
        String value = dfTime.format(now);
        this.redisService.leftPush(key, value);
        ResponseBase responseBase = new ResponseBase();
        responseBase.setResultMessage("ok");
        System.out.println(1);
        return responseBase;
    }

}
