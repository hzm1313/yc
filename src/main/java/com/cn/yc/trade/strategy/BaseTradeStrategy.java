package com.cn.yc.trade.strategy;

import com.cn.yc.bean.PolicyResult;
import com.cn.yc.bean.TradeInfo;
import com.cn.yc.utils.TRADECONSTANTS;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/3/2.
 */
public class BaseTradeStrategy implements TradeStrategy {

    @Override
    public PolicyResult tradeCompute(TradeInfo tradeInfo,BigDecimal useNum) {
        PolicyResult policyResult = new PolicyResult();
        policyResult.setExcecute(false);
        //计算差价
        BigDecimal diffPrice = tradeInfo.getSellPrice().subtract(tradeInfo.getBuyPrice());
        BigDecimal tradeNum = tradeInfo.getSellNum().min(tradeInfo.getBuyNum()).min(useNum);
        policyResult.setTradeNum(tradeNum);
        policyResult.setTradeProfit(diffPrice.multiply(tradeNum));
        policyResult.setTradeProfitRate(diffPrice.divide(diffPrice.multiply(tradeNum)));
        if(policyResult.getTradeProfitRate().doubleValue()- TRADECONSTANTS.TRADE_PROFIT_CHECK_RATE>0){
            policyResult.setExcecute(true);
        }
        return policyResult;
    }
}
