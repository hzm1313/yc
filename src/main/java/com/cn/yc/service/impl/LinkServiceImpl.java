package com.cn.yc.service.impl;

import com.cn.yc.bean.*;
import com.cn.yc.service.LinkService;
import com.cn.yc.spider.SpiderBaseModel;
import com.cn.yc.spider.SpiderLinkTokenInfoModel;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.JSONStrReaderUtils;
import com.cn.yc.utils.LinkUrl;
import com.cn.yc.web.ws.WechatConnector;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * Created by hasee on 2017/12/14.
 */
@Service
public class LinkServiceImpl implements LinkService {
    protected Logger logger = LoggerFactory.getLogger(WechatConnector.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String getLinkInfo() {
        SpiderLinkTokenInfoModel spiderLinkTokenInfoModel = new SpiderBaseModel();
        LinkTokenSpiderInfo playWkc = null, uyl = null, wjw = null;
        List<LinkTokenSpiderInfo> infoList = new ArrayList<>();
        String playWkcStr = null, uylStr = null, wjwStr = null;


        try {
            playWkcStr = redisTemplate.boundValueOps(Constants.PLAYWKC_TRADE_INFO_KEY).get();
            playWkc = JSONStrReaderUtils.jsonToObj(playWkcStr, PlayWkcDO.class);
            infoList.add(playWkc);
        } catch (Exception e) {
            logger.error("spiderPlayWkc error {}", e.getMessage());
        }
        try {
            uylStr = redisTemplate.boundValueOps(Constants.UYL_TRADE_INFO_KEY).get();
            uyl = JSONStrReaderUtils.jsonToObj(uylStr, UylDO.class);
            infoList.add(uyl);
        } catch (Exception e) {
            logger.error("spiderUyl error {}", e.getMessage());
        }
        try {
            wjwStr = redisTemplate.boundValueOps(Constants.WJW_TRADE_INFO_KEY).get();
            wjw = JSONStrReaderUtils.jsonToObj(wjwStr, WjwDO.class);
            infoList.add(wjw);
        } catch (Exception e) {
            logger.error("spiderWjw error {}", e.getMessage());
        }
        WkyVO wkyVO = null;
        String wkyVOStr = redisTemplate.boundValueOps(Constants.LINK_INFO).get();
        wkyVO = JSONStrReaderUtils.jsonToObj(wkyVOStr, WkyVO.class);
        if (wkyVO == null) {
            wkyVO = new WkyVO();
        }
        wkyVO.setWjwSell(wjw.getSellPrice().toString());
        wkyVO.setWjwBuy(wjw.getBuyPrice().toString());
        wkyVO.setPlayWkcSell(playWkc.getSellPrice().toString());
        wkyVO.setPlayWkcBuy(playWkc.getBuyPrice().toString());
        wkyVO.setUylSell(uyl.getSellPrice().toString());
        wkyVO.setUylBuy(uyl.getBuyPrice().toString());
        //计算价格
        if (infoList != null && infoList.size() == 3) {
            for (int i = 0; i < infoList.size() - 1; i++) {
                for (int j = 0; j < infoList.size() - i - 1; j++) {//比较两个整数
                    if (infoList.get(j).getSellPrice().compareTo(infoList.get(j + 1).getSellPrice()) > 0) {
                        LinkTokenSpiderInfo temp = infoList.get(j);
                        infoList.set(j, infoList.get(j + 1));
                        infoList.set(j + 1, temp);
                    }
                }
            }
            wkyVO.setPlatformTrade(infoList.get(2).getSpiderPlatform() + "/" + infoList.get(0).getSpiderPlatform());
            wkyVO.setPriceMarginPer(infoList.get(2).getSellPrice().divide(infoList.get(0).getSellPrice(), 3, ROUND_HALF_DOWN).
                    multiply(new BigDecimal(10)).doubleValue());
        }
        return JSONStrReaderUtils.objToJson(wkyVO);
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
    public String getHtmlTable() {
        String value = redisTemplate.boundValueOps(Constants.HTML_INFO).get();
        if (StringUtils.isBlank(value)) {
            if (redisTemplate.boundValueOps(Constants.HTML_INFO).setIfAbsent(Constants.REDIS_USED)) {
                return value;
            }
            return updateHtmlDate();
        } else {
            return value;
        }
    }
}
