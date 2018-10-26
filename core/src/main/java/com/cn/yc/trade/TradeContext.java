package com.cn.yc.trade;

import com.cn.yc.bean.PolicyResult;
import com.cn.yc.bean.TradeInfo;
import com.cn.yc.trade.strategy.TradeStrategy;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/3/2.
 */
public class TradeContext {
    private TradeStrategy tradeStrategy;

    public TradeContext(TradeStrategy tradeStrategy){
        this.tradeStrategy = tradeStrategy;
    }

    public PolicyResult tradeStrategyCompute(TradeInfo tradeInfo, BigDecimal useBigDecimal){
        return tradeStrategy.tradeCompute(tradeInfo,useBigDecimal);
    }
}
