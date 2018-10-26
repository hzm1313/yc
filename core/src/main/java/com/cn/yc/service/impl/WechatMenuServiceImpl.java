package com.cn.yc.service.impl;

import com.cn.yc.pojo.Button;
import com.cn.yc.pojo.ComplexButton;
import com.cn.yc.pojo.Menu;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.service.WechatMenuService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Created by DT167 on 2017/6/2.
 */
@Service
public class WechatMenuServiceImpl implements WechatMenuService {

    private static Logger logger = LoggerFactory.getLogger(WechatMenuServiceImpl.class);

    @Override
    public void create_menu()  {
        try{
            ComplexButton complexButton1 = new ComplexButton();
            Menu menu = new Menu();
            menu.setButton(new ComplexButton[]{complexButton1});

            JSONObject jsonObject = JSONObject.fromObject(menu);
            String body = jsonObject.toString();
            System.out.println(body);
            String accessToekn = HttpUtils.getAccessToken(Constants.appId, Constants.secret);
            PostMethod method = new PostMethod(Constants.wxCreateMenu+"?access_token="+accessToekn);
            method.setRequestEntity(new StringRequestEntity(body, "application/json", "UTF-8"));
            org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.OK.value()) {
                System.out.println(method.getResponseBodyAsString());
            }
            System.out.println(statusCode);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }
}
