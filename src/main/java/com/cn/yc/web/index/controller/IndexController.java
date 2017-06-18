package com.cn.yc.web.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by hasee on 2017/6/18.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String getPray(@RequestParam(value="name", required=false, defaultValue="World") String name,Model model){
        model.addAttribute("name",name);
        return "index";
    }
}
