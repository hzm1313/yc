package com.cn.yc.news;

import com.cn.yc.bean.NewsDO;

import java.util.List;

/**
 * Created by hasee on 2017/12/26.
 */
public abstract class NewsFactory {
    public abstract <T extends NewsDO> T getNews(String news, Class<T> c);

    public abstract <T extends NewsDO> List<T> getNewsList(String news, Class<T> c);
}
