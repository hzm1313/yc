package com.cn.yc.spider;

import com.cn.yc.bean.LinkTokenSpiderInfo;

import java.util.List;

/**
 * Created by DT167 on 2018/1/15.
 */
public abstract class SpiderLinkTokenInfoModel {
    public abstract LinkTokenSpiderInfo spiderWjw();

    public abstract LinkTokenSpiderInfo spiderPlayWkc();

    public abstract LinkTokenSpiderInfo spiderUyl();

    public abstract List<LinkTokenSpiderInfo> spiderInfoList();
}
