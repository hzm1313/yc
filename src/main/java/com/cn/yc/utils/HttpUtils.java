package com.cn.yc.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;

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
}
