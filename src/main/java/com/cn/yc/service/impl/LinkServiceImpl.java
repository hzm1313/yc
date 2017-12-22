package com.cn.yc.service.impl;

import com.cn.yc.bean.WkyVO;
import com.cn.yc.service.LinkService;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.JsonUtils;
import com.cn.yc.utils.LinkUrl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by hasee on 2017/12/14.
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String updateHttpInfo() {
        WkyVO wkyVO = new WkyVO();
        String cexResult = HttpUtils.sendGetCexRequest(LinkUrl.cexInfoUrl);
        JSONArray cexArray = JSONArray.fromObject(cexResult);
        JSONArray uylArray = null,wjwArray;
        JSONObject wjwObject,wkyObject;
        JSONObject job = null;
        if (StringUtils.isNotBlank(cexResult) && cexArray != null) {
            for (int i = 0; i < cexArray.size(); i++) {
                job = cexArray.getJSONObject(i);
                if(job.get("currency_mark").equals("WKC")){
                    wkyVO.setCex(new BigDecimal(job.get("new_price").toString()).toString());
                    break;
                }
            }
        }
        String uylResult = HttpUtils.sendGetRequest(LinkUrl.uylInfoUrl);
        JSONObject uylObject = JSONObject.fromObject(uylResult);
        uylObject = JSONObject.fromObject(uylObject.get("url"));
        uylArray = JSONArray.fromObject(uylObject.get("wkc_doge"));
        wkyVO.setUyl(uylArray.get(1).toString());
        String wjwResult = HttpUtils.sendWjwRequest(LinkUrl.wjwInfoUrl);
        wjwObject = JSONObject.fromObject(wjwResult);
        wjwObject = JSONObject.fromObject(wjwObject.get("url"));
        wjwArray = JSONArray.fromObject(wjwObject.get("wkb_cny"));
        wkyVO.setWjw(wjwArray.get(1).toString());
        String wkyAboutInfo = HttpUtils.sendQueryWkbAboutInfo();
        if(StringUtils.isNotBlank(wkyAboutInfo)){
            wkyObject = JSONObject.fromObject(wkyAboutInfo);
            wkyObject = wkyObject.getJSONObject("data");
            wkyVO.setWkbNum(wkyObject.get("wkb_num").toString());
            wkyVO.setBlockNum(wkyObject.get("block_num").toString());
            wkyVO.setAverageOnlinetime(wkyObject.get("average_onlinetime").toString());
            wkyVO.setAverageBandwidth(wkyObject.get("average_bandwidth").toString());
            wkyVO.setAverageDisk(wkyObject.get("average_disk").toString());
        }

        String result = JsonUtils.objToJson(wkyVO);



        redisTemplate.boundValueOps(Constants.LINK_INFO).set(result);
        return result;
    }

    @Override
    public String updateHtmlDate() {
        Document doc = null;
        try {
            doc = Jsoup.connect(LinkUrl.dylsInfoUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element main = doc.getElementById("table-container");

        redisTemplate.boundValueOps(Constants.HTML_INFO).set(main.toString());
        return main.toString();
    }

    @Override
    public String getLinkInfo() {
        String value = redisTemplate.boundValueOps(Constants.LINK_INFO).get();
        if (StringUtils.isBlank(value)) {
            if (redisTemplate.boundValueOps(Constants.LINK_INFO).setIfAbsent(Constants.REDIS_USED)) {
                return value;
            }
            return updateHttpInfo();
        } else {
            return value;
        }
    }

}
