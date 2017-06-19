package com.cn.yc.web.pray.controller;

import com.cn.yc.bean.Pray;
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
    @RequestMapping("/pray")
    public String getPray(@RequestParam(value="prayObject", required=false, defaultValue="fo_fx_ysf.jpg") String prayObject, Model model){
        model.addAttribute("prayObject",prayObject);
        return "pray";
    }

    @RequestMapping("/toPray")
    public String toPray(Pray pray){

        return "";
    }
}
