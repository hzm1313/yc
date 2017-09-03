package com.cn.yc.web.controller.pray;

import com.cn.yc.pojo.PrayDO;
import com.cn.yc.pojo.ResponseBase;
import com.cn.yc.service.PrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        List<PrayDO> prayList = prayServiceImpl.getPrayList(null);
        return "pray";
    }

    @RequestMapping("/toPray")
    public String toPray(PrayDO pray){
        return "";
    }

    @RequestMapping("/savePray")
    public @ResponseBody  ResponseBase savePray(PrayDO pray, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseBase responseBase = new ResponseBase();
        responseBase.setContent("ok");
        return responseBase;
    }


}
