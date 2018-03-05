package com.cn.yc.service.impl;

import com.cn.yc.bean.TradeVO;
import com.cn.yc.bean.WkyVO;
import com.cn.yc.service.TradeService;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.LinkUrl;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DT167 on 2018/1/5.
 */
@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<TradeVO> getTradeInfo() {
        String result = redisTemplate.boundValueOps(Constants.TRADE_INFO_LIST).get();
        List<TradeVO> tradeVOList = new ArrayList<>();
        if(StringUtils.isBlank(result)){
            //重写爬虫
            String url = LinkUrl.wjwInfoUrl;
            Map<String,String> headerMap = new HashMap<>();
            headerMap.put("Host", "www.chinawkb.com");
            headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            headerMap.put("Referer", "http://www.chinawkb.com/");
            headerMap.put("Origin", "http://www.chinawkb.com");
            headerMap.put("X-Requested-With", "XMLHttpRequest");
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("id", "u"));
            formParams.add(new BasicNameValuePair("t", "0.032256690523117" + (int) (Math.random() * 9) + (int) (Math.random() * 9)));
            result = HttpUtils.sendPostRequest(url,headerMap,formParams);
            JSONObject wjwObject = JSONObject.fromObject(result);
            if(wjwObject!=null){

            }
        }
        if(StringUtils.isBlank(result)){

        }
        return null;
    }
}
