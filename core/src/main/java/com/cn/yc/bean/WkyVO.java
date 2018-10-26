package com.cn.yc.bean;

/**
 * Created by hasee on 2017/12/17.
 */
public class WkyVO {
    /*    private String playWkc;
        private String uyl;
        private String wjw;*/
    private String playWkcSell;
    private String uylSell;
    private String wjwSell;
    private String playWkcBuy;
    private String uylBuy;
    private String wjwBuy;
    //挖矿总量
    private String wkbNum;
    //挖矿高度
    private String blockNum;
    //人均在线时间 小时
    private String averageOnlinetime;
    //人均上行带宽 单位Mbps
    private String averageBandwidth;
    //人均贡献存储
    private String averageDisk;

    private String platformTrade;

    private Double priceMarginPer;

    public String getPlatformTrade() {
        return platformTrade;
    }

    public void setPlatformTrade(String platformTrade) {
        this.platformTrade = platformTrade;
    }

    public Double getPriceMarginPer() {
        return priceMarginPer;
    }

    public void setPriceMarginPer(Double priceMarginPer) {
        this.priceMarginPer = priceMarginPer;
    }

    public String getWkbNum() {
        return wkbNum;
    }

    public void setWkbNum(String wkbNum) {
        this.wkbNum = wkbNum;
    }

    public String getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(String blockNum) {
        this.blockNum = blockNum;
    }

    public String getAverageOnlinetime() {
        return averageOnlinetime;
    }

    public void setAverageOnlinetime(String averageOnlinetime) {
        this.averageOnlinetime = averageOnlinetime;
    }

    public String getAverageBandwidth() {
        return averageBandwidth;
    }

    public void setAverageBandwidth(String averageBandwidth) {
        this.averageBandwidth = averageBandwidth;
    }

    public String getAverageDisk() {
        return averageDisk;
    }

    public void setAverageDisk(String averageDisk) {
        this.averageDisk = averageDisk;
    }

    public String getPlayWkcSell() {
        return playWkcSell;
    }

    public void setPlayWkcSell(String playWkcSell) {
        this.playWkcSell = playWkcSell;
    }

    public String getUylSell() {
        return uylSell;
    }

    public void setUylSell(String uylSell) {
        this.uylSell = uylSell;
    }

    public String getWjwSell() {
        return wjwSell;
    }

    public void setWjwSell(String wjwSell) {
        this.wjwSell = wjwSell;
    }

    public String getPlayWkcBuy() {
        return playWkcBuy;
    }

    public void setPlayWkcBuy(String playWkcBuy) {
        this.playWkcBuy = playWkcBuy;
    }

    public String getUylBuy() {
        return uylBuy;
    }

    public void setUylBuy(String uylBuy) {
        this.uylBuy = uylBuy;
    }

    public String getWjwBuy() {
        return wjwBuy;
    }

    public void setWjwBuy(String wjwBuy) {
        this.wjwBuy = wjwBuy;
    }
}
