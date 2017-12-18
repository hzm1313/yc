package com.cn.yc.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DT167 on 2017/6/2.
 */
public class HttpUtils {
    protected static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String getAccessToken(String appId,String secert){
        String accessToken = sendGetRequest(Constants.wxAccessTokenUrl+"&appid=" +
                Constants.appId + "&secret=" + Constants.secret);
        accessToken = JsonUtils.jsonToMapGetVal(accessToken, "access_token");
        return accessToken;
    }

    public static String sendGetRequest(String url) {
        HttpEntity httpEntity = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = new DefaultHttpClient().execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.OK.value()) {
                httpEntity = response.getEntity();
                if (httpEntity != null) {
                    return JsonUtils.read(httpEntity);
                }
            }
        } catch (IOException e) {
            logger.error("*** Error in send get request due to IOException [{}]", e.getMessage());
        }
        return null;
    }

    public static String sendGetCexRequest(String url) {
        HttpEntity httpEntity = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Host","www.cex.com");
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            HttpResponse response = new DefaultHttpClient().execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.OK.value()) {
                httpEntity = response.getEntity();
                if (httpEntity != null) {
                    return JsonUtils.read(httpEntity);
                }
            }
        } catch (IOException e) {
            logger.error("*** Error in send get request due to IOException [{}]", e.getMessage());
        }
        return null;
    }

    public static String sendWjwRequest(String url) {
        HttpEntity httpEntity = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Host","www.chinawkb.com");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            httpPost.setHeader("Referer","http://www.chinawkb.com/");
            httpPost.setHeader("Origin","http://www.chinawkb.com");
            httpPost.setHeader("X-Requested-With","XMLHttpRequest");
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("id","u"));
            formParams.add(new BasicNameValuePair("t", "0.032256690523117"+(int)(Math.random()*9)+(int)(Math.random()*9)));
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse response = new DefaultHttpClient().execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.OK.value()) {
                httpEntity = response.getEntity();
                if (httpEntity != null) {
                    return JsonUtils.read(httpEntity);
                }
            }
        } catch (IOException e) {
            logger.error("*** Error in send get request due to IOException [{}]", e.getMessage());
        }
        return null;
    }

    public static String sendQueryWkbAboutInfo(){
        HttpEntity httpEntity = null;
        try {
            HttpPost httpPost = new HttpPost(LinkUrl.wkyAboutInfoUrl);
            httpPost.setHeader("Host","account.onethingpcs.com");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            httpPost.setHeader("Referer","http://red.xunlei.com/index.php?r=site/coin");
            httpPost.setHeader("Origin","http://red.xunlei.com");
           /* List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("id","u"));
            formParams.add(new BasicNameValuePair("t", "0.032256690523117"+(int)(Math.random()*9)+(int)(Math.random()*9)));
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(entity);*/
            HttpResponse response = new DefaultHttpClient().execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.OK.value()) {
                httpEntity = response.getEntity();
                if (httpEntity != null) {
                    return JsonUtils.read(httpEntity);
                }
            }
        } catch (IOException e) {
            logger.error("*** Error in send get request due to IOException [{}]", e.getMessage());
        }
        return null;
    }

}
