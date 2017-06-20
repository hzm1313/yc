package com.cn.yc.web.pray.service.impl;

import com.cn.yc.bean.Pray;
import com.cn.yc.web.pray.service.PrayService;
import com.cn.yc.web.pray.mapper.PrayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DT167 on 2017/6/19.
 */
@Service
public class PrayServiceImpl implements PrayService{
    @Autowired
    private PrayMapper prayMapper;

    @Override
    public String toPray(Pray pray) {
        return null;
    }

    @Override
    public List<Pray> getPrayList(Pray pray) {
        return prayMapper.selectPray(null);
    }
}
