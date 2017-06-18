package com.cn.yc.web.pray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by hasee on 2017/6/18.
 */
@Controller
public class PrayController
{
    @RequestMapping("/getPray")
    public String getPray(@RequestParam(value="prayObject", required=false, defaultValue="fo_fx_ysf.jpg") String name, Model model){
        return "pray";
    }
}
