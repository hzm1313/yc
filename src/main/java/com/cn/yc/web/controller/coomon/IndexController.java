package com.cn.yc.web.controller.coomon;

import com.cn.yc.bean.ResponseBase;
import com.cn.yc.service.QqRoobotService;
import com.cn.yc.service.RedisService;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cn.yc.utils.Base64Utils;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.web.ws.WechatConnector;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController
{
    @Autowired
    private RedisService redisService;

    @Autowired
    private QqRoobotService qqRoobotService;

    @RequestMapping({"/init"})
    @ResponseBody
    public void init()
    {
       /* //处理需要初始化的数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                qqRoobotService.initStartQqRoot();
            }
        }).start();*/
    }


    @RequestMapping({"/zf"})
    @ResponseBody
    public String zf(@RequestParam(value="urlParm", defaultValue="11") String urlParm)
    {
        System.out.println(urlParm);
        String decodeUrl = Base64Utils.decryptBASE64(urlParm);
        redisService.leftPush("testJlu",decodeUrl);
        String result = redisService.get(decodeUrl);
        if (StringUtils.isBlank(result)) {
            result = HttpUtils.sendGetRequest(decodeUrl);
            System.out.println("请求获取数据");
            redisService.set(decodeUrl, result, 4L);
        }
        return result;
    }

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
