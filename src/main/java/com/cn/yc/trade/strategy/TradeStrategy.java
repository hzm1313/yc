package com.cn.yc.trade.strategy;

import com.cn.yc.bean.PolicyResult;
import com.cn.yc.bean.TradeInfo;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/2/28.
 */
public interface TradeStrategy {
    public PolicyResult tradeCompute(TradeInfo tradeInfo, BigDecimal useNum);
}
