package com.cn.yc.bean.HuoBi;


import java.math.BigDecimal;

/**
 * Created by hasee on 2018/4/15.
 */
public class ReseauStratge {
    private BigDecimal tradePrice;
    private BigDecimal tradeNum;
    private BigDecimal holdNum;
    private Boolean isCanBuy;
    private BigDecimal holdAmount;
    private BigDecimal tradeAmount;

    public void updatePercent() {
        if(tradePrice!=null){
            tradePrice.setScale(7,BigDecimal.ROUND_DOWN);
        }
        if(tradeNum!=null){
            tradeNum.setScale(7,BigDecimal.ROUND_DOWN);
        }
        if(holdAmount!=null){
            holdAmount.setScale(7,BigDecimal.ROUND_DOWN);
        }
        if(tradeAmount!=null){
            tradeAmount.setScale(7,BigDecimal.ROUND_DOWN);
        }
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public ReseauStratge(BigDecimal holdAmount) {
        this.tradePrice = new BigDecimal("0");
        this.tradeNum = new BigDecimal("0");
        this.holdNum = new BigDecimal("0");
        this.holdAmount = holdAmount;
        this.isCanBuy = true;
        this.tradeAmount = new BigDecimal("0");
    }

    public BigDecimal getHoldAmount() {
        return holdAmount;
    }

    public void setHoldAmount(BigDecimal holdAmount) {
        this.holdAmount = holdAmount;
    }

    public BigDecimal getHoldNum() {
        return holdNum;
    }

    public void setHoldNum(BigDecimal holdNum) {
        this.holdNum = holdNum;
    }

    public Boolean getCanBuy() {
        return isCanBuy;
    }

    public void setCanBuy(Boolean canBuy) {
        isCanBuy = canBuy;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(BigDecimal tradeNum) {
        this.tradeNum = tradeNum;
    }
}
