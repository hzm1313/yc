package com.cn.yc.bean;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/3/2.
 */
public class PolicyResult {
    private boolean isExcecute;
    private BigDecimal tradeNum;
    private BigDecimal tradeProfit;
    private BigDecimal tradeProfitRate;

    public boolean isExcecute() {
        return isExcecute;
    }

    public void setExcecute(boolean excecute) {
        isExcecute = excecute;
    }

    public BigDecimal getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(BigDecimal tradeNum) {
        this.tradeNum = tradeNum;
    }

    public BigDecimal getTradeProfit() {
        return tradeProfit;
    }

    public void setTradeProfit(BigDecimal tradeProfit) {
        this.tradeProfit = tradeProfit;
    }

    public BigDecimal getTradeProfitRate() {
        return tradeProfitRate;
    }

    public void setTradeProfitRate(BigDecimal tradeProfitRate) {
        this.tradeProfitRate = tradeProfitRate;
    }
}
