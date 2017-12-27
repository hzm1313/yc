package com.cn.yc.service.impl;

import com.cn.yc.bean.NewsDO;
import com.cn.yc.bean.QqNewsDO;
import com.cn.yc.component.BkbCompoent;
import com.cn.yc.news.NewsFactory;
import com.cn.yc.news.QqNewsFactory;
import com.cn.yc.service.LinkSpiderService;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import com.cn.yc.utils.JsonUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/12/24.
 */
@Service
public class LinkSpiderServiceImpl implements LinkSpiderService {

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
            List<QqNewsDO> qqNewsDOList = newsFactory.getNewsList(html,QqNewsDO.class);
            int i=1;
            for(QqNewsDO qqNewsDO:qqNewsDOList){
                String json = JsonUtils.objToJson(qqNewsDO);
                BkbCompoent.setBkbNewsString(json);
            }
        }
    }

    @Override
    public NewsDO getNews() {
        return BkbCompoent.getBkbNews();
    }

    @Override
    public List<NewsDO> getNewsList() {
        return BkbCompoent.getBkbNewsList(10);
    }
}
