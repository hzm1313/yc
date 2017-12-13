package com.cn.yc.service;

import com.cn.yc.pojo.PrayDO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public abstract interface PrayService
{
    public abstract String toPray(PrayDO paramPrayDO);

    public abstract List<PrayDO> getPrayList(PrayDO paramPrayDO);
}
