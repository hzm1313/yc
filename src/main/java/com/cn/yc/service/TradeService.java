package com.cn.yc.service;

import com.cn.yc.bean.TradeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DT167 on 2018/1/5.
 */
@Service
public interface TradeService {
    public List<TradeVO> getTradeInfo();
}
