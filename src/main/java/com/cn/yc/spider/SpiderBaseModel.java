package com.cn.yc.spider;

import com.cn.yc.bean.LinkTokenSpiderInfo;
import com.cn.yc.bean.WjwDO;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.LinkUrl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.ibatis.jdbc.Null;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DT167 on 2018/1/15.
 */
public class SpiderBaseModel extends SpiderLinkTokenInfoModel {
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
        return null;
    }

    @Override
    public LinkTokenSpiderInfo spiderUyl() {
        return null;
    }

    @Override
    public List<LinkTokenSpiderInfo> spiderInfoList() {
        return null;
    }
}
