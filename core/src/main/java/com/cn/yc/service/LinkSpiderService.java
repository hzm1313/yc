package com.cn.yc.service;

import com.cn.yc.bean.LinkTokenSpiderInfo;
import com.cn.yc.bean.NewsDO;
import com.cn.yc.bean.NewsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hasee on 2017/12/24.
 */
@Service
public interface LinkSpiderService {
    public void updateLinkInfo();
    public String getLinkInfo();
    public void spiderTradeInfo();
    public List<LinkTokenSpiderInfo> getSpiderTradeInfo();
    public void spiderNews();
    public NewsVO getNews();
    public List<NewsVO> getNewsList();
}
