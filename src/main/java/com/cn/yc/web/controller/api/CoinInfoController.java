package com.cn.yc.web.controller.api;

import com.cn.yc.bean.ResponseBase;
import com.cn.yc.service.LinkService;
import com.cn.yc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hasee on 2017/12/14.
 */
@Controller("/api")
public class CoinInfoController {
    @Autowired
    private LinkService linkService;


    @RequestMapping({"/getLinkInfo"})
    @ResponseBody
    public ResponseBase getPray()
    {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setResultMessage(linkService.getLinkInfo());
        System.out.println(1);
        return responseBase;
    }
}
