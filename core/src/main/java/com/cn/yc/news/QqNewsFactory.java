package com.cn.yc.news;

import com.cn.yc.bean.NewsDO;
import com.cn.yc.bean.QqNewsDO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hasee on 2017/12/26.
 */
public class QqNewsFactory extends NewsFactory {

    Pattern pattern = Pattern.compile("\\d\\d\\d\\d年\\d\\d\\月\\d\\d日 \\d\\d:\\d\\d");

    @Override
    public <T extends NewsDO> T getNews(String news, Class<T> c) {
        return null;
    }

    @Override
    public <T extends NewsDO> List<T> getNewsList(String news, Class<T> c) {
        List<QqNewsDO> qqNewsDOList = new ArrayList<>();
        QqNewsDO qqNewsDO;
        Elements elementsTmp = null;
        Document doc = Jsoup.parse(news);
        Element element = doc.getElementById("content_left");
        Elements elements = element.getElementsByClass("result");
        for (Element elementTmp : elements) {
            qqNewsDO = new QqNewsDO();
            Matcher m = pattern.matcher(elementTmp.html());
            boolean result = m.find();
            if (result == true) {
                qqNewsDO.setCreatedTime(m.group());
            }
            elementsTmp = elementTmp.getElementsByClass("c-title");
            if (elementsTmp == null) {
                return null;
            }
            elementTmp = elementsTmp.get(0);
            elementsTmp = elementTmp.getElementsByTag("a");
            if (elements == null) {
                return null;
            }
            elementTmp = elementsTmp.get(0);
            qqNewsDO.setTitle(elementTmp.text());
            qqNewsDO.setUrl(elementTmp.attr("href"));
            qqNewsDOList.add(qqNewsDO);

        }
        return (List<T>) qqNewsDOList;
    }

}
