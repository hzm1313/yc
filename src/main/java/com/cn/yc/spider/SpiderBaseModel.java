package com.cn.yc.spider;

import com.cn.yc.bean.LinkTokenSpiderInfo;

import java.util.List;

/**
 * Created by DT167 on 2018/1/15.
 */
public class SpiderBaseModel extends SpiderLinkTokenInfoModel {
    @Override
    public LinkTokenSpiderInfo spiderWjw() {
        return null;
    }

    @Override
    public LinkTokenSpiderInfo spiderPlayWkc() {
        return null;
    }

    @Override
    public LinkTokenSpiderInfo spiderUyl() {
        return null;
    }

    @Override
    public List<LinkTokenSpiderInfo> spiderInfoList() {
        return null;
    }
}
