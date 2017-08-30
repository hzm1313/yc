package com.cn.yc.service.impl;

import com.cn.yc.pojo.PrayDO;
import com.cn.yc.service.PrayService;
import com.cn.yc.mapper.PrayMapper;
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
    public String toPray(PrayDO pray) {
        return null;
    }

    @Override
    public List<PrayDO> getPrayList(PrayDO pray) {
        return prayMapper.selectPray(null);
    }
}
