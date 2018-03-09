package com.cn.yc.bean;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/1/5.
 */
public class TradeInfo {
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private BigDecimal buyNum;
    private BigDecimal sellNum;
    private TradePlatform buyPlatform;
    private TradePlatform sellPlatform;
    private Double profitMargin;

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(BigDecimal buyNum) {
        this.buyNum = buyNum;
    }

    public BigDecimal getSellNum() {
        return sellNum;
    }

    public void setSellNum(BigDecimal sellNum) {
        this.sellNum = sellNum;
    }

    public TradePlatform getBuyPlatform() {
        return buyPlatform;
    }

    public void setBuyPlatform(TradePlatform buyPlatform) {
        this.buyPlatform = buyPlatform;
    }

    public TradePlatform getSellPlatform() {
        return sellPlatform;
    }

    public void setSellPlatform(TradePlatform sellPlatform) {
        this.sellPlatform = sellPlatform;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }
}
