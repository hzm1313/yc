package com.cn.yc.service.impl;

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
    public void getTradeInfo() {
    }
}
