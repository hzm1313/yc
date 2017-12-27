package com.cn.yc.service;

import com.cn.yc.bean.NewsDO;
import com.cn.yc.bean.NewsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hasee on 2017/12/24.
 */
@Service
public interface LinkSpiderService {
    public void spiderNews();
    public NewsVO getNews();
    public List<NewsVO> getNewsList();
}
