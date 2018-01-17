package com.cn.yc.service.impl;

import com.cn.yc.bean.*;
import com.cn.yc.component.BkbCompoent;
import com.cn.yc.news.NewsFactory;
import com.cn.yc.news.QqNewsFactory;
import com.cn.yc.service.LinkService;
import com.cn.yc.service.LinkSpiderService;
import com.cn.yc.spider.SpiderBaseModel;
import com.cn.yc.spider.SpiderLinkTokenInfoModel;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.JSONStrReaderUtils;
import com.cn.yc.web.ws.WechatConnector;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/12/24.
 */
@Service
public class LinkSpiderServiceImpl implements LinkSpiderService {
    protected Logger logger = LoggerFactory.getLogger(LinkService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void updateLinkInfo() {
        WkyVO wkyVO = new WkyVO();
        JSONObject wkyObject = null;
        String wkyAboutInfo = HttpUtils.sendQueryWkbAboutInfo();
        if (StringUtils.isNotBlank(wkyAboutInfo)) {
            wkyObject = JSONObject.fromObject(wkyAboutInfo);
            wkyObject = wkyObject.getJSONObject("data");
            wkyVO.setWkbNum(wkyObject.get("wkb_num").toString());
            wkyVO.setBlockNum(wkyObject.get("block_num").toString());
            wkyVO.setAverageOnlinetime(wkyObject.get("average_onlinetime").toString());
            wkyVO.setAverageBandwidth(wkyObject.get("average_bandwidth").toString());
            wkyVO.setAverageDisk(wkyObject.get("average_disk").toString());
        }
        String result = JSONStrReaderUtils.objToJson(wkyVO);
        redisTemplate.boundValueOps(Constants.LINK_INFO).set(result);
    }

    @Override
    public String getLinkInfo() {
        return redisTemplate.boundValueOps(Constants.LINK_INFO).get();
    }

    @Override
    public void spiderTradeInfo() {
        SpiderLinkTokenInfoModel spiderLinkTokenInfoModel = new SpiderBaseModel();
        LinkTokenSpiderInfo playWkc = null, Uyl = null, Wjw = null;
        try {
            playWkc = spiderLinkTokenInfoModel.spiderPlayWkc();
        } catch (Exception e) {
            logger.error("spiderPlayWkc error {}", e.getMessage());
        }
        try {
            Uyl = spiderLinkTokenInfoModel.spiderUyl();
        } catch (Exception e) {
            logger.error("spiderUyl error {}", e.getMessage());
        }
        try {
            Wjw = spiderLinkTokenInfoModel.spiderWjw();
        } catch (Exception e) {
            logger.error("spiderWjw error {}", e.getMessage());
        }
        if (playWkc != null) {
            redisTemplate.boundValueOps(Constants.PLAYWKC_TRADE_INFO_KEY).set(JSONStrReaderUtils.objToJson(playWkc));
        }
        if (Uyl != null) {
            redisTemplate.boundValueOps(Constants.UYL_TRADE_INFO_KEY).set(JSONStrReaderUtils.objToJson(Uyl));
        }
        if (Wjw != null) {
            redisTemplate.boundValueOps(Constants.WJW_TRADE_INFO_KEY).set(JSONStrReaderUtils.objToJson(Wjw));
        }
    }

    @Override
    public List<LinkTokenSpiderInfo> getSpiderTradeInfo() {
        String tradeInfo = redisTemplate.boundValueOps(Constants.PLAYWKC_TRADE_INFO_KEY).get();
        LinkTokenSpiderInfo linkTokenSpiderInfo = null;
        List<LinkTokenSpiderInfo> linkTokenSpiderInfoList = new ArrayList<>();
        if (StringUtils.isNotBlank(tradeInfo)) {
            linkTokenSpiderInfo = JSONStrReaderUtils.jsonToObj(tradeInfo, PlayWkcDO.class);
            linkTokenSpiderInfoList.add(linkTokenSpiderInfo);
        }
        tradeInfo = redisTemplate.boundValueOps(Constants.UYL_TRADE_INFO_KEY).get();
        if (StringUtils.isNotBlank(tradeInfo)) {
            linkTokenSpiderInfo = JSONStrReaderUtils.jsonToObj(tradeInfo, UylDO.class);
            linkTokenSpiderInfoList.add(linkTokenSpiderInfo);
        }
        tradeInfo = redisTemplate.boundValueOps(Constants.WJW_TRADE_INFO_KEY).get();
        if (StringUtils.isNotBlank(tradeInfo)) {
            linkTokenSpiderInfo = JSONStrReaderUtils.jsonToObj(tradeInfo, WjwDO.class);
            linkTokenSpiderInfoList.add(linkTokenSpiderInfo);
        }
        return linkTokenSpiderInfoList;
    }

    @Override
    public void spiderNews() {
        String html = null;
        List<NameValuePair> params = null;
        Elements elementsTmp = null;
        for (String baiduKey : Constants.LINK_KEY_ARRAY) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ct", "1"));
            params.add(new BasicNameValuePair("rn", "20"));
            params.add(new BasicNameValuePair("ie", "utf-8"));
            params.add(new BasicNameValuePair("tn", "news"));
            params.add(new BasicNameValuePair("bs", baiduKey));
            params.add(new BasicNameValuePair("word", baiduKey));
            html = HttpUtils.getBaiduNews(params);
            NewsFactory newsFactory = new QqNewsFactory();
            List<QqNewsDO> qqNewsDOList = newsFactory.getNewsList(html, QqNewsDO.class);
            int i = 1;
            for (QqNewsDO qqNewsDO : qqNewsDOList) {
                String url = qqNewsDO.getUrl();
                String title = qqNewsDO.getTitle();
                synchronized (this) {
                    if (redisTemplate.boundHashOps(Constants.NEWS_URL_HASH_KEY).get(url) != null
                            || redisTemplate.boundHashOps(Constants.NEWS_TITLE_HASH_KEY).get(title) != null) {
                        continue;
                    }
                    String json = JSONStrReaderUtils.objToJson(qqNewsDO);
                    BkbCompoent.setBkbNewsString(json);
                    redisTemplate.boundHashOps(Constants.NEWS_URL_HASH_KEY).put(url, url);
                    redisTemplate.boundHashOps(Constants.NEWS_TITLE_HASH_KEY).put(title, title);
                }
            }
        }
    }

    @Override
    public NewsVO getNews() {
        return BkbCompoent.getBkbNews();
    }

    @Override
    public List<NewsVO> getNewsList() {
        return BkbCompoent.getBkbNewsList(10);
    }
}
