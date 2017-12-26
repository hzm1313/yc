package com.cn.yc.service.impl;

import com.cn.yc.service.LinkSpiderService;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/12/24.
 */
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
            Document doc = Jsoup.parse(html);
                Element element = doc.getElementById("content_left");
            Elements elements = element.getElementsByClass("result");
            for (Element elementTmp : elements) {
                elementsTmp = elementTmp.getElementsByClass("c-title");
                if (elementsTmp == null) {
                    return;
                }
                elementTmp = elementsTmp.get(0);
                elementsTmp = elementTmp.getElementsByTag("a");
                if (elements == null) {
                    return;
                }
                elementTmp = elementsTmp.get(0);
                System.out.println(elementTmp.text());
                System.out.println(elementTmp.attr("href"));
            }
        }
    }

    @Override
    public String getNews() {
        return null;
    }
}
