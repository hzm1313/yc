package com.cn.yc.bean;

import java.math.BigDecimal;

/**
 * Created by DT167 on 2018/1/15.
 */
public class LinkTokenSpiderInfo {
    private BigDecimal sellPrice;
    private BigDecimal buyPrice;
    private Double sellNum;
    private Double buyNum;
    private String spiderPlatform;

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellNum() {
        return sellNum;
    }

    public void setSellNum(Double sellNum) {
        this.sellNum = sellNum;
    }

    public Double getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Double buyNum) {
        this.buyNum = buyNum;
    }

    public String getSpiderPlatform() {
        return spiderPlatform;
    }

    public void setSpiderPlatform(String spiderPlatform) {
        this.spiderPlatform = spiderPlatform;
    }
}
