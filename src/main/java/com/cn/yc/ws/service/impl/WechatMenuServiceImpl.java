package com.cn.yc.ws.service.impl;

import com.cn.yc.pojo.Button;
import com.cn.yc.pojo.ComplexButton;
import com.cn.yc.pojo.Menu;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.ws.service.WechatMenuService;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Created by DT167 on 2017/6/2.
 */
public class WechatMenuServiceImpl implements WechatMenuService {

    private static Logger logger = LoggerFactory.getLogger(WechatMenuServiceImpl.class);

    @Override
    public void create_menu()  {
        try{
            ComplexButton complexButton1 = new ComplexButton();
            complexButton1.setName("祈福");
            Button button1 =new Button();
            button1.setName("观音菩萨祈福");
            button1.setKey("d1");
            button1.setType("view");
            button1.setUrl("http://www.baidu.com");
            Button button2 =new Button();
            button2.setName("文殊菩萨祈福");
            button2.setKey("d2");
            button2.setType("view");
            button2.setUrl("http://www.baidu.com");
            Button button3 =new Button();
            button3.setName("佛祖祈福");
            button3.setKey("d3");
            button3.setType("view");
            button3.setUrl("http://www.baidu.com");
            complexButton1.setSub_button(new Button[]{button1, button2, button3});

            ComplexButton complexButton2 = new ComplexButton();
            complexButton2.setName("静心");
            button1 =new Button();
            button1.setName("木鱼");
            button1.setKey("m1");
            button1.setType("view");
            button1.setUrl("http://www.baidu.com");
            complexButton2.setSub_button(new Button[]{button1});

            ComplexButton complexButton3 = new ComplexButton();
            complexButton3.setName("苦海");
            button1 =new Button();
            button1.setName("回头是岸");
            button1.setKey("c1");
            button1.setType("click");
            complexButton3.setSub_button(new Button[]{button1});


            Menu menu = new Menu();
            menu.setButton(new ComplexButton[]{complexButton1, complexButton2, complexButton3});

            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(menu);
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

    public static void main(String[] args){
        WechatMenuServiceImpl tt =new WechatMenuServiceImpl();
        tt.create_menu();
    }
}
