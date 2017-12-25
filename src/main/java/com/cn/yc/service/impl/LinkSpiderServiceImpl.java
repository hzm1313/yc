package com.cn.yc.service.impl;

import com.cn.yc.service.LinkSpiderService;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

/**
 * Created by hasee on 2017/12/24.
 */
public class LinkSpiderServiceImpl implements LinkSpiderService {
    @Override
    public void spiderNews() {
        String html=null;
        for(String baiduKey: Constants.LINK_KEY_ARRAY){
            html = HttpUtils.getBaidu(baiduKey);
            Document doc = Jsoup.parse(html);
        }
    }

    @Override
    public String getNews() {
        return null;
    }
}
