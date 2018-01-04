package com.cn.yc.bean;

/**
 * Created by hasee on 2018/1/1.
 */
public class TradeInfo {
    private String rateOfProfit;
    private String buyRate;
    private String sellRate;
    private String nowDate;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRateOfProfit() {
        return rateOfProfit;
    }

    public void setRateOfProfit(String rateOfProfit) {
        this.rateOfProfit = rateOfProfit;
    }

    public String getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(String buyRate) {
        this.buyRate = buyRate;
    }

    public String getSellRate() {
        return sellRate;
    }

    public void setSellRate(String sellRate) {
        this.sellRate = sellRate;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }
}
