package com.cn.yc.web.pray.service;

import com.cn.yc.bean.Pray;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DT167 on 2017/6/19.
 */
public interface PrayService {
    public String toPray(Pray pray);

    public List<Pray> getPrayList(Pray pray);
}
