package com.cn.yc.bean;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/3/2.
 */
public class TradePlatform {
    private String tradeName;
    //交易手续费
    private BigDecimal tradeBrokerage;
    //转账手续费
    private BigDecimal transferBrokerage;

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public BigDecimal getTradeBrokerage() {
        return tradeBrokerage;
    }

    public void setTradeBrokerage(BigDecimal tradeBrokerage) {
        this.tradeBrokerage = tradeBrokerage;
    }

    public BigDecimal getTransferBrokerage() {
        return transferBrokerage;
    }

    public void setTransferBrokerage(BigDecimal transferBrokerage) {
        this.transferBrokerage = transferBrokerage;
    }
}
