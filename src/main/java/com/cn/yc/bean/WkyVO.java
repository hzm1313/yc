package com.cn.yc.bean;

/**
 * Created by hasee on 2017/12/17.
 */
public class WkyVO {
    private String cex;
    private String uyl;
    private String wjw;
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

    public String getCex() {
        return cex;
    }

    public void setCex(String cex) {
        this.cex = cex;
    }

    public String getUyl() {
        return uyl;
    }

    public void setUyl(String uyl) {
        this.uyl = uyl;
    }

    public String getWjw() {
        return wjw;
    }

    public void setWjw(String wjw) {
        this.wjw = wjw;
    }
}
