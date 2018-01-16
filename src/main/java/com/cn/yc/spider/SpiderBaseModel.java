package com.cn.yc.spider;

import com.cn.yc.bean.LinkTokenSpiderInfo;
import com.cn.yc.bean.PlayWkcDO;
import com.cn.yc.bean.UylDO;
import com.cn.yc.bean.WjwDO;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.LinkUrl;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.ibatis.jdbc.Null;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DT167 on 2018/1/15.
 */
public class SpiderBaseModel extends SpiderLinkTokenInfoModel {
    private static Pattern  pattern = Pattern.compile("\\d*\\.?\\d*");

    @Override
    public LinkTokenSpiderInfo spiderWjw() {
        String result = null;
        LinkTokenSpiderInfo wjwDO = new WjwDO();
        JSONArray wjwArray = null;
        String url = LinkUrl.wjwTradeInfoUrl;
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "www.chinawkb.com");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        headerMap.put("Referer", "http://www.chinawkb.com/trade/index/market/wkb_cny/");
        headerMap.put("X-Requested-With", "XMLHttpRequest");
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("market", "wkb_cny"));
        formParams.add(new BasicNameValuePair("trade_moshi", "1"));
        formParams.add(new BasicNameValuePair("t", "0.032256690523117" + (int) (Math.random() * 9) + (int) (Math.random() * 9)));
        result = HttpUtils.sendPostRequest(url, headerMap, formParams);
        JSONObject wjwObject = JSONObject.fromObject(result);
        if(wjwObject!= null){
            wjwObject = JSONObject.fromObject(wjwObject.get("depth"));
            if(wjwObject==null){
                return wjwDO;
            }
            wjwArray = JSONArray.fromObject(wjwObject.get("buy"));
            if(wjwArray==null||wjwArray.size()<=0){
                return wjwDO;
            }
            wjwArray = JSONArray.fromObject(wjwArray.get(0));
            if(wjwArray.size()>=2){
                wjwDO.setBuyPrice(new BigDecimal(wjwArray.get(0).toString()));
                wjwDO.setBuyNum(Double.valueOf(wjwArray.get(1).toString()));
            }
            wjwArray = JSONArray.fromObject(wjwObject.get("sell"));
            if(wjwArray==null||wjwArray.size()<=0){
                return wjwDO;
            }
            wjwArray = JSONArray.fromObject(wjwArray.get(0));
            if(wjwArray.size()>=2){
                wjwDO.setSellPrice(new BigDecimal(wjwArray.get(0).toString()));
                wjwDO.setSellNum(Double.valueOf(wjwArray.get(1).toString()));
            }
        }
        wjwDO.setSpiderPlatform(Constants.wjw);
        return wjwDO;
    }

    @Override
    public LinkTokenSpiderInfo spiderPlayWkc() {
        String result = null;
        LinkTokenSpiderInfo playWkc = new PlayWkcDO();
        JSONArray wjwArray = null;
        //BUY
        String url = LinkUrl.playWkcBuyInfoUrl;
        result = HttpUtils.sendGetRequest(url);
        Document doc = Jsoup.parse(result);
        Elements elements = doc.getElementsByClass("counter-list");
        Element element = elements.get(0);
        elements = element.getElementsByClass("price");
        element = elements.get(0);
        String priceStr = element.text();
        Matcher m = pattern.matcher(priceStr);
        if (m.find( )) {
            playWkc.setBuyPrice(new BigDecimal(m.group().toString()));
        }
        //SELL
        url = LinkUrl.playWkcSellInfoUrl;
        result = HttpUtils.sendGetRequest(url);
        doc = Jsoup.parse(result);
        elements = doc.getElementsByClass("counter-list");
        element = elements.get(0);
        elements = element.getElementsByClass("price");
        element = elements.get(0);
        priceStr = element.text();
        m = pattern.matcher(priceStr);
        if (m.find( )) {
            playWkc.setSellPrice(new BigDecimal(m.group().toString()));
        }
        playWkc.setSpiderPlatform(Constants.playWkc);
        return playWkc;
    }

    @Override
    public LinkTokenSpiderInfo spiderUyl() {
        String result = null;
        LinkTokenSpiderInfo ulyDO = new UylDO();
        String url = LinkUrl.ulyTradeInfoUrl;
        url=url+"?t=0.742612568269316"+(int) (Math.random() * 9);
        result = HttpUtils.sendGetRequest(url);
        JSONObject jsonObject = JSONObject.fromObject(result);
        jsonObject = JSONObject.fromObject(jsonObject.get("depth"));
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("b"));
        if(jsonArray==null||jsonArray.size()<=0){
            return ulyDO;
        }
        jsonArray = JSONArray.fromObject(jsonArray.get(0));
        if(jsonArray==null||jsonArray.size()<=0){
            return ulyDO;
        }
        ulyDO.setBuyPrice(new BigDecimal(jsonArray.get(0).toString()));
        ulyDO.setBuyNum(Double.valueOf(jsonArray.get(1).toString()));

        jsonArray = JSONArray.fromObject(jsonObject.get("s"));
        if(jsonArray==null||jsonArray.size()<=0){
            return ulyDO;
        }
        jsonArray = JSONArray.fromObject(jsonArray.get(0));
        if(jsonArray==null||jsonArray.size()<=0){
            return ulyDO;
        }
        ulyDO.setSellPrice(new BigDecimal(jsonArray.get(0).toString()));
        ulyDO.setSellNum(Double.valueOf(jsonArray.get(1).toString()));
        ulyDO.setSpiderPlatform(Constants.uyl);
        return ulyDO;
    }

    @Override
    public List<LinkTokenSpiderInfo> spiderInfoList() {
        return null;
    }
}
