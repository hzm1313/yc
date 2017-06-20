package com.cn.yc.web.pray.controller;

import com.cn.yc.bean.Pray;
import com.cn.yc.web.pray.service.PrayService;
import com.cn.yc.web.pray.service.impl.PrayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by hasee on 2017/6/18.
 */
@Controller
public class PrayController
{
    @Autowired
    private PrayService prayServiceImpl;

    @RequestMapping("/getPray")
    public String getPray(@RequestParam(value="prayObject", required=false, defaultValue="fo_fx_ysf.jpg") String prayObject, Model model){
        model.addAttribute("prayObject",prayObject);
        List<Pray> prayList = prayServiceImpl.getPrayList(null);
        return "pray";
    }

    @RequestMapping("/toPray")
    public String toPray(Pray pray){
        return "";
    }


}
