package com.cn.yc.bean;

/**
 * Created by hasee on 2018/4/7.
 */
public class HuobiDepthDetail {
    private String[][] bids;
    private String[][] asks;
    private String ts;
    private String version;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[][] getBids() {
        return bids;
    }

    public void setBids(String[][] bids) {
        this.bids = bids;
    }

    public String[][] getAsks() {
        return asks;
    }

    public void setAsks(String[][] asks) {
        this.asks = asks;
    }
}
