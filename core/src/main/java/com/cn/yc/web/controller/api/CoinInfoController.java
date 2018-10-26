package com.cn.yc.web.controller.api;

import com.cn.yc.bean.ResponseBase;
import com.cn.yc.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hasee on 2017/12/14.
 */
@Controller("/api")
public class CoinInfoController {
    @Autowired
    private LinkService linkService;


    @RequestMapping({"/getLinkInfo"})
    @ResponseBody
    @ExceptionHandler
    public ResponseBase getPray()
    {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setResultMessage(linkService.getLinkInfo());
        return responseBase;
    }
    @RequestMapping({"/getTable"})
    @ResponseBody
    public String revealTable(){
        String result = linkService.getHtmlTable();
        return result.toString();
    }

}
