package com.cn.yc.component;

import com.cn.yc.bean.NewsDO;
import com.cn.yc.bean.NewsVO;
import com.cn.yc.utils.Constants;
import com.cn.yc.utils.JsonUtils;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/12/22.
 */
@Component
public class BkbCompoent {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static BkbCompoent bkbCompoent;

    @PostConstruct
    public void init(){
        bkbCompoent = this;
        bkbCompoent.redisTemplate = this.redisTemplate;
    }

    public static String getBkbString(){
        return bkbCompoent.redisTemplate.boundValueOps(Constants.LINK_INFO).get();
    }

    public static void setBkbNewsString(String news){
        bkbCompoent.redisTemplate.boundListOps(Constants.LINK_NEWS_INFO).leftPush(news);
    }

    public static NewsVO getBkbNews(){
        NewsVO newsVO = null;
        String result = bkbCompoent.redisTemplate.boundListOps(Constants.LINK_NEWS_INFO).index(0);
        if(result!=null){
            newsVO = JsonUtils.jsonToObj(result,NewsVO.class);
        }
        return newsVO;
    }

    public static List<NewsVO> getBkbNewsList(int num){
        String result = null;
        NewsVO newsVO = null;
        List<NewsVO> newsDOList = new ArrayList<>();
        for(int index =0;index<num;index++){
            result = bkbCompoent.redisTemplate.boundListOps(Constants.LINK_NEWS_INFO).index(index);
            newsVO = JsonUtils.jsonToObj(result,NewsVO.class);
            newsDOList.add(newsVO);
        }
        return newsDOList;
    }
}
