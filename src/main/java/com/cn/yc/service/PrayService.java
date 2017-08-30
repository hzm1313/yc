package com.cn.yc.service;

import com.cn.yc.pojo.PrayDO;

import java.util.List;

/**
 * Created by DT167 on 2017/6/19.
 */
public interface PrayService {
    public String toPray(PrayDO prayDO);

    public List<PrayDO> getPrayList(PrayDO prayDO);
}
